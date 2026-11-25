# SmartPark Guarita (SPG)

Aplicativo Android para o tablet da portaria que substitui a planilha compartilhada e as credenciais físicas no controle do estacionamento do câmpus.

Projeto final de Programação de Dispositivos Móveis (PDIM), BCC, IFSP, 2026-2. Desenvolvido com o processo ágil [SpinOff](http://arum.tec.br/SpinOff/index.htm).

## Perfis

- **Porteiro:** busca a placa (autorizado, não autorizado ou troca temporária válida), registra entrada e saída e vê os veículos presentes.
- **Administrador:** cadastra servidores, veículos e trocas temporárias, e aprova ou recusa solicitações.
- **Servidor/Professor:** solicita uma troca temporária de veículo e acompanha o pedido.
- **Painel:** mostra a ocupação e as entradas e saídas por turno.

## Tecnologias

Kotlin, Jetpack Compose e Room (persistência local). Arquitetura em camadas: tela, ViewModel, repositório e Room.

## Equipe

- Pedro Henrique Modesto Marchiotto: Scrum Master e desenvolvedor
- Lucas Verissimo Carvalho: requisitos e testes
- Gustavo Carvalho: interface (Compose) e protótipo
- Vitor Saviolli Gonsalez: persistência (Room) e regras de negócio
- Gustavo Silva Guimarães de Brito: documentação (SpinOff), ViewModel e repositório, Painel, testes e demonstração

Product Owner: Prof. Fabio Santos.

## Acompanhamento

- [Backlog do Produto](Documentos/Backlog_do_Produto.md): lista priorizada de histórias e tarefas.
- [Quadro de Tarefas](Documentos/Quadro_de_Tarefas.md): tarefas dos sprints, com caixas para marcar o que está pronto.
- [Histórias de Usuário](Documentos/Historias_de_Usuario.md): descrição e testes de aceitação.

Prazos: Etapa 3 em 27/11/2026 e apresentação em 04/12/2026.

## Documentos

Tudo está em [`Documentos/`](Documentos/), organizado por artefato do SpinOff.

| Documento | Arquivo |
|---|---|
| Termo de Abertura do Projeto | [TAP_SmartPark_Guarita_v0.1.pdf](Documentos/TAP_SmartPark_Guarita_v0.1.pdf), [TAP_v1.md](Documentos/TAP_v1.md) |
| Visão | [Visao.md](Documentos/Visao.md) |
| Necessidades do Cliente | [Necessidades_do_Cliente.md](Documentos/Necessidades_do_Cliente.md) |
| Perguntas da entrevista | [Entrevista_Perguntas.md](Documentos/Entrevista_Perguntas.md) |
| Ata de Reunião | [Ata_Reuniao_Entrevista.md](Documentos/Ata_Reuniao_Entrevista.md) |
| Histórias de Usuário | [Historias_de_Usuario.md](Documentos/Historias_de_Usuario.md) |
| Backlog do Produto | [Backlog_do_Produto.md](Documentos/Backlog_do_Produto.md) e `Backlog_do_Produto.xlsx` |
| Backlog do Sprint e Quadro de Tarefas | [Quadro_de_Tarefas.md](Documentos/Quadro_de_Tarefas.md) e `Backlog_Sprint_e_Quadro.xlsx` |
| Modelo de Casos de Uso | [Modelo_Casos_de_Uso.md](Documentos/Modelo_Casos_de_Uso.md) |
| Protótipo | [Prototipo.md](Documentos/Prototipo.md) |
| Modelo de Arquitetura | [Modelo_Arquitetura.md](Documentos/Modelo_Arquitetura.md) |
| Checklist de Projeto | [Checklist_de_Projeto.md](Documentos/Checklist_de_Projeto.md) |
| Issues, labels e milestones | [Issues_GitHub.md](Documentos/Issues_GitHub.md) |

As planilhas `.xlsx` são as versões para entrega no Moodle; o GitHub não mostra o conteúdo delas, por isso existem as versões em Markdown. Os arquivos `Template - *` são os modelos do SpinOff, e a pasta `Documentos/docx/` tem as versões em Word.

## Como contribuir

Veja o [CONTRIBUTING.md](CONTRIBUTING.md) para o fluxo de branches e commits. O acompanhamento das tarefas é feito em Issues e Projects.

## Código

O aplicativo Android está em [`SmartParkGuarita/`](SmartParkGuarita/), organizado em camadas:

- `domain/`: enums, validação de placa e resultados das consultas.
- `data/`: entidades, DAOs, banco (Room) e o repositório com as regras de negócio.
- `viewmodel/`: ViewModels de cada perfil e do painel.
- `ui/`: telas em Jetpack Compose, tema e navegação.

Os quatro perfis estão implementados (histórias US01 a US12): porteiro, administrador, servidor/professor e painel. Todos os dados ficam no próprio aparelho, sem servidor. Falta registrar os testes de aceitação de cada história e revisar o que o professor responder na entrevista. Os detalhes estão em [Modelo_Arquitetura.md](Documentos/Modelo_Arquitetura.md).

## Como rodar

1. Abra a pasta `SmartParkGuarita/` no Android Studio e espere o Gradle sincronizar.
2. Escolha um emulador ou um tablet Android (API 26 ou superior, com a depuração USB ligada) e clique em Run.
3. Na primeira execução o app cria dados fictícios. Para testar, na tela do porteiro use `ABC1D23` (veículo cadastrado), `BRA2E19` (troca temporária de exemplo, válida por 7 dias) e `AAA0000` (não autorizado).
4. Para testar o fluxo da troca temporária: no perfil Servidor/Professor, faça uma solicitação; no perfil Administrador, aprove em Solicitações; no perfil Porteiro, consulte a placa pedida.

Testes unitários: `./gradlew :app:testDebugUnitTest` dentro da pasta `SmartParkGuarita/`.
