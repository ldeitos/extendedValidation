# Tarcius
## Auditoria de atividade

### Objetivo
Tarcius é um componente que provê uma API simplificada para registro de auditoria das atividades dos usuários em uma aplicação.

Baseado nos conceitos e características disponibilizadas pelo CDI, possibilita o registro de chamadas a métodos, bem como dos valores dos parâmetros identificados, atribuindo referências que visam dar sentido de negócio às informações coletadas.

Concebido para ser adaptável às necessidades e particularidades de cada aplicação, delega ao usuário do componente a definição do modelo da auditoria, a composição dos dados coletados no modelo definido e o envio desses ao repositório escolhido para os dados de auditoria, além de possibilitar a definição de tradutores específicos para os parâmetros ou o uso de tradutores padrões disponibilizados.

### Modelo de uso
O mecanismo baseia-se na interceptação da chamada de métodos identificados com a anotação *@Audit* através de um *interceptor* CDI que deve ser ativado com a inclusão do seguinte trecho no arquivo beans.xml:

```xml
<interceptors>
    <class>com.github.ldeitos.tarcius.audit.interceptor.AuditInterceptor</class>
</interceptors>
```

Cabe a observação de que *interceptors* do CDI somente são acionados quando um método público é invocado por outra classe, mas nunca quando esta chamada é realizada internamente da própria classe que tenha o método anotado. Para maiores informações sobre *interceptors* do CDI consulte a [documentação oficial](https://docs.oracle.com/javaee/6/tutorial/doc/gkhjx.html).

O processo de auditoria realizada pelo *interceptor* divide-se em quatro fases:

1. Identificação dos parâmetros a serem auditados
2. Tradução dos parâmetros
3. Formatação dos dados coletados
4. Envio dos dados formatados para o repositório de auditoria

#### Identificação dos parâmetros a serem auditados
Nesta fase são identificados todos os parâmetros do método que estejam identificados com a anotação *@Audited* para posterior tradução.

#### Tradução dos parâmetros para *String*
Nesta fase é aplicada a tradução dos parâmetros anteriormente identificados para *String*, e podem ser dos seguintes tipos:

```java
TranslateType.STRING_VALUE
```

```java
TranslateType.JAXB_XML
```

```java
TranslateType.JAXB_FORMATTED_XML
```

```java
TranslateType.JAXB_JSON
```

```java
TranslateType.CUSTOM
```