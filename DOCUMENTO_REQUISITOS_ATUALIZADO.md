# Documento de Requisitos

## 1. Dados do Projeto

- Nome do projeto: Sistema de Gerenciamento de Projetos API
- Tipo de solução: API REST para gerenciamento de projetos
- Tema do Projeto Integrador: Gestão de projetos, equipe, atividades, custos, recursos e riscos
- Tecnologia principal: Java 21 com Spring Boot
- Persistência: Spring Data JPA com PostgreSQL
- Testes: Spring Boot Test com H2
- Artefato atual do sistema: `0.0.1-SNAPSHOT`
- Versão do documento: `1.0`
- Data da versão: `30/03/2026`

## 2. Visão Geral

O sistema tem como objetivo apoiar o gerenciamento de projetos por meio de uma API REST capaz de cadastrar e consultar projetos, usuários, participantes, atividades, recursos, custos e riscos. A aplicação foi construída em arquitetura em camadas, com controllers para exposição dos endpoints, services para regras de negócio, repositories para acesso aos dados, entities para modelagem persistente e DTOs para entrada e saída das operações.

## 3. Requisitos Funcionais

### RF01 - Cadastrar e manter usuários

- Descrição: O sistema deve permitir cadastrar, consultar, atualizar e remover usuários com nome, e-mail, senha, telefone e perfil.
- Prioridade: Alta
- Justificativa da prioridade: Usuários são a base para a vinculação de participantes aos projetos.
- Dependências:
- Nenhuma dependência funcional anterior.
- Relaciona-se com RN01.

### RF02 - Cadastrar e manter projetos

- Descrição: O sistema deve permitir cadastrar, consultar, atualizar e remover projetos contendo nome, descrição, objetivo, status, prioridade, datas, orçamento previsto e percentual concluído.
- Prioridade: Alta
- Justificativa da prioridade: O projeto é a entidade central do sistema e base para os demais módulos.
- Dependências:
- Nenhuma dependência funcional anterior.
- Relaciona-se com RN03.

### RF03 - Vincular participantes aos projetos

- Descrição: O sistema deve permitir registrar participantes em projetos, definindo usuário, função no projeto, papel de acesso e situação de atividade.
- Prioridade: Alta
- Justificativa da prioridade: Sem participantes não é possível organizar responsabilidades dentro do projeto.
- Dependências:
- Depende de RF01 para existência do usuário.
- Depende de RF02 para existência do projeto.
- Relaciona-se com RN01 e RN02.

### RF04 - Cadastrar e acompanhar atividades do projeto

- Descrição: O sistema deve permitir cadastrar, consultar, atualizar e remover atividades associadas a um projeto, com status, prioridade, datas, percentual de conclusão e responsável.
- Prioridade: Alta
- Justificativa da prioridade: O acompanhamento das atividades representa a execução prática do projeto.
- Dependências:
- Depende de RF02 para vincular a atividade a um projeto.
- Depende de RF03 quando houver responsável definido para a atividade.
- Relaciona-se com RN02 e RN03.

### RF05 - Registrar recursos e custos do projeto

- Descrição: O sistema deve permitir cadastrar recursos utilizados no projeto e registrar custos previstos e realizados, vinculando-os ao projeto e, quando aplicável, à atividade e ao recurso.
- Prioridade: Média
- Justificativa da prioridade: O controle financeiro é importante para monitoramento, mas depende do cadastro prévio do projeto e pode evoluir após a estrutura básica do sistema.
- Dependências:
- Depende de RF02 para vincular recurso e custo ao projeto.
- Depende de RF04 quando o custo estiver associado a uma atividade.
- Relaciona-se com RN03.

### RF06 - Registrar e monitorar riscos

- Descrição: O sistema deve permitir cadastrar, consultar, atualizar e remover riscos de um projeto, com categoria, probabilidade, impacto, criticidade, status, estratégia de resposta e plano de mitigação.
- Prioridade: Média
- Justificativa da prioridade: O gerenciamento de riscos agrega valor ao acompanhamento do projeto, mas ocorre após a estrutura básica do projeto estar definida.
- Dependências:
- Depende de RF02 para vincular o risco a um projeto.
- Relaciona-se com RN03.

## 4. Requisitos Não Funcionais

### RNF01 - Arquitetura e interoperabilidade

- Descrição: O sistema deve ser disponibilizado como API REST, utilizando HTTP e troca de dados em formato JSON.
- Prioridade: Alta

### RNF02 - Persistência e integridade dos dados

- Descrição: O sistema deve persistir os dados em banco relacional, utilizando Spring Data JPA com PostgreSQL no ambiente principal.
- Prioridade: Alta

### RNF03 - Validação e confiabilidade de entrada

- Descrição: O sistema deve validar os dados recebidos nas requisições, impedindo gravação de campos obrigatórios ausentes, valores fora de faixa, e-mails inválidos e textos acima dos limites definidos.
- Prioridade: Alta

## 5. Regras de Negócio

### RN01 - Vínculo obrigatório entre participante, usuário e projeto

- Descrição: Um participante só pode ser cadastrado se estiver associado a um usuário existente e a um projeto existente.

### RN02 - Responsável da atividade deve pertencer ao projeto

- Descrição: Uma atividade deve estar vinculada a um projeto, e o responsável informado, quando existir, deve ser um participante do mesmo projeto, preferencialmente em situação ativa.

### RN03 - Restrições de valores e faixas

- Descrição: O sistema deve respeitar os seguintes limites:
- Percentual concluído e percentual de conclusão entre 0 e 100.
- Probabilidade e impacto de risco entre 1 e 5.
- Criticidade do risco entre 1 e 25.
- Orçamento previsto, custo unitário, valor previsto e valor real maiores ou iguais a zero.

## 6. Matriz Resumida de Dependências

| Requisito | Depende de | Regras relacionadas |
| --- | --- | --- |
| RF01 | Nenhum | RN01 |
| RF02 | Nenhum | RN03 |
| RF03 | RF01, RF02 | RN01, RN02 |
| RF04 | RF02, RF03 | RN02, RN03 |
| RF05 | RF02, RF04 | RN03 |
| RF06 | RF02 | RN03 |

## 7. Critério de Prioridade Adotado

- Alta: requisito essencial para o funcionamento principal do sistema.
- Média: requisito importante para ampliar o valor da solução, mas não bloqueia a operação básica.
- Baixa: requisito desejável, porém não essencial na primeira entrega.

## 8. Observações para uso no template em PDF

- O conteúdo acima já está alinhado ao projeto Spring atual.
- Caso o template possua campos adicionais como autor, professor, turma ou instituição, esses dados podem ser preenchidos manualmente.
- Se quiser, este material também pode ser convertido em uma versão mais formal, com redação acadêmica e linguagem pronta para entrega.
