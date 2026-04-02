# Analise de Projetos - Base para Slides

## 1. Tema do Projeto

Sistema de Gerenciamento de Projetos API

Este projeto foi desenvolvido para apoiar o planejamento, organizacao, acompanhamento e controle de projetos por meio de uma API REST. A solucao centraliza informacoes sobre projetos, equipe, atividades, riscos, recursos e custos.

## 2. Problema Identificado

Em muitos contextos, o gerenciamento de projetos acontece de forma descentralizada, com informacoes espalhadas em planilhas, mensagens, documentos e controles manuais. Isso gera problemas como:

- dificuldade para acompanhar o andamento real do projeto
- falta de visibilidade sobre equipe e responsabilidades
- controle insuficiente de custos e recursos
- baixa rastreabilidade de riscos
- dificuldade para consolidar informacoes para tomada de decisao

## 3. Solucao Proposta

A proposta do sistema e fornecer um backend estruturado para gerenciamento de projetos, permitindo:

- cadastrar projetos e acompanhar seu ciclo de vida
- vincular usuarios e participantes a cada projeto
- organizar atividades e responsaveis
- controlar recursos utilizados
- registrar custos previstos e realizados
- monitorar riscos e acoes de mitigacao

## 4. Objetivo Geral

Desenvolver uma API REST para gerenciamento de projetos, capaz de estruturar as informacoes essenciais de execucao, equipe, custos, recursos e riscos, apoiando o acompanhamento e a tomada de decisao.

## 5. Objetivos Especificos

- permitir o cadastro e manutencao de projetos
- organizar os participantes vinculados a cada projeto
- registrar atividades e acompanhar sua execucao
- controlar recursos e custos
- identificar e acompanhar riscos do projeto
- oferecer uma base de dados consistente para futuras expansoes, como dashboards e relatorios

## 6. Area de Analise de Projetos

Sob a perspectiva de Analise de Projetos, este sistema atua em pontos essenciais da gestao:

- definicao do escopo basico do projeto
- acompanhamento de prazo por atividades
- controle de custo planejado e realizado
- organizacao de responsabilidades
- classificacao e acompanhamento de riscos
- monitoramento do progresso por percentual de conclusao

Ou seja, o sistema nao e apenas um cadastro de informacoes. Ele foi pensado para apoiar analise, controle e visibilidade do projeto.

## 7. Stakeholders Envolvidos

Os principais interessados no sistema sao:

- gerente de projeto
- coordenador de equipe
- membros executores
- administradores do sistema
- stakeholders que precisam acompanhar a evolucao do projeto

Esses perfis aparecem no sistema principalmente por meio de:

- `PerfilUsuario`
- `PapelAcesso`

## 8. Principais Necessidades do Negocio

Durante a analise do problema, as principais necessidades identificadas foram:

- ter um cadastro central de projetos
- saber quem participa de cada projeto
- acompanhar atividades por status, prioridade e responsavel
- visualizar custos previstos e custos reais
- controlar recursos do projeto
- registrar riscos com nivel de criticidade

## 9. Escopo Funcional da Solucao

O sistema foi dividido em modulos de negocio:

### 9.1 Projetos

Responsavel pelo cadastro do projeto e por atributos como:

- nome
- descricao
- objetivo
- status
- prioridade
- datas
- orcamento previsto
- percentual concluido

### 9.2 Usuarios

Representa as pessoas cadastradas no sistema, com:

- nome
- email
- senha
- telefone
- perfil

### 9.3 Participantes

Relaciona um usuario a um projeto e informa:

- funcao no projeto
- papel de acesso
- situacao ativa

### 9.4 Atividades

Representa a execucao do trabalho do projeto:

- titulo
- descricao
- status
- prioridade
- prazo
- percentual de conclusao
- responsavel

### 9.5 Recursos

Representa os recursos usados pelo projeto:

- nome
- tipo
- quantidade
- custo unitario

### 9.6 Custos

Permite acompanhar:

- valor previsto
- valor real
- data de lancamento
- vinculacao opcional a atividade
- vinculacao opcional a recurso

### 9.7 Riscos

Permite analisar riscos com:

- categoria
- probabilidade
- impacto
- criticidade
- status
- estrategia de resposta
- plano de mitigacao

## 10. Visao Analitica das Entidades

Na perspectiva de analise de projetos, o sistema foi modelado para responder perguntas importantes:

- Qual e o estado atual do projeto
- Quem participa de cada projeto
- Quais atividades estao em andamento ou atrasadas
- Qual o percentual de progresso
- Quais recursos estao sendo usados
- Onde os custos reais ultrapassaram os previstos
- Quais riscos exigem maior atencao

## 11. Relacionamentos que Fazem Sentido para a Analise

### Projeto como entidade central

Todas as demais estruturas orbitam o projeto:

- um projeto possui participantes
- um projeto possui atividades
- um projeto possui recursos
- um projeto possui custos
- um projeto possui riscos

### Participante como elo entre pessoas e execucao

O participante conecta o usuario ao projeto e tambem permite indicar o responsavel por atividades.

### Custo como elemento de controle

O custo pode ser associado:

- ao projeto diretamente
- a uma atividade especifica
- a um recurso especifico

Isso amplia a capacidade de analise financeira.

## 12. Regras Importantes para a Analise

Algumas regras do sistema sao importantes porque garantem consistencia analitica:

- toda atividade deve pertencer a um projeto
- todo participante deve estar ligado a um usuario e a um projeto
- todo risco deve pertencer a um projeto
- todo recurso deve pertencer a um projeto
- todo custo deve pertencer a um projeto
- percentuais nao podem ultrapassar 100
- valores monetarios nao podem ser negativos
- probabilidade e impacto do risco possuem escala limitada

Essas regras evitam informacoes incoerentes na base do sistema.

## 13. Requisitos Funcionais Mais Relevantes para os Slides

Os requisitos que mais representam a proposta analitica do projeto sao:

- cadastrar e manter projetos
- vincular participantes aos projetos
- cadastrar e acompanhar atividades
- registrar custos planejados e realizados
- cadastrar e acompanhar riscos
- registrar recursos utilizados no projeto

## 14. Requisitos Nao Funcionais Mais Importantes

- a API deve operar com arquitetura REST e trafego em JSON
- os dados devem ser persistidos em banco relacional
- o sistema deve validar os dados recebidos
- a estrutura deve permitir manutencao e evolucao futura

## 15. Diferenciais Analiticos do Projeto

Este projeto se destaca porque integra varias dimensoes da gestao em uma mesma base:

- andamento do projeto
- equipe envolvida
- execucao das atividades
- custos
- recursos
- riscos

Isso cria uma estrutura adequada para evolucoes futuras como:

- indicadores de desempenho
- dashboards gerenciais
- relatorios consolidados
- alertas de risco
- comparacao entre custo previsto e custo real

## 16. Possiveis Indicadores que o Sistema Pode Gerar no Futuro

Mesmo que ainda nao estejam implementados, o modelo atual ja permite gerar indicadores como:

- percentual medio de conclusao por projeto
- quantidade de atividades por status
- custo total previsto por projeto
- custo total realizado por projeto
- variacao entre custo previsto e custo real
- quantidade de riscos por criticidade
- quantidade de participantes por projeto
- volume de recursos por tipo

## 17. Visao de Banco de Dados para Slides

Tabelas principais:

- `projetos`
- `usuarios`
- `participantes`
- `atividades`
- `recursos`
- `custos`
- `riscos`

Relacionamentos principais:

- `usuarios 1:N participantes`
- `projetos 1:N participantes`
- `projetos 1:N atividades`
- `participantes 1:N atividades`
- `projetos 1:N recursos`
- `projetos 1:N custos`
- `atividades 1:N custos`
- `recursos 1:N custos`
- `projetos 1:N riscos`

## 18. Arquitetura como Ponto de Analise

O projeto foi estruturado em camadas:

- `controller`
- `service`
- `repository`
- `entity`
- `dto`
- `enums`

Essa divisao e importante para a analise porque demonstra:

- separacao de responsabilidades
- organizacao do codigo
- facilidade de manutencao
- facilidade de testes
- possibilidade de evolucao do sistema

## 19. Conclusao Analitica

Do ponto de vista de Analise de Projetos, a solucao atende a uma necessidade real de organizacao e monitoramento. O sistema transforma elementos dispersos da gestao de projetos em uma estrutura unica, consistente e preparada para consulta, acompanhamento e evolucao futura.

O projeto entrega uma base tecnica e conceitual adequada para:

- controle operacional
- apoio gerencial
- rastreabilidade
- ampliacao futura para relatorios e indicadores

## 20. Sugestao de Estrutura de Slides

### Slide 1 - Titulo

- Sistema de Gerenciamento de Projetos API
- Analise de Projetos

### Slide 2 - Problema

- falta de centralizacao
- dificuldade de acompanhamento
- falhas no controle de custos, riscos e equipe

### Slide 3 - Solucao

- API REST para centralizar a gestao do projeto

### Slide 4 - Objetivos

- objetivo geral
- objetivos especificos

### Slide 5 - Modulos do Sistema

- projetos
- usuarios
- participantes
- atividades
- recursos
- custos
- riscos

### Slide 6 - Modelagem do Banco

- tabelas principais
- relacionamentos

### Slide 7 - Visao Analitica

- controle de prazo
- controle de custo
- controle de risco
- controle de equipe

### Slide 8 - Requisitos

- principais requisitos funcionais
- principais requisitos nao funcionais

### Slide 9 - Beneficios

- organizacao
- rastreabilidade
- base para dashboards
- apoio a decisao

### Slide 10 - Conclusao

- visao final do valor do projeto

## 21. Frases Curtas para Usar na Apresentacao

- O projeto foi pensado para centralizar a gestao de informacoes de um projeto.
- A modelagem permite acompanhar execucao, equipe, custos, recursos e riscos.
- O sistema cria uma base consistente para analise e tomada de decisao.
- A estrutura em camadas facilita manutencao, testes e evolucao.
- O banco de dados foi modelado para refletir o ciclo real de um projeto.
