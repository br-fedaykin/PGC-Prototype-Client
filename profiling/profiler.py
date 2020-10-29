#!/usr/bin/env python
#-*- coding: utf-8 -*-

import csv
import shlex, subprocess
import random, string
import os
import io
import contextlib
from concurrent import futures
from concurrent.futures import ThreadPoolExecutor
from shutil import copyfile
import javaobj
import time
import math
import base64

DCPABE = '../lib/dcpabe-1.2.0-jar-with-dependencies.jar'
SMART_DCPABE = '../hyperdcpabe-client-0.1-SNAPSHOT-jar-with-dependencies.jar'
ALPHABET = string.ascii_letters + string.digits + '_' + ''.join([x + x.upper() for x in 'çãéúáõâêû'])
TEMP_FOLDER = '../profiling/temp/'
NUM_THREADS = 2
DEBUG = False
START = None

class TreeNode:
    def __init__(self):
        self.left = None
        self.right = None

    def isLeaf(self):
        return self.left is None and self.right is None

    def buildRandomBinaryTree(self, number_leafs):
        if (number_leafs == 1):
            return self
        leftSize = random.randint(1, number_leafs - 1)
        self.left = TreeNode().buildRandomBinaryTree(leftSize)
        self.right = TreeNode().buildRandomBinaryTree(number_leafs - leftSize)
        return self

    def parseTreeToABEPolicy(self, autoridades, operators):
        if self.isLeaf():
            nome = random.choice(list(autoridades.keys()))
            return random.choice(autoridades[nome]), [nome]
        pks = []
        result = random.choice(operators)
        for subTree in [self.left, self.right]:
            subPolicy, partial_pks = subTree.parseTreeToABEPolicy(autoridades, operators)
            pks.extend([x for x in partial_pks if x not in pks])
            result = result + ' ' + subPolicy
        return result, pks

def runJAVACommand(jar, command, params):
    args = 'java -jar {} {} {}'.format(jar, command, params)
    exitCode = None
    try:
        sanitizedArgs = shlex.split(args)
        exitCode = subprocess.call(sanitizedArgs, timeout = 30)
    except Exception as e:
        print(e)
    return exitCode

def gsetup():
    params = '-f {p}gpfile'.format(p=TEMP_FOLDER)
    runJAVACommand(DCPABE, 'gsetup', params)

def inspectJavaObject(data, measurer_func):
    all_results = []
    for row in data:
        partial_results = []
        with open(row['filename'], 'rb') as f:
            with contextlib.redirect_stderr(io.StringIO()):
                jobj = javaobj.loads(b''.join(f.readlines()))
                partial_results.extend(measurer_func(jobj, **row['kwargs']))
        all_results.extend(partial_results)
    return all_results

def inspectJSONObject(data, measurer_func):
    all_results = []
    for row in data:
        partial_results = []
        with open(row['filename'], 'rb') as f:
            with contextlib.redirect_stderr(io.StringIO()):
                jobj = javaobj.loads(b''.join(f.readlines()))
                partial_results.extend(measurer_func(jobj, **row['kwargs']))
        all_results.extend(partial_results)
    return all_results

def publicKeySizeMeasure(pks, name = None, atributo = None, run_id = None):
    result = []
    for atributo in pks:
        eg1g1ai_size = len(pks[atributo].eg1g1ai)
        g1yi_size = len(pks[atributo].g1yi)
        result.append([run_id, atributo, name, eg1g1ai_size, g1yi_size])
    return result

def asetup(run_id, num_authorities=1, num_attributes=1, measurer=None):
    worker_number = run_id % NUM_THREADS
    atributos = [generateRandomString() for x in range(num_attributes)]
    autoridades = {'{:02}.{:06}.{}.'.format(worker_number, x, generateRandomString()):[] for x in range(num_authorities)}
    for attr in atributos:
        autoridades[random.choice(list(autoridades.keys()))].append(attr)
    autoridades = {nome:autoridades[nome] for nome in autoridades if len(autoridades[nome]) > 0}
    measureData = []
    for name in autoridades:
        auth_number = name[:9]
        params = '-f {p}gpfile {auth} {p}{an}secKey {p}{an}pubKey {attr}'.format(p=TEMP_FOLDER,an=auth_number, auth=name, attr=' '.join(autoridades[name]))
        runJAVACommand(DCPABE, 'asetup', params)
        if measurer is not None:
            data = {}
            data['filename'] = TEMP_FOLDER + auth_number + 'pubKey'
            data['kwargs'] = {'run_id': run_id, 'name': name}
            measureData.append(data)
    if measurer is not None:
        return inspectJavaObject(measureData, measurer)
    else:
        return autoridades

def generateRandomString(minSize = 5, maxSize = 30):
    return ''.join(random.choices(ALPHABET, k=random.randint(minSize, maxSize)))

def ciphertextByteSizeMeasure(cipher, policySize = None, policy = None, run_id = None):
    result = []
    len_c0 = len(cipher.c0)
    len_c1 = sum([len(x) for x in cipher.c1])
    len_c2 = sum([len(x) for x in cipher.c2])
    len_c3 = sum([len(x) for x in cipher.c3])
    len_c0_encoded = len(base64.b64encode(bytes([x & 0xff for x in cipher.c0])))
    len_c1_encoded = len(b','.join([base64.b64encode(bytes([x & 0xff for x in y])) for y in cipher.c1]))
    len_c2_encoded = len(b','.join([base64.b64encode(bytes([x & 0xff for x in y])) for y in cipher.c2]))
    len_c3_encoded = len(b','.join([base64.b64encode(bytes([x & 0xff for x in y])) for y in cipher.c3]))
    result.append([run_id, policySize, policy, len_c0, len_c1, len_c2, len_c3, len_c0_encoded, len_c1_encoded, len_c2_encoded, len_c3_encoded])
    return result

def encrypt(run_id, policySize = 1, measurer = None, operators = ['and', 'or']):
    worker_number = run_id % NUM_THREADS
    autoridades = asetup(run_id, num_authorities=int(1 + policySize/3), num_attributes=policySize)
    policy, names = randomPolicy(policySize, autoridades, operators)
    if DEBUG:
        print('run_id: {:07}, policy: "{}"'.format(run_id, policy))
    pks = ['{p}{an}pubKey'.format(p=TEMP_FOLDER, an=x[:9]) for x in names]
    params = '-f {p}gpfile {p}file{tn}.txt "{rp}" {p}encFile{tn} {pks}'.format(p=TEMP_FOLDER, tn=worker_number, rp=policy, pks=' '.join(pks))
    exitCode = runJAVACommand(DCPABE, 'encrypt', params)
    if exitCode != 0:
        print('ATENÇÃO: o seguinte input causou um erro de execução em java: {}.'.format(params))
        errorfolder = TEMP_FOLDER + 'erro{}/'.format(time.time())
        for pubKey in pks:
            copyfile(pubKey, errorfolder +  pubKey)
        resourceFile = 'file{}.txt'.format(worker_number)
        copyfile(TEMP_FOLDER + resourceFile, errorfolder + resourceFile)
        copyfile(TEMP_FOLDER + 'gpfile', errorfolder + 'gpfile')
        print('Arquivos de entrada copiados para a pasta {}'.format(errorfolder))
    elif measurer is not None:
        measureData = [{
            'filename': '{p}encFile{tn}'.format(p=TEMP_FOLDER, tn=worker_number),
            'kwargs':{'policySize': policySize, 'policy':policy, 'run_id': run_id}
        }]
        return inspectJavaObject(measureData, measurer)

def randomPolicy(policySize, autoridades, operators):
    randomTree = TreeNode().buildRandomBinaryTree(policySize)
    return randomTree.parseTreeToABEPolicy(autoridades, operators)

def gatherDataFromDCPABECommand(n, command, csv_output_file, label, command_kwargs, rodada = 0, max_rodadas=1):
    with ThreadPoolExecutor(max_workers=NUM_THREADS) as executor:
        buffer_size = NUM_THREADS*5
        TET = []
        for i in range(int(n/buffer_size)):
            partial_start = time.time()
            jobs = []
            for j in range(buffer_size):
                run_id = n*rodada + i*buffer_size + j
                jobs.append(executor.submit(command, run_id, **command_kwargs))
            file_mode = 'w'
            if os.path.exists(csv_output_file):
                file_mode = 'a'
                label = None
            jobs = futures.wait(jobs)
            results = []
            for x in jobs.done:
                results.extend(x.result())
            with open(csv_output_file, file_mode, newline='', encoding='utf-8') as f:
                writer = csv.writer(f)
                if label is not None:
                    writer.writerow(label)
                writer.writerows(results)
            percentage = ((i+1)*buffer_size + n*rodada)/(n*max_rodadas)
            if int(10000*percentage) > 0:
                partial_time = printNumberAsTime(time.time() - partial_start)
                elapsed_time_ = time.time() - START
                elapsed_time = printNumberAsTime(elapsed_time_)
                TET = printNumberAsTime(elapsed_time_/percentage)
                print('{:.2%} pronto em {}. Tempo total: {}. TET: {}'.format(percentage, partial_time, elapsed_time, TET))
        if (max_rodadas != 1):
            print('subrotina {} terminada.'.format(rodada))
        else:
            print('Terminado.')

def printNumberAsTime(measure):
    h = int(measure/3600)
    m = int(measure/60 - h*60)
    s = measure - h*3600 - m*60
    time_str = '{:.02}s'.format(s)
    if (m != 0):
        time_str = '{}m'.format(m) + time_str
    if (h != 0):
        time_str = '{}h'.format(h) + time_str
    return time_str

def experimentCiphertextSize():
    print('Start experiment to measure Ciphertext size.')
    label = ['run_id', 'policySize', 'policy', 'len_c0', 'len_c1', 'len_c2', 'len_c3', 'len_c0_encoded', 'len_c1_encoded', 'len_c2_encoded', 'len_c3_encoded']
    ops_list = [['and', 'or'], ['and'], ['or']]
    ops_names = ['RandomOps', 'ANDOperator', 'OROperator']
    max_rodadas = 100
    gsetup()
    for i in range(len(ops_list)):
        for j in range(0, max_rodadas, 5):
            command_kwargs = {'measurer': ciphertextByteSizeMeasure, 'policySize': j+1, 'operators': ops_list[i]}
            gatherDataFromDCPABECommand(100, encrypt, 'sizeOfCiphertext{}.csv'.format(ops_names[i]), label, rodada=j, max_rodadas = max_rodadas*3, command_kwargs=command_kwargs)
    print('Finished experiment to measure Ciphertext size.')


def main():
    print('***EXPERIMENT***\n')
    random.seed('Honk Honk')
    global DEBUG, START
    START = time.time()
    # DEBUG = True
    experimentCiphertextSize()

if __name__ == "__main__":
    main()
