# <h1 align="center">OPENWEATHER - Forecast Città, temperatura</h1>

Questo è un progetto per l'esame di Programmazione ad Oggetti 2020/2021 in cui l'applicazione sviluppata è un servizio meteo che, data una o più città, faccia visualizzare tutte le informazioni attuali relative alla temperatura e le relative previsioni per i successivi 5 giorni. Il servizio dovrà salvare le informazioni attuali ogni 5 ore, infine dovrà generare delle statistiche. Il sistema potrà suggerire all'utente una lista predefinita di città da poter utilizzare.
 
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
 * 7.[ Test](#test)
 * 8.[ Software utilizzati ](#software)
 * 9.[ Autori ](#autori)
 
 
<a name="descr"></a>
## 1. Installazione

La nostra applicazione può essere clonata su Github tramite l'applicazione o tramite comando sul Prompt dei comandi.
 
```
git clone https://github.com/FilippoCaterbetti/ProgettoOOP
```

Una volta clonata può essere importata su un ambiente di sviluppo Java (come [Eclipse](https://www.eclipse.org/downloads/)), come *Existing Maven Project* nella cartella *Maven*

<a name="guida"></a>
## 2. Guida all'uso
É necessario installare un json-simple-1.1.1.jar disponibile [qui](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple111jar.htm) e aggiungerlo alla libreria del progetto.

Per un'esecuzione più veloce del programma, la ricerca della regex nella lista delle città si avvierà su un file JSON che deve essere salvato nella cartella del progetto e inserito all'interno con nome:

>city.list.json 

Questo file può essere scaricato dal sito ufficiale [qui](https://bulk.openweathermap.org/sample/) . Il file che ci serve è nominato *city.list.json.gz*
<h1 align="center"><img src="https://github.com/FilippoCaterbetti/ProgettoOOP/blob/794fe3f3a130d005049a36febe5b3118b3502047/UML/indexOfSimpleJson.png?raw=true" width="500" height="350"/></h1>

L'unità di misura impostata dal programma è il grado Celsius.
Se si vuole cambiare unità di misura basterà variare il valore della stringa `unit` [qui](https://github.com/FilippoCaterbetti/ProgettoOOP/blob/31403def4d639860b192962996a7306ad26d0939/OPENWEATHER/src/main/java/com/project/OPENWEATHER/service/ServiceApplication.java) .
Inserire " &units=imperial " per temperature in Fahrenheit, mentre basterà cancellare `unit` dal `link` e mettere sotto commento la variabile String `unit` se si vuole la temperatura in Kelvin (perché di default secondo documentazione dell'API)



<a name="api"></a>
## 3. API Reference
Come API abbiamo usato **5 day weather forecast** disponibile [qui](https://openweathermap.org/forecast5#name5)

Questa API, disponibile anche tramite un piano gratuito, permette di avere le previsioni a 5 giorni che sono disponibili in qualsiasi località o città. Include i dati delle previsioni del tempo con incrementi di 3 ore. La previsione è disponibile in formato JSON o XML.

Aprendo un account gratuito è possibile avere accesso a delle API Key personali che possono essre usati. Una volta ottenuta, è possibile sostituirla all'interno del progetto modificando il valore della variabile `ApiKey` nella classe [ServiceApplication](https://github.com/FilippoCaterbetti/ProgettoOOP/blob/31403def4d639860b192962996a7306ad26d0939/OPENWEATHER/src/main/java/com/project/OPENWEATHER/service/ServiceApplication.java#L46)


<a name="uml"></a>
## 4. Diagrammi UML

<a name="casiuso"></a>
### 4.1 Diagramma dei casi d'uso

<a name="classi"></a>
### 4.2 Diagramma delle classi

<a name="sequenze"></a>
### 4.3 Diagramma delle sequenze

<a name="app"></a>
## 5. Applicazione
Tramite L'API di [OpenWeather](https://openweathermap.org/forecast5#name5) il programma riceve i dati meteo della città 

Precisamente con dati meteo vengono restituiti la temperatura reale, percepita, massima, minima e media.
Ecco alcuni esempi di città
|    "id"   |  "name"    |  "country" |
|:---------:|:----------:|:----------:|
|  3173435  |   "Milan"  |    "IT"    |
|  3169070  |   "Rome"   |    "IT"    |
|  3181927  |  "Bologna" |    "IT"    |
|  6542126  |  "Ancona"  |    "IT"    |

<a name="rotte"></a>
## 6. Rotte
Le richieste effettuate dall'utente su Postman saranno disponibili all'indirizzo
```
localhost:8080
```
Le rotte disponibili sono:


<a name="test"></a>
## 7. Test
Sono stati implementati diversi test per controllare la correttezza dell'applicazione. Sono disponibili [qui](https://github.com/FilippoCaterbetti/ProgettoOOP/tree/main/OPENWEATHER/src/test/java/com/project/OPENWEATHER)


<a name="software"></a>
## 8. Software utilizzati
* Ambiente di sviluppo : [Eclipse](https://www.eclipse.org/downloads/)
* Software per diagrammi UML : [StarUML](https://staruml.io/) 
* Software per la gestione di librerie : [Maven](https://maven.apache.org/)
* Framework per applicazioni in Java : [Spring Boot](https://spring.io/projects/spring-boot)
* Ambiente di sviluppo API : [Postman](https://www.postman.com/)


<a name="autori"></a>
## 9. Autori
|         Autori          |   Commit  |
|-------------------------|:---------:|
| Filippo Caterbetti      |   33.3%   |
| Leandro Falasca Zamponi |   33.3%   |
| Roberta Cantarini       |   33.3%   |

