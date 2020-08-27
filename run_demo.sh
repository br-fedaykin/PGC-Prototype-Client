data=./demo_data.sh

#!/bin/bash

# this should kill ganache-cli after calling exit, but it didn't work. Idk why
# trap "exit" INT TERM ERR
# trap "kill 0" EXIT

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

source $data

case $milestone in
    1) echo "mileston 1"

    # admin inicia o sistema e cria os contratos
    java -jar $1 --init http://127.0.0.1:7545 ${admin[name]} ${admin[email]} ${admin[privateKey]}

    # certificador cria perfil e atributo, e publica ambos
    java -jar $1 --create-user ${crm[name]} ${crm[email]} ${crm[privateKey]}
    java -jar $1 --create-certifier
    java -jar $1 --create-attributes atributo1 atributo2 atributo3
    java -jar $1 --publish user certifier attributes

    # usuário 1 - Bob, cria perfil e solicita concessão do atributo 1 (chave pessoal ABE)
    java -jar $1 --create-user ${bob[name]} ${bob[email]} ${bob[privateKey]}
    java -jar $1 --publish user
    java -jar $1 --request-attribute ${crm[gid]} atributo1

    # usuário 2 - Alice, cria perfil, recebe chaves públicas e criptografa um documento
    java -jar $1 --create-user ${alice[name]} ${alice[email]} ${alice[privateKey]}
    java -jar $1 --publish user
    java -jar $1 --get-attributes ${crm[gid]} atributo1

    echo "put a file inside Alice folder for encryption"
    sleep 5

    read -p "write file name in Alice folder for encryption: " filename
    java -jar $1 --encrypt $filename atributo1 ${crm[gid]}
    java -jar $1 --send $filename

    # certificador recebe requisição de atributo e o concede ao Bob
    java -jar $1 --load ${crm[gid]}
    java -jar $1 --check-requests pending
    java -jar $1 --yield-attributes ${bob[gid]} 0
    java -jar $1 --send attributes ${bob[gid]}

    # usuário 1 - Bob, de posse do atributo, o descriptografa
    java -jar $1 --load ${bob[gid]}
    java -jar $1 --check-requests ok
    java -jar $1 --check-requests download
    java -jar $1 --get-recordings ${alice[gid]} $filename
    java -jar $1 --decrypt $filename ;;
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

exit
