# Documentacao Completa do Projeto

## 1. Visao Geral

Este projeto e uma API REST desenvolvida com Spring Boot para gerenciamento de projetos. O sistema organiza informacoes de projetos, usuarios, participantes, atividades, riscos, recursos e custos, permitindo o cadastro, consulta, atualizacao e remocao desses dados.

O backend segue uma arquitetura em camadas e foi estruturado para representar o ciclo de vida de um projeto:

- criacao do projeto
- vinculacao de usuarios como participantes
- distribuicao de atividades
- controle de recursos
- acompanhamento de custos
- monitoramento de riscos

## 2. Objetivo do Sistema

O objetivo do sistema e centralizar o gerenciamento operacional de projetos em uma API unica, permitindo:

- registrar projetos e seu andamento
- manter uma equipe vinculada a cada projeto
- distribuir responsabilidades por atividade
- controlar recursos e custos
- registrar e acompanhar riscos

## 3. Stack Tecnologica

- Java 21
- Spring Boot 4.0.3
- Spring Web
- Spring Data JPA
- Spring Validation
- PostgreSQL no ambiente principal
- H2 nos testes
- Lombok
- Maven
- empacotamento `war`

Versao atual do artefato:

- `0.0.1-SNAPSHOT`

## 4. Arquitetura do Projeto

O projeto esta organizado em pacotes principais:

- `controller`: recebe as requisicoes HTTP e expoe os endpoints REST
- `service`: concentra a logica de aplicacao e o fluxo entre entidades e repositorios
- `repository`: realiza o acesso ao banco com Spring Data JPA
- `entity`: modela as tabelas do banco de dados
- `dto`: define os objetos de entrada e saida da API
- `enums`: padroniza os valores de status, perfis, prioridades e tipos

Fluxo principal de uma requisicao:

`Cliente -> Controller -> Service -> Repository -> Banco de Dados`

## 5. Como o Sistema Esta Organizado

### 5.1 Controllers

Cada modulo possui um controller com operacoes CRUD completas:

- `/projetos`
- `/usuarios`
- `/participantes`
- `/atividades`
- `/recursos`
- `/custos`
- `/riscos`

Cada controller segue o mesmo padrao de endpoints CRUD. No projeto real, isso aparece para cada plural correspondente, por exemplo:

- `GET /projetos`
- `POST /usuarios`
- `PUT /atividades/{id}`
- `DELETE /custos/{id}`

Os controllers retornam `ResponseEntity` e usam:

- `200 OK` para busca e atualizacao
- `201 Created` para criacao
- `204 No Content` para remocao

### 5.2 Services

As classes de service fazem o trabalho central da aplicacao:

- buscam entidades por id
- lancam `ResponseStatusException` com `404 NOT_FOUND` quando o registro nao existe
- convertem `RequestDto` em entidade
- convertem entidade em `ResponseDto`
- salvam e atualizam os dados

Os services tambem fazem o carregamento de relacionamentos. Exemplo:

- uma atividade busca o projeto e o participante responsavel
- um participante busca o usuario e o projeto
- um custo busca projeto, atividade e recurso
- um risco busca o projeto ao qual pertence

### 5.3 Repositories

Todos os repositories herdam de `JpaRepository`, o que ja fornece:

- `findAll`
- `findById`
- `save`
- `delete`

Alem disso, o projeto possui consultas derivadas e algumas queries customizadas.

Exemplos importantes:

- `ProjetoRepository.findByStatus`
- `ProjetoRepository.findByNomeContainingIgnoreCase`
- `UsuarioRepository.findByEmail`
- `ParticipanteRepository.findByProjetoId`
- `AtividadeRepository.findByProjetoIdAndStatus`
- `CustoRepository.findByProjetoIdAndTipo`
- `RiscoRepository.findByProjetoIdAndStatus`

Queries customizadas presentes:

- projetos com orcamento minimo
- atividades com percentual minimo
- participantes por projeto e papel
- recursos com custo unitario minimo
- riscos com criticidade minima
- usuarios por trecho do nome
- custos acima do previsto

## 6. Modelo de Dominio

O sistema possui 7 entidades principais:

- `Projeto`
- `Usuario`
- `Participante`
- `Atividade`
- `Recurso`
- `Custo`
- `Risco`

O centro do dominio e a entidade `Projeto`.

## 7. Tabelas do Banco de Dados

### 7.1 Tabela `projetos`

Representa o cadastro principal de cada projeto.

Campos:

- `id`
- `nome`
- `descricao`
- `objetivo`
- `status`
- `prioridade`
- `data_inicio`
- `data_fim`
- `orcamento_previsto`
- `percentual_concluido`

Relacionamentos:

- um projeto possui muitos participantes
- um projeto possui muitas atividades
- um projeto possui muitos riscos
- um projeto possui muitos recursos
- um projeto possui muitos custos

### 7.2 Tabela `usuarios`

Representa os usuarios cadastrados no sistema.

Campos:

- `id`
- `nome`
- `email`
- `senha`
- `telefone`
- `perfil`

Relacionamentos:

- um usuario pode participar de varios projetos por meio da tabela `participantes`

### 7.3 Tabela `participantes`

Representa a participacao de um usuario dentro de um projeto.

Campos:

- `id`
- `usuario_id`
- `projeto_id`
- `funcao_no_projeto`
- `papel_acesso`
- `ativo`

Relacionamentos:

- muitos participantes pertencem a um usuario
- muitos participantes pertencem a um projeto
- um participante pode ser responsavel por varias atividades

Observacao importante:

- `Participante` funciona como entidade de ligacao entre `Usuario` e `Projeto`, mas tambem guarda informacoes de contexto como funcao, papel e se esta ativo.

### 7.4 Tabela `atividades`

Representa tarefas ou entregas dentro de um projeto.

Campos:

- `id`
- `titulo`
- `descricao`
- `status`
- `prioridade`
- `data_inicio`
- `prazo`
- `data_conclusao`
- `percentual_conclusao`
- `projeto_id`
- `responsavel_id`

Relacionamentos:

- muitas atividades pertencem a um projeto
- muitas atividades podem ter um participante como responsavel
- uma atividade pode ter varios custos associados

### 7.5 Tabela `recursos`

Representa recursos disponiveis ou utilizados no projeto.

Campos:

- `id`
- `nome`
- `tipo`
- `descricao`
- `quantidade`
- `custo_unitario`
- `projeto_id`

Relacionamentos:

- muitos recursos pertencem a um projeto
- um recurso pode aparecer em varios registros de custo

### 7.6 Tabela `custos`

Representa valores planejados e reais do projeto.

Campos:

- `id`
- `descricao`
- `tipo`
- `valor_previsto`
- `valor_real`
- `data_lancamento`
- `projeto_id`
- `atividade_id`
- `recurso_id`

Relacionamentos:

- muitos custos pertencem a um projeto
- um custo pode estar vinculado opcionalmente a uma atividade
- um custo pode estar vinculado opcionalmente a um recurso

### 7.7 Tabela `riscos`

Representa riscos identificados no projeto.

Campos:

- `id`
- `titulo`
- `descricao`
- `categoria`
- `probabilidade`
- `impacto`
- `criticidade`
- `status`
- `estrategia_resposta`
- `plano_mitigacao`
- `projeto_id`

Relacionamentos:

- muitos riscos pertencem a um projeto

## 8. Relacionamentos Entre as Entidades

Visao resumida:

- `Projeto 1:N Participante`
- `Usuario 1:N Participante`
- `Projeto 1:N Atividade`
- `Participante 1:N Atividade`
- `Projeto 1:N Recurso`
- `Projeto 1:N Custo`
- `Atividade 1:N Custo`
- `Recurso 1:N Custo`
- `Projeto 1:N Risco`

Representacao textual:

```text
Usuario ----< Participante >---- Projeto
                                 |----< Atividade >---- Participante
                                 |----< Recurso
                                 |----< Custo >---- Atividade
                                 |         |
                                 |         ---- Recurso
                                 ----< Risco
```

## 9. Enums Utilizados no Sistema

### 9.1 `Prioridade`

- `BAIXA`
- `MEDIA`
- `ALTA`
- `CRITICA`

### 9.2 `StatusProjeto`

- `PLANEJADO`
- `EM_ANDAMENTO`
- `PAUSADO`
- `CONCLUIDO`
- `CANCELADO`

### 9.3 `StatusAtividade`

- `NAO_INICIADA`
- `EM_ANDAMENTO`
- `BLOQUEADA`
- `CONCLUIDA`
- `CANCELADA`

### 9.4 `StatusRisco`

- `IDENTIFICADO`
- `EM_ANALISE`
- `EM_TRATAMENTO`
- `MITIGADO`
- `ENCERRADO`

### 9.5 `PerfilUsuario`

- `ADMINISTRADOR`
- `GERENTE_PROJETO`
- `ANALISTA`
- `MEMBRO_EQUIPE`
- `STAKEHOLDER`

### 9.6 `PapelAcesso`

- `ADMINISTRADOR_PROJETO`
- `COORDENADOR`
- `EXECUTOR`
- `VISUALIZADOR`

### 9.7 `TipoRecurso`

- `HUMANO`
- `MATERIAL`
- `TECNOLOGICO`
- `FINANCEIRO`
- `SERVICO`

### 9.8 `TipoCusto`

- `PLANEJADO`
- `OPERACIONAL`
- `AQUISICAO`
- `RH`
- `IMPREVISTO`

### 9.9 `CategoriaRisco`

- `ESCOPO`
- `PRAZO`
- `CUSTO`
- `QUALIDADE`
- `RECURSOS`
- `TECNOLOGIA`
- `COMUNICACAO`
- `EXTERNO`

## 10. DTOs e Contratos da API

O projeto utiliza DTOs para separar o contrato da API das entidades persistidas.

Cada modulo possui:

- um `RequestDto` para entrada
- um `ResponseDto` para saida

Isso ajuda a:

- controlar quais campos entram na API
- validar os dados recebidos
- evitar retorno desnecessario de dados sensiveis

Exemplo importante:

- `UsuarioResponseDto` nao retorna a senha

## 11. Validacoes Aplicadas

O projeto usa `jakarta.validation` nos DTOs.

Exemplos de validacao que ja existem:

- campos obrigatorios com `@NotBlank` e `@NotNull`
- tamanho maximo com `@Size`
- e-mail valido com `@Email`
- numeros positivos com `@Positive`
- numeros positivos ou zero com `@PositiveOrZero`
- limite maximo com `@Max`

Exemplos praticos:

- nome de projeto obrigatorio
- e-mail de usuario obrigatorio e valido
- senha com minimo de 6 caracteres
- percentual de projeto e atividade limitado a 100
- probabilidade e impacto do risco limitados a 5
- criticidade do risco limitada a 25
- valores monetarios nao negativos

## 12. Regras de Funcionamento Observadas no Codigo

Mesmo sem uma camada separada de regras de negocio complexas, o codigo ja evidencia varias regras:

- toda atividade deve pertencer a um projeto
- todo participante deve pertencer a um usuario e a um projeto
- todo recurso deve pertencer a um projeto
- todo risco deve pertencer a um projeto
- todo custo deve pertencer a um projeto
- custo pode opcionalmente estar associado a uma atividade
- custo pode opcionalmente estar associado a um recurso
- atividade pode opcionalmente ter um responsavel
- quando um id informado nao existe, a API responde com erro `404`

## 13. Endpoints Disponiveis

### 13.1 Projetos

- `GET /projetos`
- `GET /projetos/{id}`
- `POST /projetos`
- `PUT /projetos/{id}`
- `DELETE /projetos/{id}`

### 13.2 Usuarios

- `GET /usuarios`
- `GET /usuarios/{id}`
- `POST /usuarios`
- `PUT /usuarios/{id}`
- `DELETE /usuarios/{id}`

### 13.3 Participantes

- `GET /participantes`
- `GET /participantes/{id}`
- `POST /participantes`
- `PUT /participantes/{id}`
- `DELETE /participantes/{id}`

### 13.4 Atividades

- `GET /atividades`
- `GET /atividades/{id}`
- `POST /atividades`
- `PUT /atividades/{id}`
- `DELETE /atividades/{id}`

### 13.5 Recursos

- `GET /recursos`
- `GET /recursos/{id}`
- `POST /recursos`
- `PUT /recursos/{id}`
- `DELETE /recursos/{id}`

### 13.6 Custos

- `GET /custos`
- `GET /custos/{id}`
- `POST /custos`
- `PUT /custos/{id}`
- `DELETE /custos/{id}`

### 13.7 Riscos

- `GET /riscos`
- `GET /riscos/{id}`
- `POST /riscos`
- `PUT /riscos/{id}`
- `DELETE /riscos/{id}`

## 14. Fluxo de Uso do Sistema

Um fluxo natural de utilizacao do sistema seria:

1. cadastrar usuarios
2. cadastrar um projeto
3. vincular usuarios como participantes do projeto
4. cadastrar atividades e definir responsaveis
5. cadastrar recursos do projeto
6. registrar custos previstos e realizados
7. registrar riscos e acompanhar sua mitigacao

Esse fluxo mostra que o sistema foi desenhado para representar a estrutura de um projeto de ponta a ponta.

## 15. Testes Existentes

O projeto possui testes focados principalmente na camada de repositorio.

Ha testes para:

- `ProjetoRepository`
- `UsuarioRepository`
- `ParticipanteRepository`
- `AtividadeRepository`
- `RecursoRepository`
- `CustoRepository`
- `RiscoRepository`

Base de apoio dos testes:

- uso de H2 no escopo de teste
- `@SpringBootTest`
- execucao transacional
- classe `RepositoryTestSupport` para criar dados de exemplo

Os testes ajudam a validar:

- persistencia basica
- consultas derivadas
- queries customizadas
- relacionamentos entre entidades

## 16. Pontos Fortes do Projeto

- estrutura limpa e separada por responsabilidade
- dominio bem definido para gestao de projetos
- CRUD completo para todos os modulos principais
- uso de DTOs para entrada e saida
- uso de validacoes declarativas
- tratamento simples de recurso nao encontrado
- testes de repositorio ja presentes
- modelagem coerente entre projeto, pessoas, tarefas, custos e riscos

## 17. Pontos que Ainda Podem Evoluir

O sistema atual funciona bem como base, mas ainda pode crescer em varios aspectos:

- autenticacao e autorizacao reais com Spring Security
- padronizacao global de erros
- paginacao e filtros nos endpoints
- documentacao automatica com Swagger/OpenAPI
- regras de negocio mais rigorosas
- auditoria de criacao e atualizacao
- validacao cruzada entre entidades
- testes de controller e service

Exemplos de evolucao de regra:

- impedir responsavel de atividade que nao pertence ao mesmo projeto
- impedir duplicidade de participante no mesmo projeto
- impedir exclusao de projeto com dependencias sem estrategia definida
- calcular criticidade automaticamente a partir de probabilidade e impacto

## 18. Resumo Final

O que foi criado aqui e uma API backend completa para gerenciamento de projetos. O sistema modela os principais elementos de um ambiente de projeto real:

- projeto
- equipe
- atividades
- recursos
- custos
- riscos

Tecnicamente, o projeto demonstra:

- uso de Spring Boot com arquitetura em camadas
- persistencia com JPA
- modelagem relacional coerente
- organizacao por dominio
- validacao de entrada
- testes basicos de repositorio

Em termos de entendimento global, o projeto ja esta em um ponto muito bom para:

- apresentar em sala
- documentar academicamente
- servir de base para uma versao mais avancada
- evoluir para um sistema com autenticacao, dashboards e relatorios
