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



