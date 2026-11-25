# Visão

**SPG - SmartPark Guarita**
Versão 0.1 (rascunho, a revisar depois da entrevista com o cliente)

## Histórico de revisões

| Data | Versão | Descrição | Autor |
|---|---|---|---|
| 25/11/2026 | 0.1 | Versão inicial, a partir do enunciado e do TAP 0.1 | Gustavo Silva Guimarães de Brito |

---

## 1. Introdução

### 1.1. Resumo do negócio

Nos Institutos Federais, o acesso de veículos de docentes e servidores ao câmpus é controlado por uma planilha compartilhada. O servidor informa modelo, cor e placa e recebe depois uma credencial física, como cartão ou adesivo, para a portaria identificar o veículo.

Na prática, a planilha fica desatualizada: há trocas temporárias de veículo, carros alugados e o uso eventual do carro de um familiar. Isso afeta a portaria, os servidores e a administração do câmpus.

### 1.2. Objetivo do sistema

O SmartPark Guarita é um aplicativo Android para o tablet da portaria que substitui a planilha e as credenciais físicas no controle do estacionamento. Ele permite:

- conferir uma placa de forma rápida e registrar a entrada e a saída do veículo;
- manter o cadastro de servidores e veículos atualizado, inclusive com trocas temporárias que expiram sozinhas;
- dar à administração números sobre a ocupação do estacionamento por turno.

### 1.3. Glossário

| Termo | Definição |
|---|---|
| Guarita / portaria | Local de controle de acesso do câmpus, onde o porteiro confere os veículos |
| Servidor | Docente ou técnico-administrativo do câmpus que usa o estacionamento |
| Porteiro | Pessoa que trabalha na guarita e registra entradas e saídas |
| Administrador | Pessoa responsável por cadastros e aprovações no app |
| Troca temporária | Autorização para um servidor entrar com outro veículo durante um período definido |
| Turno | Período do dia (manhã, tarde ou noite) usado para medir a ocupação. Os horários serão confirmados na entrevista |
| Perfil | Papel do usuário no app: Porteiro, Administrador, Servidor/Professor ou Painel |
| Placa | Identificação do veículo, no formato antigo (ABC-1234) ou Mercosul (ABC1D23) |
| Product Owner (PO) | Representante do cliente no processo ágil; neste projeto, o Prof. Fabio Santos |
| Sprint | Ciclo de trabalho de uma semana neste projeto |
| Room | Biblioteca Android de persistência sobre SQLite |
| Jetpack Compose | Biblioteca Android para construir a interface em Kotlin |
| LGPD | Lei Geral de Proteção de Dados Pessoais (Lei nº 13.709/2018) |

### 1.4. Referências

- Enunciado "SmartPark - Sistema de Gestão e Monitoramento do Estacionamento", Projeto Final de PDIM, BCC, 2026-2 (Moodle)
- Termo de Abertura do Projeto, versão 0.1, 15/11/2026 (`TAP_SmartPark_Guarita_v0.1.pdf`)
- SpinOff, processo de desenvolvimento ágil: http://arum.tec.br/SpinOff/index.htm
- Lei nº 13.709/2018 (LGPD)

---

## 2. Problema

| Problema | Afetados | Impacto | Necessidades (escopo) |
|---|---|---|---|
| Conferir uma placa na portaria é lento: é preciso abrir a planilha ou procurar numa lista impressa | Porteiro | Fila de veículos na entrada; risco de liberar ou barrar um veículo por engano | Como porteiro, eu quero digitar uma placa e ver na hora se o veículo está autorizado, de modo que eu não precise abrir planilha nem lista impressa (US01) |
| O cadastro é rígido: o servidor que vem com outro veículo não consegue atualizá-lo com rapidez | Servidores, administração | Servidor com veículo diferente é tratado como desconhecido; o administrador precisa mexer na planilha | Como servidor, eu quero solicitar uma troca temporária informando placa, modelo, cor, motivo e período, de modo que eu entre com outro veículo sem mexer no cadastro (US09). Como administrador, eu quero cadastrar e aprovar trocas temporárias com validade (US07, US08) |
| Não há informação sobre a ocupação: ninguém sabe se o estacionamento lota em certos dias ou turnos | Administração do câmpus | Decisões sobre vagas sem dados | Como administrador, eu quero ver a ocupação atual e as entradas e saídas por turno, de modo que eu saiba quando o estacionamento lota (US11, US12) |
| Credenciais físicas desbotam, caem ou são esquecidas | Servidores, porteiro | Veículo autorizado barrado ou identificado com dificuldade | Como porteiro, eu quero identificar o veículo pela placa, de modo que a credencial física deixe de ser necessária (US01, US02) |

---

## 3. Usuários

| Nome | Responsável/Cargo | Responsabilidades |
|---|---|---|
| Prof. Fabio Santos | Cliente e Product Owner (IFSP, disciplina PDIM) | Responder à entrevista por e-mail e aceitar as entregas |
| A definir na entrevista | Porteiro/vigilante | Consultar placas e registrar entradas e saídas na guarita |
| A definir na entrevista | Administrador do câmpus | Cadastrar servidores, veículos e trocas temporárias; aprovar solicitações |
| Servidores e docentes | Usuários do estacionamento | Solicitar trocas temporárias quando vierem com outro veículo |

---

## 4. Restrições impostas

- Aplicativo Android nativo, em Kotlin e Jetpack Compose, com persistência de dados em Room.
- Uso previsto em um tablet Android na portaria. O aparelho do servidor será confirmado na entrevista.
- Dados guardados localmente no aparelho, sem servidor na nuvem nem sincronização.
- Prazos fixos: Etapas 1 e 2 em 20/11, Etapa 3 em 27/11 e apresentação em 04/12/2026.
- Equipe pequena, com dedicação parcial e sem orçamento: apenas ferramentas gratuitas.
- Respeito à LGPD: coletar só o necessário, restringir o acesso por perfil e guardar o histórico apenas pelo tempo definido pelo cliente.
- Testes e demonstração somente com dados fictícios.
- Processo SpinOff, com os documentos feitos a partir dos modelos do site.

---

## 5. Riscos

| Risco | Impacto | Ação de mitigação |
|---|---|---|
| Requisitos mal entendidos, pois a entrevista é só por e-mail | Alto | Perguntas numeradas, rodadas de esclarecimento e registro das suposições na Ata |
| Prazo curto: três sprints de uma semana | Alto | Escopo enxuto e priorizado no Backlog do Produto; entregar primeiro o fluxo do porteiro |
| Pouca experiência com Jetpack Compose e Room | Médio | Aproveitar os exemplos das aulas e dividir o estudo entre os integrantes |
| Indisponibilidade de integrantes por outras disciplinas | Médio | Tarefas pequenas no Quadro de Tarefas e conhecimento compartilhado do código |
| Exposição de dados pessoais (nomes e placas) | Médio | Acesso por perfil e uso apenas de dados fictícios |
| Crescimento do escopo (visitantes, câmeras, cancela) | Médio | Manter as exclusões do TAP e negociar mudanças com o Product Owner |
| Demora na resposta do cliente à entrevista | Médio | Trabalhar com as suposições registradas e ajustar quando a resposta chegar |

---

## 6. Requisitos de documentação

- Relatório de código, no formato dos relatórios semanais da disciplina (etapas numeradas, blocos de código, teste sugerido, "O que aconteceu?" e prints)
- Projeto do Android Studio compactado (.zip) e o executável (.apk)
- Roteiro de testes: os testes de aceitação de cada história de usuário (`Historias_de_Usuario.md`)
- Checklist de Projeto
- Manual de uso resumido do aplicativo, se o cliente pedir (a confirmar na entrevista)
