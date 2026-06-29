package gui;
import javax.swing.*;

import model.CurrentUser;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import database.DatabaseManager;
import database.PruefungsRepository;
import database.PruefungsteilnehmerRepository;
import model.Pruefung;
import service.PruefungsService;


public class StudentGUI {

    public StudentGUI() {
        System.out.println(
        CurrentUser.getCurrentUser().getUsername()
);

JFrame frame = new JFrame("Excel-Prüfungssystem");

frame.setSize(700, 500);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(new BorderLayout());
frame.getContentPane().setBackground(Style.BACKGROUND);

DatabaseManager db = new DatabaseManager();

db.connect();

PruefungsRepository repository = new PruefungsRepository(db);
PruefungsteilnehmerRepository teilnehmerRepository = new PruefungsteilnehmerRepository(db);
String username = CurrentUser.getCurrentUser().getUsername();
int pruefungId = teilnehmerRepository.getPruefungIdByUsername(username);
Pruefung aktuellePruefung = repository.getPruefungById(pruefungId);

final Pruefung finalePruefung = aktuellePruefung;
db.disconnect();
JLabel pruefungTitelLabel = new JLabel("Meine Prüfung");
JLabel pruefungNameLabel = new JLabel();
JLabel pruefungDatumLabel = new JLabel();
JLabel pruefungDauerLabel = new JLabel();

if (aktuellePruefung != null) {

    pruefungNameLabel.setText(
            "Prüfung: "
            + aktuellePruefung.getName());

    pruefungDatumLabel.setText(
            "Datum: "
            + aktuellePruefung.getDatum());

    pruefungDauerLabel.setText(
            "Dauer: "
            + aktuellePruefung.getDauer()
            + " Minuten");
}
pruefungTitelLabel.setFont(
        Style.TITLE_FONT);

pruefungTitelLabel.setForeground(
        Style.PRIMARY);

pruefungNameLabel.setFont(
        Style.BUTTON_FONT);

pruefungDatumLabel.setFont(
        Style.BUTTON_FONT);

pruefungDauerLabel.setFont(
        Style.BUTTON_FONT);

// Logo

JLabel logoLabel = Style.createLogo();

JPanel logoPanel =
        new JPanel(new FlowLayout(FlowLayout.RIGHT));


logoPanel.setBackground(Style.BACKGROUND);
logoPanel.add(logoLabel);


// Titel

JLabel titleLabel =
        new JLabel("Excel-Prüfungssystem");
       
        JLabel userLabel =
        new JLabel(
            "Angemeldet als: "
            + CurrentUser.getCurrentUser().getVollerName()
            + " ("
            + CurrentUser.getCurrentUser().getRole()
            + ")"
        );

JButton logoutButton = new JButton("Logout");
Style.styleButton(logoutButton);
logoutButton.setPreferredSize(Style.SMALL_BUTTON_SIZE);

userLabel.setFont(Style.BUTTON_FONT);
userLabel.setForeground(Style.PRIMARY);

titleLabel.setFont(Style.TITLE_FONT);
titleLabel.setForeground(Style.PRIMARY);


// Timer

JLabel timerLabel =
        new JLabel("Zeit: 00:00");

timerLabel.setFont(Style.TITLE_FONT);
timerLabel.setForeground(Style.PRIMARY);


// Statusmeldungen

JLabel infoLabel =
        new JLabel("");
infoLabel.setFont(Style.BUTTON_FONT);
infoLabel.setForeground(Style.PRIMARY);

if (aktuellePruefung != null) {

    if (aktuellePruefung.getStatus().equals("GEPLANT")) {

        infoLabel.setText(
                "Prüfung wurde noch nicht freigegeben.");

    } else if (aktuellePruefung.getStatus().equals("BEENDET")) {

        infoLabel.setText(
                "Prüfung wurde bereits beendet.");
    }
}


// Buttons

JButton uploadButton = new JButton("Lösung hochladen");
Style.styleButton(uploadButton);
JButton startButton = new JButton("Prüfung starten");
Style.styleButton(startButton);

if (aktuellePruefung != null) {

    if (!aktuellePruefung.getStatus().equals("GESTARTET")) {

        startButton.setEnabled(false);
    }
}

// Timer-Variable

final int[] sekunden = {0};

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                sekunden[0]++;
                if (sekunden[0] == 10800) {

                    infoLabel.setText("Prüfungszeit vorbei! Bitte Lösung hochladen.");
                }

                if (sekunden[0] >= 11700) {


                    uploadButton.setEnabled(false);

                     ((Timer)e.getSource()).stop();

                    infoLabel.setText("Upload-Zeit abgelaufen!");
                }

                int minuten = sekunden[0] / 60;
                int restSekunden = sekunden[0] % 60;

                timerLabel.setText(
                    String.format("Zeit: %02d:%02d", minuten, restSekunden)
                 );
            }
        });
        

startButton.addActionListener(new ActionListener() {

    public void actionPerformed(ActionEvent e) {
        timer.start();
        infoLabel.setText("Viel Erfolg!");
        uploadButton.setEnabled(true);
        try {

            if (finalePruefung != null) {

                Desktop.getDesktop().open(new File(finalePruefung.getAufgabenPfad()));

            } else {

                infoLabel.setText( "Keine Prüfung verfügbar.");
}

        } catch (Exception ex) {

             ex.printStackTrace();
        }
    }
});

        uploadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = new JFileChooser();

                int result = chooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile();
                
                timer.stop();

                PruefungsService.pruefeDatei( finalePruefung.getId(),file.getAbsolutePath());
                infoLabel.setText("Lösung wurde hochgeladen.");
                }
            }
        });

        logoutButton.addActionListener(
                new ActionListener() {

            public void actionPerformed(
                    ActionEvent e) {

                CurrentUser.setCurrentUser(null);

                frame.dispose();

                new LoginGUI();
             }
        });

JPanel headerPanel = new JPanel(new BorderLayout());

headerPanel.setBackground(Style.BACKGROUND);
headerPanel.add(logoutButton,BorderLayout.WEST);
headerPanel.add(logoLabel,BorderLayout.EAST);


JPanel buttonPanel = new JPanel(new BorderLayout());
buttonPanel.setBackground(Style.BACKGROUND);


JPanel topButtonPanel = new JPanel();
topButtonPanel.setBackground(Style.BACKGROUND);

topButtonPanel.add(startButton);
topButtonPanel.add(uploadButton);


buttonPanel.add(topButtonPanel, BorderLayout.CENTER);
buttonPanel.add(timerLabel, BorderLayout.NORTH);
timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

JPanel userPanel = new JPanel( new FlowLayout( FlowLayout.LEFT));
userPanel.setBackground( Style.BACKGROUND);
userPanel.add(userLabel);
userPanel.add(logoutButton);

JPanel pruefungPanel = new JPanel();

pruefungPanel.setBackground(Style.BACKGROUND);
pruefungPanel.setLayout( new BoxLayout(pruefungPanel,BoxLayout.Y_AXIS));
pruefungTitelLabel.setAlignmentX( Component.CENTER_ALIGNMENT);
pruefungNameLabel.setAlignmentX( Component.CENTER_ALIGNMENT);
pruefungDatumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
pruefungDauerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

pruefungPanel.add(pruefungTitelLabel);
pruefungPanel.add(Box.createVerticalStrut(10));
pruefungPanel.add(pruefungNameLabel);
pruefungPanel.add(pruefungDatumLabel);
pruefungPanel.add(pruefungDauerLabel);
pruefungPanel.add(Box.createVerticalStrut(20));
pruefungPanel.add(infoLabel);

infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

JPanel centerPanel = new JPanel(new BorderLayout());

centerPanel.setBackground(Style.BACKGROUND);

centerPanel.add(userPanel, BorderLayout.NORTH);
centerPanel.add(pruefungPanel, BorderLayout.CENTER);
centerPanel.add(buttonPanel, BorderLayout.SOUTH);

frame.add(headerPanel, BorderLayout.NORTH);
frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}