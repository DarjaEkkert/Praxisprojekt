package model;

public class AufgabeErgebnis {

    private int nummer;
    private AufgabenStatus status;

    private double punkte;
    private double maxPunkte;

    private String meldung;
    private String kommentar;

    public AufgabeErgebnis(
            int nummer,
            AufgabenStatus status,
            double punkte,
            double maxPunkte,
            String meldung,
            String kommentar) {

        this.nummer = nummer;
        this.status = status;
        this.punkte = punkte;
        this.maxPunkte = maxPunkte;
        this.meldung = meldung;
        this.kommentar = kommentar;
    }

    public int getNummer() {
        return nummer;
    }

    public AufgabenStatus getStatus() {
        return status;
    }

    public void setStatus(AufgabenStatus status) {
        this.status = status;
    }

    public double getPunkte() {
        return punkte;
    }

    public void setPunkte(double punkte) {
        this.punkte = punkte;
    }

    public double getMaxPunkte() {
        return maxPunkte;
    }

    public String getMeldung() {
        return meldung;
    }

    public void setMeldung(String meldung) {
        this.meldung = meldung;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}