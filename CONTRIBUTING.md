## Antes de começar a trabalhar

1. Abra o GitHub Desktop e clique em **Fetch origin** e depois em **Pull origin** para trazer o que os outros fizeram.
2. Confira a sua branch. Não trabalhe direto na `main` se a mudança for grande.

## Branches

Uma branch por pessoa ou por funcionalidade, com o nome no formato `nome/assunto`:

- `gustavob/painel`
- `gustavoc/telas-compose`
- `vitor/room-entidades`
- `pedro/projeto-base`
- `lucas/testes-us01`

Como há dois Gustavos na equipe, use `gustavob` para Gustavo Silva Guimarães de Brito e `gustavoc` para Gustavo Carvalho.

Para mudanças pequenas de documentação, pode-se commitar direto na `main`, desde que cada pessoa mexa em arquivos diferentes.

## Commits

- Pequenos e frequentes.
- Mensagem em português, no imperativo, dizendo o que mudou: `Adiciona tela de consulta de placa`, `Corrige validação da placa`.
- Só commite código que compila.

## Juntar na `main`

1. Dê pull da `main` na sua branch para pegar as novidades.
2. Confirme que o app compila.
3. Abra um Pull Request para a `main` e peça a revisão de alguém da equipe, ou faça o merge se for uma mudança pequena e combinada.

## O que não subir

O `.gitignore` já ignora `build/`, `.gradle/`, `.idea/`, `local.properties`, chaves de assinatura e arquivos `.apk`. Nunca suba senhas, chaves nem dados reais de pessoas ou veículos. Nos testes e na demonstração, use apenas dados fictícios.

## Onde está cada coisa

| Para quê | Onde |
|---|---|
| Histórias de usuário e testes de aceitação | `Documentos/Historias_de_Usuario.md` |
| Backlog do Produto | `Documentos/Backlog_do_Produto.xlsx` |
| Backlog do Sprint e Quadro de Tarefas | `Documentos/Backlog_Sprint_e_Quadro.xlsx` |
| Arquitetura e modelo de dados | `Documentos/Modelo_Arquitetura.md` |
| Protótipo das telas | `Documentos/Prototipo.md` |

## Acompanhamento

As tarefas do sprint ficam em **Issues** e no quadro em **Projects**, com os marcos (milestones) Sprint 2, Sprint 3 e Apresentação. Os textos prontos para criar as issues estão em `Documentos/Issues_GitHub.md`.
