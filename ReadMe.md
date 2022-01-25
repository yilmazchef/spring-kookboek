# Java EE kookboek van **Ch€f** - IoC, DI, XML, JSON, YAML, Spring

---

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

XML wordt gebruikt in vele aspecten van webontwikkeling. Het wordt vaak gebruikt om gegevens van presentatie te scheiden.

- XML wordt gebruikt in vele aspecten van webontwikkeling. Het wordt vaak gebruikt om gegevens van presentatie te scheiden.- XML scheidt gegevens van presentatie: XML bevat geen informatie over hoe deze moet worden weergegeven. Dezelfde XML-gegevens kunnen in veel verschillende presentatiescenario's worden gebruikt. Hierdoor is er met XML een volledige scheiding tussen data en presentatie.

- XML is vaak een aanvulling op HTML: in veel HTML-toepassingen wordt XML gebruikt om gegevens op te slaan of te transporteren, terwijl HTML wordt gebruikt om dezelfde gegevens op te maken en weer te geven.

- XML scheidt gegevens van HTML: Wanneer u gegevens in HTML weergeeft, hoeft u het HTML-bestand niet te bewerken wanneer de gegevens veranderen. Met XML kunnen de gegevens worden opgeslagen in afzonderlijke XML-bestanden. Met een paar regels JavaScript-code kunt u een XML-bestand lezen en de gegevensinhoud van elke HTML-pagina bijwerken.

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

U leert veel meer over het gebruik van XML en JavaScript in de DOM van deze zelfstudie.

#### XML Transaction Data

Er bestaan duizenden XML-indelingen, in veel verschillende industrieën, om dagelijkse gegevenstransacties te beschrijven:

- Stocks en Aandelen
- Financiële transacties
- Medische gegevens
- Wiskundige gegevens
- Wetenschappelijke metingen
- Nieuws informatie
- Weerdiensten


#### Voorbeelden: XML Nieuws

**XMLNews is a specification for exchanging news and other information.**

Het gebruik van een standaard maakt het voor zowel nieuwsproducenten als nieuwsconsumenten gemakkelijker om elke vorm van nieuwsinformatie te produceren, ontvangen en archiveren in verschillende hardware-, software- en programmeertalen.

Een voorbeeld  XMLNews document:

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

Een XML nationale weerdienst van NOAA (National Oceanic and Atmospheric Administration):

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

JSON is een **tekstformaat** voor het opslaan en transporteren van gegevens

JSON is "zelfbeschrijvend" en gemakkelijk te begrijpen

#### Voorbeelden: Javascript program

Dit voorbeeld is een JSON-data:

'{"name":"John", "age":30, "car":null}'

Het definieert een object met 3 eigenschappen:

-   name
-   age
-   car

Elke property heeft een waarde.

Als u de JSON-data parseert met een JavaScript-programma, hebt u toegang tot de gegevens als een object:

let personName = obj.name;  
let personAge = obj.age;


#### Wat is JSON?

- JSON is een lichtgewicht data-uitwisseling formaat
- JSON is platte tekst geschreven in JavaScript-objectnotatie
- JSON wordt gebruikt om gegevens tussen computers te verzenden
- JSON is taalonafhankelijk

De JSON-syntaxis is afgeleid van JavaScript-objectnotatie, maar de JSON-indeling is alleen tekst. Code voor het lezen en genereren van JSON bestaat in veel programmeertalen.

#### Waarom wordt  JSON gebruikt?

De JSON-indeling is syntactisch vergelijkbaar met de code voor het maken van JavaScript-objecten. Hierdoor kan een JavaScript-programma eenvoudig JSON-gegevens converteren naar JavaScript-objecten.

Omdat de indeling alleen tekst is, kunnen JSON-gegevens eenvoudig tussen computers worden verzonden en door elke programmeertaal worden gebruikt.

JavaScript heeft een ingebouwde functie voor het converteren van JSON-tekenreeksen naar JavaScript-objecten:

`JSON.parse()`

JavaScript heeft ook een ingebouwde functie voor het converteren van een object naar een JSON-tekenreeks:

`JSON.stringify()`

U kunt pure tekst van een server ontvangen en deze als JavaScript-object gebruiken.

U kunt een JavaScript-object naar een server verzenden in pure tekstindeling.

U kunt met gegevens werken als JavaScript-objecten, zonder ingewikkelde parsering en vertalingen.


#### Bewaren van JSON Data

Bij het opslaan van gegevens moeten de gegevens een bepaald formaat hebben en ongeacht waar u ervoor kiest om ze op te slaan, is _text_ altijd een van de wettelijke formaten.

JSON maakt het mogelijk om JavaScript-objecten als tekst op te slaan.

### YAML

#### Wat is YAML?

YAML is een data serialisatietaal voor het opslaan van informatie in een door mensen leesbare vorm. Het stond oorspronkelijk voor "Yet Another Markup Language" maar is sindsdien veranderd in "YAML Ain't Markup Language" om zich te onderscheiden als anders dan een echte markup-taal.

Het is vergelijkbaar met XML- en JSON-bestanden, maar gebruikt een meer minimalistische syntaxis, zelfs met behoud van vergelijkbare mogelijkheden. YAML wordt vaak gebruikt om configuratiebestanden te maken in IoC-programma's (Infrastructure as Code) of om containers in de DevOps-ontwikkelingspijplijn te beheren.

Meer recent is YAML gebruikt om automatiseringsprotocollen te maken die een reeks opdrachten kunnen uitvoeren die in een YAML-bestand worden vermeld. Dit betekent dat uw systemen onafhankelijker en responsiever kunnen zijn zonder extra aandacht van ontwikkelaars.

Naarmate meer en meer bedrijven DevOps en virtualisatie omarmen, wordt YAML snel een must-have vaardigheid voor moderne ontwikkelaarsposities. YAML is ook gemakkelijk te integreren met bestaande technologieën door de ondersteuning van populaire technologieën zoals Python met behulp van PyYAML-bibliotheek, Docker of Ansible.


#### YAML vs JSON vs XML

- YAML (.yml):
  -- Door mensen leesbare code
  -- Minimalistische syntaxis
  -- Uitsluitend ontworpen voor gegevens
  - Vergelijkbaar inline stijl met JSON (is een superset van JSON)
    -- Staat opmerkingen toe
    -- Tekenreeksen zonder aanhalingstekens
    -- Beschouwd als de "schonere" JSON
    -- Geavanceerde functies (uitbreidbare gegevenstypen, relationele ankers en toewijzingstypen met behoud van sleutelvolgorde)
    -- Use Case: YAML is het beste voor data-zware apps die DevOps-pijplijnen of VM's gebruiken. Het is ook handig voor wanneer andere ontwikkelaars in uw team vaak met deze gegevens werken en daarom meer leesbaar moeten zijn.

- JSON
  -- Moeilijker te lezen
  -- Expliciete, strenge syntaxisvereisten
  - Vergelijkbare inline stijl als YAML (sommige YAML-parsers kunnen JSON-bestanden lezen)
    -- Geen reacties
    -- Strings vereisen dubbele aanhalingstekens
  - Use Case: JSON heeft de voorkeur in webontwikkeling omdat het het beste is voor serialisatieformaten en het verzenden van gegevens via HTTP-verbindingen.

- XML
  -- Moeilijker te lezen
  -- Meer uitgebreid
  - Fungeert als een opmaaktaal, terwijl YAML voor gegevensopmaak is
    -- Bevat meer functies dan YAML, zoals tag attributen
    -- Meer rigide gedefinieerd documentschema
    -- Use Case: XML is het beste voor complexe projecten die nauwkeurige controle over validatie, schema en naamruimte vereisen. XML is niet leesbaar voor mensen en vereist meer bandbreedte en opslagcapaciteit, maar biedt ongeëvenaarde controle.


#### Eigenschappen van YAML

- **Ondersteuning voor meerdere documenten**: U kunt meerdere YAML-documenten in één YAML-bestand hebben om het instellen of parseren van bestanden eenvoudiger te maken. De scheiding tussen elk document wordt gemarkeerd door drie streepjes (---)

```yaml

---
player: playerOne
action: attack (miss)
---
player: playerTwo
action: attack (hit)
---

```

- **Ingebouwde opmerkingen**:MET YAML kunt u opmerkingen toevoegen aan bestanden met behulp van het hash-symbool (#), vergelijkbaar met Python-opmerkingen.

```yaml

key: #Here is a single-line comment 
   - value line 5
   #Here is a 
   #multi-line comment
 - value line 13

```

- **Leesbare syntaxis**: YAML-bestanden gebruiken een inspringingssysteem vergelijkbaar met Python om de structuur van uw programma weer te geven. U moet spaties gebruiken om inspringingen te maken in plaats van tabbladen om verwarring te voorkomen. Het snijdt ook veel van de "ruis" -opmaak in JSON- en XML-bestanden, zoals aanhalingstekens, haakjes en accolades. Samen verhogen deze opmaakspecificaties de leesbaarheid van YAML-bestanden die verder gaan dan XML en JSON.

Een voorbeeld van YAML-data:

```yaml

12345
Imaro:
   author: Charles R. Saunders
   language: English
   publication-year: 1981
   pages: 224

```

Een voorbeeld JSON-data

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

Merk op dat dezelfde informatie wordt overgebracht; Het verwijderen van dubbele aanhalingstekens, komma's en haakjes in het YAML-bestand maakt het echter veel gemakkelijker om in één oogopslag te lezen.


- ** Impliciet en expliciet typen **: YAML biedt veelzijdigheid in typen door gegevenstypen automatisch te detecteren, terwijl het ook expliciete typopties ondersteunt. Om gegevens als een bepaald type te taggen, neemt u eenvoudig !! [typeNaam] voor de waarde.

```yaml

# The value should be an int:
is-an-int: !!int 14.10
# Turn any value to a string:
is-a-str: !!str 67.43
# The next value should be a boolean:
is-a-bool: !!bool true

```

- **Geen uitvoerbare opdrachten**: Als gegevensrepresentatie-indeling bevat YAML geen uitvoerbare bestanden. Het is daarom zeer veilig om YAML-bestanden uit te wisselen met externe partijen. YAML moet worden geïntegreerd met andere talen, zoals Perl of Java, om uitvoerbare bestanden toe te voegen.


## Spring Framework

Spring Framework is een open source Java-platform dat uitgebreide infrastructuurondersteuning biedt voor het zeer eenvoudig en zeer snel ontwikkelen van robuuste Java-applicaties. Spring Framework is oorspronkelijk geschreven door Rod Johnson en werd voor het eerst uitgebracht onder de Apache 2.0-licentie in juni 2003. Deze tutorial is geschreven op basis van Spring Framework versie 4.1.6 uitgebracht in maart 2015.

### Waarom is Spring een must voor de ontwikkelaars?

Spring is het populairste applicatie-ontwikkelingsraamwerk voor Java EE (Enterprise Edition). Miljoenen ontwikkelaars over de hele wereld gebruiken Spring Framework om goed presterende, gemakkelijk testbare en herbruikbare code te maken.

Spring is lichtgewicht als het gaat om grootte en transparantie. De basisversie van het Spring-Framework is ongeveer 2 MB. De kernfuncties van het Spring Framework kunnen worden gebruikt bij het ontwikkelen van elke Java-applicatie, maar er zijn uitbreidingen voor het bouwen van webapplicaties bovenop het Java EE-platform. Spring Framework-doelen om J2EE-ontwikkeling gebruiksvriendelijker te maken en goede programmeerpraktijken te bevorderen door een op POJO gebaseerd programmeermodel mogelijk te maken. POJO staat voor plain-java-objects, simpele java objecten worden op elk project gebruikt.

### Applicaties van het Spring Framework

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

#### Ontwikkelingsstappen

- [ ] Maak een eenvoudig Maven-project -> SpringKookboek
- [ ] Project Directory Structuur -> Maven
- [ ] Voeg gerelateerde dependencies naar het project toe.
- [ ] Voeg jar-afhankelijkheden toe aan pom.xml -> Automatisch of Manueel

#### Project Directory Structuur

- [ ] src / main / java: maak de volgende lagen aan: models, repositories, services, views, configs, utils
- [ ] src / main / resources: voeg de volgende bestanden naar resources toe: data.xml, data.json, data.yml
- [ ] pom.xml: zorg ervoor dat pom.xml is al gecreëerd worden.

#### Voeg jar-afhankelijkheden toe aan pom.xml

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


- [ ] Model definitie
  Laten we een artikelmodel bouwen. JSON-versie van een artikel wordt in de volgende kern gepresenteerd:


```json

{
  "title": "Amazing history of XML.",
  "publicationDate": "12/04/1575",
  "authors": [
    {
      "firstName": "John",
      "lastName": "Doe"
    },
    {
      "firstName": "Marry",
      "lastName": "Boe"
    }
  ],
  "content": "History of XML is not amazing. Fin."
}

```

Het artikelmodel bestaat uit een titel, publicatiedatum die de DD/MM/JJJJ-indeling gebruikt (DD-dag, MM-maand, JJJJ-jaar) en een reeks auteurs. Elke auteur heeft de voornaam en de tweede naam. Aan het einde hebben we een inhoudelijk element. XML kan op deze manier worden gemodelleerd:

```xml

<?xml version="1.0" encoding="UTF-8" ?>
<article>
    <title>Amazing history of XML.</title>
    <publicationDate>12/04/1575</publicationDate>
    <authors>
        <author>
            <firstName>John</firstName>
            <lastName>Doe</lastName>
        </author>
        <author>
            <firstName>Marry</firstName>
            <lastName>Boe</lastName>
        </author>
    </authors>
    <content>History ox XML is not amazing. Fin.</content>
</article>   

```

Onze Java POJO's(Plain Old Java Object)gebruiken String, java.util.Date en Lijst types:

```java

public class Article {
   private String title;
   private Date publicationDate;
   private List<Author> authors;
   private String content;
}

```

```java

public class Author {
   private String firstName;
   private String lastName;
}

```


- [ ] Serialisatie

Over het algemeen kunnen we serialisatie begrijpen als een proces van het transformeren van gegevens in bits. Geserialiseerde gegevens kunnen via een netwerk worden overgedragen of opgeslagen en indien nodig opnieuw worden gemaakt. In onze use-case bouwen we een artikelobject en transformeren we het in JSON- en XML-tekenreeksen.

Bekijk het artikel parser en zijn serialisatiemethoden:

```java

public class ArticleParser {

  private static final ObjectMapper XML_MAPPER = new XmlMapper();
  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

  static {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
    XML_MAPPER.setDateFormat(simpleDateFormat);
    JSON_MAPPER.setDateFormat(simpleDateFormat);
  }

  public static String serialize(Article article, Format format) {
    return format.equals(Format.JSON) ? serialize(article, JSON_MAPPER) : serialize(article, XML_MAPPER);
  }

  private static String serialize(Article article, ObjectMapper objectMapper) {
    try {
      return objectMapper.writeValueAsString(article);
    } catch (IOException e) {
      throw new UncheckedIOException("Oh no!", e);
    }
  }
}

```

Allereerst, om één code te gebruiken om zowel XML als JSON te serialiseren, hebben we twee ObjectMappersnodig . De eerste is de standaard Jackson's ObjectMapper (JSON_MAPPER in regel 3) voor JSON-formaat en XmlMapper (XML_MAPPER in regel 2) voor XML-formaat. Daarnaast stellen we de datumnotatie van ObjectMappers in op onze behoeften (regels 6-8).

De tweede is een speciale versie van dataformat-xml library. Het werkt op JSON-abstracties onder de motorkap, maar het is in staat om geldige XML's te produceren.

De methode serialiseren in regel 11 neemt een instantie van een artikel dat we willen serialiseren en de indeling die een enumtype is (XML, JSON). Vervolgens wordt de private methode serialize (regel 15) aangeroepen met de juiste ObjectMapper-instantie.

Ten slotte transformeren we het artikel naar een tekenreeks door de methode writeValueAsString in regel 16 aan te roepen.

Om het te laten werken hebben we nog een belangrijk onderdeel nodig. In onze artikel- en auteursmodellen moeten we velden annoteren, zodat Jackson weet hoe hij het in en van een bepaald formaat moet plaatsen. Laten we eens kijken naar de geannoteerde velden van artikel- en auteurklassen:

```java

public class Article {
  @JsonProperty
  private String title;

  @JsonProperty
  private Date publicationDate;

  @JsonProperty
  private List<Author> authors;

  @JsonProperty
  @JsonSerialize(using = CustomSerializer.class)
  private String content;
}

```

```java

public class Author {
  @JsonProperty
  private String firstName;

  @JsonProperty
  private String lastName;
}

```

JsonProperty is de meest basale annotatie. Standaard zorgt het ervoor dat Jackson de veldnaam gebruikt zoals deze is. De veldtitel Tekenreeks wordt bijvoorbeeld geserialiseerd als een titelveld in JSON en XML. Als uw veldnaam afwijkt van de veldnamen in de opgegeven indeling, kunt u deze aanpassen door de waarde voor de annotatie te definiëren. Hier is een voorbeeld: JsonProperty(value ="myTitle").

Zoals u kunt zien in regel 8 zal Jackson standaardcontainers behandelen met dezelfde JsonProperty-annotatie. We hoefden alleen maar velden van de klasse Auteur te annoteren. De hele collectie wordt door de bibliotheek geserialiseerd en gedeserialiseerd.

Soms hebben we aangepaste serializers nodig. Om dit te doen kunt u JsonSerialize-annotaties gebruiken die een klassenaam van een serializer nemen als de waarde voor "using" attribuut (regel 12). De aangepaste serialisator kan de klassen JsonSerializer of StdSerializer uitbreiden en de serialisatiemethode implementeren.

Als we de waarde van het inhoudsveld willen inkorten, kunnen we de volgende aangepaste serializer gebruiken:


```java

public class CustomSerializer extends JsonSerializer<String> {

  @Override
  public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      gen.writeString(value.trim());
  }
}


```

Laten we een artikelobject bouwen en serialiseren naar JSON en XML met behulp van de code die wordt gepresenteerd in ArticleParser:

```java

package be.intec.springkookboek.views;


import be.intec.springkookboek.models.Article;
import be.intec.springkookboek.models.Author;
import be.intec.springkookboek.models.Format;
import be.intec.springkookboek.utils.ArticleParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class MainView implements CommandLineRunner {

	private static final Logger LOG = Logger.getLogger(MainView.class.getName());

	public static void main( String[] args ) {

		SpringApplication.run( MainView.class, args );
	}


	@Override
	public void run( final String... args ) throws Exception {

		Article article = new Article()
				.setTitle( "The art of parsing." )
				.setPublicationDate( new Date() )
				.setAuthors( List.of( new Author( "Justin", "Bieber" ), new Author( "Nikola", "Tesla" ) ) )
				.setContent( "Interesting story." );

		String xmlArticle = ArticleParser.serialize( article, Format.XML );
		String jsonArticle = ArticleParser.serialize( article, Format.JSON );

		LOG.info( xmlArticle );
		LOG.info( jsonArticle );
	}

}



```

Laten we de uitvoer voor beide formaten controleren.

```json

{
  "title": "The art of parsing.",
  "publicationDate": "29/09/2020",
  "content": "Interesting story.",
  "authors": [
    {
      "firstName": "John",
      "lastName": "Smith"
    },
    {
      "firstName": "Betty",
      "lastName": "Kowalski"
    }
  ]
}

```

```xml

<Article>
	<title>The art of parsing.</title>
	<publicationDate>29/09/2020</publicationDate>
	<author>
		<author>
			<firstName>John</firstName>
			<lastName>Smith</lastName>
		</author>
		<author>
			<firstName>Betty</firstName>
			<lastName>Kowalski</lastName>
		</author>
	</author>
	<content>Interesting story.</content>
</Article>

```

JSON ziet er goed uit, maar XML... Er ging iets mis. Allereerst wordt het hoofdelement "Artikel" genoemd met de hoofdletter en we wilden "artikel". Het tweede probleem is zichtbaar in regel 4. Collection of Authors werd geserialiseerd met het inpakelement "author". Het moeten echter "auteurs" zijn met "auteur" -elementen erin.

Om deze problemen op te lossen, laten we kleine wijzigingen aanbrengen in de artikelklasse:

```java

@JacksonXmlRootElement(localName = "article")
public class Article {

  @JsonProperty
  private String title;

  @JsonProperty
  private Date publicationDate;

  @JsonProperty
  @JacksonXmlElementWrapper(localName = "authors")
  @JacksonXmlProperty(localName = "author")
  private List<Author> authors;

  @JsonProperty
  @JsonSerialize(using = CustomSerializer.class)
  private String content;
}


```

In de eerste regel hebben we de annotatie JacksonXmlRootElement(localName = "article")toegevoegd. Het lost het probleem op met het element artikel met hoofdletters.

In regel 11 en 12 hebben we het probleem opgelost met de verzameling auteurs. Om het wrapping element van de collectie te hernoemen werd JacksonXmlElementWrapper annotatie gebruikt en ingesteld op "auteurs". Daarnaast stellen we de waarde localName van de JacksonXmlProperty-annotatie in op "author". Als gevolg hiervan krijgen we de volgende XML, die aan onze eisen voldoet:


```xml

<article>
	<title>The art of parsing.</title>
	<publicationDate>29/09/2020</publicationDate>
	<content>Interesting story.</content>
	<authors>
		<author>
			<firstName>John</firstName>
			<lastName>Smith</lastName>
		</author>
		<author>
			<firstName>Betty</firstName>
			<lastName>Kowalski</lastName>
		</author>
	</authors>
</article>


```

- [ ] Deserialisatie

Laten we nu eens kijken naar de code die artikelen deserializeert. We laden het bestand met JSON- en XML-versies van artikelen en zetten deze om in echte Java-objectinstanties.

```java

 public static Article deserialize(String article, Format format) {
    return format.equals(Format.JSON) ? deserialize(article, JSON_MAPPER) : deserialize(article, XML_MAPPER);
  }

  private static Article deserialize(String article, ObjectMapper objectMapper) {
    try {
      return objectMapper.readValue(article, Article.class);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException("Bad!", e);
    }
  }


```

De bovenstaande code is analoog aan de serialisatiecode. We gebruiken dezelfde truc met het leveren van de juiste ObjectMapper voor een bepaald formaat en we houden dezelfde code voor deserialisatie zelf.

In regel 7 gebruiken we de readValue-methode van objectMapper en bieden we het stringbevattende artikel plus article.class als tweede argument. Het resulteert in het artikelobject.

Met alle elementen op hun plaats kunnen we JSON- en XML-bestanden laden en deserialiseren:

```java

String jsonStringArticle = Files.readString(Paths.get("src/main/resources/article.json"));
Article deserializedJsonArticle = ArticleParser.deserialize(jsonStringArticle, Format.JSON);
LOG.info(deserializedJsonArticle.toString());

    String xmlStringArticle = Files.readString(Paths.get("src/main/resources/article.xml"));
    Article deserializedXmlArticle = ArticleParser.deserialize(xmlStringArticle, Format.XML);
    LOG.info(deserializedXmlArticle.toString());

```

Logboeken laten ons zien of alle artikeleigenschappen correct zijn ingesteld:

- [ ]  Pak het af!

De belangrijkste afhaalmaaltijd uit dit artikel is een oplossing om één code te hebben voor twee veelgebruikte formaten. Dankzij zo'n aanpak hebben we minder code te onderhouden. En dat is meestal maar goed ook.

Ik moedig u aan om een kijkje te nemen in dataformaat-xml-documentatie om meer details te vinden over de functies (met name streaming API die erg handig is bij het omgaan met documenten die niet in het geheugen passen) en de beperkingen ervan.

Je kunt de hele code vinden in het artikel op de Github repository:
https://github:com/yilmazchef/spring-kookboek



#### Het uitvoeren van de code

- [ ] Voer de volgende terminal-commando uit om je project laten automatisch gebouwd worden: <code> mvn clean install</code>
- [ ] Om het app te starten voer de volgende commando op terminal uit: <code> mvn spring-boot:run </code>

#### Aankondigingen over de opdracht

- Het type van dit opdracht: Individueel opdracht

Als je klaar bent, uploader je de code als zip-bestand naar "**<u>Persoonlijke privé-map</u>**". Vergeet niet dat een juiste naamgevingsconventie volgen essentieel is; bijvoorbeeld: "SpringKookboek Yilmaz Mustafa.zip".
Zorgen ervoor dat je het mark-down-bestand (***.md) aan de zip hebt toegevoegd. Geen wachtwoord voor het zip is vereist.



### Het bericht van de Ch€f:

> > Begin waar je bent. Gebruik wat je hebt. Doe wat je kan.

