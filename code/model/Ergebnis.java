package model;

public class Ergebnis {

    private String username;
    private double prozent;
    private boolean bestanden;

    public Ergebnis(
            String username,
            double prozent,
            boolean bestanden) {

        this.username = username;
        this.prozent = prozent;
        this.bestanden = bestanden;
    }

    public String getUsername() {

        return username;
    }

    public double getProzent() {

        return prozent;
    }

    public boolean isBestanden() {

        return bestanden;
    }

    @Override
    public String toString() {

        return username
                + "   "
                + String.format("%.2f", prozent)
                + "%   "
                + (bestanden
                        ? "Bestanden"
                        : "Nicht bestanden");
    }
}