# Java-SE-Projects
**ITMO university projects for mastering Java SE technologies.**
The major project of this repository is CollectionManagerGUI. All projects before this are intermediate.

## Description
This is a Java app, that provides remote database access, working with data using special commands, support for many users through authorization. Multithreaded request processing for different operations processing (authorization, reading request, executing request, sending responses) is implemented on the server side using Thread Pools. Also client GUI implemented too. For client GUI implementation involved JavaFX and its various capabilities like TableView, Observable lists for data updates for various users in live mode etc.

## Technologies used
* **JDBC** for DB access using PostgreSQL
* **TCP** for connection
* **JWT** for monitor the sessions of different users
* **Gradle** as primary assembler

* Also Java capabilities, that were used: Concurrency, Stream API, Serialization, Executors.

## Patterns used
* Builder
* Singleton
* Factory method
* Command
* Template method

