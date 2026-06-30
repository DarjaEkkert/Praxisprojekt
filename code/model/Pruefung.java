package model;
public class Pruefung {

    private int id;
    private String name;
    private String datum;
    private String gruppe;
    private int dauer;

    private String aufgabenPfad;
    private String loesungsPfad;
    private String teilnehmerPfad;
    private String status;

    public Pruefung(
            int id,
            String name,
            String datum,
            String gruppe,
            int dauer,
            String aufgabenPfad,
            String loesungsPfad,
            String teilnehmerPfad,
            String status) {
        this.id = id;
        this.name = name;
        this.datum = datum;
        this.gruppe = gruppe;
        this.dauer = dauer;
        this.aufgabenPfad = aufgabenPfad;
        this.loesungsPfad = loesungsPfad;
        this.teilnehmerPfad = teilnehmerPfad;
        this.status=status;
    }

    public int getId() {
    return id;
    }
    public String getName() {
        return name;
    }

    public String getDatum() {
        return datum;
    }

    public String getGruppe() {
        return gruppe;
    }

    public int getDauer() {
        return dauer;
    }

    public String getAufgabenPfad() {
        return aufgabenPfad;
    }

    public String getLoesungsPfad() {
        return loesungsPfad;
    }

    public String getTeilnehmerPfad() {
        return teilnehmerPfad;
    }
    public String getStatus() {
    return status;
    }
    public void setStatus(String status) {
    this.status = status;
    }

    @Override
    public String toString() {

    return name + " - " + datum;
}
}