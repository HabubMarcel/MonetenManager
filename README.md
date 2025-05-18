# 💰Monetenmanager

## Einführung

**Monetenmanager** ist eine Java-Konsolenanwendung zur Verwaltung persönlicher Finanzen.  
Nutzer können Kategorien anlegen, Transaktionen erfassen, Budgets setzen und sich Monatsübersichten anzeigen lassen.

Das Projekt wurde im Rahmen eines Praxisprojekts nach **Domain Driven Design (DDD)** und **Clean Architecture** entwickelt.  
Es verfolgt einen CLI-basierten Ansatz zur einfachen Nutzung direkt im Terminal.

---

## Projektübersicht

### Technologien

- **Java 19**
- **Spring Boot**
- **PostgreSQL** (über Docker-Container)
- **Maven** für Build & Dependency Management
- **Spring Data JPA**
- **SLF4J** für Logging

### Architektur & Konzepte

- Domain Driven Design (DDD)
- Clean Architecture (mehrschichtige Trennung)
- Value Objects, Aggregate Roots, Domain Services
- Unit Tests mit Mockito

---

## Setup & Startanleitung

**Voraussetzungen:**  
Docker Desktop installiert & gestartet, Java 19 + Maven installiert

### 1. PostgreSQL-Datenbank starten

```bash
docker-compose up -d
```

Dies startet einen PostgreSQL-Container mit den passenden Zugangsdaten.

### 2. Anwendung starten

```bash
.\mvnw.cmd spring-boot:run
```
oder

```bash
mvn spring-boot:run
```

>Danach wird die CLI gestartet. Die Bedienung erfolgt über Texteingabe im Terminal.

#### optional vor Start der Anwendung

```bash
./mvnw clean compile
```

---

## Grundfunktionen

| Bereich          | Funktion                                                |
|------------------|---------------------------------------------------------|
| **Nutzer**       | Registrierung, Login mit E-Mail + Passwort              |
| **Kategorien**   | Kategorien erstellen (Einnahme / Ausgabe / Sparziel)    |
| **Transaktionen**| Geld-Ein- oder Ausgänge erfassen & kategorisieren       |
| **Budgets**      | Budget für eine Kategorie in einem Zeitraum festlegen   |
| **Übersicht**    | Monatliche Übersicht zu Ausgaben und Budgets            |

> Beim ersten Login können Nutzer automatisch sinnvolle Standardkategorien anlegen oder eigene definieren.

### Sparziele (Savings)
In Monetenmanager werden Sparziele als eigene Ausgabenkategorie behandelt. Wenn du monatlich 200 € sparen willst, legst du dafür eine Kategorie mit Sparziel-Flag an und weist diesem ein Budget zu.

So kannst du geplantes Sparverhalten wie eine Ausgabe tracken und sie in der Monatsübersicht sichtbar machen, ohne das mit realen Einzahlungen (z. B. auf ein Sparkonto) zu verwechseln.

---

## Hinweise für die Bewertung

- Die Anwendung nutzt **ausschließlich lokale Ressourcen**
- Keine Registrierung bei externen Diensten nötig
- Die Datenbank wird **isoliert per Docker** gestartet
- Die Anwendung ist vollständig **ohne IDE lauffähig** und kann über das Terminal gestartet werden. Für die lokale Entwicklung wird Visual Studio Code empfohlen, da es in dieser Umgebung problemlos funktioniert hat. In IntelliJ IDEA kann es vereinzelt zu Ausführungsproblemen kommen.

---

## Entwickler

**Marcel**
