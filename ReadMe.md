# Java EE kookboek van **Ch€f** - IoC, DI, XML, JSON, YAML, Spring



[TOC]

---

## Inversion of Control (IoC) & Dependency Injection (DI)

### Wat staat dependency injection voor?

Het is moeilijk om afhankelijkheidsinjectie in enkele zinnen te begrijpen, dus het is beter om te beginnen met een 
codevoorbeeld.

### Dependency + Injection = ?

Op een standaard coderingen kunnen we klas-associaties of dependency-relaties zien, bijvoorbeeld:

```java

public class ClientA {

 ServiceB service;

 public void doSomething() { 

 String info = service.getInfo();

 }

}

```

Hier gebruikt klasse ClientA klasse ServiceB die is geschreven zoals hieronder, bijvoorbeeld:

```java

public class ServiceB {

 public String getInfo() {

  return "ServiceB’s Info";

 }

}

```

Van de klasse ClientA wordt gezegd dat deze afhankelijk is van de klasse ServiceB en ServiceB wordt een **_dependency_** van ClientA genoemd. Dit soort afhankelijkheid is erg triviaal in het programmeren.

Wanneer de code van de toepassing echter groter en complexer wordt, brengt de hardgecodeerde afhankelijkheid tussen klassen enkele nadelen met zich mee:

- De code is inflexibel - het is moeilijk te onderhouden en uit te breiden, want wanneer een klasse permanent afhankelijk is van een andere klasse, verander dan in de afhankelijke klasse mijn vereiste verandering in de afhankelijke klasse. En het is onmogelijk om de afhankelijke klasse later te wijzigen zonder de code bij te werken en opnieuw te compileren.
- De code is moeilijk voor unit testing, want wanneer je alleen de functionaliteiten van een klasse wilt testen, moet je ook andere afhankelijke klassen testen.
- De code is moeilijk te hergebruiken omdat de klassen nauw aan elkaar gekoppeld zijn.

Daarom komt afhankelijkheidsinjectie om deze nadelen aan te pakken, waardoor de code flexibeler wordt voor wijzigingen, gemakkelijk voor het testen van eenheden en echt herbruikbaar. Hoe werkt afhankelijkheidsinjectie dan?

Ten eerste worden interfaces gebruikt om de typen klassen te definiëren, zodat de implementatie later kan worden gewijzigd. Bijvoorbeeld, met de bovenstaande code - de interfaces Client en Service worden geïntroduceerd:

```java

public interface Client {

 void doSomething();

}

public interface Service {

 String getInfo();

}

```

Dan wordt de klasse ServiceB een implementatie van Service zoals hieronder:

```java

public class ServiceB implements Service {

 @Override

 public String getInfo() {

 return "ServiceB's Info";

 }

}

```

Dan is het mogelijk om verschillende implementaties van Service te hebben, bijvoorbeeld ServiceC en ServiceD:

```java

public class ServiceC implements Service {

 @Override

 public String getInfo() {

 return "ServiceC's Info";

 }

}

public class ServiceD implements Service {

 @Override

 public String getInfo() {

 return "ServiceD's Info";

 }

}

```

En de klasse ClientA implementeert nu de Client-interface en gebruikt de Service-interface in plaats van een concrete klasse - de implementatie van de eigenlijke Service wordt "**geïnjecteerd**" in deze klasse via de constructor - **_constructor injection_**, zoals hieronder weergegeven:

```java

public class ClientA implements Client {

 Service service;

 public ClientA(Service service) {

 this.service = service;

 }

 @Override

 public void doSomething() {

 String info = service.getInfo();

 }

}

```

De klasse ClientA is nu niet afhankelijk van specifieke implementaties van Service. In plaats van een instantie van afhankelijke klasse rechtstreeks in ClientA te maken, is de afhankelijkheidsinjectiecontainer of het framework nu verantwoordelijk voor het maken van die instantie en deze via de constructor in de klasse ClientA injecteren. Bijvoorbeeld:

```java

Service service = new ServiceB();

Client client = new ClientA(service);

client.doSomething();

```

Hier wordt een implementatie van Service is ServiceB gemaakt en doorgegeven aan ClientA, die niet op de hoogte is van de daadwerkelijke implementatie die het gebruikt. ClientA weet alleen dat het geïnjecteerde object van het type Service is.

Naast constructor-injectie wordt **_setter injection_** gebruikt om het afhankelijke object door te geven aan het afhankelijke object. Voeg de volgende settermethode toe aan de klasse ClientA:

```java

public void setService(Service service) {

 this.service = service;

}

```

Dan kunnen we op deze manier overstappen op de implementatie van verschillende Service's, bijvoorbeeld ServiceC:

```java

((ClientA) client).setService(new ServiceC());

client.doSomething();

```

Dat is een Java-codevoorbeeld over afhankelijkheidsinjectie. Merk op dat we de code schrijven om de afhankelijkheden handmatig te maken en te injecteren. In de praktijk zal een dependency injection container/framework zoals Spring de bedrading automatisch doen. U declareert de afhankelijkheidsinformatie via XML-bestand of annotaties in Java-klassen en het framework beheert de afhankelijkheden voor u.

### Conclusie

Afhankelijkheidsinjectie is een techniek waarmee de clientcode onafhankelijk kan zijn van de services waarop deze vertrouwt. De client heeft geen controle over hoe objecten van de services worden gemaakt - het werkt met een implementatie van de service via de interface. Dit is enigszins omgekeerd aan triviaal programmeren, dus afhankelijkheidsinjectie wordt ook wel **_inversion van control_** genoemd.

Het maakt de code flexibeler, uitbreidbaarder, onderhoudbaar, testbaar en herbruikbaar - dus afhankelijkheidsinjectie is erg populair in moderne programmering. In Java wordt afhankelijkheidsinjectie ondersteund sinds Java EE 6 - genaamd _CDI (Contexts and Dependency Injection)_. En het Spring-framework is gebaseerd op afhankelijkheidsinjectie, evenals andere frameworks zoals Google Guice en Play.

## Data Types

Welk technologieën worden gedeelde datatypen gebruikt?

Hieronder kunnen we hetzelfde data met verschillende typen terugvinden:

### XML: e**X**tensible **M**arkup **L**anguage

XML is used in many aspects of web development. It is often used to separate data from presentation.

- XML Separates Data from Presentation: XML does not carry any information about how to be displayed. The same XML data can be used in many different presentation scenarios. Because of this, with XML, there is a full separation between data and presentation.

- XML is Often a Complement to HTML: In many HTML applications, XML is used to store or transport data, while HTML is used to format and display the same data.

- XML Separates Data from HTML: When displaying data in HTML, you should not have to edit the HTML file when the data changes. With XML, the data can be stored in separate XML files. With a few lines of JavaScript code, you can read an XML file and update the data content of any HTML page.

#### Voorbeelden: Books.xml

```xml

<xml version="1.0" encoding="UTF-8">  

<bookstore>

    <book category="cooking">  
        <title lang="en">Everyday Italian</title>  
        <author>Giada De Laurentiis</author>  
        <year>2005</year>  
        <price>30.00</price>  
    </book>

    <book category="children">  
        <title lang="en">Harry Potter</title>  
        <author>J K. Rowling</author>  
        <year>2005</year>  
        <price>29.99</price>  
    </book>

    <book category="web">  
        <title lang="en">XQuery Kick Start</title>  
        <author>James McGovern</author>  
        <author>Per Bothner</author>  
        <author>Kurt Cagle</author>  
        <author>James Linn</author>  
        <author>Vaidyanathan Nagarajan</author>  
        <year>2003</year>  
        <price>49.99</price>  
    </book>

    <book category="web" cover="paperback">  
        <title lang="en">Learning XML</title>  
        <author>Erik T. Ray</author>  
        <year>2003</year>  
        <price>39.95</price>  
    </book>

</bookstore>

```

You will learn a lot more about using XML and JavaScript in the DOM section of this tutorial.


#### XML Transaction Data

Thousands of XML formats exist, in many different industries, to describe day-to-day data transactions:

-   Stocks and Shares
-   Financial transactions
-   Medical data
-   Mathematical data
-   Scientific measurements
-   News information
-   Weather services

___

#### Voorbeelden: XML Nieuws

**XMLNews is a specification for exchanging news and other information.**

Using a standard makes it easier for both news producers and news consumers to produce, receive, and archive any kind of news information across different hardware, software, and programming languages.

An example XMLNews document:

```xml

<xml version="1.0" encoding="UTF-8">  

<nitf>  
    <head>  
        <title>Colombia Earthquake</title>  
    </head>  
    <body>  
        <headline>  
            <hl1>143 Dead in Colombia Earthquake</hl1>  
        </headline>  
        <byline>  
            <bytag>By Jared Kotler, Associated Press Writer</bytag>  
        </byline>  
        <dateline>  
            <location>Bogota, Colombia</location>  
            <date>Monday January 25 1999 7:28 ET</date>  
        </dateline>  
    </body>  
</nitf>

```

#### Voorbeelden: XML Weather Service

An XML national weather service from NOAA (National Oceanic and Atmospheric Administration):

```xml

<xml version="1.0" encoding="UTF-8"> 

<current_observation>

    <credit>NOAA's National Weather Service</credit>  
    <credit_URL>http://weather.gov/</credit_URL>

    <image>  
        <url>http://weather.gov/images/xml_logo.gif</url>  
        <title>NOAA's National Weather Service</title>  
        <link>http://weather.gov</link>  
    </image>

    <location>New York/John F. Kennedy Intl Airport, NY</location>  
    <station_id>KJFK</station_id>  
    <latitude>40.66</latitude>  
    <longitude>-73.78</longitude>  
    <observation_time_rfc822>Mon, 11 Feb 2008 06:51:00 -0500 EST</observation_time_rfc822>

    <weather>A Few Clouds</weather>  
    <temp_f>11</temp_f>  
    <temp_c>-12</temp_c>  
    <relative_humidity>36</relative_humidity>  
    <wind_dir>West</wind_dir>  
    <wind_degrees>280</wind_degrees>  
    <wind_mph>18.4</wind_mph>  
    <wind_gust_mph>29</wind_gust_mph>  
    <pressure_mb>1023.6</pressure_mb>  
    <pressure_in>30.23</pressure_in>  
    <dewpoint_f>-11</dewpoint_f>  
    <dewpoint_c>-24</dewpoint_c>  
    <windchill_f>-7</windchill_f>  
    <windchill_c>-22</windchill_c>  
    <visibility_mi>10.00</visibility_mi>

    <icon_url_base>http://weather.gov/weather/images/fcicons/</icon_url_base>  
    <icon_url_name>nfew.jpg</icon_url_name>  
    <disclaimer_url>http://weather.gov/disclaimer.html</disclaimer_url>  
    <copyright_url>http://weather.gov/disclaimer.html</copyright_url>

</current_observation>

```

### JSON:  **J**ava**S**cript **O**bject **N**otation

JSON is a **text format** for storing and transporting data

JSON is "self-describing" and easy to understand

#### Voorbeelden: Javascript program

This example is a JSON string:

'{"name":"John", "age":30, "car":null}'

It defines an object with 3 properties:

-   name
-   age
-   car

Each property has a value.

If you parse the JSON string with a JavaScript program, you can access the data as an object:

let personName = obj.name;  
let personAge = obj.age;


#### What is JSON?

- JSON is a lightweight data-interchange format
- JSON is plain text written in JavaScript object notation
- JSON is used to send data between computers
- JSON is language independent


The JSON syntax is derived from JavaScript object notation, but the JSON format is text only. Code for reading and generating JSON exists in many programming languages. The JSON format was originally specified by [Douglas Crockford](http://www.crockford.com/).


#### Why Use JSON?

The JSON format is syntactically similar to the code for creating JavaScript objects. Because of this, a JavaScript program can easily convert JSON data into JavaScript objects.

Since the format is text only, JSON data can easily be sent between computers, and used by any programming language.

JavaScript has a built in function for converting JSON strings into JavaScript objects:

`JSON.parse()`

JavaScript also has a built in function for converting an object into a JSON string:

`JSON.stringify()`

You can receive pure text from a server and use it as a JavaScript object.

You can send a JavaScript object to a server in pure text format.

You can work with data as JavaScript objects, with no complicated parsing and translations.

#### Storing JSON Data

When storing data, the data has to be a certain format, and regardless of where you choose to store it, _text_ is always one of the legal formats.

JSON makes it possible to store JavaScript objects as text.

### YAML

#### What is YAML?

YAML is a data serialization language for storing information in a human-readable form. It originally stood for “Yet Another Markup Language” but has since been changed to “YAML Ain’t Markup Language” to distinguish itself as different from a true markup language.

It is similar to XML and JSON files but uses a more minimalist syntax even while maintaining similar capabilities. YAML is commonly used to create configuration files in Infrastructure as Code (IoC) programs or to manage containers in the DevOps development pipeline.

More recently, YAML has been used to create automation protocols that can execute a series of commands listed in a YAML file. This means your systems can be more independent and responsive without additional developer attention.

As more and more companies embrace DevOps and virtualization, YAML is quickly becoming a must-have skill for modern developer positions. YAML is also easy to incorporate with existing technologies through the support of popular technologies like Python using PyYAML library, Docker, or Ansible.


#### YAML vs JSON vs XML

- YAML (.yml):
  -- Human-readable code
  -- Minimalist syntax
  -- Solely designed for data
  -- Similar inline style to JSON (is a superset of JSON)
  -- Allows comments
  -- Strings without quotation marks
  -- Considered the “cleaner” JSON
  -- Advanced features (extensible data types, relational anchors, and mapping types preserving key order)
  -- Use Case: YAML is best for data-heavy apps that use DevOps pipelines or VMs. It’s also helpful for when other developers on your team will work with this data often and therefore need it to be more readable.

- JSON
  -- Harder to read
  -- Explicit, strict syntax requirements
  -- Similar inline style to YAML (some YAML parsers can read JSON files)
  -- No comments
  -- Strings require double quotes
  -- Use Case: JSON is favored in web development as it’s best for serialization formats and transmitting data over HTTP connections.

- XML
  -- Harder to read
  -- More verbose
  -- Acts as a markup language, while YAML is for data formatting
  -- Contains more features than YAML, like tag attributes
  -- More rigidly defined document schema
  -- Use Case: XML is best for complex projects that require fine control over validation, schema, and namespace. XML is not human-readable and requires more bandwidth and storage capacity, but offers unparalleled control.


#### Features YAML offers

- **Multi-document support**: You can have multiple YAML documents in a single YAML file to make file organization or data parsing easier.The separation between each document is marked by three dashes (---)

```yaml

---
player: playerOne
action: attack (miss)
---
player: playerTwo
action: attack (hit)
---

```

- **Built-in commenting**:YAML allows you to add comments to files using the hash symbol (#) similar to Python comments.

```yaml

key: #Here is a single-line comment 
   - value line 5
   #Here is a 
   #multi-line comment
 - value line 13

```

- **Readable syntax**: YAML files use an indentation system similar to Python to show the structure of your program. You’re required to use spaces to create indentation rather than tabs to avoid confusion. It also cuts much of the “noise” formatting found in JSON and XML files such as quotation marks, brackets, and braces. Together, these formatting specifications increase the readability of YAML files beyond XML and JSON.

YAML Example

```yaml

12345
Imaro:
   author: Charles R. Saunders
   language: English
   publication-year: 1981
   pages: 224

```

JSON Example

```json

12345678
{
  "Imaro": {
    "author": "Charles R. Saunders",
    "language": "English",
     "publication-year": "1981",
     "pages": 224,
  }
}

```

Notice that the same information is conveyed; however, the removal of double quotes, commas, and brackets throughout the YAML file makes it much easier to read at a glance.


- **Implicit and Explicit typing**: YAML offers versatility in typing by auto-detecting data types while also supporting explicit typing options. To tag data as a certain type, simply include !![typeName] before the value.

```yaml

# The value should be an int:
is-an-int: !!int 14.10
# Turn any value to a string:
is-a-str: !!str 67.43
# The next value should be a boolean:
is-a-bool: !!bool true

```

- **No executable commands**: As a data-representation format, YAML does not contain executables. It’s therefore very safe to exchange YAML files with external parties. YAML must be integrated with other languages, like Perl or Java, to add executables.


## Spring Framework

Spring Framework is een open source Java-platform dat uitgebreide infrastructuurondersteuning biedt voor het zeer eenvoudig en zeer snel ontwikkelen van robuuste Java-applicaties. Spring Framework is oorspronkelijk geschreven door Rod Johnson en werd voor het eerst uitgebracht onder de Apache 2.0-licentie in juni 2003. Deze tutorial is geschreven op basis van Spring Framework versie 4.1.6 uitgebracht in maart 2015.

### Waarom is Spring een must voor de ontwikkelaars?

Spring is het populairste applicatie-ontwikkelingsraamwerk voor Java EE (Enterprise Edition). Miljoenen ontwikkelaars over de hele wereld gebruiken Spring Framework om goed presterende, gemakkelijk testbare en herbruikbare code te maken.

Spring is lichtgewicht als het gaat om grootte en transparantie. De basisversie van het Spring-Framework is ongeveer 2 MB. De kernfuncties van het Spring Framework kunnen worden gebruikt bij het ontwikkelen van elke Java-applicatie, maar er zijn uitbreidingen voor het bouwen van webapplicaties bovenop het Java EE-platform. Spring Framework-doelen om J2EE-ontwikkeling gebruiksvriendelijker te maken en goede programmeerpraktijken te bevorderen door een op POJO gebaseerd programmeermodel mogelijk te maken. POJO staat voor plain-java-objects, simpele java objecten worden op elk project gebruikt.

### Applications of Spring

Hieronder volgt de lijst met enkele van de grote voordelen van het gebruik van Spring Framework −

- **POJO-gebaseerd**: Spring stelt ontwikkelaars in staat om enterprise-class applicaties te ontwikkelen met behulp van POJO's. Het voordeel van het gebruik van alleen POJO's is dat je geen EJB-containerproduct nodig hebt, zoals een applicatieserver, maar dat je de mogelijkheid hebt om alleen een robuuste servlet-container zoals Tomcat of een ander commercieel product te gebruiken.

- **Modulair**: De lente is modulair georganiseerd. Hoewel het aantal pakketten en klassen aanzienlijk is, hoeft u zich alleen zorgen te maken over degene die u nodig heeft en de rest te negeren.

- **Integratie met bestaande frameworks**: Spring vindt het wiel niet opnieuw uit, maar maakt echt gebruik van enkele van de bestaande technologieën, zoals verschillende ORM-frameworks, logging-frameworks, JEE-, Quartz- en JDK-timers en andere weergavetechnologieën.

- **Testabliteit**: Het testen van een applicatie die is geschreven met Spring is eenvoudig omdat omgevingsafhankelijke code naar dit framework wordt verplaatst. Door JavaBeanstyle POJO's te gebruiken, wordt het bovendien gemakkelijker om afhankelijkheidsinjectie te gebruiken voor het injecteren van testgegevens.

- **Web MVC**: Het webframework van Spring is een goed ontworpen web MVC-framework, dat een geweldig alternatief biedt voor webframeworks zoals Struts of andere overontwikkelde of minder populaire webframeworks.

- **Central Exception Handling**: Spring biedt een handige API om technologiespecifieke uitzonderingen (bijvoorbeeld door JDBC, Hibernate of JDO) om te zetten in consistente, ongecontroleerde uitzonderingen.

- **Lichtgewicht**: Lichtgewicht IoC-containers zijn doorgaans licht van gewicht, vooral in vergelijking met bijvoorbeeld EJB-containers. Dit is gunstig voor het ontwikkelen en implementeren van toepassingen op computers met beperkte geheugen- en CPU-bronnen.

- **Transactiebeheer**: Spring biedt een consistente interface voor transactiebeheer die kan worden geschaald naar een lokale transactie (bijvoorbeeld met een enkele database) en kan worden opgeschaald naar wereldwijde transacties (bijvoorbeeld met behulp van JTA).



## OPDRACHTEN


### OPDRACHT OBJECT-MAPPEN

In this assignment, you will learn how to do it in Java by using dataformat-xml — Jackson library extension in such a way that you only need to write your code once. 
We will go through the main features including model definition, serialization, and deserialization. Finally, you 
will be assigned to create a yaml object serializer and deserializer yourself.

 
#### Gebruikte technologieën en tools

- [ ] Intellij IDEA

- [ ] Maven 3.*

- [ ] JavaSE 11.*

- [ ] Spring Boot Starter

Laten we stap voor stap beginnen met het ontwikkelen van de Data-lezer applicatie met Maven als projectmanagement- en bouwtool.

Als je hebt de vereiste technologieën niet geïnstalleerd kunt je de volgende commando's' uitvoeren via het terminal (Windows 7+):

#### Scoop: Een commandolijn app-installer voor Windows

Zorg ervoor dat [PowerShell 5](https://aka.ms/wmf5download) (of hoger, inclusief [PowerShell Core](https://docs.microsoft.com/en-us/powershell/scripting/install/installing-powershell-core-on-windows?view=powershell-6)) en [.NET Framework 4.5](https://www.microsoft.com/net/download) (of hoger) zijn geïnstalleerd. Voer vervolgens het:

```powershell
Invoke-Expression (New-Object System.Net.WebClient).DownloadString('https://get.scoop.sh')

# or shorter
iwr -useb get.scoop.sh | iex
```

**Opmerking:** als u een foutmelding krijgt, moet u mogelijk het uitvoeringsbeleid wijzigen (d.w.z. Powershell inschakelen) met

```powershell
Set-ExecutionPolicy RemoteSigned -scope CurrentUser
```

**Scoop installeert de tools die je kent en waar je van houdt**. Om alle nodige applicaties automatisch te installeren voer de volgende uit: 

```powershell
scoop install sudo git curl wget corretto11-jdk maven IntelliJ-IDEA-Ultimate-portable
```


### Ontwikkelingsstappen

- [ ] Maak een eenvoudig Maven-project -> SpringKookboek
- [ ] Project Directory Structuur -> Maven
- [ ] Voeg gerelateerde dependencies naar het project toe.
- [ ] Voeg jar-afhankelijkheden toe aan pom.xml -> Automatisch of Manueel


### Project Directory Structuur

- [ ] src / main / java: maak de volgende lagen aan: models, repositories, services, views, configs, utils
- [ ] src / main / resources: voeg de volgende bestanden naar resources toe: data.xml, data.json, data.yml
- [ ] pom.xml: zorg ervoor dat pom.xml is al gecreëerd worden.

### Voeg jar-afhankelijkheden toe aan pom.xml

- [ ] Definieer het Parent-project

```xml

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.3</version>
    <relativePath/>
  </parent>

```

- [ ] Lombok (Optioneel)

```xml
<dependency>
<groupId>org.projectlombok</groupId>
<artifactId>lombok</artifactId>
<version>1.18.22</version>
</dependency>
```

- [ ] Spring Boot starter web

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.6.3</version>
</dependency>

```

- [ ] Spring Boot starter test

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

```


- [ ] Jackson XML Mapper

```xml

<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.13.1</version>
</dependency>

```

- [ ] Jackson JSON Mapper


```xml

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.13.1</version>
</dependency>


```


- [ ] Jackson YAML Mapper

```xml

<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-yaml</artifactId>
    <version>2.13.1</version>
</dependency>

```


- [ ] Eigenschappen van pom.xml zijn hieronder gedeeld:

```xml
<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
</properties>
```


- [ ]  Build-configuraties zijn hieronder gedeeld:

```xml

<build>

    <plugins>

      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <excludes>
            <exclude>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
            </exclude>
          </excludes>
        </configuration>
      </plugin>

    </plugins>

  </build>


```

### How to Parse JSON & XML Using the Same Code in Java






### Het uitvoeren van de code

- [ ] Voer de volgende terminal-commando uit om je project laten automatisch gebouwd worden: <code> mvn clean install</code>
- [ ] Om het app te starten voer de volgende commando op terminal uit: <code> mvn spring-boot:run </code>


### Aankondigingen

- Het type van dit opdracht: Individueel opdracht

Als je klaar bent, uploadeer je de code als zip-bestand naar "**<u>Persoonlijke privé-map</u>**". Vergeet niet dat een juiste naamgevingsconventie volgen essentieel is; bijvoorbeeld: "SpringKookboek Yilmaz Mustafa.zip". 
Zorgen ervoor dat je het mark-down-bestand (***.md) aan de zip hebt toegevoegd. Geen wachtwoord voor het zip is vereist.


### Het bericht van de Ch€f:

> > Begin waar je bent. Gebruik wat je hebt. Doe wat je kan.

