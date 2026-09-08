package davutcagri.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player {

    private final int x;
    private final int y;

    private final int width;
    private final int height;

    private final int centerX;
    private final int centerY;

    private double angle;

    private int health = 100;

    private BufferedImage texture;

    public Player(int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;

        this.width = 100;
        this.height = 100;

        this.x = this.centerX - width / 2;
        this.y = this.centerY - height / 2;

        try {
            this.texture = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player.png")));
        } catch (IOException e) {
            System.out.println("Failed to load player texture: " + e.getMessage());
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(angle, centerX, centerY);
        g2d.drawImage(texture, x, y, width, height, null);
        g2d.dispose();
    }

    public void setAngle(double mouseX, double mouseY) {
        angle = Math.atan2(mouseY - centerY, mouseX - centerX);
    }

    public Rectangle getBounds() {
        return new Rectangle(x+13, y+13, width-26, height-26);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
