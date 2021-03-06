# <h1 align="center">OPENWEATHER - Forecast Città, temperatura</h1>

Questo è un progetto per l'esame di Programmazione ad Oggetti 2020/2021 in cui l'applicazione sviluppata è un servizio meteo che, data una città, faccia visualizzare tutte le informazioni attuali relative alla temperatura e le relative previsioni per i successivi 5 giorni. Il servizio dovrà salvare le informazioni attuali ogni 5 ore, infine dovrà generare delle statistiche con filtraggio di un periodo, di una stringa e di una soglia di errore. Il sistema potrà suggerire all'utente una lista predefinita di città da poter utilizzare.
 
 ### Indice
 * 1.[ Installazione ](#descr)
 * 2.[ Guida all'uso ](#guida)
 * 3.[ API reference ](#api)
 * 4.[ Diagrammi UML ](#uml)
   * 4.1 [ Diagramma casi d'uso](#casiuso) 
   * 4.2 [ Diagramma delle classi](#classi)
   * 4.3 [ Diagramma delle sequenze](#sequenze)
 * 5.[ Applicazione ](#app)
 * 6.[ Rotte ](#rotte)
   * 6.1 [ Metodo GET](#GET)
        * 6.1.1 [ /temp](#temp)
        * 6.1.2 [ /cities](#cities)
        * 6.1.3 [ /OpenWeather](#OpenWeather)
        * 6.1.4 [ /FiveHoursInfo](#FiveHourInfo)
   * 6.2 [ Metodo POST](#POST)
        * 6.2.1 [ /errors](#errors)
        * 6.2.2 [ /regex](#regex)
        * 6.2.3 [ /stats](#stats)
        * 6.2.4 [ /filters](#filters)
        * 6.2.5 [ /statsHistory](#statsHistory)
 * 7.[ Test](#test)
 * 8.[ Software utilizzati ](#software)
 * 9.[ Autori ](#autori)
 
 ---
<a name="descr"></a>
## 1. Installazione

La nostra applicazione può essere clonata su Github tramite l'applicazione o tramite comando sul Prompt dei comandi.
 
```
git clone https://github.com/FilippoCaterbetti/ProgettoOOP
```

Una volta clonata può essere importata su un ambiente di sviluppo Java (come [Eclipse](https://www.eclipse.org/downloads/)), come *Existing Maven Project* nella cartella *Maven*

---
<a name="guida"></a>
## 2. Guida all'uso
É necessario installare un json-simple-1.1.1.jar disponibile [qui](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple111jar.htm) e aggiungerlo alla libreria del progetto su *Build path* -> *Configure build path* -> *Libraries* -> *Add External Jar* .

Per un'esecuzione più veloce del programma, la ricerca della regex nella lista delle città si avvierà su un file JSON che deve essere salvato all'interno della cartella  *ProgettoOOP\OPENWEATHER*  del progetto e inserito all'interno con nome:

>city.list.json 

Questo file può essere scaricato dal sito ufficiale [qui](https://bulk.openweathermap.org/sample/) . Il file che ci serve è nominato *city.list.json.gz*
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/794fe3f3a130d005049a36febe5b3118b3502047/UML/indexOfSimpleJson.png?raw=true" width="500" height="350"/></h1>
Il file è stato inserito compresso per via delle dimensioni ( circa 40 MB ) che superano il limite imposto da GitHub di 25 MB.

L'unità di misura impostata dal programma è il grado Celsius.
Se si vuole cambiare unità di misura basterà variare il valore della stringa `unit` [qui](https://github.com/FilippoCaterbetti/ProgettoOOP/blob/31403def4d639860b192962996a7306ad26d0939/OPENWEATHER/src/main/java/com/project/OPENWEATHER/service/ServiceApplication.java) .
Inserire " &units=imperial " per temperature in Fahrenheit, mentre basterà cancellare `unit` dal `link` e mettere sotto commento la variabile String `unit` se si vuole la temperatura in Kelvin (perché di default secondo documentazione dell'API)


---
<a name="api"></a>
## 3. API Reference
Come API abbiamo usato **5 day weather forecast** disponibile [qui](https://openweathermap.org/forecast5#name5)

Questa API, disponibile anche tramite un piano gratuito, permette di avere le previsioni a 5 giorni che sono disponibili in qualsiasi località o città. Include i dati delle previsioni del tempo con incrementi di 3 ore. La previsione è disponibile in formato JSON o XML.

Aprendo un account gratuito è possibile avere accesso a delle API Key personali che possono essre usati. Una volta ottenuta, è possibile sostituirla all'interno del progetto modificando il valore della variabile `ApiKey` nella classe [ServiceApplication](https://github.com/FilippoCaterbetti/ProgettoOOP/blob/31403def4d639860b192962996a7306ad26d0939/OPENWEATHER/src/main/java/com/project/OPENWEATHER/service/ServiceApplication.java#L46)


---
<a name="uml"></a>
## 4. Diagrammi UML

<a name="casiuso"></a>
### 4.1 Diagramma dei casi d'uso
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/UseCaseDiagram1%20.png?raw=true"/></h1>

<a name="classi"></a>
### 4.2 Diagramma delle classi
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/ClassDiagram3.png?raw=true"/></h1>

com.project.OPENWEATHER
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/Diagramm%20Class%20(main).PNG?raw=true"/></h1>

com.project.OPENWEATHER.model
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/Diagramm%20Class%20(model).PNG?raw=true" width="500" height="350"/></h1>

com.project.OPENWEATHER.controller
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/Diagram%20Class%20(controller2).PNG?raw=true" width="500" height="350"/></h1>

com.project.OPENWEATHER.service
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/Diagram%20Class%20(service).PNG?raw=true" width="600" height="350"/></h1>

com.project.OPENWEATHER.error
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/Diagramm%20Class%20(error).PNG?raw=true" width="600" height="350"/></h1>

com.project.OPENWEATHER.StatsAndFilters
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/Diagram%20Class%20(statsAndFilters).PNG?raw=true" width="600" height="350"/></h1>

com.project.OPENWEATHER.exception
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/Diagram%20Class%20(exception).PNG?raw=true"/></h1>


<a name="sequenze"></a>
### 4.3 Diagramma delle sequenze
SequenceDiagram1
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/SequenceDiagram1.png?raw=true" width="500" height="350"/></h1>

SequenceDiagram2
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/SequenceDiagram2.png?raw=true"/></h1>


---
<a name="app"></a>
## 5. Applicazione
Tramite L'API di [OpenWeather](https://openweathermap.org/forecast5#name5) il programma riceve i dati meteo della città. 

Precisamente con dati meteo vengono restituiti la temperatura reale, percepita, massima, minima e media.

[Qui](https://github.com/FilippoCaterbetti/ProgettoOOP/tree/main/OPENWEATHER/temperature) trovate la cartella contenente i file con le temperature delle rispettive città con cui sono stati eseguite le prove.

Ecco alcuni esempi di città:
|    "id"   |  "name"    |  "country" |
|:---------:|:----------:|:----------:|
|  3173435  |   "Milano"  |    "IT"    |
|  3165523  |"Torino"    |    "IT"    |
|  3181927  |  "Bologna" |    "IT"    |
|  3183087  |  "Ancona"  |    "IT"    |



---
<a name="rotte"></a>
## 6. Rotte
Le richieste effettuate dall'utente su Postman saranno disponibili all'indirizzo:
```
localhost:8080
```
Le rotte disponibili sono:
| Metodo      |   Rotta    |  Descrizione |
|:---------:|:----------:|:----------|
|  `GET`  |   [/temp?name=Ancona](#temp)  |    Temperature delle prossime 24 ore    |
|  `GET`  |   [/cities](#cities)  |    Lista predefinita con alcune città consigliate    |
|  `GET`  |  [/OpenWeather?name=Ancona](#OpenWeather)  | Temperature dei prossimi 5 giorni     |
|  `GET`  |  [/FiveHoursInfo?name=Milano](#FiveHourInfo) |  Salva ogni cinque ore le temperature della città    |
|  `POST`  |  [/filters](#filters)  |   Filtra le statistiche in base alle informazioni che si vogliono |
|  `POST`  |   [/errors](#errors)  | Filtra le statistiche sulle temperature in base ad una soglia di errore e ai giorni di predizione  |
|  `POST`  |   [/stats](#stats)   | Mostra la media della temperatura reale, massima, minima, percepita e la la varianza delle tempertura reale e percepita a seconda del periodo   |
|  `POST`  |  [/findRegex](#regex) | Cerca una regex all'interno della lista delle città disponibili   |
|  `POST`  |  [/statsHistory](#statsHistory)  |  Filtra in base al periodo le statistiche sulle temperature della città  |

<b name="GET"></b>
### 6.1 Metodo GET

<b name="temp"></b>
### 6.1.1 /temp?name=
Rotta GET che mostra le temperature attuali e delle prossime 24 ore di una città qualsiasi inserita dall'utente.
Restituisce un JSONArray contenente i JSONObject con all'interno le informazioni sulle varie temperature disponibili, la data, l'ora.
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/rotte/Rottatemp.png?raw=true"/></h1>


---
<b name="cities"></b>
### 6.1.2 /cities
Rotta GET che mostra una lista di città consigliate all'utente.
Se si vogliono modificare le città basterà cambiare l'array di stringhe e la rotta troverà automaticamente tutti i valori necessari.
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/rotte/Rottacities.png?raw=true"/></h1>


---
<b name="OpenWeather"></b>
### 6.1.3 /OpenWeather?name=
Rotta  GET che mostra le temperature future della città inserita per i 5 giorni successivi (temperatura reale, massima, minima, percepita e media)

<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/rotte/RottaOpenWeather.png?raw=true"/></h1>

---
<a name="FiveHourInfo"></a>
### 6.1.4 /FiveHoursInfo?name=
Rotta GET che salva ogni cinque ore le temperature della città inserita dall'utente e restituisce il path del file dove vengono salvati.

<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/rotte/RottaFiveHoursInfo.png?raw=true"/></h1>


---
<a name="POST"></a>
### 6.2 Metodo POST




<b name="errors"></b>
### 6.2.1 /errors
Rotta POST che filtra le statistiche sulle temperature di una o più città in base ad una soglia di errore e ai giorni di predizione (da 1 a 5 giorni successivi).

L'utente deve inserire un Body di questo tipo:

```
{
    "città": [
        {
         "name": "Ancona"
        }
    ],
     "error": 1,
     "value": "$gt",
     "period": 3    
 }
 ```
 **error** è la soglia di errore
 Il **period** indica i giorni di predizione ed è compreso tra 1 e 5 (inclusi)
 Il **value** può essere di questo tipo
 |    Value    |   Significato  |
|:-----------:|:---------:|
| ` $gt `  |   > |
| ` =  ` |   = |
|  ` $lt  ` | <   |
  
  
 ---
 
 
 <b name="regex"></b>
###  6.2.2 /findRegex
Richiede un Body di questo tipo:
```
{
   	"regex" : "Ancon.*"
}
```


"regex" rappresenta la sottostringa da trovare e ricercare nel file JSON contenente le città con all'interno quella sottostringa, si prega di inserire la sintassi delle regex per qualsiasi tipo di ricerca. Nella tabella sono riportati alcuni esempi. Ricordiamo che è case sensitive, se vogliamo renderlo case insensitive usare la sintassi adatta.
  |    Regex    |   Descrizione  |
|:-----------:|:---------:|
| ` To.*  `  |   cerca tutte le parole che iniziano per *To* |
| ` .*to  `  |   cerca tutte le parole che finiscono per *to* |
| ` .*to.* ` |   cerca tutte le parole che hanno *to* all'interno della parola |
|  ` (?i).*to.*  ` |  per renderlo case insensitive   |
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/rotte/RottafindRegex.png?raw=true"/></h1>
  
  ---

<b name="stats"></b>
###  6.2.3 /stats
Rotta POST che mostra la media della temperatura reale, massima, minima e percepita, e la varianza delle tempertura reale e percepita a seconda del periodo scelto (da oggi a 5 giorni ).

Il Body deve essere di questo tipo:
```
{
	"città" : "Milano",
	"period" : "oggi"
}
```
|    period disponibili    |  
|:-----------:|
| ` oggi ` 
| ` 5 giorni ` o  ` cinque giorni` |
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/rotte/Rottastats.png?raw=true"/></h1>

---

<b name="filters"></b>
###  6.2.4 /filters
Rotta  POST che filtra le statistiche in base alle informazioni che si vogliono sapere.

Richiede un Body di questo tipo:
```
{
    "città": [
          {
            "name": "Milano"
          }
       ],
    "period": 1,
    "param" : "temp_max"
}
```
|    param disponibili   |   Period disponibili  |
|:-----------:|:---------:|
| ` temp_max `  |   ` 1 ` |
| ` temp_min ` |   ` 5 ` |
|  ` feels_like ` | /  |
|  ` temp ` | /  |
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/rotte/Rottafilters.png?raw=true"/></h1>


---

<b name="statsHistory"></b>
###  6.2.5 /statsHistory
Rotta di tipo POST che filtra, in base al periodo, le statistiche sulle temperature di una o più città.

Il Body deve essere di questo tipo: 
```
{
    "città": [
        {
            "name": "Bologna"  
        }
    ],
    "period": "giornaliera"
}
```
 |    Città disponibili    |   Period disponibili  |
|:-----------:|:---------:|
| ` Ancona `  |   ` giornaliera ` |
| ` Milano ` |   ` settimanale ` |
|  ` Torino ` | ` mensile `   |
|  ` Bologna ` | /  |

---

<a name="test"></a>
## 7. Test
Sono stati implementati diversi test per controllare la correttezza dell'applicazione. Sono disponibili [qui](https://github.com/FilippoCaterbetti/ProgettoOOP/tree/main/OPENWEATHER/src/test/java/com/project/OPENWEATHER)

<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/main/UML/test/Class%20Diagram%20(Test).PNG?raw=true"/></h1>

---
<a name="software"></a>
## 8. Software utilizzati
* Ambiente di sviluppo : [Eclipse](https://www.eclipse.org/downloads/)
* Software per diagrammi UML : [StarUML](https://staruml.io/) 
* Software per la gestione di librerie : [Maven](https://maven.apache.org/)
* Framework per applicazioni in Java : [Spring Boot](https://spring.io/projects/spring-boot)
* Ambiente di sviluppo API : [Postman](https://www.postman.com/)

---
<a name="autori"></a>
## 9. Autori
|         Autori          |   Commit  |
|-------------------------|:---------:|
| Filippo Caterbetti      |   33.3%   |
| Leandro Falasca Zamponi |   33.3%   |
| Roberta Cantarini       |   33.3%   |

