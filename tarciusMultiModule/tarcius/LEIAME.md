# Tarcius
## Auditoria de atividade

### Objetivo
Tarcius é um componente que provê uma API simplificada para registro de auditoria das atividades dos usuários em uma aplicação.

Baseado nos conceitos e características disponibilizadas pelo CDI, possibilita o registro de chamadas a métodos, bem como dos valores dos parâmetros identificados, atribuindo referências que visam dar sentido de negócio às informações coletadas.

Concebido para ser adaptável às necessidades e particularidades de cada aplicação, delega ao usuário do componente a definição do modelo da auditoria, a composição dos dados coletados no modelo definido e o envio desses ao repositório escolhido para os dados de auditoria, além de possibilitar a definição de tradutores específicos para os parâmetros ou o uso de tradutores padrões disponibilizados.

### Modelo de uso
O mecanismo baseia-se na interceptação da chamada de métodos identificados com a anotação *@Audit* através de um *interceptor* CDI que deve ser ativado com a inclusão do seguinte trecho no arquivo *beans.xml*:

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
A anotação pode ser aplicada aos parâmetros a serem auditados no método marcado para interceptação ou diretamente no *bean* que deverá ser auditado, conforme abaixo:

Identificando no parâmetro
```java
public class VendasBC {

    @Audit(auditRef="consulta de vendas")
    public void consultar(@Audited(auditRef="parâmetros aplicados na consulta") Parametro par){}

}
``` 

Identificando no bean
```java
@Audited(auditRef="parâmetros aplicados na consulta") 
public class Parametro {

    ...

}
```
```java
public class VendasBC {

    @Audit(auditRef="consulta de vendas")
    public void consultar(Parametro par){}

}
```
Quando anotado no *bean*, sempre que esse for utilizado como parâmetro em um método interceptado ele será considerado como parte do conteúdo a ser auditado. Entretanto, caso seja necessário ignorar em algum caso específico um parâmetro cuja classe está anotada, basta identifica-lo com a anotação *@NotAudited*, como abaixo:

```java
public class VendasBC {

    @Audit(auditRef="consulta de vendas")
    public void consultar(@NotAudited Parametro par){}

}
```
 
#### Tradução dos parâmetros para *String*
Nesta fase é aplicada a tradução dos parâmetros anteriormente identificados para *String*. Esta tradução pode ser resolvida por tradutores pré-definidos e disponibilizados pelo componente ou ainda por tradutores customizados criados para casos específicos. A configuração do tradutor a ser aplicado se faz através do atributo *translator* da anotação *@Audited*, e podem ter os seguintes valores:

```java
TranslateType.STRING_VALUE
```
Tradutor que resolve o valor do parâmetro auditado para uma *String* representativa do objeto, através do método String.*valueOf()*.
```java
TranslateType.JAXB_XML
```
Tradutor que resolve o valor do parâmetro auditado para o formato XML utilizando o padrão da [API JAXB]( https://jaxb.java.net/). O XML resultante por este tradutor é apresentado em uma linha única, sem formatação. 
```java
TranslateType.JAXB_FORMATTED_XML
```
Tradutor que resolve o valor do parâmetro auditado para o formato XML utilizando o padrão da [API JAXB]( https://jaxb.java.net/). Ao contrário do anterior, o resultado deste tradutor é o XML formatado.
```java
TranslateType.JAXB_JSON
```
Tradutor que resolve o valor do parâmetro auditado para o formato JSON utilizando o componente [**jersey-json**]( https://jersey.java.net/documentation/1.19/json.html), extensão da [API JAXB]( https://jaxb.java.net) que utiliza o mesmo mecanismo de anotações para determinar o modelo do resultado. Para este formatador o resultado é retornado em uma linha única, sem formatação.
```java
TranslateType.CUSTOM
```