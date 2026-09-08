package davutcagri.panel;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private final int width;
    private final int height;

    private JLabel title;
    private JButton playButton;

    public MenuPanel(int width, int height) {
        this.width = width;
        this.height = height;

        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        title = new JLabel("Zombie Doom");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        this.add(title, c);

        playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(150, 50));
        playButton.setFont(new Font("Arial", Font.BOLD, 24));
        playButton.addActionListener(e -> {
            GamePanel gamePanel = new GamePanel(width, height);
            getParent().add(gamePanel);
            getParent().revalidate();
        });
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        this.add(playButton, c);
    }
}
