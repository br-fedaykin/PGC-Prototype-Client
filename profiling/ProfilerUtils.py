import contextlib
import csv
import io
import os
import random
import shlex
import string
import subprocess
import time
from concurrent import futures
from concurrent.futures import ThreadPoolExecutor

import javaobj

ALPHABET = string.ascii_letters + string.digits + \
    '_' + ''.join([x + x.upper() for x in 'çãéúáõâêû'])

TEMP_FOLDER = '../profiling/temp/'
NUM_THREADS = 2
DEBUG = False
START = time.time()

class TreeNode:
    def __init__(self):
        self.left = None
        self.right = None

    def isLeaf(self):
        return self.left is None and self.right is None

    def build_random_binary_tree(self, number_leafs):
        if number_leafs == 1:
            return self
        left_size = random.randint(1, number_leafs - 1)
        self.left = TreeNode().build_random_binary_tree(left_size)
        self.right = TreeNode().build_random_binary_tree(number_leafs - left_size)
        return self


    def parseTreeToABEPolicy(self, autoridades, operators):
        if self.isLeaf():
            nome = random.choice(list(autoridades.keys()))
            return random.choice(autoridades[nome]), [nome]
        pks = []
        result = random.choice(operators)
        for subTree in [self.left, self.right]:
            sub_policy, partial_pks = subTree.parseTreeToABEPolicy(
                autoridades, operators)
            pks.extend([x for x in partial_pks if x not in pks])
            result = result + ' ' + sub_policy
        return result, pks


def runJAVACommand(jar, command, params):
    args = 'java -jar {} {} {}'.format(jar, command, params)
    exit_code = None
    try:
        sanitized_args = shlex.split(args)
        exit_code = subprocess.call(sanitized_args, timeout=30)
    except Exception as e:
        print(e)
    return exit_code


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


def generate_random_string(min_size=5, max_size=30):
    return ''.join(random.choices(ALPHABET, k=random.randint(min_size, max_size)))


def random_policy(policy_size, autoridades, operators):
    random_tree = TreeNode().build_random_binary_tree(policy_size)
    return random_tree.parseTreeToABEPolicy(autoridades, operators)


def gather_data_from_command(n, command, csv_output_file, label, command_kwargs, rodada=0, max_rodadas=1):
    buffer_size = NUM_THREADS * 5
    for i in range(int(n / buffer_size)):
        partial_start = time.time()
        results = []
        for j in range(buffer_size):
            run_id = n * rodada + i * buffer_size + j
            results.extend(command(run_id, **command_kwargs))
        file_mode = 'w'
        if os.path.exists(csv_output_file):
            file_mode = 'a'
            label = None
        with open(csv_output_file, file_mode, newline='', encoding='utf-8') as f:
            writer = csv.writer(f)
            if label is not None:
                writer.writerow(label)
            writer.writerows(results)
        percentage = ((i + 1) * buffer_size + n *
                        rodada) / (n * max_rodadas)
        if int(10000 * percentage) > 0:
            partial_time = printNumberAsTime(time.time() - partial_start)
            elapsed_time_ = time.time() - START
            elapsed_time = printNumberAsTime(elapsed_time_)
            tet = printNumberAsTime(elapsed_time_ / percentage)
            print(
                '{:.2%} pronto em {}. Tempo total: {}. tet: {}'.format(percentage, partial_time, elapsed_time, tet))
    if max_rodadas != 1:
        print('subrotina {} terminada.'.format(rodada))
    else:
        print('Terminado.')

def gather_data_from_command_multicore(n, command, csv_output_file, label, command_kwargs, rodada=0, max_rodadas=1):
    with ThreadPoolExecutor(max_workers=NUM_THREADS) as executor:
        buffer_size = NUM_THREADS * 25
        for i in range(int(n / buffer_size)):
            partial_start = time.time()
            jobs = []
            for j in range(buffer_size):
                run_id = n * rodada + i * buffer_size + j
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
            percentage = ((i + 1) * buffer_size + n *
                          rodada) / (n * max_rodadas)
            if int(10000 * percentage) > 0:
                partial_time = printNumberAsTime(time.time() - partial_start)
                elapsed_time_ = time.time() - START
                elapsed_time = printNumberAsTime(elapsed_time_)
                tet = printNumberAsTime(elapsed_time_ / percentage)
                print(
                    '{:.2%} pronto em {}. Tempo total: {}. tet: {}'.format(percentage, partial_time, elapsed_time, tet))
        if max_rodadas != 1:
            print('subrotina {} terminada.'.format(rodada))
        else:
            print('Terminado.')


def printNumberAsTime(measure):
    h = int(measure / 3600)
    m = int(measure / 60 - h * 60)
    s = round(measure - h * 3600 - m * 60, ndigits=2)
    time_str = '{}s'.format(s)
    if m != 0:
        time_str = '{}m'.format(m) + time_str
    if h != 0:
        time_str = '{}h'.format(h) + time_str
    return time_str
