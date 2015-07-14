#ExtendedValidation
##Uma extensão do BeanValidation 1.1

1. [Objetivo](#obj)
  1. [Flexível, estensível e fácil de usar](#flxEstEasUse)
2. [Características](#feat)
  1. [Integrado ao CDI](#cdiInt)
  2. [Suporte estendido a parametrização do texto das mensagens](#extMssParamSup)
  3. [Múltiplas fontes de mensagens](#multMssSrc)
    1. [Configuração de múltiplos arquivos de mensagens](#confMultFileSrc)
    2. [Redefinição da origem das mensagens](#redMssgSrc)
3. [Configuração e uso](#config)

###<a name="obj">Objetivo</a>
####<a name="flxEstEasUse">Flexível, estensível e fácil de usar</a>
Este componente não é uma implementação completa do [*BeanValidation API 1.1*](http://cdi-spec.org/), e sim uma extensão que adiciona funcionalidades a essa, independente da implementação concreta utilizada, e pode facilmente ser integrada a projetos novos ou existentes.

Todas as implementações de elementos da API delegam, ao final de seu incremento à funcionalidade, a conclusão do processamento ao seu par padrão definido pela implementação da API escolhida. Este princípio garante que comportamentos existentes em projetos legados não sejam afetados, minimizando riscos na adoção do componente neste cenário.

Idealizado para facilitar o uso da validação através da [*BeanValidation API*](http://cdi-spec.org/) em projetos grandes e complexos, apresentando soluções como: permitir o desacoplamento entre as *Constraint* das implementações dos *ConstraintValidator*; possibilita especificar múltiplos arquivos de mensagens ou mesmo substituir o uso de arquivos por outra fonte, como por exemplo banco de dados, módulos ou aplicações externas, rotinas mainframe etc.; também disponibiliza um incremento à API para simplificar a atribuição de valores a parâmetros especificados em mensagens na fase de interpolação.

###<a name="feat">Características</a>
####<a name="cdiInt">Integrado ao CDI</a>
O *ExtendedValidation* possui uma implementação de *ConstraintValidatorFactory* integrada ao contexto de dependências da [API CDI](http://cdi-spec.org/), possibilitando que a instância do validador declarado pela *Constraint* seja recuperada a partir de uma interface, tornando possível desacoplar esses elementos.

A especificação do [*BeanValidation API 1.1*](http://cdi-spec.org/) relaciona a descrição da *Constraint* com seu validador diretamente através da classe desse último, como exemplificado no modelo abaixo:
![](http://extendedvalidation.sourceforge.net/images/ConstraintValidation1.png)

Afim de possibilitar a desvinculação entre a Constraint e a implementação de seu validador, a factory de validadores implementada pelo ExtendedValidation permite que seja atribuído uma interface à Constraint, a qual será implementada pelo validador, que poderá ou não estar fisicamente no mesmo componente, como a seguir:
![](http://extendedvalidation.sourceforge.net/images/ConstraintValidation2.png)

Projetos Enterprise compostos por um grande número de módulos, e que possuem interdependência de dados, costumam apresentar aumento de complexidade no gerenciamento de dependências proporcional ao seu crescimento, principalmente em cenários em que há times distintos trabalhando simultaneamente em módulos relacionados.

O diagrama abaixo apresenta o cenário tradicional, aonde cada componente de negócio que possua relacionamento com outro carrega consigo uma dependência direta com o componente origem dos elementos a serem compartilhados.
![](http://extendedvalidation.sourceforge.net/images/Tradicional.png)

Este modelo pode levar a situações de dependência circular, cenário geralmente indesejado devido a fragilidade resultante do aumento da complexidade na gestão, manutenção e evolução dos componentes envolvidos.

Tendo em mente que entidades e interfaces de serviços mudam com menor frequência que os comportamentos de negócio, o desacoplamento possibilitado pelo *ExtendedValidation* viabiliza a adoção de uma arquitetura segmentada em componentes com responsabilidades mais coesas e independentes, de forma a permitir que as dependências sejam estabelecidas entre os elementos com menor possibilidade de alteração ao longo da vida do sistema, como demonstrado no diagrama a seguir:
![](http://extendedvalidation.sourceforge.net/images/Desacoplado.png)

A Factory implementada permite a convivência entre o modelo proposto e o padrão definido pela [*BeanValidation API 1.1*](http://cdi-spec.org/), ou seja, é possível existir definições de *Constraint* relacionadas a interfaces e outras definindo classes concretas, ambas serão igualmente tratadas na fase de resolução do validador a ser aplicado.

####<a name="extMssParamSup">Suporte estendido a parametrização do texto das mensagens</a>

As possibilidades de parametrização do texto das mensagens geradas para o sistema através da violação das Constraint foram ampliadas com o uso do ExtendedValidation.

A [*BeanValidation API 1.1*](http://cdi-spec.org/) define que os campos declarados na *Constraint*, além de *EL expressions* e algumas variáveis pré-definidas podem ser utilizadas para resolver a mensagem na fase de interpolação. Abaixo está exemplificado como um campo ("label") é utilizado para valorizar um parâmetro definido em uma mensagem.
```java
// Definição da Constraint
@Constraint(validatedBy = MyValidator.class)
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MyConstraint {
    String label() default "";
    String message() default "{label} invalida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```
```java
// Aplicação da Constraint em uma entidade
@MyConstraint(label="Minha entidade")
public class MyEntity {
    ...
}
```

No exemplo demonstrado a mensagem gerada seria "Minha entidade invalida".

O mecanismo proposto pela [*BeanValidation API 1.1*](http://cdi-spec.org/) é muito flexível e atende a maioria dos cenários comuns de uso. Entretanto, em sistemas cujo as mensagens são pré-cadastradas e reaproveitadas em múltiplos cenários, ou ainda quando a *constraint* é reutilizada gerando mensagens diferentes em momentos distintos, a necessidade de prever todos os parâmetros que serão utilizados ao longo do tempo no momento da definição da *constraint* pode tornar-se inconveniente.

Com a proposta de ampliar as possibilidades de parametrização de mensagens e permitir que isso seja realizado no momento da aplicação da *constraint*, e não no de sua definição, o *ExtendedValidation* adiciona a possibilidade de declarar o campo ***messageParameters*** na *constraint*. Caso o campo esteja presente, seu conteúdo é processado pela implementação do interpolador disponibilizado pelo componente, que extrai o par 'chave=valor' e envia para o interpolador default da implementação da API escolhida para o projeto.

```java
// Definição da Constraint
@Constraint(validatedBy = MyValidator.class)
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MyConstraint {
    String[] messageParameters() default {};
    String message() default "{label} invalida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```
```java
// Aplicação da Constraint em uma entidade
@MyConstraint(messageParameters={"label=Minha entidade"})
public class MyEntity {
    ...
}
```

A mensagem resultante será idêntica a do caso anterior.

É importante salientar que o componente não efetiva a resolução dos parâmetros na mensagem, esta fase é executada pelo interpolador padrão da implementação concreta da API utilizada no projeto. A implementação de interpolador do componente é um *decorator* que tem apenas a função de processar os parâmetros definidos em ***messageParameters*** e enviá-los ao interpolador padrão como se tivessem sido definidos da forma especificada pela API.

A parametrização de mensagens pode ser realizada no esquema 'chave=valor' ou apenas informando o valor a ser interpolado. Entretanto, neste caso, é necessário que a mensagem defina o parâmetro no padrão de índice, como em: "{0} invalida".

Uma vez que o campo ***messageParameters*** é um array, é possível informar múltiplos parâmetros adicionando-os separados por vírgula, como nos exemplos abaixo:
```java
@MyConstraint(messageParameters={"par1=val1", "par2=val2", "par3=val3"}, 
              message="{par1} deve ser diferente de {par2} e {par3}")
public class MyEntity {
    ...
}

@MyConstraint(messageParameters={"val1", "val2", "val3"}, 
              message="{0} deve ser diferente de {1} e {2}")
public class MyEntity {
    ...
}
```

####<a name="multMssSrc">Múltiplas fontes de mensagens</a>

A [*BeanValidation API 1.1*](http://cdi-spec.org/) possibilita duas formas para atribuir mensagens à *constraint*:

- Atribuindo o texto da mensagem diretamente ao atributo ***message***, seja na declaração ou no aplicação da *constraint*, como exemplificado abaixo:
```java
@Constraint(validatedBy = MyValidator.class)
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MyConstraint {
    String[] messageParameters() default {};
    String message() default "Minha Mensagem";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
 
// OU //
 
@MyConstraint(message="Minha Mensagem")
public class MyClass {
    ...
}
```

- Atribuir ao atributo "message" uma chave que identifica a mensagem a ser recuperada no arquivo "ValidationMessages.properties", como segue:
```java
@Constraint(validatedBy = MyValidator.class)
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MyConstraint {
    String[] messageParameters() default {};
    String message() default "{meusistema.minhamensagem}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
 
// OU //
 
@MyConstraint(message="{meusistema.minhamensagem}")
public class MyClass {
    ...
}
```

Em consonância com a especificação, o *ExtendedValidation* mantém as formas de especificar as mensagens acima demonstradas, porém expande as possibilidades de definição do repositório das mensagens, disponibilizando um mecanismo simplificado para configuração de múltiplos arquivos *.properties* ou mesmo definir uma implementação própria que recupere as mensagens a partir de outro tipo de fonte como banco de dados, módulo ou aplicação externa ao sistema, rotinas mainframe, etc.

Esta abordagem dispensa que aplicações compostas por múltiplos módulos necessitem centralizar as mensagens em um único arquivo, procedimento que pode trazer transtornos em ambientes aonde há o desenvolvimento concorrente de módulos por times distintos, além de facilitar o reaproveitamento de fontes de mensagens legadas.

#####<a name="confMultFileSrc">Configuração de múltiplos arquivos de mensagens</a>
A abordagem padrão de origem de mensagem adotado pelo *ExtendedValidation* é a recuperação em arquivos  *.properties*, sendo que é possível se utilizar de múltiplos arquivos para este fim. Existem duas formas para configurar este comportamento:

- Definir a variável de ambiente "com.github.ldeitos.validation.message.files" atribuindo a essa a lista de nomes de arquivos a serem considerados separados por ",". O exemplo a seguir demonstra a configuração da variável de sistema ao iniciar a aplicação java, entretanto a forma de efetuar esta configuração varia de acordo com o ambiente, como por exemplo o servidor de aplicação utilizado.
```bash
java myApp -Dcom.github.ldeitos.validation.message.files= moduleXErrorMessages, moduleYErrorMessages, ... 
```

- Configurar através do arquivo ***extendedValidation.xml***
```xml
<!--?xml version="1.0" encoding="UTF-8"?-->
<extended-validation>
    <message-files>
        <message-file>moduleXErrorMessages</message-file>
        <message-file>moduleYErrorMessages</message-file>
        ...
    </message-files>
</extended-validation>
```

Em ambos os casos o arquivo padrão definido pela API, o ***ValidationMessages.properties***, será considerado caso esteja presente no *path* da aplicação, independente de ter sido ou não mencionado na configuração. Caso ambas as formas forem utilizadas concorrentemente, tanto o conteúdo do arquivo de configuração quanto da variável de sistema será considerado, sendo que duplicações são eliminadas.

Finalmente, caso uma mesma chave esteja definida em mais de um arquivo, será recuperada a que for encontrada primeiro pelo mecanismo.

#####<a name="redMssgSrc">Redefinição da origem das mensagens</a>
Como já mencionado também é possível substituir a fonte das mensagens, ou seja, utilizar outro tipo de repositório que não arquivos *.properties*. Para este fim, o *ExtendedValidation* disponibiliza a interface *MessagesSource*, a qual pode ser implementada livremente pela equipe do projeto afim de utilizar o meio desejado para recuperar as mensagens do sistema.

Para substituir o *MessagesSource* padrão do componente basta configurar a sua implementação no arquivo ***extendedValidation.xml***:
```xml
<!--?xml version="1.0" encoding="UTF-8"?-->
<extended-validation>
    <message-source>com.foo.bar.MyMessageSource</message-source>
</extended-validation>
```

Também é disponibilizado a classe abstrata *AbstractMessagesSource*, cuja implementação verifica se o template de mensagem recebido está no formato padrão de chave de busca - *"{chave.para.minhamensagem}"* - ou, opcionalmente, a variação identificada por *"[...]"*, antes de tentar recuperar a mensagem na origem definida.

Caso se utilize a classe abstrata e o template definido na *constraint* não seja compatível com o padrão de chave de busca, o próprio texto do template é retornado pelo *MessagesSource*. É importante que o mesmo comportamento seja adotado pelas implementações próprias, tanto para este caso como quando a chave utilizada não recupera nenhuma mensagem.

###<a name="config">Configuração e uso</a>

Para utilizar o ExtendedValidation é necessário adicionar ao projeto as seguintes dependências:

- [extendedValidation](http://search.maven.org/#search%7Cga%7C1%7Cextendedvalidation)
- [extendedValidation-core](http://search.maven.org/#search%7Cga%7C1%7Cextendedvalidation)
- [BeanValidation API 1.1](http://search.maven.org/#artifactdetails%7Cjavax.validation%7Cvalidation-api%7C1.1.0.Final%7Cjar)
- Uma implementação da *BeanValidation API 1.1* ([Ex.: Hibernate Validator](http://search.maven.org/#artifactdetails%7Corg.hibernate%7Chibernate-validator%7C5.1.2.Final%7Cjar))

O *ExtendedValidation* já possui as configurações necessárias para que seus recursos sejam integrados ao mecanismo de validação. Entretanto, caso seu projeto possua configurações próprias declaradas no arquivo ***validation.xml*** é necessário que uma das seguintes ações sejam tomadas:

- Remover as configurações atuais e substituí-las pelos mecanismos disponibilizados pelo *ExtendedValidation*
- Adicionar as configurações necessárias para o correto funcionamento do *ExtendedValidation*, conforme segue:
```xml
<!--?xml version="1.0" encoding="UTF-8"?-->
<validation-config>
  <message-interpolator>
  com.github.ldeitos.validation.impl.interpolator.ExtendedParameterMessageInterpolator
  </message-interpolator>
   
  <constraint-validator-factory>
  com.github.ldeitos.validation.impl.factory.CdiLookupConstraintValidationFactory
  </constraint-validator-factory>
    
  ...
</validation-config>
```

O uso do arquivo de configuração ***extendedValidation.xml***, o qual deverá estar na pasta META-INF, somente é exigido caso seja preciso configurar múltiplos arquivos como fonte das mensagens ou se for necessário substituir a implementação padrão de *MessagesSource* do componente, conforme já mencionado anteriormente.

O arquivo de configuração deve ser declarado, se necessário, uma única vez no projeto, ou seja, caso o projeto seja composto de múltiplos módulos, apenas uma ocorrência do arquivo deverá estar presente no path da aplicação.

Abaixo segue modelo do arquivo ***extendedValidation.xml*** para referência.
```xml
<!--?xml version="1.0" encoding="UTF-8"?-->
<extended-validation>
    <!--Nome qualificado da instância de MessagesSource a ser utilizado-->
    <message-source></message-source> 
    <!--Lista de arquivos que conterão as mensagens da aplicação-->
    <message-files>
        <!--nome de arquivo, sem extensão-->
        <message-file></message-file>
        <message-file></message-file>
    </message-files>
</extended-validation>
```