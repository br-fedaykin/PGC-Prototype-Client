#!/bin/bash

trap "exit" INT TERM ERR
trap "kill 0" EXIT

# command and option to call ganache-cli without blocking the shell in linux environments
shell=sh
option=-c

# command and option to call ganache-cli without blocking the shell in Windows environment
if [[ "$OSTYPE" = "msys" ]]; then
    shell=cmd
    option="//c start"
fi

echo "Initalizing Ganache..."

$shell $option ganache-cli -a 10 -d -m "pumpkin immense certain snack please patch universe leisure reopen truth eight gown" -p 7545

sleep 3
read -p 'Choose milestone to test: ' milestone

case $milestone in
1) echo "Um" ;;
2) echo "dois" ;;
3) echo "três" ;;
4) echo "quatro" ;;
5) echo "cinco" ;;
6) echo "seis" ;;
7) echo "sete" ;;
8) echo "oito" ;;
9) echo "nove" ;;
10) echo "dez" ;;
*) echo "Opção Inválida!" ;;
esac
