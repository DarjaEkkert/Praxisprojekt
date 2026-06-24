import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.Dimension;

public class Style {

    public static final Color PRIMARY =
            new Color(196, 30, 58);

    public static final Color DARK =
            new Color(45, 45, 45);

    public static final Color BACKGROUND =
            new Color(248, 249, 250);

    public static final Color PANEL =
            Color.WHITE;

    public static final Font TITLE_FONT =
            new Font("Serif", Font.BOLD, 32);

    public static final Font NORMAL_FONT =
            new Font("SansSerif", Font.PLAIN, 14);

    public static final Font BUTTON_FONT =
            new Font("SansSerif", Font.BOLD, 14);
        public static JLabel createLogo() {

    ImageIcon icon = new ImageIcon("assets/logo.png");

    Image image = icon.getImage().getScaledInstance(
            180,
            80,
            Image.SCALE_SMOOTH);

    return new JLabel(new ImageIcon(image));
}

    public static void styleButton(JButton button) {

        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
    }
    public static void styleTitle(JLabel label) {

    label.setForeground(PRIMARY);
    label.setFont(TITLE_FONT);
}
public static void styleInfo(JLabel label) {

    label.setForeground(PRIMARY);
    label.setFont(BUTTON_FONT);
}

public static final Dimension BUTTON_SIZE = new Dimension(250, 60);

public static final Dimension SMALL_BUTTON_SIZE = new Dimension(120, 40);

public static final Dimension FIELD_SIZE = new Dimension(300, 35);
}