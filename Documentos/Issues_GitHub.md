# Issues, labels e milestones para criar no GitHub

Textos prontos para a equipe criar as issues (aba **Issues**, botão **New issue**) e organizar o quadro (aba **Projects**). Os testes de aceitação completos de cada história estão em `Historias_de_Usuario.md`, e as tarefas de cada sprint em `Backlog_Sprint_e_Quadro.xlsx`.

## 1. Labels

Crie em Issues, **Labels**, **New label**.

| Label | Uso |
|---|---|
| `história` | História de usuário |
| `tarefa` | Tarefa técnica ou de processo |
| `documentação` | Documento do SpinOff |
| `bug` | Defeito encontrado |
| `prioridade: alta` | Entregar primeiro |
| `prioridade: média` | |
| `prioridade: baixa` | |
| `perfil: porteiro` | Histórias do porteiro |
| `perfil: administrador` | Histórias do administrador |
| `perfil: servidor` | Histórias do servidor |
| `perfil: painel` | Histórias do painel |

## 2. Milestones

Crie em Issues, **Milestones**, **New milestone**.

| Milestone | Data |
|---|---|
| Sprint 2 (Etapa 2) | 20/11/2026 |
| Sprint 3 (Etapa 3) | 27/11/2026 |
| Apresentação | 04/12/2026 |

## 3. Quadro (Projects)

Em **Projects**, **New project**, escolha o modelo **Board** e deixe as colunas **A fazer**, **Fazendo** e **Feito**, como no Quadro de Tarefas do SpinOff. Adicione as issues ao quadro.

## 4. Issues

Cada bloco traz o título, as labels, o milestone e o texto para colar. Troque `Documentos/Historias_de_Usuario.md#...` pelo link do arquivo no GitHub, se quiser.

### US01 - Consultar placa
- **Labels:** `história`, `perfil: porteiro`, `prioridade: alta`
- **Milestone:** Sprint 2 (Etapa 2)

Como porteiro, eu quero digitar uma placa e ver na hora se o veículo está autorizado, de modo que eu não precise abrir planilha nem lista impressa.

Testes de aceitação: `Documentos/Historias_de_Usuario.md`, seção US01. É a história escolhida para testar a arquitetura.

### US02 - Registrar entrada
- **Labels:** `história`, `perfil: porteiro`, `prioridade: alta`
- **Milestone:** Sprint 2 (Etapa 2)

Como porteiro, eu quero registrar a entrada de um veículo autorizado, de modo que fique guardado quem entrou, quando e por qual porteiro.

Testes de aceitação: seção US02.

### US03 - Registrar saída
- **Labels:** `história`, `perfil: porteiro`, `prioridade: alta`
- **Milestone:** Sprint 2 (Etapa 2)

Como porteiro, eu quero registrar a saída de um veículo, de modo que a lista de presentes e a ocupação fiquem corretas.

Testes de aceitação: seção US03.

### US04 - Ver veículos presentes
- **Labels:** `história`, `perfil: porteiro`, `prioridade: média`
- **Milestone:** Sprint 3 (Etapa 3)

Como porteiro, eu quero ver a lista de veículos que estão dentro do câmpus agora, de modo que eu saiba quem ainda não saiu.

Testes de aceitação: seção US04.

### US05 - Gerenciar servidores
- **Labels:** `história`, `perfil: administrador`, `prioridade: alta`
- **Milestone:** Sprint 2 (Etapa 2)

Como administrador, eu quero cadastrar, editar e excluir servidores, de modo que o cadastro fique sempre atualizado.

Testes de aceitação: seção US05.

### US06 - Gerenciar veículos
- **Labels:** `história`, `perfil: administrador`, `prioridade: alta`
- **Milestone:** Sprint 2 (Etapa 2)

Como administrador, eu quero cadastrar, editar e excluir veículos de um servidor, de modo que a portaria consulte placas confiáveis.

Testes de aceitação: seção US06.

### US07 - Cadastrar troca temporária
- **Labels:** `história`, `perfil: administrador`, `prioridade: alta`
- **Milestone:** Sprint 3 (Etapa 3)

Como administrador, eu quero cadastrar uma troca temporária de veículo com início e fim de validade, de modo que a placa seja autorizada só durante o período.

Testes de aceitação: seção US07.

### US08 - Aprovar ou recusar solicitação
- **Labels:** `história`, `perfil: administrador`, `prioridade: média`
- **Milestone:** Sprint 3 (Etapa 3)

Como administrador, eu quero aprovar ou recusar as solicitações dos servidores, de modo que só trocas legítimas sejam autorizadas.

Testes de aceitação: seção US08.

### US09 - Solicitar troca temporária
- **Labels:** `história`, `perfil: servidor`, `prioridade: média`
- **Milestone:** Sprint 3 (Etapa 3)

Como servidor, eu quero solicitar uma troca temporária informando placa, modelo, cor, motivo e período, de modo que eu entre com outro veículo sem precisar mexer no cadastro.

Testes de aceitação: seção US09.

### US10 - Acompanhar solicitação
- **Labels:** `história`, `perfil: servidor`, `prioridade: baixa`
- **Milestone:** Sprint 3 (Etapa 3)

Como servidor, eu quero ver o status dos meus pedidos, de modo que eu saiba se minha troca foi aprovada.

Testes de aceitação: seção US10.

### US11 - Ver ocupação atual
- **Labels:** `história`, `perfil: painel`, `prioridade: média`
- **Milestone:** Sprint 3 (Etapa 3)

Como administrador, eu quero ver quantos veículos estão no estacionamento agora, de modo que eu acompanhe a lotação.

Testes de aceitação: seção US11.

### US12 - Ver entradas e saídas por turno
- **Labels:** `história`, `perfil: painel`, `prioridade: baixa`
- **Milestone:** Sprint 3 (Etapa 3)

Como administrador, eu quero ver as entradas e saídas por turno (manhã, tarde e noite), de modo que eu saiba em quais períodos o estacionamento lota.

Testes de aceitação: seção US12.

### Tarefas de processo

#### Criar o projeto Android e definir a arquitetura
- **Labels:** `tarefa`, `prioridade: alta`
- **Milestone:** Sprint 2 (Etapa 2)

Criar o projeto no Android Studio (Kotlin, Compose e Room), organizar os pacotes em camadas, montar a navegação e a seleção de perfil, e o banco inicial com dados fictícios. Ver `Documentos/Modelo_Arquitetura.md`.

#### Testes de sistema e correção de defeitos
- **Labels:** `tarefa`, `prioridade: alta`
- **Milestone:** Sprint 3 (Etapa 3)

Executar os testes de sistema no emulador e no tablet, corrigir os defeitos e registrar as evidências (prints).

#### Relatório de código, .apk e documentos finais
- **Labels:** `tarefa`, `documentação`, `prioridade: alta`
- **Milestone:** Sprint 3 (Etapa 3)

Gerar o `.apk` e o `.zip` do projeto, o relatório de código, o Checklist de Projeto, a Ata da revisão final e o Backlog do Produto atualizado.

#### Apresentação final
- **Labels:** `tarefa`, `documentação`, `prioridade: alta`
- **Milestone:** Apresentação

Preparar e ensaiar a apresentação (a duração será confirmada com o professor) com demonstração no emulador ou no celular, mostrando os documentos mais importantes.
