#!/bin/bash
echo "Initalizing Ganache..."

cmd //c start ganache-cli -a 10 -d -m "pumpkin immense certain snack please patch universe leisure reopen truth eight gown" -p 7545

read -p 'Choose milestone to test: ' milestone

case $milestone in
1) echo "Um" ;;
2) echo "dois" ;;
3) echo "tres" ;;
4) echo "quatro" ;;
5) echo "cinco" ;;
6) echo "seis" ;;
7) echo "sete" ;;
8) echo "oito" ;;
9) echo "nove" ;;
10) echo "dez" ;;
*) echo "Opcao Invalida!" ;;
esac
