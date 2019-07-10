# Protótipo do PGC

## Prazos

### Milestone1 entrega 19/06

Uso do mock de acesso ao blockchain para armazenar/recuperar prontuários

Código da Reunião 9

#### Cenário: novo prontuário

Cliente 1 java usa o código do ABE (cria usuário, obtém atributo1, envia prontuário1 - i.e., pdf1 encriptado)
Cliente 2 java usa o código do ABE (supondo que possui o atributo1, obtém prontuário1 codificado e o decodifica)

### Milestone2 entrega 03/07

Uso do mock de acesso ao blockchain para armazenar/recuperar prontuarios

#### cenário: novo prontuário

Cliente 1 java (usuário já criado) obtém novos atributos: atributo2 e atributo3. O atributo1 já foi obtido no milestone1.
Cliente 1 java envia prontuario2 (i.e., pdf2 encriptado) com atributo2 AND atributo3
Cliente 2 java (só com atributo1) tenta mas não consegue obter o prontuário2 (nem decodificá-lo) pois não tem atributos.

#### cenário: atualiza somente prontuário

Cliente 1 java (usuário já criado) envia prontuário1, mesmo atributo1, novo pdf1
Classe Prontuario tem atributo dataEnvio, portanto atualizar atributo.
Cliente 2 java obtém prontuário1 codificado e o decodifica (pois possui atributo1 do milestone1).

#### cenário: atualiza somente permissões do prontuário

Cliente 1 java procura prontuario1 e atualiza: a) atributo1 por atributo1 AND atributo2 e b) dataEnvio.
Cliente 2 java (só com atributo1) tenta mas não consegue obter o prontuário1 (nem decodificá-lo) pois não tem atributo2.

### Milestone3 entrega 17/07

sem integração com códigos do milestone1 e milestone2
Criar o contract do milestone1 em uma base de teste:
Escolher se será no Ethereum (usando o Remix)
Escolher se será no Hyperledger (usando o Composer)

### Milestone4 31/07

sem integração com códigos do milestone1 e milestone2
Criar o contract do milestone2 em uma base de teste escolhida no milestone3

### Milestone5 14/08

Integrar o código dos milestones1 e milestone2 com os contract criados nos milestone3 e milestone4
