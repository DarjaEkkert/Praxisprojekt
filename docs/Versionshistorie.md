\## 24.06.2026

\### Echte Namen, Zugangsdaten-PDF und Prüfungsfreigabe



\#### Umgesetzte Funktionen



\* Die Benutzerverwaltung wurde um die Speicherung von Vorname und Nachname erweitert.

\* Beim Import der Teilnehmerliste werden nun vollständige Benutzerdaten in der Datenbank gespeichert.

\* In den Oberflächen von Studenten und Dozenten werden die echten Namen der Benutzer angezeigt.

\* Für jede Prüfung können automatisch Zugangsdaten als PDF-Datei erzeugt werden.

\* Die PDF enthält Prüfungsinformationen, Teilnehmernamen, Benutzernamen und Passwörter.

\* Die Teilnehmerdaten werden direkt aus der Datenbank geladen und nicht mehr über Testdaten erzeugt.

\* Das Datenmodell wurde um einen Prüfungsstatus erweitert.

\* Neue Prüfungen erhalten automatisch den Status `GEPLANT`.

\* Dozenten können Prüfungen starten und beenden.

\* Der Status wird dabei zwischen `GEPLANT`, `GESTARTET` und `BEENDET` geändert und in der Datenbank gespeichert.

\* Studenten können Prüfungen nur starten, wenn diese vom Dozenten freigegeben wurden.

\* Bei nicht freigegebenen oder bereits beendeten Prüfungen werden entsprechende Statusmeldungen angezeigt.



\#### Technische Umsetzung



\* Erweiterung der Tabelle `users` um Vorname und Nachname.

\* Anpassung der Klassen `User`, `UserRepository` und der Login-Verarbeitung.

\* Implementierung eines Teilnehmerimports aus Excel inklusive automatischer Benutzererzeugung.

\* Einführung der Klasse `PdfService` zur Erstellung von Zugangsdaten-PDFs.

\* Erweiterung der Tabelle `pruefungen` um das Attribut `status`.

\* Implementierung von Statusänderungen über das `PruefungsRepository`.

\* Integration der Prüfungsstatusprüfung in die StudentGUI und DozentGUI.



\#### Ergebnis



Das Prüfungssystem unterstützt nun personalisierte Benutzerkonten mit echten Namen, die automatische Erstellung von Zugangsdaten-PDFs sowie eine zentrale Prüfungsfreigabe durch den Dozenten. Studenten können Prüfungen erst nach Freigabe starten und erhalten abhängig vom aktuellen Prüfungsstatus entsprechende Rückmeldungen.

## 30.06.2026 – Generische Bewertungsengine

### Umgesetzte Funktionen

- Die Bewertungslogik wurde von einer hartcodierten Klausurauswertung auf eine generische Vergleichslogik umgestellt.
- Die Musterlösung wird anhand der in der Datenbank gespeicherten Prüfung geladen.
- Studentenlösung und Musterlösung werden automatisch Blatt für Blatt verglichen.
- Das System erkennt Aufgaben anhand der Aufgabenüberschriften in der Musterlösung.
- Mehrere Formeln innerhalb einer Aufgabe werden zu einer gemeinsamen Aufgabenbewertung zusammengefasst.
- Fehlerhafte Formeln werden mit Blatt, Zeile und Spalte ausgegeben.

### Technische Umsetzung

- Einführung der Methode `vergleicheArbeitsmappe()`.
- Iteration über alle Arbeitsblätter, Zeilen und Zellen der Arbeitsmappe.
- Dynamische Erkennung des Zelltyps (`FORMULA`, `STRING`, `NUMERIC`).
- Einführung einer aufgabenbasierten Bewertungslogik mit den Variablen `aktuelleAufgabe`, `aktuelleAufgabeFehler`, `richtigeAufgaben` und `falscheAufgaben`.
- Nutzung des in der Datenbank gespeicherten Pfads zur Musterlösung anstelle einer fest programmierten Datei.

### Ergebnis

Die Bewertungsengine ist nicht mehr auf eine einzelne Beispielklausur beschränkt. Neue Excel-Prüfungen können ohne Änderungen am Quellcode bewertet werden, sofern sie dieselbe Aufgabenstruktur verwenden. Die Auswertung erfolgt auf Aufgabenebene statt auf Zellebene und bildet damit den realen Bewertungsablauf deutlich besser ab.


## 01.07.2026 – Vorbereitung der manuellen Korrektur

### Umgesetzte Funktionen

- Die Bewertungsengine wurde auf eine objektbasierte Ergebnisverwaltung umgestellt.
- Für jede automatisch bewertete Aufgabe wird nun ein `AufgabeErgebnis` erzeugt.
- Das System unterscheidet zwischen automatisch korrekt bewerteten Aufgaben und Aufgaben, die manuell geprüft werden müssen.
- Die Gesamtpunktzahl wird nicht mehr während der Bewertung gezählt, sondern aus allen `AufgabeErgebnis`-Objekten berechnet.
- Damit wurde die Grundlage für eine spätere manuelle Korrektur durch den Dozenten geschaffen.

### Technische Umsetzung

- Erweiterung der Klasse `AufgabeErgebnis` um die Attribute `punkte` und `maxPunkte`.
- Anpassung der Methode `vergleicheArbeitsmappe()`, sodass sie eine `List<AufgabeErgebnis>` zurückliefert.
- Einführung der Statuswerte `AUTOMATISCH_RICHTIG` und `MANUELL_PRUEFEN` innerhalb der Bewertungslogik.
- Umstellung der Punkteberechnung auf Basis der erzeugten Ergebnisobjekte.
- Bereinigung der Bewertungsengine durch Entfernen der bisherigen Aufgabenzähler und temporären Debug-Ausgaben.

### Ergebnis

Die Bewertungsengine liefert nun strukturierte Ergebnisse für jede einzelne Aufgabe. Dadurch können automatisch erkannte Problemfälle für eine spätere manuelle Korrektur markiert werden, ohne die bestehende automatische Bewertung zu verändern. Gleichzeitig wurde die Architektur der Bewertungsengine erweitert und für die nächsten Entwicklungsschritte vorbereitet.
