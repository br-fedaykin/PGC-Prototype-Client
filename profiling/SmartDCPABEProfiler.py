import os
import random
import time
import base64

import ProfilerUtils as Util
import bitcoinaddress as btc

from ProfilerUtils import TEMP_FOLDER, DEBUG, START, NUM_THREADS

SMART_DCPABE = '../lib/hyperdcpabe-client-0.1-SNAPSHOT-jar-with-dependencies.jar'


class User:
    def __init__(self, name, wallet=None, private_key=None):
        self.name = name
        self.email = name.lower() + '@email.com'
        if wallet is None or private_key is None:
            new_wallet()
        self.gid = '{}-{}'.format(self.name, self.wallet)

    def new_wallet(self):
        newWallet = btc.Wallet()
        private_key = newWallet.key.hex
        wallet = newWallet.address.pubaddr1
        self.wallet = wallet
        self.private_key = private_key


ADMIN = User('admin', '0xFae373E0BFfaE794fA818D749D6da38D4f7cA986',
             'e4d8c81796894ea5bf202e3a3204948dddd62f4d709c278bf8096898957be241')

BOB = User('Bob')
ALICE = User('Alice')
CRM = User('CRM')


def start_blockchain():
    mnemonic = 'pumpkin immense certain snack please patch universe leisure reopen truth eight gown'
    args = 'ganache-cli -a 10 -d -m "{}" -p 7545 -k petersburg'.format(
        mnemonic)
    os.system('start cmd /k {}'.format(args))
    time.sleep(5)
    print('Blockchain initialized')


def init_smart_dcpabe(folder):
    params = '{} {} {} --profile -d'.format(ADMIN.name,
                                            ADMIN.email, ADMIN.private_key, folder)
    Util.runJAVACommand(SMART_DCPABE, 'init', params)


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
    result.append([run_id, policy_size, policy, len_c0, len_c1, len_c2, len_c3,
                   len_c0_encoded, len_c1_encoded, len_c2_encoded, len_c3_encoded])
    return result


def publishAuthorities(run_id, policy_size, num_authorities):
    worker_number = run_id % NUM_THREADS
    atributos = [Util.generate_random_string() for _ in range(policy_size)]
    autoridades = {'{:02}.{:06}.{}.'.format(worker_number, x, Util.generate_random_string()): [] for x in
                   range(num_authorities)}
    for attr in atributos:
        autoridades[random.choice(list(autoridades.keys()))].append(attr)
    autoridades = {nome: autoridades[nome]
                   for nome in autoridades if len(autoridades[nome]) > 0}
    for name in autoridades:
        auth_number = name[:9]
        authority = User('CRM.' + auth_number)

        params = '-f {p}gpfile {auth} {p}{an}secKey {p}{an}pubKey {attr}'
        params = params.format(p=TEMP_FOLDER, an=auth_number,
                               auth=name, attr=' '.join(autoridades[name]))
        Util.runJAVACommand(SMART_DCPABE, 'asetup', params)
        # cmd.execute("create-user", CRM.NAME, CRM.EMAIL, CRM.PRIV_KEY)
        # cmd.execute("create-certifier")
        # cmd.execute("create-attributes", "atributo1",
        #             "atributo2",  "atributo3")
        # cmd.execute("publish", "user", "certifier", "attributes")


def publish_encrypted_file(run_id, policy_size=1, measurer=None, operators=['and', 'or']):
    worker_number = run_id % Util.NUM_THREADS
    init_smart_dcpabe(str(worker_number))
    autoridades = publishAuthorities(
        policy_size, int(run_id, 1 + policy_size/3))


def experiment_maximum_ciphertext_allowed():
    global START
    print('Start experiment to measure maximum size o Ciphertext to publish.')
    label = ['run_id', 'policy_size', 'policy', 'len_c0', 'len_c1', 'len_c2', 'len_c3',
             'len_c0_encoded', 'len_c1_encoded', 'len_c2_encoded', 'len_c3_encoded', 'gasCost']
    ops_list = [['and', 'or'], ['and'], ['or']]
    ops_names = ['RandomOps', 'ANDOperator', 'OROperator']
    max_rodadas = 100
    start_blockchain()
    for i in range(len(ops_list)):
        for j in range(0, max_rodadas):
            command_kwargs = {'measurer': ciphertext_byte_size_measure,
                              'policy_size': j+1, 'operators': ops_list[i]}
            Util.gather_data_from_command(10, publish_encrypted_file, 'sizeOfCiphertext{}.csv'.format(
                ops_names[i]), label, rodada=j, max_rodadas=max_rodadas * 3, command_kwargs=command_kwargs)
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
