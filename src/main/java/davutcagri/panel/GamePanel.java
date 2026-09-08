package davutcagri.panel;

import davutcagri.entity.Bullet;
import davutcagri.entity.Player;
import davutcagri.entity.Zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel {

    private final int width;
    private final int height;

    private final int centerX;
    private final int centerY;

    private BufferedImage background;

    private int zombieSpawnDelay = 1000;
    private int previousScore = 0;
    private int score = 0;
    private int wave = 1;
    private boolean isGameOver = false;
    private boolean isGameWon = false;
    private double zombieSpeed = 1.5;

    private final JLabel healthLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel();
    private final JLabel waveLabel = new JLabel();

    private final Player player;
    private final List<Zombie> zombies = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();

    private Timer gameTimer;
    private Timer playerTimer;
    private Timer zombieSpawner;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        this.centerX = width / 2;
        this.centerY = height / 2;

        this.setPreferredSize(new Dimension(width, height));

        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/background.png")));
        } catch (IOException e) {
            System.out.println("Failed to load background image: " + e.getMessage());
        }

        player = new Player(centerX, centerY);

        healthLabel.setText("Health: " + player.getHealth());
        scoreLabel.setText("Score: " + 0);
        waveLabel.setText("Wave: " + wave);

        healthLabel.setForeground(Color.WHITE);
        scoreLabel.setForeground(Color.WHITE);
        waveLabel.setForeground(Color.WHITE);

        add(scoreLabel);
        add(healthLabel);
        add(waveLabel);

        gameTimer = new Timer(10, e -> {
            updateGame();
            updateZombies();
            updateBullets();
            repaint();
        });

        playerTimer = new Timer(500, e -> {
            updatePlayer();
        });

        zombieSpawner = new Timer(zombieSpawnDelay, e -> {
            spawnZombie();
        });

        gameTimer.start();
        zombieSpawner.start();
        playerTimer.start();

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                player.setAngle(e.getX(), e.getY());
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Bullet bullet = new Bullet(centerX, centerY, 10, 10, e.getX(), e.getY());
                bullets.add(bullet);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, width, height, null);
        player.render(g);

        for (Zombie zombie : zombies) {
            zombie.render(g);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    private void spawnZombie() {

        int spawnX = 0;
        int spawnY = 0;

        Random random = new Random();
        int side = random.nextInt(4);

        switch (side) {
            case 0 -> {
                spawnX = random.nextInt(width);
            }
            case 1 -> {
                spawnX = width;
                spawnY = random.nextInt(height);
            }
            case 2 -> {
                spawnX = random.nextInt(width);
                spawnY = height;
            }
            case 3 -> {
                spawnY = random.nextInt(height);
            }
        }

        Zombie zombie = new Zombie(spawnX, spawnY, centerX, centerY, zombieSpeed);
        zombies.add(zombie);
    }

    private void updateGame() {

        if (score == previousScore + 10) {
            previousScore = score;
            wave++;
            waveLabel.setText("Wave: " + wave);
            updateWave();
        }

        if (isGameWon) {
            stopGame();
            JOptionPane.showMessageDialog(null, "You Won!");
            System.exit(0);
        }

        if (isGameOver) {
            stopGame();
            JOptionPane.showMessageDialog(null, "Game Over!");
            System.exit(0);
        }

        scoreLabel.setText("Score: " + score);
    }

    private void updateWave() {
        switch (wave) {
            case 1 -> {
                zombieSpeed = 1.5;
                zombieSpawnDelay = 1000;
            }
            case 2 -> {
                zombieSpeed = 2.0;
                zombieSpawnDelay = 800;
            }
            case 3 -> {
                zombieSpeed = 2.5;
                zombieSpawnDelay = 600;
            }
            case 4 -> {
                zombieSpeed = 3.0;
                zombieSpawnDelay = 400;
            }
            case 5 -> {
                isGameWon = true;
                break;
            }
        }

        zombieSpawner.setDelay(zombieSpawnDelay);
    }

    private void updatePlayer() {

        for (Zombie zombie : zombies) {
            if (zombie.isCrosed()) {
                player.setHealth(player.getHealth() - 1);
                if (player.getHealth() <= 0) {
                    player.setHealth(0);
                    isGameOver = true;
                }
            }
        }

        healthLabel.setText("Health: " + player.getHealth());
    }

    private void updateBullets() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();

            Iterator<Zombie> zombieIterator = zombies.iterator();
            while (zombieIterator.hasNext()) {
                Zombie zombie = zombieIterator.next();
                if (bullet.getBounds().intersects(zombie.getBounds())) {
                    zombieIterator.remove();
                    bulletIterator.remove();
                    score++;
                    break;
                }
            }

        }
    }

    private void updateZombies() {
        for (Zombie zombie : zombies) {
            zombie.update(player);
        }
    }

    private void stopGame() {
        gameTimer.stop();
        playerTimer.stop();
        zombieSpawner.stop();
    }

}
