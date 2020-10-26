#!/usr/bin/env python
#-*- coding: utf-8 -*-

import csv
import shlex, subprocess
import random, string
import os
from concurrent import futures
from concurrent.futures import ThreadPoolExecutor
import javaobj
import time

DCPABE = '../lib/dcpabe-1.2.0-jar-with-dependencies.jar'
PROTOTYPE = 'hyperdcpabe-client-0.1-SNAPSHOT-jar-with-dependencies.jar'
ALPHABET = string.ascii_letters + string.digits + '_' + ''.join([x + x.upper() for x in 'çãéúáõâêû'])
TEMP_FOLDER = '../profiling/temp/'
NUM_THREADS = 5
data = {}

def gsetup():
    params = '-f {p}gpfile'.format(p=TEMP_FOLDER)
    runDCPABECommand(DCPABE, 'gsetup', params)

def asetup(run_id):
    global data
    worker_number = run_id % NUM_THREADS
    atributo = ''.join(random.choices(ALPHABET, k=random.randint(5, 30)))
    autoridade = ''.join(random.choices(ALPHABET, k=random.randint(5, 30)))
    params = '-f {p}gpfile {auth} {p}secKey{tn} {p}pubKey{tn} {attr}'.format(p=TEMP_FOLDER, auth=autoridade, attr=atributo, tn=worker_number)
    runDCPABECommand(DCPABE, 'asetup', params)
    with open('temp/pubKey'+str(worker_number), 'rb') as f:
        pks = javaobj.loads(b''.join(f.readlines()))
        eg1g1ai_size = len(pks[atributo].eg1g1ai)
        g1yi_size = len(pks[atributo].g1yi)
        return [run_id, atributo, autoridade, eg1g1ai_size, g1yi_size]

def measureSizeOfPubKeys(n):
    global data
    with ThreadPoolExecutor(max_workers=NUM_THREADS) as executor:
        buffer_size = NUM_THREADS*100
        start = time.time()
        for i in range(int(n/buffer_size)):
            partial_start = time.time()
            gsetup()
            jobs = []
            for j in range(buffer_size):
                jobs.append(executor.submit(asetup, j))
            file_mode = 'w'
            label = ['run', 'autoridade', 'atributo', 'tamanho eg1g1ai', 'tamanho g1yi']
            if os.path.exists('sizeOfPubKey.csv'):
                file_mode = 'a'
                label = None
            jobs = futures.wait(jobs)
            with open('sizeOfPubKey.csv', file_mode, newline='') as f:
                writer = csv.writer(f)
                if label is not None:
                    writer.writerow(label)
                writer.writerows([x.result() for x in jobs.done])
            partial_time = printNumberAsTime(time.time() - partial_start)
            elapsed_time = printNumberAsTime(time.time() - start)
            percentage = (i+1)*buffer_size/(n)
            print('{:.2%} pronto em {}. Executando há {}.'.format(percentage, partial_time, elapsed_time))
        print('Terminado.')

def printNumberAsTime(measure):
    h = int(measure/3600)
    m = int(measure/60 - h*60)
    s = measure - h*3600 - m*60
    ms = int(1000*(measure - int(measure)))
    output = '{}s.{}ms'
    if (m != 0):
        output = '{}m' + output
    if (h != 0):
        output = '{}h' + output
    return output.format(h, m, s, ms)

def main():
    measureSizeOfPubKeys(10000)

def runDCPABECommand(jar, command, params):
    args = 'java -jar {} {} {}'.format(jar, command, params)
    try:
        sanitizedArgs = shlex.split(args)
        subprocess.call(sanitizedArgs)
    except Exception as e:
        print(e)

if __name__ == "__main__":
    main()
