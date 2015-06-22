# Tarcius
## Auditoria de atividade

### Objetivo
Tarcius é um componente que provê uma API simplificada para registro de auditoria das atividades dos usuários em uma aplicação.

Baseado nos conceitos e características disponibilizadas pelo CDI, possibilita o registro de chamadas a métodos, bem como dos valores dos parâmetros identificados, atribuindo referências que visam dar sentido de negócio às informações coletadas.

Concebido para ser adaptável às necessidades e particularidades de cada aplicação, delega ao usuário do componente a definição do modelo da auditoria, a composição dos dados coletados no modelo definido e o envio desses ao repositório escolhido para os dados de auditoria, além de possibilitar a definição de tradutores específicos para os parâmetros ou o uso de tradutores padrões disponibilizados.
