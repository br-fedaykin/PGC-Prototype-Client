#!/usr/bin/env python
# -*- coding: utf-8 -*-

import javaobj
import io
import contextlib
import base64
import random
import time
import os

import ProfilerUtils as Util

from shutil import copyfile
from ProfilerUtils import TEMP_FOLDER, DEBUG, NUM_THREADS

DCPABE = '../lib/dcpabe-1.2.0-jar-with-dependencies.jar'


def gsetup():
    params = '-f {p}gpfile'.format(p=TEMP_FOLDER)
    Util.runJAVACommand(DCPABE, 'gsetup', params)


def inspect_java_object(data, measurer_func):
    all_results = []
    for row in data:
        partial_results = []
        with open(row['filename'], 'rb') as f:
            with contextlib.redirect_stderr(io.StringIO()):
                jobj = javaobj.loads(b''.join(f.readlines()))
                partial_results.extend(measurer_func(jobj, **row['kwargs']))
        all_results.extend(partial_results)
    return all_results


def public_key_size_measure(pks, name=None, run_id=None):
    result = []
    for atributo in pks:
        eg1g1ai_size = len(pks[atributo].eg1g1ai)
        g1yi_size = len(pks[atributo].g1yi)
        result.append([run_id, atributo, name, eg1g1ai_size, g1yi_size])
    return result


def asetup(run_id, num_authorities=1, num_attributes=1, measurer=None):
    worker_number = run_id % NUM_THREADS
    atributos = [Util.generate_random_string() for _ in range(num_attributes)]
    autoridades = {'{:02}.{:06}.{}.'.format(worker_number, x, Util.generate_random_string()): [] for x in
                   range(num_authorities)}
    for attr in atributos:
        autoridades[random.choice(list(autoridades.keys()))].append(attr)
    autoridades = {nome: autoridades[nome]
                   for nome in autoridades if len(autoridades[nome]) > 0}
    measure_data = []
    for name in autoridades:
        auth_number = name[:9]
        params = '-f {p}gpfile {auth} {p}{an}secKey {p}{an}pubKey {attr}'
        params = params.format(p=TEMP_FOLDER, an=auth_number,
                               auth=name,
                               attr=' '.join(autoridades[name]))
        Util.runJAVACommand(DCPABE, 'asetup', params)
        if measurer is not None:
            data = {'filename': TEMP_FOLDER + auth_number +
                    'pubKey', 'kwargs': {'run_id': run_id, 'name': name}}
            measure_data.append(data)
    if measurer is not None:
        return inspect_java_object(measure_data, measurer)
    else:
        return autoridades


def ciphertext_byte_size_measure(cipher, policy_size=None, policy=None, run_id=None):
    result = []
    len_c0 = len(cipher.c0)
    len_c1 = sum([len(x) for x in cipher.c1])
    len_c2 = sum([len(x) for x in cipher.c2])
    len_c3 = sum([len(x) for x in cipher.c3])
    len_c0_encoded = len(base64.b64encode(
        bytes([x & 0xff for x in cipher.c0])))
    len_c1_encoded = len(
        b','.join([base64.b64encode(bytes([x & 0xff for x in y])) for y in cipher.c1]))
    len_c2_encoded = len(
        b','.join([base64.b64encode(bytes([x & 0xff for x in y])) for y in cipher.c2]))
    len_c3_encoded = len(
        b','.join([base64.b64encode(bytes([x & 0xff for x in y])) for y in cipher.c3]))
    result.append(
        [run_id, policy_size, policy, len_c0, len_c1, len_c2, len_c3, len_c0_encoded, len_c1_encoded, len_c2_encoded,
         len_c3_encoded])
    return result


def encrypt(run_id, policy_size=1, measurer=None, operators=('and', 'or')):
    worker_number = run_id % NUM_THREADS
    autoridades = asetup(run_id, num_authorities=int(
        1 + policy_size / 3), num_attributes=policy_size)
    policy, names = Util.random_policy(policy_size, autoridades, operators)
    if DEBUG:
        print('run_id: {:07}, policy: "{}"'.format(run_id, policy))
    pks = ['{p}{an}pubKey'.format(p=TEMP_FOLDER, an=x[:9]) for x in names]
    params = '-f {p}gpfile {p}file{tn}.txt "{rp}" {p}encFile{tn} {pks}'.format(
        p=TEMP_FOLDER, tn=worker_number, rp=policy, pks=' '.join(pks))
    exit_code = Util.runJAVACommand(DCPABE, 'encrypt', params)
    if exit_code != 0:
        print('ATENÇÃO: o seguinte input causou um erro de execução em java: {}.'.format(params))
        errorfolder = TEMP_FOLDER + 'erro{}/'.format(time.time())
        os.mkdir(errorfolder)
        for pubKey in pks:
            copyfile(pubKey, errorfolder + pubKey)
        resource_file = 'file{}.txt'.format(worker_number)
        copyfile(TEMP_FOLDER + resource_file, errorfolder + resource_file)
        copyfile(TEMP_FOLDER + 'gpfile', errorfolder + 'gpfile')
        print('Arquivos de entrada copiados para a pasta {}'.format(errorfolder))
    elif measurer is not None:
        measure_data = [{
            'filename': '{p}encFile{tn}'.format(p=TEMP_FOLDER, tn=worker_number),
            'kwargs': {'policy_size': policy_size, 'policy': policy, 'run_id': run_id}
        }]
        return inspect_java_object(measure_data, measurer)


def experiment_ciphertext_size():
    print('Start experiment to measure Ciphertext size.')
    label = ['run_id', 'policy_size', 'policy', 'len_c0', 'len_c1', 'len_c2', 'len_c3', 'len_c0_encoded',
             'len_c1_encoded', 'len_c2_encoded', 'len_c3_encoded']
    ops_list = [['and', 'or'], ['and'], ['or']]
    ops_names = ['RandomOps', 'ANDOperator', 'OROperator']
    max_rodadas = 100
    gsetup()
    for i in range(len(ops_list)):
        min_rodadas = 0
        for j in range(min_rodadas, max_rodadas):
            command_kwargs = {'measurer': ciphertext_byte_size_measure,
                              'policy_size': j + 1, 'operators': ops_list[i]}
            Util.gather_data_from_command_multicore(100, encrypt, 'sizeOfCiphertext{}.csv'.format(ops_names[i]), label, rodada=j,
                                          max_rodadas=max_rodadas * 3, command_kwargs=command_kwargs)
    print('Finished experiment to measure Ciphertext size.')


def main():
    print('***EXPERIMENT***\n')
    random.seed('Honk Honk')
    Util.START = time.time()
    Util.DEBUG = True
    experiment_ciphertext_size()


if __name__ == "__main__":
    main()
