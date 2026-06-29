\# TST-E1 – Projektaufgabe Testen



\## Projekt



\*\*Projekt:\*\* Praxisprojekt – Excel-Prüfungssystem (Java)



\*\*Programmiersprache:\*\* Java



\*\*Testframework:\*\* JUnit 5



\*\*Mocking:\*\* Mockito (geplant für A3)



\---



\# AI-Nutzung



Für die Bearbeitung dieser Einsendeaufgabe wurde ChatGPT als Unterstützung eingesetzt.



Die KI wurde verwendet, um



\* JUnit einzurichten,

\* Testfälle zu entwickeln,

\* sowie die Dokumentation zu strukturieren.



Alle vorgeschlagenen Lösungen wurden geprüft, an das bestehende Projekt angepasst und eigenständig umgesetzt.



\---



\# Entwicklungsprotokoll



\## Schritt 1 – Testumgebung vorbereiten



\### Ziel



Einrichtung einer Testumgebung für das bestehende Java-Projekt.



\### Umsetzung



\* Neuer Git-Branch `tst-e1` erstellt.

\* JUnit 5 (`junit-platform-console-standalone-1.13.4.jar`) heruntergeladen.

\* JUnit in den Ordner `code/lib` eingefügt.

\* JUnit über \*Referenced Libraries\* in VS Code eingebunden.



\### Ergebnis



Das Projekt ist nun für die Erstellung von Unit-Tests vorbereitet.



Git-Commit:



```

A1: JUnit 5 als Testframework hinzugefügt

```




\### Auswahl der Testklasse



Für die Unit-Tests wurde die Klasse `PruefungsService` ausgewählt. Die Methoden `pruefeZelle()` und `pruefeBlatt3()` enthalten fachliche Prüfungslogik und können unabhängig von der grafischen Benutzeroberfläche getestet werden. Dadurch lassen sich verschiedene Eingaben gezielt überprüfen und die Rückgabewerte mit JUnit validieren.



\##  Unit-Tests



Für die Unit-Tests habe ich die Methode `pruefeBlatt3()` aus meinem Prüfungssystem ausgewählt. Diese Methode überprüft, ob ein Student die richtige Excel-Formel für die Berechnung der Versandkosten verwendet hat.



Folgende Testfälle wurden mit JUnit umgesetzt:



\* korrekte IF-Formel → Test erfolgreich

\* falsche Formel (SUM statt IF) → Methode liefert `false`

\* Zelle enthält Text statt Formel → `IllegalStateException`

\* Formel steht in der falschen Zelle → `NullPointerException`

\* alternative fachlich richtige Formel (`IF(A1<29.99,4.99,0)`) → wird von der aktuellen Implementierung nicht akzeptiert



Während der Tests ist aufgefallen, dass die Methode nur eine bestimmte Schreibweise der Formel akzeptiert. Eine logisch gleichwertige Formel wird als falsch bewertet. Außerdem werden fehlende oder falsche Zelltypen derzeit nicht abgefangen, sondern führen zu Exceptions. Diese Tests helfen dabei, solche Schwachstellen frühzeitig zu erkennen.

Git-Commit:

```

A1: Implementiere Unit-Tests für PruefungsService

```



\## A2 – Shopping Cart (TDD)



Für diese Aufgabe wurde ein einfacher Warenkorb entwickelt. Dabei wurde das Prinzip \*\*Test Driven Development (TDD)\*\* verwendet.



Zuerst wurde ein Test geschrieben, der überprüft, ob ein neuer Warenkorb leer ist. Da die Methode `getItemCount()` zu diesem Zeitpunkt noch nicht existiert, schlägt der Test zunächst fehl (RED). Erst danach wird die Methode implementiert, sodass der Test erfolgreich ausgeführt werden kann (GREEN). Anschließend wird der Code bei Bedarf verbessert (REFACTOR).



Die Entwicklung erfolgt schrittweise. Nach jedem Schritt wird ein eigener Git-Commit erstellt, damit die TDD-Vorgehensweise in der Git-History nachvollziehbar ist.



\### Feature 1: Neuer Warenkorb



Der erste Test prüft, ob ein neu erstellter Warenkorb leer ist. Zuerst wurde nur der Test geschrieben. Da die Methode `getItemCount()` noch nicht existierte, war der Test zunächst rot (RED).



Anschließend wurde die Methode `getItemCount()` implementiert. Sie gibt die Anzahl der Elemente im Warenkorb zurück. Danach war der Test erfolgreich (GREEN).



\### Feature 2: Artikel hinzufügen



Als nächstes wurde die Funktion zum Hinzufügen eines Artikels entwickelt.



Zuerst wurde der Test `artikelKannHinzugefuegtWerden()` geschrieben. Der Test erwartet, dass nach dem Hinzufügen eines Artikels die Anzahl der Artikel im Warenkorb 1 beträgt.



Da die Methode `addItem()` zu diesem Zeitpunkt noch nicht existierte, schlug der Test zunächst fehl (RED). Erst danach wird die Methode implementiert, damit der Test erfolgreich ausgeführt werden kann (GREEN).



Zum Schluss wurde die Methode verbessert. Vor dem Hinzufügen eines Artikels wird jetzt geprüft, ob der Artikel `null` oder leer ist. Dadurch wird verhindert, dass ungültige Einträge im Warenkorb gespeichert werden. Die vorhandenen Tests wurden erneut ausgeführt und waren weiterhin erfolgreich.(REFACTOR)



\### Feature 3: Artikel entfernen



Als drittes Feature wurde das Entfernen eines Artikels umgesetzt. Zuerst wurde ein Test geschrieben, der einen Artikel zum Warenkorb hinzufügt und anschließend wieder entfernt. Da die Methode `removeItem()` noch nicht vorhanden war, schlug der Test zunächst fehl (RED).



Nach dem fehlgeschlagenen Test wurde die Methode removeItem() implementiert. Sie entfernt einen Artikel aus der Liste des Warenkorbs. Anschließend wurde der Test erneut ausgeführt und erfolgreich bestanden(GREEN).

\#### REFACTOR



Zum Abschluss wurde die Methode `removeItem()` verbessert. Vor dem Entfernen eines Artikels wird jetzt geprüft, ob der übergebene Wert `null` oder leer ist. Dadurch wird der Code robuster, ohne das Verhalten der bereits vorhandenen Funktionen zu verändern. Alle Tests wurden anschließend erneut erfolgreich ausgeführt.(REFACTOR)











