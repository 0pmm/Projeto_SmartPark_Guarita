# Necessidades do Cliente

**SPG - SmartPark Guarita**
Versão 0.1 (rascunho, a revisar depois da entrevista com o cliente)

Este documento reúne as informações e os desejos do cliente sobre o processo de controle do estacionamento. Por enquanto ele só tem o que consta no enunciado e no TAP 0.1. As respostas da entrevista ainda não chegaram e serão incluídas na seção 4.

## 1. Cliente

Institutos Federais, administração do câmpus, responsável pela portaria e pelo estacionamento de docentes e servidores. No projeto, o cliente é representado pelo Prof. Fabio Santos, que atua como Product Owner.

## 2. Situação atual (informada pelo cliente no enunciado)

- O acesso de veículos de docentes e servidores é controlado por uma planilha compartilhada.
- O servidor informa modelo, cor e placa e recebe depois uma credencial física (cartão ou adesivo) para a portaria identificar o veículo.
- A planilha fica desatualizada: há trocas temporárias de veículo, carros alugados e uso eventual do carro de um familiar.

## 3. Dores observadas

| Nº | Dor | Quem é afetado |
|---|---|---|
| D1 | Conferência lenta na portaria: para checar uma placa é preciso abrir a planilha ou procurar numa lista impressa | Portaria |
| D2 | Cadastro rígido: o servidor que vem com outro veículo não consegue atualizar o registro com rapidez | Servidores |
| D3 | Sem informação sobre ocupação: ninguém sabe se o estacionamento lota em certos dias ou turnos | Administração |
| D4 | Credenciais físicas desbotam, caem ou são esquecidas | Servidores e portaria |

## 4. Respostas da entrevista

Ainda não há respostas. As perguntas estão em `Entrevista_Perguntas.md` e serão registradas na `Ata_Reuniao_Entrevista.md`. Quando o professor responder, esta seção resume as necessidades confirmadas.

## 5. Suposições da equipe (a confirmar)

O que não for perguntado ou respondido vira suposição da equipe. Até a entrevista, valem estas:

| Nº | Suposição | Pergunta relacionada |
|---|---|---|
| S1 | A portaria tem um tablet Android de uso exclusivo do aplicativo | 2 |
| S2 | Os turnos são manhã, tarde e noite, com horários a definir | 11 |
| S3 | A solicitação de troca temporária do servidor precisa de aprovação do administrador | 12 |
| S4 | O servidor usa o aplicativo no próprio tablet ou em outro aparelho Android, a definir | 3 |
| S5 | O perfil é escolhido na entrada do aplicativo, sem login e senha | 5 |
| S6 | A carga inicial de servidores e veículos é feita pelo administrador, sem importar a planilha atual | 6, 7 |
| S7 | Os dados ficam só no tablet, sem internet nem servidor | 26 |
| S8 | Uma troca temporária vale por um período com início e fim, e depois expira sozinha | 13 |

## 6. Necessidades identificadas

Estas necessidades alimentam o Backlog do Produto e as histórias de usuário (`Historias_de_Usuario.md`).

- N1: conferir uma placa na portaria rapidamente, sem planilha nem lista impressa (D1, D4).
- N2: registrar a entrada e a saída dos veículos, com data, hora e porteiro.
- N3: manter o cadastro de servidores e veículos atualizado (D2).
- N4: permitir trocas temporárias de veículo, com validade, que expiram sozinhas (D2).
- N5: permitir que o próprio servidor solicite a troca temporária e acompanhe o pedido (D2).
- N6: informar a ocupação do estacionamento e o movimento por turno (D3).
- N7: proteger os dados pessoais (nome, placa, horários) conforme a LGPD.
