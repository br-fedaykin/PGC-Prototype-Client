#!/usr/bin/env python
# -*- coding: utf-8 -*-

import csv
import json
import os
import random
import time
import base64

import ProfilerUtils as Util
import bitcoinaddress as btc

from ProfilerUtils import TEMP_FOLDER, DEBUG, START, NUM_THREADS

SMART_DCPABE = '../lib/rinkeby/hyperdcpabe-client-0.1.1-SNAPSHOT-jar-with-dependencies.jar'
LOG_FOLDER = 'logs'

MAX_ATRIBUTOS = 100
ATTRIBUTES = ['atributo{:03}'.format(x) for x in range(MAX_ATRIBUTOS)]

JVM_DISABLE_WARNING = "--add-opens java.base/sun.security.provider=ALL-UNNAMED -jar"
TOKEN="http://127.0.0.1:7545"

class User:
    def __init__(self, name, wallet=None, private_key=None):
        self.name = name
        self.email = name.lower() + '@email.com'
        if wallet is None or private_key is None:
            new_wallet()
        else:
            self.wallet = wallet
            self.private_key = private_key
        self.gid = '{}-{}'.format(self.name, self.wallet)

    def new_wallet(self):
        newWallet = btc.Wallet()
        private_key = newWallet.key.hex
        wallet = newWallet.address.pubaddr1
        self.wallet = wallet
        self.private_key = private_key

    def data(self):
        return {'name': self.name, 'email': self.email, 'gid': self.gid, 'wallet': self.wallet, 'privkey': self.private_key}


ADMIN = User('admin', '0xFae373E0BFfaE794fA818D749D6da38D4f7cA986',
             'e4d8c81796894ea5bf202e3a3204948dddd62f4d709c278bf8096898957be241')
BOB = User('Bob', '0xF7908374b1a445cCf65F729887dbB695c918BEfc',
           'ab0439882857ffb5859c1a3a6bf40a6848daeaab6605c873c3e425de53c2c4ab')
ALICE = User('Alice', '0xb038476875480BCE0D0FCf0991B4BB108A3FCB47',
             '4237a475aa6579f2a0fc85d90cbcda1fad3db70391315a6c37b51de3a8cb503a')
CRM = User('CRM', '0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0',
           'e15b910f8c61580befebecff2d79abf38998035cbc317400a96c4736a424f6dc')


def start_system(rootAddress):
    params = '{name} {email} {privkey} -n {network}'.format(**ADMIN.data(), network=TOKEN)
    if rootAddress is not None:
        params = params + ' -r ' + rootAddress
    Util.runJAVACommand(SMART_DCPABE, 'init', params, timeout=3000, javaargs = JVM_DISABLE_WARNING)
    Util.runJAVACommand(SMART_DCPABE, 'create-user', '{name} {email} {privkey}'.format(**BOB.data()), javaargs = JVM_DISABLE_WARNING)
    Util.runJAVACommand(SMART_DCPABE, 'publish', 'user')
    Util.runJAVACommand(SMART_DCPABE, 'create-user', '{name} {email} {privkey}'.format(**ALICE.data()), javaargs = JVM_DISABLE_WARNING)
    Util.runJAVACommand(SMART_DCPABE, 'publish', 'user', javaargs = JVM_DISABLE_WARNING)
    print('system initialized')


def publishAttributes():
    Util.runJAVACommand(SMART_DCPABE, 'create-user', '{name} {email} {privkey}'.format(**CRM.data()), javaargs = JVM_DISABLE_WARNING)
    Util.runJAVACommand(SMART_DCPABE, 'create-certifier', javaargs = JVM_DISABLE_WARNING)
    Util.runJAVACommand(SMART_DCPABE, 'create-attributes', ' '.join(ATTRIBUTES), javaargs = JVM_DISABLE_WARNING)
    Util.runJAVACommand(SMART_DCPABE, 'publish', 'user certifier attributes', timeout=9000, javaargs = JVM_DISABLE_WARNING)


def getAttributes():
    Util.runJAVACommand(SMART_DCPABE, 'get-attributes', '{} {}'.format(CRM.gid, ' '.join(ATTRIBUTES)), timeout=3000, javaargs = JVM_DISABLE_WARNING)


def publish_encrypted_file(policy_size, operators = ['and', ' or']):
    filepath = r'data\client\Bob-0xf7908374b1a445ccf65f729887dbb695c918befc\data_file{}.txt'
    with open(filepath.format(policy_size), 'w') as f:
        f.write('File created for testing and profiling...')
    policy, _ = Util.random_policy(policy_size, {CRM.gid: ATTRIBUTES}, operators)
    params = 'data_file{number}.txt "{policy}" {authority}'.format(number=policy_size, policy=policy, authority=CRM.gid)
    Util.runJAVACommand(SMART_DCPABE, 'encrypt', params, javaargs = JVM_DISABLE_WARNING)
    exitCode = None
    exitCode = Util.runJAVACommand(SMART_DCPABE, 'send', 'data_file{}.txt --profile --finish-profile'.format(policy_size), timeout=3600, javaargs = JVM_DISABLE_WARNING)
    data = [policy_size, policy, exitCode]
    if exitCode == 1 or len(os.listdir(LOG_FOLDER)) == 0:
        data.extend([0, 0, 0, 0, 0, 0])
        return data
    files = [x for x in os.listdir(LOG_FOLDER) if x.startswith('execData-')]
    files.sort()
    logPath = None
    for log in files:
        with open('{}/{}'.format(LOG_FOLDER, log), 'r') as f:
            if 'addCiphertext' in f.read():
                logPath = '{}/{}'.format(LOG_FOLDER, log)
                break
    with open(logPath, 'r') as f:
        json_obj = json.load(f)
        profile = None
        for obj in json_obj['tasks'][0]['methodStack']:
            if obj['method'] == 'addCiphertext':
                profile = obj
                break
        else:
            raise Exception('métricas de Ciphertext não foram encontrados.')
        data.extend([profile['execTime'], profile['gasCost'], profile['gasPrice'], profile['etherCost'], profile['gasLimit'], json_obj['timestamp']])
    for log in os.listdir(LOG_FOLDER):
        os.remove('{}/{}'.format(LOG_FOLDER, log))
    return data


def gather_data(csv_output_file, label, rodada=0, max_rodadas=1):
    partial_start = time.time()
    file_mode = 'w'
    if os.path.exists(csv_output_file):
        file_mode = 'a'
        label = None
    with open(csv_output_file, file_mode, newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        if label is not None:
            writer.writerow(label)
        writer.writerow(publish_encrypted_file(rodada + 1))
    percentage = rodada / max_rodadas
    if int(1000 * percentage) > 0:
        partial_time = Util.printNumberAsTime(time.time() - partial_start)
        elapsed_time_ = time.time() - START
        elapsed_time = Util.printNumberAsTime(elapsed_time_)
        tet = Util.printNumberAsTime(elapsed_time_ / percentage)
        print(
            '{:.2%} pronto em {}. Tempo total: {}. tet: {}'.format(percentage, partial_time, elapsed_time, tet))


def experiment_maximum_ciphertext_allowed():
    global START
    print('Start experiment to measure maximum size o Ciphertext to publish...')
    label = ['policy_size', 'policy', 'exitCode', 'execTime', 'gasCost', 'gasPrice', 'etherCost', 'gasLimit', 'timestamp']
    rootAddress = None
    start_system(rootAddress)
    if rootAddress is None:
        publishAttributes()
    getAttributes()
    Util.runJAVACommand(SMART_DCPABE, 'load', BOB.gid, javaargs = JVM_DISABLE_WARNING)
    for j in range(16, MAX_ATRIBUTOS):
        gather_data('sizeOfCiphertextRinkeby.csv', label, rodada=j, max_rodadas=MAX_ATRIBUTOS)
    print('Finished experiment to measure Ciphertext size.')


def main():
    print('***EXPERIMENT***\n')
    random.seed('Honk Honk')
    global DEBUG, START
    Util.START = time.time()
    Util.DEBUG = True
    experiment_maximum_ciphertext_allowed()


if __name__ == "__main__":
    main()
