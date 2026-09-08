package davutcagri.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Zombie {

    private double x;
    private double y;

    private final int width;
    private final int height;

    private final int targetX;
    private final int targetY;

    private double speedX;
    private double speedY;

    private boolean isCrosed = false;

    private double movementSpeed;

    private int animationCounter = 0;

    private double angle;

    private final Map<String, BufferedImage> textures = new HashMap<>();
    private BufferedImage currentTexture;

    public Zombie(int x, int y, int targetX, int targetY, double movementSpeed) {
        this.x = x;
        this.y = y;

        this.width = 50;
        this.height = 50;

        this.targetX = targetX;
        this.targetY = targetY;

        this.movementSpeed = movementSpeed;

        loadTextures();
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;

        g2d.rotate(angle, centerX, centerY);
        g2d.drawImage(currentTexture, (int) x, (int) y, width, height, null);
        g2d.dispose();
    }

    public void update(Player player) {
        if (getBounds().intersects(player.getBounds())) {
            isCrosed = true;
            return;
        }

        setAngle();

        x += speedX;
        y += speedY;

        animationCounter++;

        int animationSpeed = 15;
        if (animationCounter >= animationSpeed) {
            if (currentTexture == textures.get("zombie1")) {
                currentTexture = textures.get("zombie2");
            } else {
                currentTexture = textures.get("zombie1");
            }
            animationCounter = 0;
        }
    }

    private void loadTextures() {
        try {
            BufferedImage zombie1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/zombie1.png")));
            BufferedImage zombie2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/zombie2.png")));

            textures.put("zombie1", zombie1);
            textures.put("zombie2", zombie2);

            currentTexture = zombie1;
        } catch (IOException e) {
            System.out.println("Failed to load zombie textures: " + e.getMessage());
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x+5, (int) y+5, width-10, height-10);
    }

    public boolean isCrosed() {
        return isCrosed;
    }

    public void setAngle() {
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;

        double deltaX = targetX - centerX;
        double deltaY = targetY - centerY;

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 0) {
            speedX = deltaX / distance * movementSpeed;
            speedY = deltaY / distance * movementSpeed;
        }

        angle = Math.atan2(deltaY, deltaX);
    }
}


