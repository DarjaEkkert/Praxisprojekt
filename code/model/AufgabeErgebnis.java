package model;

public class AufgabeErgebnis {

    private int nummer;

    private AufgabenStatus status;

    private String meldung;

    public AufgabeErgebnis(
            int nummer,
            AufgabenStatus status,
            String meldung) {

        this.nummer = nummer;
        this.status = status;
        this.meldung = meldung;
    }

    public int getNummer() {

        return nummer;
    }

   public AufgabenStatus getStatus() {

    return status;
}

    public String getMeldung() {

        return meldung;
    }

}