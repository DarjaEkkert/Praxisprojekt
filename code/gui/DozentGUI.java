import javax.swing.*;

import model.CurrentUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import database.DatabaseManager;
import database.PruefungsRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.Pruefung;
import service.PdfService;

import java.util.List;

public class DozentGUI {

    public DozentGUI() {

        JFrame frame = new JFrame("Dozentenbereich");

        frame.setSize(1000, 600);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        frame.getContentPane().setBackground(
                Style.BACKGROUND);

       
        // Benutzeranzeige oben
        

        JLabel userLabel =
                new JLabel(
                        "Angemeldet als: "
                                + CurrentUser.getCurrentUser().getVollerName()
                                + " ("
                                + CurrentUser.getCurrentUser().getRole()
                                + ")");
        JButton logoutButton = new JButton("Logout");
        Style.styleButton(logoutButton);
        logoutButton.setPreferredSize(Style.SMALL_BUTTON_SIZE);

        logoutButton.addActionListener(
                new ActionListener() {

                public void actionPerformed( ActionEvent e) {

                CurrentUser.setCurrentUser(null);

                frame.dispose();

                new LoginGUI();
        }
        });

        JPanel userPanel = new JPanel( new FlowLayout( FlowLayout.LEFT));

        userPanel.setBackground( Style.BACKGROUND);
        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        userLabel.setFont(Style.BUTTON_FONT);
        userLabel.setForeground(Style.PRIMARY);

        JLabel logoLabel = Style.createLogo();

        JPanel headerPanel =new JPanel(new BorderLayout());

        headerPanel.setBackground( Style.BACKGROUND);
        headerPanel.add( userPanel, BorderLayout.WEST);
        headerPanel.add(logoLabel,  BorderLayout.EAST);

        
        // Linke Seite
        

        JPanel leftPanel = new JPanel();

        leftPanel.setLayout(
                new BoxLayout(
                        leftPanel,
                        BoxLayout.Y_AXIS));

        leftPanel.setBackground(
                Style.BACKGROUND);
        DatabaseManager db = new DatabaseManager();

        db.connect();

        PruefungsRepository repository =
                new PruefungsRepository(db);

        List<Pruefung> pruefungen =
                repository.getAllPruefungen();
        db.disconnect();
                
//prüfungen nach Datum sortieren
        pruefungen.sort((p1, p2) -> {

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(
                                "dd.MM.yyyy");

                LocalDate d1 =
                        LocalDate.parse(
                                p1.getDatum(),
                                formatter);

                LocalDate d2 =
                        LocalDate.parse(
                                p2.getDatum(),
                                formatter);

                return d1.compareTo(d2);
        });

        JLabel vergangeneLabel =
                new JLabel("Vergangene Prüfungen");

        DefaultListModel<String> vergangeneModel =
                new DefaultListModel<>();
        

        JLabel geplanteLabel =
                new JLabel("Geplante Prüfungen");

        DefaultListModel<String> geplanteModel =
                new DefaultListModel<>();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate heute = LocalDate.now();

        for (Pruefung pruefung : pruefungen) {

                LocalDate pruefungsDatum =
                        LocalDate.parse(
                                pruefung.getDatum(),
                                formatter);

                String eintrag =
                        pruefung.getName()
                        + " - "
                        + pruefung.getDatum();

                if (pruefungsDatum.isBefore(heute)) {

                        vergangeneModel.addElement(eintrag);

                } else {

                        geplanteModel.addElement(eintrag);
                }
        }

        JList<String> vergangeneListe =
                new JList<>(vergangeneModel);

        JList<String> geplanteListe =
                new JList<>(geplanteModel);

        leftPanel.add(vergangeneLabel);
        leftPanel.add(new JScrollPane(vergangeneListe));

        leftPanel.add(Box.createVerticalStrut(20));

        leftPanel.add(geplanteLabel);
        leftPanel.add(new JScrollPane(geplanteListe));

       
        // Rechte Seite
       

        JPanel rightPanel = new JPanel();

        rightPanel.setBackground(
                Style.BACKGROUND);

        rightPanel.setLayout(
                new BoxLayout(
                rightPanel,
                BoxLayout.Y_AXIS));


        JButton pruefungButton = new JButton("Prüfung anlegen");

        pruefungButton.addActionListener(
                 e -> new PruefungAnlegenGUI()
        );

        JButton ergebnisseButton = new JButton("Ergebnisse ansehen");

        JButton studentenButton = new JButton("Studenten verwalten");
        JButton zugangsdatenButton =  new JButton("Zugangsdaten erstellen");

        zugangsdatenButton.addActionListener(e -> {
                DatabaseManager pdfDb = new DatabaseManager();
                pdfDb.connect();

                PruefungsRepository pdfRrepository = new PruefungsRepository(db);

                int pruefungId = pdfRrepository.getLetztePruefungId();

                PdfService pdfService = new PdfService();

                pdfService.createZugangsdatenPdf(pruefungId);

                pdfDb.disconnect();
        });

        JButton statusButton =  new JButton("Prüfung starten");

        Pruefung letztePruefung =
                repository.getPruefungById(
                        repository.getLetztePruefungId());

                if (letztePruefung != null
                         && letztePruefung.getStatus().equals("GESTARTET")) {

                statusButton.setText("Prüfung beenden");
        }
statusButton.addActionListener(e -> {

    DatabaseManager pdfDb = new DatabaseManager();

    pdfDb.connect();

    PruefungsRepository pdfRepository = new PruefungsRepository(pdfDb);

    int pruefungId = pdfRepository.getLetztePruefungId();

    Pruefung pruefung = pdfRepository.getPruefungById(pruefungId);

    if (pruefung.getStatus().equals("GEPLANT")) {

        pdfRepository.updateStatus(
                pruefungId,
                "GESTARTET");

        statusButton.setText(
                "Prüfung beenden");

        JOptionPane.showMessageDialog(
                frame,
                "Prüfung gestartet.");

    } else if (pruefung.getStatus().equals("GESTARTET")) {

        pdfRepository.updateStatus(
                pruefungId,
                "BEENDET");

        statusButton.setEnabled(false);

        JOptionPane.showMessageDialog(
                frame,
                "Prüfung beendet.");
    }

    pdfDb.disconnect();
});

        JLabel actionLabel = new JLabel("Dozentenfunktionen");
        actionLabel.setFont(Style.TITLE_FONT);
        actionLabel.setForeground(Style.PRIMARY);
        actionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pruefungButton.setPreferredSize(Style.BUTTON_SIZE);
        ergebnisseButton.setPreferredSize(Style.BUTTON_SIZE);
        studentenButton.setPreferredSize(Style.BUTTON_SIZE);

        pruefungButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ergebnisseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentenButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Style.styleButton(pruefungButton);
        Style.styleButton(ergebnisseButton);
        Style.styleButton(studentenButton);
        Style.styleButton(zugangsdatenButton);
        Style.styleButton(statusButton);
        
        rightPanel.add(Box.createVerticalStrut(50));
        rightPanel.add(actionLabel);
        rightPanel.add(Box.createVerticalStrut(40));
        rightPanel.add(pruefungButton);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(ergebnisseButton);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(studentenButton);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(zugangsdatenButton);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(statusButton);

        
        // SplitPane
        

        JSplitPane splitPane =
                new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        leftPanel,
                        rightPanel);

        splitPane.setDividerLocation(330);

        frame.add(headerPanel,BorderLayout.NORTH);
        frame.add(splitPane,BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}