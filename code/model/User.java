package model;
public class User {

    private String username;
    private String password;
    private Role role;
    private String vorname;
    private String nachname;

    public User(
        String username,
        String password,
        Role role,
        String vorname,
        String nachname) {

    this.username = username;
    this.password = password;
    this.role = role;
    this.vorname = vorname;
    this.nachname = nachname;
}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getVollerName() {
        return vorname + " " + nachname;
    }
}