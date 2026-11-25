# Histórias de Usuário: SmartPark Guarita (SPG)

Versão provisória, baseada no TAP 0.1. Será revisada depois da entrevista com o Product Owner.
Formato do template SpinOff: descrição, testes de aceitação e protótipo. Todos os dados abaixo são fictícios.

## Suposições a confirmar na entrevista

- Horários dos turnos (manhã, tarde e noite).
- Se a solicitação do servidor precisa de aprovação do administrador.
- Por qual aparelho o servidor acessa o app.
- Por quanto tempo o histórico de entradas e saídas é guardado.

## Resumo

| Nº | Perfil | História | Prioridade | Situação |
|---|---|---|---|---|
| US01 | Porteiro | Consultar placa | Alta | Implementada |
| US02 | Porteiro | Registrar entrada | Alta | Implementada |
| US03 | Porteiro | Registrar saída | Alta | Implementada |
| US04 | Porteiro | Ver veículos presentes | Média | Implementada |
| US05 | Administrador | Gerenciar servidores | Alta | Implementada |
| US06 | Administrador | Gerenciar veículos | Alta | Implementada |
| US07 | Administrador | Cadastrar troca temporária | Alta | Implementada |
| US08 | Administrador | Aprovar ou recusar solicitação | Média | Implementada |
| US09 | Servidor | Solicitar troca temporária | Média | Implementada |
| US10 | Servidor | Acompanhar solicitação | Baixa | Implementada |
| US11 | Painel | Ver ocupação atual | Média | Implementada |
| US12 | Painel | Ver entradas e saídas por turno | Baixa | Implementada |

"Implementada" quer dizer que a funcionalidade existe no app. Os testes de aceitação de cada história ainda precisam ser executados e registrados com prints.

A prioridade é uma sugestão: o TAP diz para entregar primeiro o fluxo do porteiro.

---

## US01 - Consultar placa

- **Caso de uso:** Consultar placa
- **Número da história:** US01
- **Estimativa:** a definir

**Descrição:** Como porteiro, eu quero digitar uma placa e ver na hora se o veículo está autorizado, de modo que eu não precise abrir planilha nem lista impressa.

**Testes de aceitação**

Pré-condição: perfil Porteiro selecionado.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Placa de veículo cadastrado | Placa cadastrada | "Autorizado" com o nome do servidor |
| 02 | Placa desconhecida | Placa que não existe no cadastro | "Não autorizado" |
| 03 | Troca temporária dentro da validade | BRA2E19, com troca válida hoje | "Autorizado - troca temporária, válida até 23h59 de hoje" |
| 04 | Troca temporária vencida | BRA2E19, com troca válida só até ontem | "Não autorizado" |
| 05 | Placa em formato inválido | "12" | Mensagem de placa inválida |

**Protótipo:** a referenciar.

## US02 - Registrar entrada

- **Caso de uso:** Registrar entrada
- **Número da história:** US02
- **Estimativa:** a definir

**Descrição:** Como porteiro, eu quero registrar a entrada de um veículo autorizado, de modo que fique guardado quem entrou, quando e por qual porteiro.

**Testes de aceitação**

Pré-condição: placa consultada e autorizada (US01).

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Entrada de veículo autorizado | Placa autorizada | Registro criado com data, hora e porteiro |
| 02 | Entrada de veículo não autorizado | Placa não autorizada | Registro não é criado e o app avisa |
| 03 | Entrada duplicada | Placa que já está dentro | Mensagem de que o veículo já está no câmpus |

**Protótipo:** a referenciar.

## US03 - Registrar saída

- **Caso de uso:** Registrar saída
- **Número da história:** US03
- **Estimativa:** a definir

**Descrição:** Como porteiro, eu quero registrar a saída de um veículo, de modo que a lista de presentes e a ocupação fiquem corretas.

**Testes de aceitação**

Pré-condição: perfil Porteiro selecionado.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Saída de veículo presente | Placa que está dentro | Saída registrada com data e hora |
| 02 | Saída de veículo que não entrou | Placa sem entrada aberta | Mensagem de que não há entrada registrada |

**Protótipo:** a referenciar.

## US04 - Ver veículos presentes

- **Caso de uso:** Listar veículos presentes
- **Número da história:** US04
- **Estimativa:** a definir

**Descrição:** Como porteiro, eu quero ver a lista de veículos que estão dentro do câmpus agora, de modo que eu saiba quem ainda não saiu.

**Testes de aceitação**

Pré-condição: há entradas registradas.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Lista com veículos dentro | 2 entradas e 1 saída | Aparecem só os veículos sem saída |
| 02 | Lista vazia | Nenhuma entrada em aberto | Mensagem de que não há veículos presentes |

**Protótipo:** a referenciar.

## US05 - Gerenciar servidores

- **Caso de uso:** Gerenciar servidores
- **Número da história:** US05
- **Estimativa:** a definir

**Descrição:** Como administrador, eu quero cadastrar, editar e excluir servidores, de modo que o cadastro fique sempre atualizado.

**Testes de aceitação**

Pré-condição: perfil Administrador selecionado.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Cadastrar servidor | Nome e dados obrigatórios preenchidos | Servidor salvo e exibido na lista |
| 02 | Campo obrigatório vazio | Nome em branco | Mensagem de campo obrigatório |
| 03 | Editar servidor | Novo nome | Dado atualizado |
| 04 | Excluir servidor | Servidor sem pendências | Servidor removido da lista |

**Protótipo:** a referenciar.

## US06 - Gerenciar veículos

- **Caso de uso:** Gerenciar veículos
- **Número da história:** US06
- **Estimativa:** a definir

**Descrição:** Como administrador, eu quero cadastrar, editar e excluir veículos de um servidor, de modo que a portaria consulte placas confiáveis.

**Testes de aceitação**

Pré-condição: existe ao menos um servidor cadastrado.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Cadastrar veículo | Placa válida, modelo, cor | Veículo salvo e ligado ao servidor |
| 02 | Placa inválida | "AB-1" | Mensagem de placa inválida |
| 03 | Placa repetida | Placa já cadastrada | Mensagem de placa já existente |
| 04 | Excluir veículo | Veículo existente | Veículo removido |

**Protótipo:** a referenciar.

## US07 - Cadastrar troca temporária

- **Caso de uso:** Cadastrar troca temporária
- **Número da história:** US07
- **Estimativa:** a definir

**Descrição:** Como administrador, eu quero cadastrar uma troca temporária de veículo com início e fim de validade, de modo que a placa seja autorizada só durante o período.

**Testes de aceitação**

Pré-condição: existe um servidor cadastrado.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Cadastrar troca válida | Placa, início hoje, fim hoje | Troca salva e a placa passa a constar como autorizada |
| 02 | Fim antes do início | Fim anterior ao início | Mensagem de período inválido |
| 03 | Expiração automática | Troca com fim ontem | A placa volta a aparecer como não autorizada |

**Protótipo:** a referenciar.

## US08 - Aprovar ou recusar solicitação

- **Caso de uso:** Avaliar solicitação
- **Número da história:** US08
- **Estimativa:** a definir

**Descrição:** Como administrador, eu quero aprovar ou recusar as solicitações dos servidores, de modo que só trocas legítimas sejam autorizadas.

**Testes de aceitação**

Pré-condição: existe uma solicitação pendente (US09).

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Aprovar solicitação | Solicitação pendente | Status "aprovado" e a placa autorizada no período |
| 02 | Recusar solicitação | Solicitação pendente | Status "recusado" e a placa continua não autorizada |
| 03 | Avaliar solicitação já decidida | Solicitação aprovada | O app não permite decidir de novo |

**Protótipo:** a referenciar.

## US09 - Solicitar troca temporária

- **Caso de uso:** Solicitar troca temporária
- **Número da história:** US09
- **Estimativa:** a definir

**Descrição:** Como servidor, eu quero solicitar uma troca temporária informando placa, modelo, cor, motivo e período, de modo que eu entre com outro veículo sem precisar mexer no cadastro.

**Testes de aceitação**

Pré-condição: perfil Servidor/Professor selecionado.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Solicitação completa | BRA2E19, Onix, prata, "carro da esposa", hoje | Solicitação criada com status "pendente" |
| 02 | Campo obrigatório vazio | Motivo em branco | Mensagem de campo obrigatório |
| 03 | Período inválido | Fim antes do início | Mensagem de período inválido |

**Protótipo:** a referenciar.

## US10 - Acompanhar solicitação

- **Caso de uso:** Acompanhar solicitação
- **Número da história:** US10
- **Estimativa:** a definir

**Descrição:** Como servidor, eu quero ver o status dos meus pedidos, de modo que eu saiba se minha troca foi aprovada.

**Testes de aceitação**

Pré-condição: o servidor tem solicitações.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Ver status | Pedidos pendente, aprovado e recusado | Cada pedido mostra o status correto |
| 02 | Sem pedidos | Servidor sem solicitações | Mensagem de que não há pedidos |

**Protótipo:** a referenciar.

## US11 - Ver ocupação atual

- **Caso de uso:** Consultar ocupação
- **Número da história:** US11
- **Estimativa:** a definir

**Descrição:** Como administrador, eu quero ver quantos veículos estão no estacionamento agora, de modo que eu acompanhe a lotação.

**Testes de aceitação**

Pré-condição: há entradas e saídas registradas.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Contagem atual | 5 entradas e 2 saídas | Ocupação atual de 3 veículos |
| 02 | Sem registros | Nenhuma entrada | Ocupação de 0 |

**Protótipo:** a referenciar.

## US12 - Ver entradas e saídas por turno

- **Caso de uso:** Consultar movimento por turno
- **Número da história:** US12
- **Estimativa:** a definir

**Descrição:** Como administrador, eu quero ver as entradas e saídas por turno (manhã, tarde e noite), de modo que eu saiba em quais períodos o estacionamento lota.

**Testes de aceitação**

Pré-condição: há registros em turnos diferentes.

| Nr. | Funcionalidade/Comportamento | Entradas | Resultado esperado |
|---|---|---|---|
| 01 | Agrupar por turno | Entradas às 8h, 14h e 20h | Uma entrada em cada turno |
| 02 | Turno sem movimento | Nenhum registro na noite | Noite aparece com 0 |

**Protótipo:** a referenciar.
