import os
import random
import time
import base64

import ProfilerUtils as util
from ProfilerUtils import TEMP_FOLDER, DEBUG, START

SMART_DCPABE = '../hyperdcpabe-client-0.1-SNAPSHOT-jar-with-dependencies.jar'

def startBlockchain():
    mnemonic = 'pumpkin immense certain snack please patch universe leisure reopen truth eight gown'
    args = 'ganache-cli -a 10 -d -m "{}" -p 7545 -k petersburg'.format(mnemonic)
    os.system('start cmd /k {}'.format(args))
    time.sleep(5)
    print('Blockchain initialized')

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

def publishEncryptedFile():
    pass

def experimentMaximumCiphertextAllowed():
    global START
    print('Start experiment to measure maximum size o Ciphertext to publish.')
    label = ['run_id', 'policySize', 'policy', 'len_c0', 'len_c1', 'len_c2', 'len_c3', 'len_c0_encoded', 'len_c1_encoded', 'len_c2_encoded', 'len_c3_encoded', 'gasCost']
    ops_list = [['and', 'or'], ['and'], ['or']]
    ops_names = ['RandomOps', 'ANDOperator', 'OROperator']
    max_rodadas = 100
    for i in range(len(ops_list)):
        for j in range(0, max_rodadas):
            command_kwargs = {'measurer': ciphertextByteSizeMeasure, 'policySize': j+1, 'operators': ops_list[i]}
            util.gatherDataFromCommand(10, publishEncryptedFile, 'sizeOfCiphertext{}.csv'.format(ops_names[i]), label, rodada=j, max_rodadas = max_rodadas*3, command_kwargs=command_kwargs)
    print('Finished experiment to measure Ciphertext size.')

def main():
    print('***EXPERIMENT***\n')
    random.seed('Honk Honk')
    global DEBUG, START
    START = time.time()
    DEBUG = True
    experimentMaximumCiphertextAllowed()

if __name__ == "__main__":
    main()
