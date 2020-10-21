#!/bin/bash

data=./demo_data.dat

# this should kill ganache-cli after calling exit, but it didn't work and Idk why
# trap "exit" INT TERM ERR
# trap "kill 0" EXIT

# command and option to call ganache-cli without blocking the shell in linux environments
shell=sh
option=-c

# this is necessary until DCPABE library updates org.bouncycastle version
java_args="--add-opens java.base/sun.security.provider=ALL-UNNAMED -jar"

# command and option to call ganache-cli without blocking the shell in Windows environment
if [[ "$OSTYPE" = "msys" ]]; then
    shell=cmd
    option="//c start"
fi
echo -e "SmartDCPABE DEMONSTRATION:\n"
echo -e "Initalizing Ganache...\n"

mnemonic="pumpkin immense certain snack please patch universe leisure reopen truth eight gown"
$shell $option ganache-cli -a 10 -d -m "$mnemonic" -p 7545 -k petersburg

# cleaning data of previous runnings
if [ -d "data" ]; then
    echo "Data must be cleaned for a new demonstration."
    read -p "Proceed with exclusion of data folder inside ${PWD##*/}? [y/n]: " should_delete
    if [[ "$should_delete" = "y" ]]; then
        rm -R data
    else
        echo "The current build isn't able to deal with inconsistent data."
        echo "Please delete data folder to run demonstration again."
        echo
        exit
    fi
fi

sleep 2
read -p 'Choose milestone to test: ' milestone

source $data
case $milestone in
    1) echo "milestone 1"

    # admin inicia o sistema e cria os contratos
    java $java_args $1 init --network http://127.0.0.1:7545 ${admin[name]} ${admin[email]} ${admin[privateKey]} --profile

    # certificador cria perfil e atributo, e publica ambos
    java $java_args $1 create-user ${crm[name]} ${crm[email]} ${crm[privateKey]}
    java $java_args $1 create-certifier
    java $java_args $1 create-attributes atributo1 atributo2 atributo3
    java $java_args $1 publish user certifier attributes

    # usuário 1 - Bob, cria perfil e solicita concessão do atributo 1 (chave pessoal ABE)
    java $java_args $1 create-user ${bob[name]} ${bob[email]} ${bob[privateKey]}
    java $java_args $1 publish user
    java $java_args $1 request-attributes ${crm[gid]} atributo1

    # usuário 2 - Alice, cria perfil, recebe chaves públicas e criptografa um documento
    java $java_args $1 create-user ${alice[name]} ${alice[email]} ${alice[privateKey]}
    java $java_args $1 publish user
    java $java_args $1 get-attributes ${crm[gid]} atributo1

    sleep 2
    filename=message.txt
    file_content ="File created for demonstration. Alice will configure access to this file with police \"atributo1\"."
    echo;echo $file_content > data/client/Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47/${filename}
    echo "*** FILE \"${filename}\" CREATED IN ALICE USER FOLDER TO PROCEED WITH DEMONSTRATION."; echo
    sleep 2

    java $java_args $1 encrypt message.txt atributo1 ${crm[gid]}
    java $java_args $1 send $filename

    # certificador recebe requisição de atributo e o concede ao Bob
    java $java_args $1 load ${crm[gid]}
    java $java_args $1 check-requests pending
    java $java_args $1 yield-attributes ${bob[gid]} 0
    java $java_args $1 send attributes ${bob[gid]}

    # usuário 1 - Bob, de posse do atributo, o descriptografa
    java $java_args $1 load ${bob[gid]}
    java $java_args $1 check-requests ok
    java $java_args $1 get-personal-keys
    java $java_args $1 get-recordings ${alice[gid]} $filename
    java $java_args $1 decrypt $filename ;;
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
