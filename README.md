Welcome to Tomcat 7 Spring JPA ClickStart

This is a "ClickStart" that gets you going with a simple Spring Hibernate JPA "seed" project starting point, which will show you how to use the JNDI injection through Spring Profiles. You can launch it here:

<a href="https://grandcentral.cloudbees.com/?CB_clickstart=https://raw.github.com/fbelzunc/tomcat7-spring-hibernate-jpa-clickstart/master/clickstart.json"><img src="https://d3ko533tu1ozfq.cloudfront.net/clickstart/deployInstantly.png"/></a>

This will setup a continuous deployment pipeline - a CloudBees Git repository, a Jenkins build compiling and running the test suite (on each commit).
Should the build succeed, this seed app is deployed on a Tomcat 7 container.

# Application Overview

<img alt="Bees Shop - CloudBees MySQL" src="https://raw.github.com/fbelzunc/tomcat7-spring-hibernate-jpa-clickstart/master/src/site/img/app-snapshot.png" style="width: 70%;"/>

This sample application allows you toeasily activate three different environments using Spring profiles.

<img alt="Bees Shop - CloudBees MySQL" src="https://raw.github.com/fbelzunc/tomcat7-spring-hibernate-jpa-clickstart/master/src/site/img/app-springprofiles.png" style="width: 70%;"/>

* **Production environment (By default)**

You usually want to be sure that you will deploy your application on the production environment. For this reason the production environment is enabled by default. 

```xml
  <!-- JNDI DataSource for JEE environments -->
  <!-- use relative jndi-name + resource-ref=true for https://jira.springsource.org/browse/SPR-4585 -->
  <jee:jndi-lookup id="dataSource" jndi-name="jdbc/mydb" resource-ref="true"/>
```

* **Development environment binded to a MySQL database**

This environment allows you to use your own MySQL database on the development environment.

If you would like to activate this environment, you need to activate the profile **dev-mysql**. The way is described later on this document.

* **Development environment binded to an embedded HSQL database**

This environment allows you to use an embedded HSQL database.

To make available this environment you need to activate the profile **dev-hsql**. The way is described later on this document.

### Spring Profiles overview -datasource-config.xml ###

Spring Profiles will allow you to use different environments at CloudBees platform without doing any modification on your application. You can just create a many profiles as you would like. For example, you could have a different profile for staging, testing and production. In this way, you just need one version of your application to work with the different environments.

```xml
    <beans profile="dev-mysql" >
        <!-- JNDI DataSource for JEE environments -->
        <!-- use relative jndi-name + resource-ref=true for https://jira.springsource.org/browse/SPR-4585 -->
        <jee:jndi-lookup id="dataSource" jndi-name="jdbc/mydb" resource-ref="true"/>
    </beans>
```

### How to activate a specific profile on Spring

#### How to activate Spring profiles on the development environment (locally)

There are several ways in which you can do it on the development environment:
* Through the IDE 
* Using $CATALINA_HOME/bin/setenv.sh
* Using $CATALINA_HOME/conf/catalina.properties
* Through <context-param> on web.xml

##### Through the IDE

You just need to add **-Dspring.profiles.active=PROFILE_YOU_WANT_TO_ACTIVATE** on your VM options.

##### Eclipse configuration

Go to Run->Run Configurations [Environment tab]

<img alt="Bees Shop - CloudBees MySQL" src="https://raw.github.com/fbelzunc/tomcat7-spring-hibernate-jpa-clickstart/master/src/site/img/eclipse-environment.png" style="width: 70%;"/>

#### Using $CATALINA_HOME/bin/setenv.sh

Just create a file called setenv.sh in Tomcat’s bin directory with content:

```xml
   JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=PROFILE_TO_ACTIVATE"
```

#### Using $CATALINA_HOME/conf/catalina.properties

Setting this property value:

```xml
   spring.profiles.active=PROFILE_TO_ACTIVATE
```

#### Through **context-param** on web.xml

```xml
     <context-param>
        <param-name>spring.profiles.active</param-name>
        <param-value>PROFILE_TO_ACTIVATE</param-value>
    </context-param>
```

#### How to activate Spring profiles on CloudBees platform

You have to launch this command using the CloudBees SDK

```sh
  $bees config:set -a appName -Pspring.profiles.active=PROFILE_TO_ACTIVATE
```

### Create your tables and populate them from your Spring application -datasource-config.xml ###

This ClickStart shows you how to init and how to populate your database from your Spring application without using a MySQL client. Under the tag **<jdbc:initialize-database**, you just need to add the sql sripts you want to use.

```xml
   <jdbc:initialize-database data-source="dataSource">
      <jdbc:script location="classpath:db/hsqldb/mysql/initDB.sql"/>
      <jdbc:script location="classpath:db/hsqldb/mysql/populateDB.sql"/>
   </jdbc:initialize-database>
```

# Configure JPA and Hibernate in your application

#### Declare Hibernate and JPA jars in your Maven pom.xml

```xml
<project ...>
    <dependencies>
        <dependency>
            <groupId>javax.persistence</groupId>
            <artifactId>persistence-api</artifactId>
            <version>1.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>4.2.4.Final</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>4.2.4.Final</version>
        </dependency>
        ...
    </dependencies>
</dependencies>
```

### Using Placeholder to inject the showSql and the generateDdl value from a configuration file

With Placeholder you can have a configuration file on your application and inject the value of these variables on the places that you would like on your application.

```xml
 <context:property-placeholder system-properties-mode="OVERRIDE" location="classpath:app.properties"/>
```

### Declare the JPA EntityManagerFactory -datasource-config.xml

```xml
    <!-- JPA EntityManagerFactory -->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean"
          p:dataSource-ref="dataSource">
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"
                  p:showSql="${jpa.showSql}" p:generateDdl="${jpa.generateDdl}"/>
        </property>

        <property name="persistenceUnitName" value="product"/>
        <property name="packagesToScan" value="localdomain.localhost"/>
        <property name="jpaPropertyMap">
            <map>
                <entry key="javax.persistence.validation.factory" value-ref="validator"/>
            </map>
        </property>
    </bean>

    <!-- Transaction manager for a single JPA EntityManagerFactory (alternative to JTA) -->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager"
    p:entityManagerFactory-ref="entityManagerFactory"/>
```
# Create application manually

### Create Tomcat container

```sh
  bees app:create -a product -t tomcat7
```

### Create CloudBees MySQL Database

```sh
  bees db:create product-db
```

### Bind Tomcat container to database

```sh
  bees app:bind -a product -db product-db -as mydb
```

### Deploy your application

```sh
  bees app:deploy -a product -t tomcat7 app.war
```


