package model;

public class Teilnehmer {

    private String vorname;
    private String nachname;
    private String username;
    private String passwort;

    public Teilnehmer(
            String vorname,
            String nachname,
            String username,
            String passwort) {

        this.vorname = vorname;
        this.nachname = nachname;
        this.username = username;
        this.passwort = passwort;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswort() {
        return passwort;
    }
}
