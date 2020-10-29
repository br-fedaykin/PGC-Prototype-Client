#!/usr/bin/env python
#-*- coding: utf-8 -*-

import csv
import shlex, subprocess
import random, string
import os
import io
import javaobj
import contextlib
from concurrent import futures
from concurrent.futures import ThreadPoolExecutor
from shutil import copyfile
import time
import math

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

def generateRandomString(minSize = 5, maxSize = 30):
    return ''.join(random.choices(ALPHABET, k=random.randint(minSize, maxSize)))

def randomPolicy(policySize, autoridades, operators):
    randomTree = TreeNode().buildRandomBinaryTree(policySize)
    return randomTree.parseTreeToABEPolicy(autoridades, operators)

def gatherDataFromCommand(n, command, csv_output_file, label, command_kwargs, rodada = 0, max_rodadas=1):
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
