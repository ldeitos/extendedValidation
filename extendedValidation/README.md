#ExtendedValidation
##A BeanValidation 1.0 extension

###Flexible, extensible and easy to use
This component is not a complete implementation of [*BeanValidation API 1.0*](http://cdi-spec.org/), but an extension that add functionalities to the API, regardless of the concrete implementation used, and can be easily integrated to new or existent projects.

All elements of API implementations delegates, at the end of its increment to the functionality, the conclusion of the processing to its default pair defined by the implementation of the chosen API. This principle guarantees that existing behaviours in legacy projects will not be affected, minimizing the risks in the adoption of the component in this scenario.

Idealized to make easier the usage of validation by [*BeanValidation API*](http://cdi-spec.org/) in big and complex projects, presenting solutions as: enabling uncoupling between the implementations *Constraint* of *ConstraintValidator*; make possible to specify multiple files of messages or even substitute the usage of files by another source, such as database, modules or external applications, mainframe routines etc. Also provides an addition to the API to simplify the assignment of values to parameters specified in messages in the interpolation phase.

###Integrated to CDI
The *ExtendedValidation* has an implementation of *ConstraintValidatorFactory* integrated to the dependency context of [CDI API](http://cdi-spec.org/), permitting that the instance of the validator declared by Constraint to be retrieved from an interface, making possible to uncouple those elements.

The specification of [BeanValidation API 1.0](http://cdi-spec.org/) relates the description of Constraint with its validator directly by the class of this validator, as exemplified below:
![](https://github.com/ldeitos/repository/blob/master/site/extendedValidation/images/ConstraintValidation1.png)

In order to make possible the detachment between the Constraint and the implementation of its validator, the factory of validators implemented by **ExtendedValidation** allows to set an interface to the *Constraint* which will be implemented by the validator, which will be able or not to be physically in the same component, as follow:
![](https://github.com/ldeitos/repository/blob/master/site/extendedValidation/images/ConstraintValidation2.png)

Enterprise projects composed by a large number of modules, and have interdependent data, tend to exhibit increased complexity at dependency managing proportional to its growing, especially in scenarios that have distinct teams working simultaneously at related modules.

The diagram below shows the traditional scenario, where each business component which has a relationship with another, has with it a direct dependency with the origin component of elements to be shared.
![](https://github.com/ldeitos/repository/blob/master/site/extendedValidation/images/Tradicional.png)

This model can lead to circular dependency situations, generally undesirable scenario due to fragility resulting from complexity increasing in the management, maintenance and evolution of the components involved.

Having in mind that the services interfaces changes less than the business behavior, the decoupling afforded by the *ExtendedValidation* enables the adoption of an architecture segmented in components with more cohesive and independent responsibilities, in order to allow dependencies to be established between the elements with the lowest possibility of changing over the life of the system, as shown in the following diagram:
![](https://github.com/ldeitos/repository/blob/master/site/extendedValidation/images/Desacoplado.png)

The *Factory* implemented allows the coexistence between the suggested model and the pattern defined by the [*BeanValidation API 1.0*](http://cdi-spec.org/), ie it is possible to have definitions of Constraint related to interfaces and others defining concrete classes, both will be treated equally in the resolution phase of validator to be applied.

###Extended support for parameterization of text messages

The possibilities for parameterization of the text messages generated to the system by violations of Constraint have been expanded with the use of ExtendedValidation .

The [*BeanValidation API 1.0*](http://cdi-spec.org/) defines that the fields declared in Constraint , and EL expressions and some pre-defined variables can be used to solve the message in the interpolation phase. Below is exemplified as a field ("label") is used to value a defined parameter in a message.
```java
// Definition of Constraint
@Constraint(validatedBy = MyValidator.class)
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MyConstraint {
    String label() default "";
    String message() default "Invalidates {label}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```
```java
// Application of Constraint within an entity
@MyConstraint(label="Entity")
public class MyEntity {
    ...
}
```

In the example shown the message generated would be "Invalidates Entity".

The mechanism proposed by [*BeanValidation API 1.0*](http://cdi-spec.org/) is very flexible and covers the most common usage scenarios. However, in systems whose messages are pre-registered and reused in multiple scenarios, or when the constraint is reused generating different messages at different times, the need to predict all the parameters that will be used throughout time when the definition of constraint can become inconvenient.

With the proposal to expand the possibilities for parameterization of messages and allow this to be done at the constraint application time, and not in its definition, the ExtendedValidation adds the possibility to declare "messageParameters" field in constraint . If the field is present, its contents are processed by implementing the interpolator provided by the component, which extracts the pair 'key = value' and sends it to the default interpolator implementation of the API chosen for the project.

```java
// Definition of Constraint
@Constraint(validatedBy = MyValidator.class)
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MyConstraint {
    String[] messageParameters() default {};
    String message() default "Invalidates {label}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```
```java
// Application of Constraint within an entity
@MyConstraint(messageParameters={"label=Entity"})
public class MyEntity {
    ...
}
```

The resulting message will be identical to the previous case.

It is important highlight that, the component does not effectively solve the parameters in the message, this phase is performed by the default interpolating of the concrete implementation of the API used in the project. The implementation of the interpolator component is a decorator which has only the function of processing parameters defined in "messageParameters" and send them to the standard interpolation as if they had been defined by the manner specified API.

The parameterization of messages can be held in "key = value" scheme or just setting the amount to be interpolated. However, in this case, it is necessary to set the parameter in the message index pattern, as:. '{0} invalidate "

Once the "messageParameters" field is an array , it is possible to inform multiple parameters adding them separated by commas, such as in the examples below:
```java
@MyConstraint(messageParameters={"par1=val1", "par2=val2", "par3=val3"}, 
              message="{par1} must be different than {par2} and {par3}")
public class MyEntity {
    ...
}

@MyConstraint(messageParameters={"val1", "val2", "val3"}, 
              message="{0} must be different than {1} and {2}")
public class MyEntity {
    ...
}
```

###Multiple message sources

The [*BeanValidation API 1.0*](http://cdi-spec.org/) enable two ways to assign messages to constraint:

- Assigning text message directly to the attribute "message", is the declaration or implementation of the constraint as exemplified bellow:
```java
@Constraint(validatedBy = MyValidator.class)
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MyConstraint {
    String[] messageParameters() default {};
    String message() default "My Message";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
 
// OR //
 
@MyConstraint(message="My Message")
public class MyClass {
    ...
}
```

- Assign the attribute **message** a key that identifies the message to be retrieved on file *ValidationMessages.properties*, as follow:
```java
@Constraint(validatedBy = MyValidator.class)
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MyConstraint {
    String[] messageParameters() default {};
    String message() default "{mysystem.mymessage}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
 
// OR //
 
@MyConstraint(message="{mysystem.mymessage}")
public class MyClass {
    ...
}
```

In line with the specification, the *ExtendedValidation* keep the ways of specifying messages showed above, but expands the possibilities of definition of messages repository, providing a simplified mechanism for configuring multiple files ".properties" or defining an implementation or even define its own implementation that retrieves messages from another type of source such as a database, module or external application from the system, mainframe routines, etc.

This approach dispenses that composite applications that by multiple modules need to centralize messages within only one file, approach that can bring inconvenience in environments where there is concurrent development of modules by different teams, and facilitates the reuse of legacy sources of messages.

#####Configuration of multiple messages files
The standard approach adopted by the message source *ExtendedValidation* is retrieval from files *.properties*, and it is possible to use multiple files for this purpose. There are two ways to configure this behavior:

- Set the environment variable "com.github.ldeitos.validation.message.files" attributing this to the list of filenames to be considered separated by "." The following example shows the configuration of the system variable when launching a java application, but how to make this setting varies depending on the environment, such as the application server used.
```bash
java myApp -Dcom.github.ldeitos.validation.message.files= moduleXErrorMessages, moduleYErrorMessages, ... 
```

- Configuration by the file ***extendedValidation.xml***
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

In both cases the standard file defined by the API, *ValidationMessages.properties* will be considered if present in path application, regardless of whether or not it was mentioned in the configuration. If both forms are used concurrently, both the contents of the configuration file for the system variables will be considered, and duplications are eliminated.

Finally, if the same key is defined in more than one file is retrieved that is first encountered by the engine.

#####Redefinition of message source
As already mentioned it is also possible to replace the source of the messages, or use another type of archive that no files *.properties*. To this end, the *ExtendedValidation* offers a *MessagesSource* interface, which can be freely implemented by the project in order to use desired way to retrieve the messages from the system.

To replace the *MessagesSource* standard component simply configure their implementation in the file ***extendedValidation.xml***:
```xml
<!--?xml version="1.0" encoding="UTF-8"?-->
<extended-validation>
    <message-source>com.foo.bar.MyMessageSource</message-source>
</extended-validation>
```

Also available is the abstract class *AbstractMessagesSource* , whose implementation checks if the template message received is the standard format of search key - *{key.for.mymessage}" - or, optionally, the observed variation by *[...]*, before attempting to retrieve the message at a defined source.

If you use the abstract class and the template defined in the constraint is not compatible with the default search key, the text of the template itself is returned by the *MessagesSource*. It is important that the same behaviour is adopted by own implementations, both for this case as when the key used does not retrieve any messages.

###Configuration and usage

To use the ExtendedValidation it is necessary to add to the project the following dependencies:

- [extendedValidation 1.0](http://search.maven.org/#search%7Cga%7C1%7Cextendedvalidation)
- [extendedValidation-core 1.0](http://search.maven.org/#search%7Cga%7C1%7Cextendedvalidation)
- [BeanValidation API 1.0](http://search.maven.org/#artifactdetails%7Cjavax.validation%7Cvalidation-api%7C1.0.0.GA%7Cjar)
- An implementation of *BeanValidation API 1.0* ([Ex.: Hibernate Validator](http://search.maven.org/#artifactdetails%7Corg.hibernate%7Chibernate-validator%7C4.3.2.Final%7Cjar))

The *ExtendedValidation* already has the necessary settings so that its resources are integrated to the validation mechanism. However, if your project has its own configurations declared within the file ***validation.xml***, it is necessary to take one of the following actions:

   - Remove the current configurations and replace them for the mechanisms provided by *ExtendedValidation*
   - Add the necessary configurations for the correct functioning of the *ExtendedValidation*, as follows:
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

The usage of the configuration file *extendedValidation.xml* is mandatory just if it is necessary to configure multiple files as source of messages, or if it is necessary to replace the default implementation of *MessagesSource* from the component, as already mentioned.

The configuration file must be declared, if necessary, just once in the project, in other words, if the project is composed of multiple modules, just one instance of the file must be present into the applications path.

Bellow is the template file of *extendedValidation.xml* for reference.
```xml
<!--?xml version="1.0" encoding="UTF-8"?-->
<extended-validation>
    <!--Qualified name of the instance of MessagesSource to be used-->
    <message-source></message-source> 
    <!--List of files that will have the messages of the application-->
    <message-files>
        <!--file's name, without extension--->
        <message-file></message-file>
        <message-file></message-file>
    </message-files>
</extended-validation>
```