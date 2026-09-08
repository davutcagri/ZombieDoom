package davutcagri.entity;

import java.awt.*;

public class Bullet {

    private double x;
    private double y;

    private final int width;
    private final int height;

    private double speedX;
    private double speedY;

    public Bullet(double centerX, double centerY, int width, int height, double mouseX, double mouseY) {
        this.x = centerX - (double) width / 2;
        this.y = centerY - (double) height / 2;

        this.width = width;
        this.height = height;

        double deltaX = mouseX - x;
        double deltaY = mouseY - y;


        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 0) {
            int MOVEMENT_SPEED = 5;
            speedX = deltaX / distance * MOVEMENT_SPEED;
            speedY = deltaY / distance * MOVEMENT_SPEED;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval((int) x, (int) y, width, height);
    }

    public void update() {
        x += speedX;
        y += speedY;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
