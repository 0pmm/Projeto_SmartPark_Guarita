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

- `Documentos/`: artefatos do SpinOff (TAP, atas, modelos)

## Como rodar

A ser adicionado quando o projeto Android estiver no repositório.
