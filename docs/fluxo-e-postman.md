## Visao geral do projeto

Esta aplicacao e uma API REST em Spring Boot para gestao de projetos.

O fluxo tecnico segue este caminho:

1. `controller`
Recebe a requisicao HTTP, valida o corpo com `@Valid` e delega para a camada de servico.

2. `service`
Aplica o fluxo de negocio, preenche a entidade, resolve relacionamentos por `id` e converte o resultado para DTO de resposta.

3. `EntityLookupService`
Centraliza a busca de entidades relacionadas. Quando um `id` informado nao existe, a API dispara `ResourceNotFoundException`.

4. `repository`
Usa Spring Data JPA para persistir e buscar dados no PostgreSQL.

5. `GlobalExceptionHandler`
Padroniza erros de validacao com HTTP `400` e erros de entidade inexistente com HTTP `404`.

## Entidades e dependencias

Fluxo base:

1. `Usuario`
Cadastro de pessoa do sistema.

2. `Projeto`
Entidade principal.

3. `Participante`
Liga um `usuario` a um `projeto`.

4. `EapItem`
Estrutura analitica do projeto, depende de `projeto`.

5. `Atividade`
Depende de `projeto` e pode apontar para `eapItem` e `participante`.

6. `RaciAssignacao`
Depende de `atividade` e `participante`.

7. `Recurso`
Depende de `projeto`.

8. `Custo`
Depende de `projeto` e pode apontar para `atividade` e `recurso`.

9. `Risco`
Depende de `projeto`.

10. `ChecklistQualidade`
Depende de `projeto` e pode apontar para `atividade`.

## Ordem recomendada no Postman

1. Criar usuario
2. Criar projeto
3. Criar participante
4. Criar item EAP
5. Criar atividade
6. Criar atribuicao RACI
7. Criar recurso
8. Criar custo
9. Criar risco
10. Criar checklist
11. Consultar recursos criados
12. Rodar testes negativos
13. Opcional: deletar em ordem inversa

## Arquivos criados

- `postman/demo-api.postman_collection.json`
- `postman/demo-api.local.postman_environment.json`

## Como usar

1. Suba a API.
2. Importe a collection e o environment no Postman.
3. Ative o environment `Demo API Local`.
4. Rode a pasta `01 - Fluxo Feliz` de cima para baixo.
5. Rode a pasta `02 - Validacoes`.
6. Se quiser limpar os dados criados, rode `03 - Limpeza`.
