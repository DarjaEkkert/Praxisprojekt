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



