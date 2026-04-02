# Relacionamentos do Projeto

## 1. Visao Geral

O sistema foi modelado em torno da entidade `Projeto`. Quase todos os demais elementos do banco existem para complementar um projeto, seja representando pessoas, tarefas, recursos, custos ou riscos.

As entidades principais envolvidas nos relacionamentos sao:

- `Projeto`
- `Usuario`
- `Participante`
- `Atividade`
- `Recurso`
- `Custo`
- `Risco`

## 2. Relacionamentos Principais

### 2.1 `Projeto` x `Participante`

Tipo de relacionamento:

- `1:N`

Significado:

- um projeto pode ter varios participantes
- cada participante pertence a um unico projeto

Chave estrangeira:

- `participantes.projeto_id`

No codigo:

- `Projeto` possui `List<Participante> participantes`
- `Participante` possui `Projeto projeto`

Interpretacao de negocio:

- esse relacionamento representa a equipe vinculada ao projeto

## 2.2 `Usuario` x `Participante`

Tipo de relacionamento:

- `1:N`

Significado:

- um usuario pode participar de varios projetos
- cada participante referencia um unico usuario

Chave estrangeira:

- `participantes.usuario_id`

No codigo:

- `Usuario` possui `List<Participante> participacoes`
- `Participante` possui `Usuario usuario`

Interpretacao de negocio:

- `Participante` funciona como a ligacao entre uma pessoa e um projeto
- em vez de ligar `Usuario` direto a `Projeto`, o sistema usa `Participante` para guardar tambem papel de acesso, funcao no projeto e situacao ativa

## 2.3 `Projeto` x `Atividade`

Tipo de relacionamento:

- `1:N`

Significado:

- um projeto pode ter varias atividades
- cada atividade pertence a um unico projeto

Chave estrangeira:

- `atividades.projeto_id`

No codigo:

- `Projeto` possui `List<Atividade> atividades`
- `Atividade` possui `Projeto projeto`

Interpretacao de negocio:

- esse relacionamento representa as tarefas ou entregas que fazem parte da execucao do projeto

## 2.4 `Participante` x `Atividade`

Tipo de relacionamento:

- `1:N`

Significado:

- um participante pode ser responsavel por varias atividades
- cada atividade pode ter um unico responsavel

Chave estrangeira:

- `atividades.responsavel_id`

No codigo:

- `Participante` possui `List<Atividade> atividadesResponsaveis`
- `Atividade` possui `Participante responsavel`

Interpretacao de negocio:

- esse relacionamento mostra quem executa ou responde por uma atividade dentro do projeto

Observacao:

- o responsavel da atividade e opcional, pois `responsavel_id` pode ser nulo

## 2.5 `Projeto` x `Recurso`

Tipo de relacionamento:

- `1:N`

Significado:

- um projeto pode ter varios recursos
- cada recurso pertence a um unico projeto

Chave estrangeira:

- `recursos.projeto_id`

No codigo:

- `Projeto` possui `List<Recurso> recursos`
- `Recurso` possui `Projeto projeto`

Interpretacao de negocio:

- recursos representam elementos usados no projeto, como pessoas, materiais, tecnologia, verba ou servicos

## 2.6 `Projeto` x `Custo`

Tipo de relacionamento:

- `1:N`

Significado:

- um projeto pode ter varios custos
- cada custo pertence a um unico projeto

Chave estrangeira:

- `custos.projeto_id`

No codigo:

- `Projeto` possui `List<Custo> custos`
- `Custo` possui `Projeto projeto`

Interpretacao de negocio:

- esse relacionamento garante que todo custo registrado esteja ligado a um projeto

## 2.7 `Atividade` x `Custo`

Tipo de relacionamento:

- `1:N`

Significado:

- uma atividade pode ter varios custos associados
- cada custo pode estar ligado a uma unica atividade

Chave estrangeira:

- `custos.atividade_id`

No codigo:

- `Atividade` possui `List<Custo> custos`
- `Custo` possui `Atividade atividade`

Interpretacao de negocio:

- esse relacionamento permite detalhar custos gerados por uma atividade especifica

Observacao:

- a atividade no custo e opcional, pois `atividade_id` pode ser nulo

## 2.8 `Recurso` x `Custo`

Tipo de relacionamento:

- `1:N`

Significado:

- um recurso pode aparecer em varios registros de custo
- cada custo pode estar ligado a um unico recurso

Chave estrangeira:

- `custos.recurso_id`

No codigo:

- `Recurso` possui `List<Custo> custos`
- `Custo` possui `Recurso recurso`

Interpretacao de negocio:

- esse relacionamento ajuda a rastrear quanto determinado recurso impactou financeiramente o projeto

Observacao:

- o recurso no custo e opcional, pois `recurso_id` pode ser nulo

## 2.9 `Projeto` x `Risco`

Tipo de relacionamento:

- `1:N`

Significado:

- um projeto pode ter varios riscos
- cada risco pertence a um unico projeto

Chave estrangeira:

- `riscos.projeto_id`

No codigo:

- `Projeto` possui `List<Risco> riscos`
- `Risco` possui `Projeto projeto`

Interpretacao de negocio:

- esse relacionamento representa os riscos identificados dentro do contexto de um projeto especifico

## 3. Entidade Central de Ligacao: `Participante`

`Participante` e uma das entidades mais importantes do modelo porque conecta:

- `Usuario`
- `Projeto`
- `Atividade`

Ele nao e apenas uma tabela intermediaria. Ele tambem guarda dados de contexto:

- `funcaoNoProjeto`
- `papelAcesso`
- `ativo`

Por isso, ele tem papel duplo:

- ligar usuarios a projetos
- permitir definir responsabilidades operacionais nas atividades

## 4. Entidade Financeira de Ligacao: `Custo`

`Custo` tambem e uma entidade muito importante, porque conecta diferentes dimensoes do projeto:

- obrigatoriamente a `Projeto`
- opcionalmente a `Atividade`
- opcionalmente a `Recurso`

Isso permite varios cenarios de registro:

- custo geral do projeto
- custo ligado a uma atividade
- custo ligado a um recurso
- custo ligado a atividade e recurso ao mesmo tempo

## 5. Resumo das Cardinalidades

- `Projeto 1:N Participante`
- `Usuario 1:N Participante`
- `Projeto 1:N Atividade`
- `Participante 1:N Atividade`
- `Projeto 1:N Recurso`
- `Projeto 1:N Custo`
- `Atividade 1:N Custo`
- `Recurso 1:N Custo`
- `Projeto 1:N Risco`

## 6. Diagrama Textual dos Relacionamentos

```text
Usuario
  |
  | 1:N
  v
Participante --------> Projeto
     |                   |
     | 1:N               | 1:N
     v                   v
 Atividade             Recurso
     |                   |
     | 1:N               | 1:N
     +-------> Custo <---+
                          ^
                          |
                          | N:1
                        Projeto

Projeto
  |
  | 1:N
  v
Risco
```

## 7. Leitura Pratica do Modelo

Se formos ler o sistema de forma natural, a ordem dos relacionamentos e:

1. um `Usuario` e cadastrado
2. esse usuario vira `Participante` de um `Projeto`
3. o `Projeto` possui `Atividades`, `Recursos`, `Custos` e `Riscos`
4. uma `Atividade` pode apontar para um `Participante` responsavel
5. um `Custo` pode apontar para uma `Atividade` e para um `Recurso`

Essa estrutura mostra que o sistema foi pensado para acompanhar o projeto em varias perspectivas:

- pessoas
- execucao
- financeiro
- risco

## 8. Conclusao

Os relacionamentos do sistema foram modelados para colocar o `Projeto` no centro e conectar a ele tudo o que e necessario para a gestao:

- equipe
- atividades
- recursos
- custos
- riscos

Os dois relacionamentos mais estrategicos do modelo sao:

- `Participante`, porque conecta usuarios aos projetos
- `Custo`, porque conecta projeto, atividade e recurso

Com isso, o banco consegue representar de forma coerente a estrutura de um projeto real e servir de base para consultas, relatorios e futuras evolucoes.
