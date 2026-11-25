# Termo de Abertura do Projeto (TAP)

**SPG - SmartPark Guarita**
Versão 1 (rascunho, a revisar depois da entrevista com o cliente)

## Histórico de revisões

| Data | Versão | Descrição | Autor |
|---|---|---|---|
| 15/11/2026 | 0.1 | Versão inicial: integrantes da equipe e breve descrição da solução | Equipe SmartPark Guarita |
| 25/11/2026 | 1.0 (rascunho) | Inclusão de um integrante na equipe, ajuste de papéis, riscos e premissas, correção das datas de início e remoção da referência a temas numerados que não constam no enunciado. Escopo a confirmar na entrevista | Gustavo Silva Guimarães de Brito |

---

## 1. Introdução

Este documento oficializa a abertura do projeto SmartPark Guarita (SPG), desenvolvido pela equipe Pedro Henrique Modesto Marchiotto, Lucas Verissimo Carvalho, Gustavo Carvalho, Vitor Saviolli Gonsalez e Gustavo Silva Guimarães de Brito, como projeto final de Programação de Dispositivos Móveis (PDIM, BCC, 2026-2).

### 1.1. Referências

- Enunciado "SmartPark - Sistema de Gestão e Monitoramento do Estacionamento", Projeto Final de PDIM, BCC, 2026-2 (Moodle)
- SpinOff, processo de desenvolvimento ágil: http://arum.tec.br/SpinOff/index.htm
- Lei nº 13.709/2018, Lei Geral de Proteção de Dados Pessoais (LGPD)

## 2. Objetivos do projeto

Desenvolver o SmartPark Guarita, um aplicativo Android para o tablet da portaria que substitui a planilha compartilhada e as credenciais físicas no controle do estacionamento do câmpus. O app reúne quatro perfis:

- **Porteiro:** consulta a placa e registra a entrada e a saída do veículo.
- **Administrador:** cadastra servidores, veículos e trocas temporárias com data de validade, e aprova as solicitações dos servidores.
- **Servidor/Professor:** solicita uma alteração temporária quando vier com outro veículo.
- **Painel:** mostra a ocupação do estacionamento por turno.

Com isso, o projeto pretende:

- tornar a conferência de uma placa rápida, sem planilha ou lista impressa;
- permitir que o servidor peça uma troca temporária e que ela expire sozinha ao fim do período;
- dar à administração números sobre a lotação por turno e o uso das vagas;
- aplicar os conteúdos da disciplina: Kotlin, Jetpack Compose, persistência com Room, validação de formulários, coleções, orientação a objetos e tratamento de exceções.

## 3. Solicitante

Institutos Federais, administração do câmpus, responsável pela portaria e pelo estacionamento de docentes e servidores. O cliente é representado pelo Prof. Fabio Santos, que atua como Product Owner: responde à entrevista por e-mail e aceita as entregas.

## 4. Premissas

- A portaria dispõe de um tablet Android para uso exclusivo do aplicativo.
- O Product Owner responderá às perguntas da entrevista por e-mail; o que não for perguntado será registrado como suposição da equipe.
- Os turnos (manhã, tarde e noite) e seus horários serão confirmados na entrevista.
- Se a solicitação do servidor precisa de aprovação do administrador, e por qual aparelho ele acessa o app, será confirmado na entrevista.
- A carga inicial de servidores e veículos é feita pelo perfil Administrador; nos testes e na demonstração serão usados apenas dados fictícios.
- Todos os integrantes têm acesso ao Android Studio e ao Moodle durante o projeto.

## 5. Restrições

- Entregas fixas pelo Moodle: TAP 0.1 em 13/11, Etapas 1 e 2 em 20/11, Etapa 3 em 27/11 e apresentação em 04/12/2026.
- Tecnologia obrigatória: Android Studio, Kotlin, Jetpack Compose e persistência de dados (Room).
- Equipe de cinco integrantes com dedicação parcial e sem orçamento: apenas ferramentas gratuitas.
- Processo SpinOff, com os documentos feitos a partir dos modelos do site.

### 5.1. Requisitos regulamentares e estatutários

O aplicativo trata dados pessoais (nome do servidor, placa e dados do veículo, horários de entrada e saída) e deve observar a LGPD: coletar só o necessário, restringir o acesso por perfil e guardar o histórico apenas pelo tempo definido pelo cliente. Devem ser respeitadas também as normas internas do câmpus sobre o uso do estacionamento. As regras de ética da disciplina se aplicam: código e documentos feitos pela equipe, fontes citadas e dados fictícios nos testes.

## 6. Escopo

**Requisitos do negócio:** controlar quais veículos podem entrar no câmpus, registrar entradas e saídas, manter o cadastro atualizado mesmo com trocas temporárias e informar a ocupação do estacionamento por turno.

**Funcionalidades identificadas (a confirmar na entrevista):**

- **Porteiro:** busca por placa com resultado imediato (autorizado, não autorizado ou troca temporária válida); registro de entrada e saída com data, hora e porteiro; lista de veículos presentes.
- **Administrador:** cadastro, edição e exclusão de servidores e veículos, com validação de formulários; cadastro de trocas temporárias com início e fim de validade; aprovação ou recusa das solicitações dos servidores.
- **Servidor/Professor:** solicitação de alteração temporária informando placa, modelo, cor, motivo e período; acompanhamento do pedido (pendente, aprovado ou recusado).
- **Painel:** ocupação atual e entradas e saídas por turno.

**Arquitetura candidata:** aplicativo Android nativo em Kotlin com interface em Jetpack Compose, organizado em camadas (telas, ViewModel e repositório) e persistência local com Room (SQLite) no próprio tablet. O perfil é escolhido na entrada do app. Detalhes em `Modelo_Arquitetura.md`.

### 6.1. Exclusões do escopo do projeto

- Leitura automática de placas por câmera (OCR/LPR) e acionamento de cancela.
- Controle de visitantes, prestadores de serviço e alunos.
- Servidor na nuvem, sincronização entre aparelhos e integração com sistemas institucionais.
- Migração de dados: não haverá importação da planilha atual; os cadastros são feitos no próprio app.
- Versão para iOS e publicação na Play Store.

## 7. Envolvidos no projeto

### 7.1. Equipe técnica

| Nome | Função/Papel | Alocação no projeto |
|---|---|---|
| Pedro Henrique Modesto Marchiotto | Scrum Master e Desenvolvedor | Todo o projeto (13/11 a 04/12/2026), dedicação parcial |
| Lucas Verissimo Carvalho | Analista de Requisitos e Desenvolvedor (testes) | Todo o projeto, dedicação parcial |
| Gustavo Carvalho | Desenvolvedor: interface (Jetpack Compose) e protótipo | Todo o projeto, dedicação parcial |
| Vitor Saviolli Gonsalez | Desenvolvedor: persistência (Room) e regras de negócio | Todo o projeto, dedicação parcial |
| Gustavo Silva Guimarães de Brito | Desenvolvedor: documentação (SpinOff), ViewModel e repositório, Painel, testes e demonstração | Todo o projeto, dedicação parcial |

### 7.2. Equipe do cliente

| Nome | Função/Papel | Área | Contato |
|---|---|---|---|
| Prof. Fabio Santos | Cliente e Product Owner | IFSP, disciplina PDIM (BCC) | fabio.santos@ifsp.edu.br |
| A definir na entrevista | Usuário-chave: porteiro/vigilante | Portaria/guarita do câmpus | Via Product Owner |

### 7.3. Infraestrutura

- Computadores com Android Studio e emulador Android para desenvolvimento e testes.
- Tablet ou celular Android para a demonstração.
- Repositório Git no GitHub para o código-fonte e os documentos; Moodle para as entregas; e-mail institucional para a entrevista.
- Quadro de tarefas e canal de comunicação da equipe (WhatsApp).

## 8. Riscos iniciais

| Risco | Impacto | Ação de mitigação |
|---|---|---|
| Requisitos mal entendidos, pois a entrevista é só por e-mail | Alto | Perguntas numeradas, rodadas de esclarecimento e registro das suposições na Ata de Reunião |
| Prazo curto: três sprints de uma semana | Alto | Escopo enxuto e priorizado no Backlog do Produto; entregar primeiro o fluxo do porteiro |
| Pouca experiência com Jetpack Compose e Room | Médio | Aproveitar os exemplos das aulas e dividir o estudo entre os integrantes |
| Indisponibilidade de integrantes por outras disciplinas | Médio | Tarefas pequenas no Quadro de Tarefas e conhecimento compartilhado do código |
| Exposição de dados pessoais (nomes e placas) | Médio | Acesso por perfil e uso apenas de dados fictícios em testes e na demonstração |
| Escopo crescer (visitantes, câmeras, cancela) | Médio | Manter as exclusões deste termo e negociar mudanças com o Product Owner |

## 9. Expectativa de início do projeto

Início em 13/11/2026, data prevista para a entrega do TAP 0.1 (a versão 0.1 do documento está datada de 15/11/2026). Etapa 1 (planejamento) e Etapa 2 (primeiro incremento) em 20/11/2026; Etapa 3 (produto final) em 27/11/2026; apresentação em 04/12/2026.

## 10. Custos iniciais estimados

Sem custo financeiro: Android Studio, Kotlin, Jetpack Compose, Room e as demais ferramentas são gratuitos, e a demonstração usa emulador ou aparelho próprio. O custo do projeto é o esforço dos cinco integrantes ao longo das quatro semanas.

## 11. Observações

O SmartPark Guarita reúne, em um único aplicativo com perfis (Porteiro, Administrador, Servidor/Professor e Painel), o controle de acesso na portaria, o cadastro com trocas temporárias e o monitoramento da ocupação. O TAP 0.1 citava "temas 1, 2 e 4 sugeridos para o projeto", mas o enunciado consultado não traz temas numerados; a equipe deve confirmar esse ponto com o professor. Esta é a versão 1 do termo, ainda em rascunho: escopo, papéis e funcionalidades serão confirmados depois da entrevista com o cliente.
