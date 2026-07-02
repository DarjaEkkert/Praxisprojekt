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


## 01.07.2026 – Umsetzung der manuellen Korrektur

### Umgesetzte Funktionen

- Die Bewertungsengine wurde auf eine objektbasierte Ergebnisverwaltung umgestellt.
- Für jede automatisch bewertete Aufgabe wird nun ein `AufgabeErgebnis` erzeugt.
- Das System unterscheidet zwischen automatisch korrekt bewerteten Aufgaben und Aufgaben, die manuell geprüft werden müssen.
- Die Gesamtpunktzahl wird nicht mehr während der Bewertung gezählt, sondern aus allen `AufgabeErgebnis`-Objekten berechnet.
- Für die manuelle Nachkorrektur wurde eine eigene `ManuelleKorrekturGUI` entwickelt.
- Es werden ausschließlich Aufgaben angezeigt, die den Status `MANUELL_PRUEFEN` besitzen.
- Der Dozent kann für jede Aufgabe eine individuelle Punktzahl vergeben und den Bewertungsstatus aktualisieren.
- Nach Abschluss der manuellen Korrektur werden die Gesamtpunktzahl sowie der prozentuale Prüfungserfolg automatisch neu berechnet.
- Die manuelle Korrektur wurde in den Dozentenbereich integriert und kann über die Funktion **„Ergebnisse ansehen“** geöffnet werden.

### Technische Umsetzung

- Erweiterung der Klasse `AufgabeErgebnis` um die Attribute `punkte` und `maxPunkte`.
- Anpassung der Methode `vergleicheArbeitsmappe()`, sodass sie eine `List<AufgabeErgebnis>` zurückliefert.
- Einführung der Statuswerte `AUTOMATISCH_RICHTIG`, `MANUELL_PRUEFEN`, `MANUELL_RICHTIG` und `MANUELL_FALSCH`.
- Umstellung der Punkteberechnung auf Basis der erzeugten Ergebnisobjekte.
- Implementierung des `ManuelleKorrekturService` zur Verarbeitung der manuellen Bewertung und zur Berechnung der endgültigen Gesamtpunktzahl.
- Entwicklung der `ManuelleKorrekturGUI` mit Navigation zwischen den zu prüfenden Aufgaben und Eingabe individueller Punktzahlen.
- Integration der manuellen Korrektur in die `DozentGUI` über den Button **„Ergebnisse ansehen“**.
- Bereinigung der Bewertungsengine durch Entfernen der bisherigen Aufgabenzähler und temporären Debug-Ausgaben.

### Ergebnis

Die Bewertungsengine liefert nun strukturierte Ergebnisse für jede einzelne Aufgabe. Automatisch erkannte Problemfälle werden gezielt für eine manuelle Nachkorrektur markiert, ohne die bestehende automatische Bewertung zu verändern. Der Dozent kann diese Aufgaben einzeln bewerten und individuelle Punktzahlen vergeben. Nach Abschluss der manuellen Korrektur werden die Gesamtpunktzahl sowie der prozentuale Prüfungserfolg automatisch neu berechnet. Dadurch wurde die Bewertungslogik von einer rein automatischen Auswertung zu einem vollständigen Bewertungsprozess aus automatischer und manueller Korrektur erweitert.

## 02.07.2026 – Speicherung der Korrekturergebnisse und Dozentenübersicht

### Umgesetzte Funktionen

- Für abgeschlossene Prüfungen wurde eine neue `ErgebnisseGUI` entwickelt.
- Der Dozent erhält eine Übersicht aller Studenten, die eine Lösung zur ausgewählten Prüfung abgegeben haben.
- Über den Button **„Korrektur öffnen“** kann die manuelle Nachkorrektur eines ausgewählten Studenten gestartet werden.
- Die `ManuelleKorrekturGUI` wurde erweitert und zeigt zusätzlich den Namen des ausgewählten Studenten an.
- Die manuelle Korrektur lädt ihre Daten nun vollständig aus der Datenbank und ist nicht mehr von den zuletzt berechneten Bewertungsergebnissen abhängig.
- Änderungen der vergebenen Punkte und des Bewertungsstatus werden unmittelbar in der Datenbank gespeichert.

### Technische Umsetzung

- Entwicklung der neuen `ErgebnisseGUI` zur Auswahl abgegebener Studentenlösungen.
- Implementierung neuer Repository-Methoden zum Laden der Prüfungsergebnisse und der aufgabenbezogenen Bewertungen aus der SQLite-Datenbank.
- Erweiterung der Datenbankanbindung um das Speichern und erneute Laden einzelner `AufgabeErgebnis`-Objekte.
- Implementierung einer `UPDATE`-Funktion zur dauerhaften Speicherung manueller Bewertungen in der Tabelle `aufgabe_ergebnisse`.
- Übergabe der `ergebnisId` an die `ManuelleKorrekturGUI`, sodass Änderungen direkt dem entsprechenden Prüfungsergebnis zugeordnet werden können.
- Umstellung des Korrekturablaufs von einer temporären Speicherlösung auf eine datenbankgestützte Verwaltung.

### Ergebnis

Der Dozent kann nun eine abgeschlossene Prüfung auswählen, die abgegebenen Studentenlösungen anzeigen und einzelne Prüfungen zur manuellen Nachkorrektur öffnen. Die Bewertungen werden nach der Korrektur dauerhaft in der SQLite-Datenbank gespeichert und können jederzeit erneut geladen werden. Dadurch wurde der Bewertungsprozess vollständig in die Datenbank integriert und die Grundlage für den anschließenden Export der endgültigen Prüfungsergebnisse geschaffen.