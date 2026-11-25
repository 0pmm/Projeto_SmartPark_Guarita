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

## Estrutura

- `Documentos/`: artefatos do SpinOff

| Documento | Arquivo |
|---|---|
| Termo de Abertura do Projeto | `TAP_SmartPark_Guarita_v0.1.pdf`, `TAP_v1.md` |
| Visão | `Visao.md` |
| Necessidades do Cliente | `Necessidades_do_Cliente.md` |
| Perguntas da entrevista | `Entrevista_Perguntas.md` |
| Ata de Reunião | `Ata_Reuniao_Entrevista.md` |
| Histórias de Usuário | `Historias_de_Usuario.md` |
| Backlog do Produto | `Backlog_do_Produto.xlsx` |
| Backlog do Sprint e Quadro de Tarefas | `Backlog_Sprint_e_Quadro.xlsx` |
| Modelo de Casos de Uso | `Modelo_Casos_de_Uso.md` |
| Protótipo | `Prototipo.md` |
| Modelo de Arquitetura | `Modelo_Arquitetura.md` |
| Checklist de Projeto | `Checklist_de_Projeto.md` |
| Issues, labels e milestones para o GitHub | `Issues_GitHub.md` |

Os arquivos `Template - *` são os modelos do SpinOff. A pasta `Documentos/docx/` tem as versões em Word dos documentos em Markdown.

## Como contribuir

Veja o [CONTRIBUTING.md](CONTRIBUTING.md) para o fluxo de branches e commits. O acompanhamento das tarefas é feito em Issues e Projects.

## Como rodar

A ser adicionado quando o projeto Android estiver no repositório.
