# Tarcius
## Auditoria de atividade

### Objetivo
Tarcius é um componente que provê uma API simplificada para registro de auditoria das atividades dos usuários em uma aplicação.

Baseado nos conceitos e características disponibilizadas pelo CDI, possibilita o registro de chamadas a métodos, bem como dos valores dos parâmetros identificados, atribuindo referências que visam dar sentido de negócio às informações coletadas.

Concebido para ser adaptável às necessidades e particularidades de cada aplicação, delega ao usuário do componente a definição do modelo da auditoria, a composição dos dados coletados no modelo definido e o envio desses ao repositório escolhido para os dados de auditoria, além de possibilitar a definição de tradutores específicos para os parâmetros ou o uso de tradutores padrões disponibilizados.

### Dependências e Configuração
#### Dependências
Para utilizar o componente basta adicioná-lo como dependência de seu projeto. A versão mais recente é a seguinte:
```xml
<dependency>
    <groupId>com.github.ldeitos</groupId>
    <artifactId>tarcius</artifactId>
    <version>0.1.2</version>
</dependency>
```

As principais dependências do componente são:
 - [cdi-util 0.6.2](http://search.maven.org/#artifactdetails%7Ccom.github.ldeitos%7Ccdi-util%7C0.6.2%7Cjar)
 - [commons-collections 3.2.1](http://search.maven.org/#artifactdetails%7Corg.lucee%7Ccommons-collections%7C3.2.1%7Cbundle)
 - [commons-collections4 4.0](http://search.maven.org/#artifactdetails%7Corg.apache.commons%7Ccommons-collections4%7C4.0%7Cjar)
 - [commons-configuration 1.0](http://search.maven.org/#artifactdetails%7Ccom.github.testdriven.guice%7Ccommons-configuration%7C1.0%7Cjar)
 - [commons-lang3 3.3.2](http://search.maven.org/#artifactdetails%7Corg.apache.commons%7Ccommons-lang3%7C3.3.2%7Cjar)
 - [jaxb-api 2.2.11](http://search.maven.org/#artifactdetails%7Cjavax.xml.bind%7Cjaxb-api%7C2.2.11%7Cjar)
 - [jersey-json 1.19](http://search.maven.org/#artifactdetails%7Ccom.sun.jersey%7Cjersey-json%7C1.19%7Cjar)
 
#### Configuração
As configurações do componente são efetuadas através do arquivo **tarcius.xml**, que deve ficar localizado no diretório META-INF do projeto. Segue exemplo do arquivo:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<tarcius>
	<formatter-class></formatter-class>
	<dispatcher-class></dispatcher-class>
	<interrupt-on-error></interrupt-on-error>
</tarcius>
```
As configurações possíveis são:
- **formatter-class**: define a classe que implementa o formatador da auditoria, conforme poderá ser visto com mais detalhes mais adiante. Esta configuração é obrigatória até a versão 0.1.2; a partir dessa pode-se suprimir esta configuração desde que haja apenas uma implementação do formatador disponível no *classpath*. Nesta situação esse será carregado automaticamente através do CDI.
- **dispatcher-class**: define a classe que implementa o componente que destina os dados coletados durante a auditoria, conforme será detalhado mais adiante. Esta configuração é obrigatória até a versão 0.1.2; a partir dessa pode-se suprimir esta configuração desde que haja apenas uma implementação do *dispatcher* disponível no *classpath*. Nesta situação esse será carregado automaticamente através do CDI.
- **interrupt-on-error**: define o comportamento do componente na ocorrência de exceções durante a execução do processo de auditoria. A configuração padrão é *false*, ou seja, caso ocorram erros o processamento não é interrompido, apenas uma mensagem de *warning* é gravada no log da aplicação. Caso seja configurado para *true*, a ocorrência de exceções interromperá o fluxo do processamento, sendo que a exceção causadora da interrupção será relançada através de uma *AuditException*. Esta configuração é opcional.

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

Quando o uso deste tipo de tradutor é associado à configuração do atributo *format* o resultado obtido da tradução do parâmetro para *String* é formatado de acordo com a seguinte regra:
 - Quando o parâmetro for do tipo *Date* ou extensões desse a formatação é realizada pelo *SimpleDateFormat* configurado com o formato definido em *format*;
 - Quando o parâmetro for de tipo diferente de *Date*, a formatação é realizada de acordo com a especificação do método String.*format()*;

 A associação do atributo *format* com os demais tipos de tradutores **não produz nenhum efeito**.
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

As implementações especializadas dos tradutores devem implementar a interface *ParameterResolver*, além de ser devidamente qualificado com *@CustomResolver*, como no exemplo abaixo:
```java
@ApplicationScope
@CustomResolver("resolve_pedido")
public class PedidoResolver implements ParameterResolver<Pedido> {

    public String resolve(Pedido input){...}

}
```
```java
public class VendasBC {

    @Audit(auditRef="Processar venda")
    public void processar(@Audited(auditRef="pedido", 
                                   translator=TranslateType.CUSTOM, 
                                   customResolverQualifier=@CustomResolver("resolve_pedido")) Pedido pedido){}

}
```
Recomenda-se que as implementações de *ParameterResolver* sejam definidas para o escopo de aplicação, porém este tratamento não é obrigatório.

Cabe esclarecer que, apesar de obrigatório, a ausência da configuração do atributo *customResolverQualifier* não causa erro no processo de auditoria; neste caso, será aplicado o tradutor *default* para resolver o valor auditado, obtendo assim o valor do método *toString()* do objeto.

####Formatação dos dados coletados
A fase de formatação dos dados coletados compreende a composição dos dados obtidos e traduzidos para texto nas duas fases anteriores dentro do modelo definido pelo usuário do componente.

Para o correto funcionamento do componente, é necessário que seu usuário implemente a interface *AuditDataFormatter<AD>*, aonde **AD** refere-se ao tipo genérico do modelo de dados da auditoria, também definido pelo usuário do componente. 

Abaixo segue exemplo de implementação do formatador dos dados de auditoria:
```java
public class MeuAuditDataFormatter implements AuditDataFormatter<AuditData> {

	@Override
	public AuditDataContainer<AuditData> format(AuditDataSource preAuditData) {
		AuditData auditData = new AuditData();
		StringBuilder messageBuilder = auditData.getMessage();
		String quebraLinha = System.getProperty("line.separator");

		messageBuilder.append("Método auditado: ");
		messageBuilder.append(preAuditData.getAuditReference());

		for (String entrada : preAuditData.getAuditParameterReferences()) {
			messageBuilder.append(quebraLinha).append("Parâmetro auditado: ");
			messageBuilder.append(entrada).append(quebraLinha);
			messageBuilder.append("Valor: ").append(preAuditData.getResolvedParameterValues().get(entrada));
		}

		return new AuditDataContainer<AuditData>(auditData);
	}
}
```
O parâmetro de entrada do método *AuditDataFormatter.format*, do tipo *AuditDataSource*, contém todos os dados coletados durante as fases de identificação e tradução de dados de auditoria, e podem ser acessados através dos seguintes métodos:

 - *getAuditReference()*: Retorna a referência atribuída na anotação *@Audit* através do atributo *auditRef*, ou, quando este não é informado, o nome do método auditado;
 - *getAuditParameterReferences()*: Retorna uma fila (*Queue*) contendo as referências atribuídas aos parâmetros auditados no método, obedecendo a mesma ordem apresentada nesse;
 - *getResolvedParameterValues()*: Retorna um mapa dos valores obtidos através da aplicação dos tradutores dos parâmetros configurados no atributo *translator* da anotação *@Audited*, que identifica cada parâmetro do método auditado que deve ser considerado na coleta dos dados, conforme já explicado anteriormente. A chave do mapa é a referência atribuída a cada parâmetro, citado no item acima;
 - *getParameterValues()*: Retorna um mapa das instâncias dos parâmetros auditados. A chave do mapa também é a referência atribuída a cada parâmetro;

Deve-se observar que o exemplo é apenas representativo, uma vez que tanto o modelo quanto o preenchimento é de decisão exclusiva do usuário do componente.

A partir da versão 0.1.2 basta implementar a interface *AuditDataFormatter* que o componente obterá a instância adequada através do CDI, entretanto é possível explicitar no arquivo de configuração *tarcius.xml*, o qual deverá estar localizado no diretório META-INF do projeto, o nome qualificado da implementação a ser utilizada pelo componente, conforme abaixo:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<tarcius>
	<formatter-class>com.minhaapp.MeuAuditDataFormatter</formatter-class>
	...	
</tarcius>
```
Para as versões anteriores esta configuração é **obrigatória**.

####Envio dos dados formatados para o repositório de auditoria
A fase de envio dos dados formatados para o repositório de auditoria compreende a última fase do processo de auditoria. Nesta fase, o usuário do componente deve implementar a forma de persistência do modelo formatado na fase anterior, quer seja persistindo em banco de dados, em uma fila de mensageria, no log da aplicação ou qualquer outro meio que seja definido para o projeto.

Para esta tarefa basta implementar a interface *AuditDataDispatcher<AD>* e nesta implementação dar destino ao objeto do tipo genérico *AD*, definido pelo usuário do componente, que foi formatado no passo anterior, como no exemplo abaixo:
```java
public class MeuAuditDataDispatcher implements AuditDataDispatcher<AuditData> {
	@Inject
	private EntityManager em;

	@Override
	public void dispatch(AuditData auditData) {
		em.merge(auditData);
	}
}
```
A instância do *AuditDataDispatcher* será obtida através do CDI ou, como citado no tópico anterior, através da configuração do componente, como abaixo:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<tarcius>
	...	
	<dispatcher-class>com.minhaapp.MeuAuditDataDispatcher</dispatcher-class>
	...	
</tarcius>
```
Esta configuração também é obrigatória para as versões anteriores a 0.1.2.