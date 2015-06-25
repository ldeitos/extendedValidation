# Tarcius
## Auditoria de atividade

### Objetivo
Tarcius é um componente que provê uma API simplificada para registro de auditoria das atividades dos usuários em uma aplicação.

Baseado nos conceitos e características disponibilizadas pelo CDI, possibilita o registro de chamadas a métodos, bem como dos valores dos parâmetros identificados, atribuindo referências que visam dar sentido de negócio às informações coletadas.

Concebido para ser adaptável às necessidades e particularidades de cada aplicação, delega ao usuário do componente a definição do modelo da auditoria, a composição dos dados coletados no modelo definido e o envio desses ao repositório escolhido para os dados de auditoria, além de possibilitar a definição de tradutores específicos para os parâmetros ou o uso de tradutores padrões disponibilizados.

### Modelo de uso
O mecanismo baseia-se na interceptação da chamada de métodos identificados com a anotação *@Audit* através de um *interceptor* CDI que deve ser ativado no arquivo *beans.xml* conforme abaixo:

```xml
<interceptors>
    <class>com.github.ldeitos.tarcius.audit.interceptor.AuditInterceptor</class>
</interceptors>
```

Cabe a observação de que *interceptors* do CDI somente são acionados quando um método público é invocado por outra classe, mas nunca quando esta chamada é realizada internamente da própria classe que tenha o método anotado. Para maiores informações sobre *interceptors* do CDI consulte a [documentação oficial](https://docs.oracle.com/javaee/6/tutorial/doc/gkhjx.html).

A anotação *@Audit* possibilita a especificação da referência do ponto de auditoria através do atributo *auditRef*. Esta referência é textual e pode ser utilizada para identificar o ponto de auditoria, mapeando-a para o domínio do negócio, por exemplo. Quando omitida esta configuração o nome do método interceptado é utilizado como referência. 

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
 
#### Tradução dos parâmetros
Nesta fase é aplicada a tradução dos parâmetros anteriormente identificados para *String*. Esta tradução pode ser resolvida por tradutores pré-definidos e disponibilizados pelo componente ou ainda por outros customizados para casos específicos. A configuração do tradutor a ser aplicado se faz através do atributo *translator* da anotação *@Audited*, podendo ter os seguintes valores:

```java
TranslateType.STRING_VALUE
```
Aplica o tradutor que resolve o valor do parâmetro auditado para uma *String* representativa do objeto, através do método String.*valueOf()*. Este tipo é o valor *default* do atributo.
```java
TranslateType.JAXB_XML
```
Aplica o tradutor que resolve o valor do parâmetro auditado para o formato XML utilizando o padrão da [API JAXB]( https://jaxb.java.net/). O XML resultante por este tradutor é apresentado em uma linha única, sem formatação. 
```java
TranslateType.JAXB_FORMATTED_XML
```
Aplica o tradutor que resolve o valor do parâmetro auditado para o formato XML utilizando o padrão da [API JAXB]( https://jaxb.java.net/). Ao contrário do anterior, o resultado deste tradutor é o XML formatado.
```java
TranslateType.JAXB_JSON
```
Aplica o tradutor que resolve o valor do parâmetro auditado para o formato JSON utilizando o componente [jersey-json]( https://jersey.java.net/documentation/1.19/index.html), extensão da [API JAXB]( https://jaxb.java.net) que utiliza o mesmo mecanismo de anotações para determinar o modelo do resultado. Para este formatador o resultado é retornado em uma linha única, sem formatação.
```java
TranslateType.CUSTOM
```
Quando o atributo *translator* está valorizado com este tipo, significa que a tradução do parâmetro deve ser resolvida por um tradutor especializado. Quando utilizado este valor, é **obrigatório** informar no atributo *customResolverQualifier* com o qualificador *@CustomResolver* que identifica o tradutor a ser instanciado, através do CDI, para resolver o valor do parâmetro. 

As implementações especializadas dos tradutores devem implementar a interface *ParameterResolver<I>*, além de ser devidamente qualificado com *@CustomResolver*, como no exemplo abaixo:
```java
@CustomResolver("resolve_pedido")
public class PedidoResolver implements ParameterResolver<Pedido> {

    public String resolve(Pedido input){...}

}
```
```java
public class VendasBC {

    @Audit(auditRef="Processar venda")
    public void processar(
       @Audited(auditRef="pedido", translator = TranslateType.CUSTOM, customResolverQualifier=@CustomResolver("resolve_pedido")) Pedido pedido){}

}
```
Cabe esclarecer que, apesar de obrigatório, a ausência da configuração do atributo *customResolverQualifier* não causa erro no processo de auditoria; neste caso, será aplicado o tradutor *default* para resolver o valor auditado obtendo, portanto, o valor do método *toString()* do objeto.