Zielarchitektur der Prüfungsbewertung

Ausgangssituation



Die aktuelle Bewertungsengine vergleicht die vom Studenten abgegebene Excel-Datei mit der Musterlösung. Dabei werden Arbeitsblätter, Aufgaben und Formeln automatisch erkannt und miteinander verglichen. Stimmen Formeln vollständig mit der Musterlösung überein, kann die Aufgabe automatisch bewertet werden.



Während der Entwicklung zeigte sich jedoch, dass Excel-Aufgaben häufig mehrere fachlich korrekte Lösungswege besitzen. Unterschiedliche Formeln oder alternative Funktionen können zum gleichen Ergebnis führen und sind aus fachlicher Sicht ebenfalls korrekt. Ein reiner Formelvergleich würde solche Lösungen fälschlicherweise als Fehler bewerten.



Aus diesem Grund soll die automatische Bewertung durch eine manuelle Korrekturunterstützung ergänzt werden.



Geplanter Korrekturablauf



Nach der Abgabe einer Prüfung erfolgt zunächst eine automatische Auswertung aller Aufgaben.



Die Bewertungsengine analysiert jede Aufgabe und ordnet ihr einen Bewertungsstatus zu.



Status	Bedeutung

AUTOMATISCH\_RICHTIG	Die Lösung stimmt vollständig mit der Musterlösung überein.

AUTOMATISCH\_FALSCH	Die Aufgabe enthält eindeutige Fehler, beispielsweise fehlende Formeln oder falsche Zelltypen.

MANUELL\_PRUEFEN	Die Lösung unterscheidet sich von der Musterlösung, kann jedoch fachlich korrekt sein und muss durch den Dozenten bewertet werden.



Dadurch werden ausschließlich eindeutig bewertbare Aufgaben automatisch korrigiert. Aufgaben mit möglichen Alternativlösungen werden nicht automatisch als falsch bewertet.



Manuelle Nachkorrektur



Nach Abschluss der automatischen Bewertung erhält der Dozent eine Übersicht aller Aufgaben.



Für jede Aufgabe werden der aktuelle Bewertungsstatus sowie eine entsprechende Aktion angezeigt.



Aufgabe	Status	Aktion

Aufgabe 1	Automatisch richtig	keine Aktion erforderlich

Aufgabe 2	Manuell prüfen	Aufgabe öffnen

Aufgabe 3	Automatisch falsch	keine Aktion erforderlich

Aufgabe 4	Manuell prüfen	Aufgabe öffnen



Bei Aufgaben mit dem Status MANUELL\_PRUEFEN kann der Dozent die Aufgabe öffnen. Hierzu werden die Musterlösung sowie die vom Studenten abgegebene Lösung gegenübergestellt. Anschließend entscheidet der Dozent, ob die Aufgabe als richtig oder falsch bewertet wird.



Nach der manuellen Entscheidung erhält die Aufgabe einen der folgenden Status:



MANUELL\_RICHTIG

MANUELL\_FALSCH

Abschluss der Korrektur



Die endgültige Auswertung erfolgt erst, nachdem alle Aufgaben mit dem Status MANUELL\_PRUEFEN bearbeitet wurden.



Über die Funktion „Korrektur abschließen“ werden anschließend automatisch



die Gesamtpunktzahl berechnet,

das Prüfungsergebnis bestimmt,

die Ergebnisdatei erstellt,

das Ergebnis in der Datenbank gespeichert und

die Korrektur abgeschlossen.



Dadurch ist sichergestellt, dass ausschließlich vollständig bewertete Prüfungen archiviert werden.



Vorteile der Architektur



Die Kombination aus automatischer und manueller Bewertung verbindet die Vorteile beider Verfahren.



Die automatische Bewertung reduziert den Korrekturaufwand erheblich, indem alle eindeutig bewertbaren Aufgaben ohne Eingreifen des Dozenten ausgewertet werden.



Gleichzeitig bleibt die fachliche Qualität der Bewertung erhalten, da alternative, aber korrekte Lösungswege nicht automatisch als Fehler bewertet werden. Der Dozent entscheidet ausschließlich über Aufgaben, bei denen eine automatische Bewertung nicht eindeutig möglich ist.



Dadurch entsteht ein Korrekturassistenzsystem, das den Bewertungsprozess deutlich beschleunigt und gleichzeitig eine fachlich korrekte Endbewertung gewährleistet.



Geplante technische Umsetzung



Die Bewertungsengine liefert künftig für jede Aufgabe ein Bewertungsergebnis mit den folgenden Informationen:



Aufgabennummer

Bewertungsstatus

Beschreibung der Abweichung

Punktzahl

Manuelle Entscheidung (falls erforderlich)



Diese Informationen bilden die Grundlage für die automatische Auswertung, die Dozentenansicht, die Ergebnisdatei sowie die Speicherung der Prüfungsergebnisse in der Datenbank.

