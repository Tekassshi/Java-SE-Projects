# Java-SE-Projects
**ITMO university projects for mastering Java SE technologies.**
The major project of this repository is **CollectionManagerGUI**. 
All projects before this are intermediate.

## Description
This is a Java app, that provides remote database access, working with data using special commands, support for many users through authorization. Multithreaded request processing for different operations processing (authorization, reading request, executing request, sending responses) is implemented on the server side using Thread Pools. Also client GUI implemented too. For client GUI implementation involved JavaFX and its various capabilities like TableView, Observable lists for data updates for various users in live mode etc. App supports 5 languages (English, Russian, Slovak, Spanish, Swedish).

## Technologies used
* **JDBC** for DB access using PostgreSQL
* **TCP** for connection
* **JWT** for monitor the sessions of different users
* **Log4j** for logging
* **Gradle** as primary assembler

Also Java capabilities, that were used: Concurrency, Stream API, Serialization, Executors.

## Patterns used
* Builder
* Singleton
* Factory method
* Command
* Template method

## GUI work examples
<img width="527" alt="Снимок экрана 2023-07-12 153024" src="https://github.com/tekassh1/Java-SE-Projects/assets/90504722/9ce8e931-2480-4715-a50b-37fec1530cf3">
<img width="527" alt="Снимок экрана 2023-07-12 153402" src="https://github.com/tekassh1/Java-SE-Projects/assets/90504722/ff019d73-ded4-4c31-896d-d010e98dde87">
<img width="707" alt="Снимок экрана 2023-07-12 153228" src="https://github.com/tekassh1/Java-SE-Projects/assets/90504722/bb347ecf-b33f-4046-9266-749f65216101">
<img width="707" alt="Снимок экрана 2023-07-12 153246" src="https://github.com/tekassh1/Java-SE-Projects/assets/90504722/c9eee671-3311-4961-8986-2587a3240cfc">
<img width="707" alt="Снимок экрана 2023-07-12 153255" src="https://github.com/tekassh1/Java-SE-Projects/assets/90504722/210298b7-a319-40f7-b3d8-b574f56ff6cc">
<img width="708" alt="Снимок экрана 2023-07-12 153336" src="https://github.com/tekassh1/Java-SE-Projects/assets/90504722/5a812c03-4f93-4246-b211-51108ffe6c04">
