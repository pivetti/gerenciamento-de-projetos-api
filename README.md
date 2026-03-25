# Gerenciamento de Projetos API

API REST desenvolvida com Spring Boot para apoiar o gerenciamento de projetos, cobrindo cadastro de usuarios, projetos, participantes, atividades, estrutura analitica do projeto, atribuicoes RACI, recursos, custos, riscos e checklist de qualidade.

## Objetivo

O projeto modela o fluxo de gestao de um projeto do inicio ao acompanhamento operacional:

1. cadastrar usuarios do sistema
2. criar um projeto
3. vincular participantes ao projeto
4. organizar entregas na EAP
5. cadastrar atividades e responsaveis
6. definir papeis RACI
7. controlar recursos e custos
8. mapear riscos
9. acompanhar criterios de qualidade

## Stack utilizada

- Java 21
- Spring Boot 4.0.3
- Spring Web
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Maven

## Arquitetura

O projeto segue uma estrutura classica em camadas:

- `controller`: expoe os endpoints REST e devolve `ResponseEntity`
- `service`: concentra a logica de aplicacao, preenchimento de entidades e conversao para DTO
- `repository`: interfaces JPA para persistencia
- `entity`: modelo relacional da aplicacao
- `dto`: contratos de entrada e saida da API
- `exception`: tratamento padronizado de erros
- `enums`: valores fechados usados pelo dominio

Fluxo de uma requisicao:

1. o cliente chama um endpoint HTTP
2. o controller recebe o body e valida com `@Valid`
3. o service monta ou atualiza a entidade
4. ids relacionados sao resolvidos pelo `EntityLookupService`
5. o repository persiste os dados no PostgreSQL
6. o service devolve um DTO de resposta
7. erros de validacao viram `400` e entidades nao encontradas viram `404`

## Estrutura do dominio

### 1. Usuario

Representa uma pessoa cadastrada no sistema.

Campos principais:

- `id`
- `nome`
- `email`
- `senha`
- `telefone`
- `perfil`

Observacao:

- um usuario pode participar de varios projetos por meio da entidade `Participante`

### 2. Projeto

Entidade central da aplicacao.

Campos principais:

- `id`
- `nome`
- `descricao`
- `objetivo`
- `status`
- `prioridade`
- `dataInicio`
- `dataFim`
- `orcamentoPrevisto`
- `percentualConcluido`

Relacionamentos:

- possui participantes
- possui itens da EAP
- possui atividades
- possui riscos
- possui recursos
- possui custos
- possui checklists de qualidade

### 3. Participante

Faz a ligacao entre um usuario e um projeto.

Campos principais:

- `id`
- `usuario`
- `projeto`
- `funcaoNoProjeto`
- `papelAcesso`
- `ativo`

Uso no fluxo:

- permite definir quem faz parte do projeto
- pode ser responsavel por atividades
- pode receber atribuicoes RACI

### 4. EapItem

Representa itens da Estrutura Analitica do Projeto.

Campos principais:

- `id`
- `nome`
- `descricao`
- `codigo`
- `nivel`
- `projeto`
- `itemPai`

Uso no fluxo:

- organiza o projeto em pacotes ou entregas
- pode ter hierarquia pai e filho
- pode ser associado a atividades

### 5. Atividade

Representa tarefas executaveis do projeto.

Campos principais:

- `id`
- `titulo`
- `descricao`
- `status`
- `prioridade`
- `dataInicio`
- `prazo`
- `dataConclusao`
- `percentualConclusao`
- `projeto`
- `eapItem`
- `responsavel`

Uso no fluxo:

- controla andamento operacional do projeto
- pode ter responsavel definido por `participante`
- pode receber custo, checklist e atribuicao RACI

### 6. RaciAssignacao

Define papeis RACI em uma atividade.

Campos principais:

- `id`
- `atividade`
- `participante`
- `papelRaci`

Uso no fluxo:

- indica quem e responsavel, aprovador, consultado ou informado

### 7. Recurso

Representa recursos usados no projeto.

Campos principais:

- `id`
- `nome`
- `tipo`
- `descricao`
- `quantidade`
- `custoUnitario`
- `projeto`

Uso no fluxo:

- registra insumos humanos, materiais, tecnologicos, financeiros ou servicos
- pode ser associado a custos

### 8. Custo

Registra valores previstos e reais.

Campos principais:

- `id`
- `descricao`
- `tipo`
- `valorPrevisto`
- `valorReal`
- `dataLancamento`
- `projeto`
- `atividade`
- `recurso`

Uso no fluxo:

- pode ser geral do projeto
- pode ser vinculado a uma atividade
- pode ser vinculado a um recurso

### 9. Risco

Representa riscos do projeto.

Campos principais:

- `id`
- `titulo`
- `descricao`
- `categoria`
- `probabilidade`
- `impacto`
- `criticidade`
- `status`
- `estrategiaResposta`
- `planoMitigacao`
- `projeto`

Uso no fluxo:

- registra ameacas ou situacoes de risco
- apoia acompanhamento de tratamento e mitigacao

### 10. ChecklistQualidade

Registra verificacoes de qualidade.

Campos principais:

- `id`
- `descricao`
- `criterio`
- `status`
- `dataVerificacao`
- `observacoes`
- `projeto`
- `atividade`

Uso no fluxo:

- permite validar criterios de aceite
- pode ser geral do projeto ou ligado a uma atividade

## Relacao entre as entidades

Fluxo recomendado de criacao:

1. `Usuario`
2. `Projeto`
3. `Participante`
4. `EapItem`
5. `Atividade`
6. `RaciAssignacao`
7. `Recurso`
8. `Custo`
9. `Risco`
10. `ChecklistQualidade`

Isso acontece porque varias entidades dependem do `id` de outras ja existentes.

## Endpoints disponiveis

Todos os recursos seguem o mesmo padrao:

- `GET /recurso`
- `GET /recurso/{id}`
- `POST /recurso`
- `PUT /recurso/{id}`
- `DELETE /recurso/{id}`

Recursos expostos:

- `/usuarios`
- `/projetos`
- `/participantes`
- `/eap-itens`
- `/atividades`
- `/raci`
- `/recursos`
- `/custos`
- `/riscos`
- `/checklists-qualidade`

## Validacoes

Os DTOs usam Bean Validation para garantir consistencia dos dados de entrada.

Exemplos de validacao ja implementados:

- campos obrigatorios com `@NotBlank` e `@NotNull`
- tamanhos maximos com `@Size`
- email valido com `@Email`
- limites numericos com `@Positive`, `@PositiveOrZero` e `@Max`

Exemplos praticos:

- `UsuarioRequestDto` exige nome, email valido, senha com minimo de 6 caracteres e perfil
- `ProjetoRequestDto` exige status, prioridade e percentual concluido de no maximo 100
- `RiscoRequestDto` limita probabilidade e impacto a 5 e criticidade a 25

## Tratamento de erros

O projeto possui tratamento global de excecoes.

### 400 Bad Request

Quando o body falha nas validacoes do DTO, a API responde com mensagem padronizada:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Dados de entrada invalidos",
  "details": [
    "campo: mensagem de erro"
  ]
}
```

### 404 Not Found

Quando um `id` nao existe no banco, a API responde com erro padronizado:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Projeto nao encontrado com id 999"
}
```

## Enums do dominio

### Status do projeto

- `PLANEJADO`
- `EM_ANDAMENTO`
- `PAUSADO`
- `CONCLUIDO`
- `CANCELADO`

### Prioridade

- `BAIXA`
- `MEDIA`
- `ALTA`
- `CRITICA`

### Perfil do usuario

- `ADMINISTRADOR`
- `GERENTE_PROJETO`
- `ANALISTA`
- `MEMBRO_EQUIPE`
- `STAKEHOLDER`

### Papel de acesso do participante

- `ADMINISTRADOR_PROJETO`
- `COORDENADOR`
- `EXECUTOR`
- `VISUALIZADOR`

### Status da atividade

- `NAO_INICIADA`
- `EM_ANDAMENTO`
- `BLOQUEADA`
- `CONCLUIDA`
- `CANCELADA`

### Papel RACI

- `RESPONSAVEL`
- `APROVADOR`
- `CONSULTADO`
- `INFORMADO`

### Categoria do risco

- `ESCOPO`
- `PRAZO`
- `CUSTO`
- `QUALIDADE`
- `RECURSOS`
- `TECNOLOGIA`
- `COMUNICACAO`
- `EXTERNO`

### Status do risco

- `IDENTIFICADO`
- `EM_ANALISE`
- `EM_TRATAMENTO`
- `MITIGADO`
- `ENCERRADO`

### Tipo de recurso

- `HUMANO`
- `MATERIAL`
- `TECNOLOGICO`
- `FINANCEIRO`
- `SERVICO`

### Tipo de custo

- `PLANEJADO`
- `OPERACIONAL`
- `AQUISICAO`
- `RH`
- `IMPREVISTO`

### Status do checklist

- `PENDENTE`
- `APROVADO`
- `REPROVADO`
- `NAO_APLICAVEL`

## Exemplo de fluxo de uso

Exemplo resumido de operacao:

1. criar um usuario para representar um gerente
2. criar um projeto com status e prioridade
3. vincular o usuario ao projeto como participante
4. criar um item da EAP
5. criar uma atividade apontando para o projeto, item da EAP e responsavel
6. definir uma atribuicao RACI para a atividade
7. registrar um recurso
8. registrar um custo relacionado ao recurso e a atividade
9. registrar um risco do projeto
10. criar um checklist de qualidade para acompanhar a entrega

## Como executar localmente

### Pre-requisitos

- Java 21
- Maven Wrapper do projeto ou Maven instalado
- PostgreSQL acessivel

### Configuracao

Crie um arquivo `src/main/resources/application.properties` com os dados do seu banco. Exemplo:

```properties
spring.application.name=demo
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Subindo a aplicacao

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Ou:

```powershell
.\mvnw.cmd clean package
java -jar target\demo-0.0.1-SNAPSHOT.war
```

Por padrao, a API sobe em:

```text
http://localhost:8080
```

## Testes

Hoje o projeto possui apenas um teste basico de contexto:

- `DemoApplicationTests`

Para testes funcionais da API, use os arquivos do Postman incluidos no repositorio:

- `postman/demo-api.postman_collection.json`
- `postman/demo-api.local.postman_environment.json`

Guia complementar:

- `docs/fluxo-e-postman.md`

## Exemplo de request

### Criar projeto

```http
POST /projetos
Content-Type: application/json
```

```json
{
  "nome": "Projeto API",
  "descricao": "Projeto para estudo de Spring Boot",
  "objetivo": "Centralizar o gerenciamento",
  "status": "EM_ANDAMENTO",
  "prioridade": "ALTA",
  "dataInicio": "2026-03-25",
  "dataFim": "2026-06-30",
  "orcamentoPrevisto": 10000.00,
  "percentualConcluido": 15
}
```

## Padrões adotados

- retorno de dados por DTO, sem expor entidades diretamente nos endpoints
- uso de `ResponseEntity` para controlar o status HTTP
- uso de `201 Created` no cadastro
- uso de `204 No Content` na exclusao
- uso de `EntityLookupService` para concentrar buscas por id
- uso de enums para manter consistencia no dominio

## Limitacoes atuais

- nao ha autenticacao ou autorizacao
- senha de usuario e persistida sem mecanismo de hash
- nao ha paginacao nos endpoints de listagem
- nao ha documentacao Swagger/OpenAPI
- os testes automatizados ainda sao minimos
- nao ha regras avancadas de negocio, como validacao de coerencia entre datas e status

## Possiveis evolucoes

- adicionar Spring Security com JWT
- criptografar senha com BCrypt
- incluir Swagger/OpenAPI
- implementar testes unitarios e integrados para services e controllers
- adicionar paginacao e filtros nas listagens
- criar regras de negocio mais ricas para projetos, custos e riscos
- incluir auditoria de criacao e atualizacao

## Organizacao do projeto

```text
src/
  main/
    java/com/example/demo/
      controller/
      dto/
      entity/
      enums/
      exception/
      repository/
      service/
    resources/
  test/
postman/
docs/
```

## Resumo

Este projeto implementa uma API REST de gerenciamento de projetos com foco em organizacao de dominio e relacoes entre entidades. Ele e um bom ponto de partida para estudo de Spring Boot com JPA, validacao, DTOs e modelagem de um fluxo real de negocio.
