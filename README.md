# <h1 align="center">OPENWEATHER - Forecast Città, temperatura</h1>

Questo è un progetto per l'esame di Programmazione ad Oggetti 2020/2021 in cui l'applicazione sviluppata è un servizio meteo che, data una o più città, faccia visualizzare tutte le informazioni attuali relative alla temperatura e le relative previsioni per i successivi 5 giorni. Il servizio dovrà salvare le informazioni attuali ogni 5 ore, infine dovrà generare delle statistiche. Il sistema potrà suggerire all'utente una lista predefinita di città da poter utilizzare.
 
## Installazione

La nostra applicazione può essere clonata su Github tramite l'applicazione o tramite comando sul Prompt dei comandi.
 

```
git clone https://github.com/FilippoCaterbetti/ProgettoOOP
```

Una volta clonata può essere importata su un ambiente di sviluppo Java (come [Eclipse](https://www.eclipse.org/downloads/)), come *Existing Maven Project* nella cartella *Maven*

## Guida all'uso
É necessario installare un json-simple-1.1.1.jar disponibile [qui](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple111jar.htm)

Per un'esecuzione più veloce del programma la ricerca della regex nella lista delle città si avvierà su un file JSON salvato in locale e inserito al'interno del progetto come:

>city.list.json 


Questo file può essere scaricato dal sito ufficiale [qui](https://bulk.openweathermap.org/sample/) . Il file che ci serve è nominato *city.list.json.gz*



## API Reference
Come API abbiamo usato **5 day weather forecast** disponibile [qui](https://openweathermap.org/forecast5#name5)

Questa API disponibile anche tramite un piano gratuito, permette di avere le previsioni a 5 giorni che sono disponibili in qualsiasi località o città. Include i dati delle previsioni del tempo con incrementi di 3 ore. La previsione è disponibile in formato JSON o XML.


esempi di città e relativi ID da poter usare velocemente per i test



{
  "city":{
      "id":3173435,
      "name":"Milan",
      "country":"IT"
   }
}


{
  "city":{
      "id":3169070,
      "name":"Rome",
      "country":"IT"
   }
}

{
  "city":{
      "id":6542126,
      "name":"Ancona",
      "country":"IT"
   }
}

{
  "city":{
      "id":3181927,
      "name":"Bologna",
      "country":"IT"
   }
}

{
  "city":{
      "id":6550,
      "name":"Paris",
      "country":"FR"
   }
}


