package service;

import java.util.List;
import model.AufgabeErgebnis;
import model.AufgabenStatus;

public class ManuelleKorrekturService {

    public static void bewerteAufgabe(
            AufgabeErgebnis aufgabe,
            double punkte) {

        aufgabe.setPunkte(punkte);

        if (punkte > 0) {
            aufgabe.setStatus(AufgabenStatus.MANUELL_RICHTIG);
        } else {
            aufgabe.setStatus(AufgabenStatus.MANUELL_FALSCH);
        }
    }

public static double berechneGesamtpunkte(
        List<AufgabeErgebnis> ergebnisse) {

    double gesamtpunkte = 0;

    for (AufgabeErgebnis aufgabe : ergebnisse) {

        gesamtpunkte += aufgabe.getPunkte();
    }

    return gesamtpunkte;
}

public static double berechneMaxPunkte(
        List<AufgabeErgebnis> ergebnisse) {

    double maxPunkte = 0;

    for (AufgabeErgebnis ergebnis : ergebnisse) {

        maxPunkte += ergebnis.getMaxPunkte();
    }

    return maxPunkte;
}
}