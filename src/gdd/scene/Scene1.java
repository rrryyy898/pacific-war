package gdd.scene;

import gdd.AudioPlayer;
import gdd.Game;
import static gdd.Global.*;
import gdd.SpawnDetails;
import gdd.powerup.PowerUp;
import gdd.powerup.SpeedUp;
import gdd.sprite.Alien1;
import gdd.sprite.Enemy;
import gdd.sprite.Explosion;
import gdd.sprite.Alien1;
import gdd.sprite.Player;
import gdd.sprite.Shot;
import gdd.sprite.Shot2;
import gdd.sprite.ZigZagEnemy;
import gdd.sprite.Boss;
import gdd.sprite.BossBullet;
import gdd.powerup.UpgradePowerUp;
import gdd.sprite.Bomb;
import gdd.sprite.MiniBoss1;
import gdd.sprite.MiniBoss2;
import gdd.sprite.ShipEnemy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.w3c.dom.events.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseListener;
import java.awt.Image;



public class Scene1 extends JPanel {

    private BufferedImage bgTile;
    private int scrollOffsetY = 0;

    private boolean boss1Defeated = false;
    private boolean showMissionComplete = false;

    private int missionCompleteCounter = 180;
    private boolean stage2UpgradeDropped = false;
    private boolean boss2Defeated = false;
    private Image victoryImage;
    private boolean showVictory = false;




    private int frame = 0;
    private List<PowerUp> powerups;
    private List<Enemy> enemies;
    private List<Explosion> explosions;
    private List<Shot> shots;

    private List<Bomb> bombs;

    private HashMap<Enemy, Integer> enemyNextBombFrame;

    private static final int BOMB_COOLDOWN_FRAMES = 5 * 60;

    // Stage management
    private int stage;
    private int enemiesKilled;
    // Spawn timing for random enemies
    private int spawnCounter;
    private int spawnIntervalFrames = 60; // spawn enemy once every second
    // Boss and power‑ups
    private Boss boss;
    private boolean bossActive;
    private UpgradePowerUp upgradePowerUp;
    private boolean upgradeActive;
    private Player player;

    private int bossDeathX;
    private int bossDeathY;

    private boolean showUpgradeHud = false;
    private int upgradeHudCounter = 0;


    private boolean stageIntroActive;
    /** Countdown in frames for the stage intro.  180 frames at 60fps ≈ 3 seconds. */
    private int stageIntroCounter;
    // /** Flag indicating final stage enemies have been spawned. */
    // private boolean finalStageEnemiesSpawned;
    /** Flag indicating the final boss has been spawned. */
    private boolean finalBossSpawned;
    /** Number of kills in the current stage (resets on stage change). */
    private int stageKillCount;
    /** Current upward velocity of player bullets. */
    private int bulletSpeed;
    /** Remaining lives for the player. */
    private int playerLives;
    /** Total score accumulated during the game. */
    private int score;
    /** Whether the stage two upgrade power‑up is on screen. */
    private boolean stage2UpgradeActive;
    /** The power‑up dropped by the stage two boss that enhances
     * bullet speed and player movement. */
    private PowerUp stage2UpgradePowerUp;

    private boolean firstSpeedUpDropped;
    /** Indicates whether the second speed‑up has been dropped in the current stage. */
    private boolean secondSpeedUpDropped;

    private static final int FINAL_STAGE_MAX_CONCURRENT = 3;

    final int BLOCKHEIGHT = 50;
    final int BLOCKWIDTH = 50;

    final int BLOCKS_TO_DRAW = BOARD_HEIGHT / BLOCKHEIGHT;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String message = "Game Over";

    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private final Random randomizer = new Random();

    private Timer timer;
    private final Game game;

    private int currentRow = -1;
    // TODO load this map from a file
    private int mapOffset = 0;
    private final int[][] MAP = {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };

    private HashMap<Integer, SpawnDetails> spawnMap = new HashMap<>();
    private AudioPlayer audioPlayer;
    private int lastRowToShow;
    private int firstRowToShow;

    public Scene1(Game game) {
        this.game = game;
        loadSpawnDetails();}



    private void initAudio() {
        try {
            String filePath = "gdd-space-invaders-project\\src\\audio\\scene1.wav";
            audioPlayer = new AudioPlayer(filePath);
            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Error initializing audio player: " + e.getMessage());
        }
    }

    private void loadSpawnDetails() {
        // TODO load this from a file
        spawnMap.put(50, new SpawnDetails("PowerUp-SpeedUp", 100, 0));
        spawnMap.put(200, new SpawnDetails("Alien1", 200, 0));
        spawnMap.put(300, new SpawnDetails("Alien1", 300, 0));

        spawnMap.put(400, new SpawnDetails("Alien1", 400, 0));
        spawnMap.put(401, new SpawnDetails("Alien1", 450, 0));
        spawnMap.put(402, new SpawnDetails("Alien1", 500, 0));
        spawnMap.put(403, new SpawnDetails("Alien1", 550, 0));

        spawnMap.put(500, new SpawnDetails("Alien1", 100, 0));
        spawnMap.put(501, new SpawnDetails("Alien1", 150, 0));
        spawnMap.put(502, new SpawnDetails("Alien1", 200, 0));
        spawnMap.put(503, new SpawnDetails("Alien1", 350, 0));
    }

    private void initBoard() {
        try {
            URL imageUrl = getClass().getClassLoader().getResource("images/victory.png");
            if (imageUrl != null) {
                victoryImage = new ImageIcon(imageUrl).getImage();
                System.out.println("Victory image loaded successfully.");
            } else {
                System.out.println("Victory image not found.");
            }
        } catch (Exception e) {
            System.out.println("Failed to load victory image: " + e.getMessage());
            victoryImage = null;
        }
    }

    public void start() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.black);

        try {
            bgTile = ImageIO.read(getClass().getClassLoader().getResource(IMG_BG_TILE));
        } catch (IOException e) {
            System.err.println("Failed to load background tile: " + e.getMessage());
        }


        timer = new Timer(1000 / 60, new GameCycle());
        timer.start();

        gameInit();
        initAudio();
    }

    public void stop() {
        try {

            if (timer != null) {
                timer.stop();
            }
            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        } catch (Exception e) {
            System.err.println("Error closing audio player.");
        }
    }

    private void startNextMission() {
        System.out.println("Starting next mission...");
        showMissionComplete = false;
        
        missionCompleteCounter = 0;

        // Transition to stage 2
        stage++;
        stageKillCount = 0;
        enemiesKilled = 0;
        spawnCounter = 0;
        boss = null;
        bossActive = false;
        boss1Defeated = false; // For future boss2
        firstSpeedUpDropped = false;
        secondSpeedUpDropped = false;
       
    }

    
    private void gameInit() {

        enemies = new ArrayList<>();
        powerups = new ArrayList<>();
        explosions = new ArrayList<>();
        shots = new ArrayList<>();
        bombs = new ArrayList<>();
        enemyNextBombFrame = new HashMap<>();

        // Initialise stage system
        stage = 1;
        enemiesKilled = 0;
        spawnCounter = 0;
        bossActive = false;
        boss = null;
        upgradeActive = false;
        upgradePowerUp = null;

        bossDeathX = 0;
        bossDeathY = 0;

        // Reset upgrade HUD display state at the beginning of each game
        showUpgradeHud = false;
        upgradeHudCounter = 0;

        firstSpeedUpDropped = false;
        secondSpeedUpDropped = false;


        player = new Player();

        stageIntroActive = false;
        stageIntroCounter = 0;
        // finalStageEnemiesSpawned flag removed.  Final stage spawning is handled via a queue.
        finalBossSpawned = false;
        stageKillCount = 0;
        bulletSpeed = 10; // default bullet speed
        playerLives = 3;  // default number of lives
        score = 0;
        stage2UpgradeActive = false;
        stage2UpgradePowerUp = null;
    }

    private void drawMap(Graphics g) {
        if (bgTile == null) return;

        int tileWidth = bgTile.getWidth();
        int tileHeight = bgTile.getHeight();

        // Update scroll offset
        scrollOffsetY += 1;
        if (scrollOffsetY >= tileHeight) {
            scrollOffsetY = 0;
        }

        // Fill screen with repeated tiles, scrolling upward
        for (int y = -tileHeight + scrollOffsetY; y < BOARD_HEIGHT; y += tileHeight) {
            for (int x = 0; x < BOARD_WIDTH; x += tileWidth) {
                g.drawImage(bgTile, x, y, null);
            }
        }
    }


    private void drawStarCluster(Graphics g, int x, int y, int width, int height) {
        // Set star color to white
        g.setColor(Color.WHITE);

        // Draw multiple stars in a cluster pattern
        // Main star (larger)
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        g.fillOval(centerX - 2, centerY - 2, 4, 4);

        // Smaller surrounding stars
        g.fillOval(centerX - 15, centerY - 10, 2, 2);
        g.fillOval(centerX + 12, centerY - 8, 2, 2);
        g.fillOval(centerX - 8, centerY + 12, 2, 2);
        g.fillOval(centerX + 10, centerY + 15, 2, 2);

        // Tiny stars for more detail
        g.fillOval(centerX - 20, centerY + 5, 1, 1);
        g.fillOval(centerX + 18, centerY - 15, 1, 1);
        g.fillOval(centerX - 5, centerY - 18, 1, 1);
        g.fillOval(centerX + 8, centerY + 20, 1, 1);
    }

    private void drawAliens(Graphics g) {

        for (Enemy enemy : enemies) {

            if (enemy.isVisible()) {

                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }

            if (enemy.isDying()) {

                enemy.die();
            }
        }
    }

    private void drawPowreUps(Graphics g) {

        for (PowerUp p : powerups) {

            if (p.isVisible()) {

                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
            }

            if (p.isDying()) {

                p.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            inGame = false;
        }
    }

    private void drawShot(Graphics g) {

        for (Shot shot : shots) {

            if (shot.isVisible()) {
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            }
        }
    }


    private void drawBossAndBullets(Graphics g) {
        if (bossActive && boss != null) {
            // Draw the boss sprite
            g.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);
            // Draw each boss bullet
            for (BossBullet bb : boss.getBullets()) {
                if (bb.isVisible()) {
                    g.drawImage(bb.getImage(), bb.getX(), bb.getY(), this);
                }
            }
            // Draw boss health bar
            int barMaxWidth = 200;
            int barHeight = 10;
            int barX = 10;
            int barY = 30;

            int hp = boss.getHealth();
            int maxHp = boss.getMaxHealth();

            //int barWidth = (int) ((hp / 20.0) * barMaxWidth);
            int barWidth = (int) ((hp / (double) maxHp) * barMaxWidth); // ✅ dynamic scaling

            g.setColor(Color.red);
            g.fillRect(barX, barY, barWidth, barHeight);
            g.setColor(Color.white);
            g.drawRect(barX, barY, barMaxWidth, barHeight);
            g.drawString("Boss HP: " + hp, barX, barY + barHeight + 15);
        }
        // Draw upgrade power‑up if active
        if (upgradeActive && upgradePowerUp != null && upgradePowerUp.isVisible()) {
            g.drawImage(upgradePowerUp.getImage(), upgradePowerUp.getX(), upgradePowerUp.getY(), this);
        }
    }


    private void drawBombs(Graphics g) {
        for (Bomb bomb : bombs) {
            if (bomb.isVisible()) {
                g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
            }
        }
    }

    private void drawExplosions(Graphics g) {

        List<Explosion> toRemove = new ArrayList<>();

        for (Explosion explosion : explosions) {

            if (explosion.isVisible()) {
                g.drawImage(explosion.getImage(), explosion.getX(), explosion.getY(), this);
                explosion.visibleCountDown();
                if (!explosion.isVisible()) {
                    toRemove.add(explosion);
                }
            }
        }

        explosions.removeAll(toRemove);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        // Clear the screen
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);

        if (inGame) {
            // Draw background and moving entities
            drawMap(g);

            // ✅ Draw the status bar AFTER background so it's not overwritten
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, BOARD_WIDTH, 25);
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(14f));
            String stageName;
            if (stage == 1) {
                stageName = "Stage 1";
            } else if (stage == 2) {
                stageName = "Stage 2";
            } else {
                stageName = "Final Stage";
            }
            g.drawString("Current: " + stageName, 10, 18);
            g.drawString("Score: " + score, 150, 18);
            g.drawString("Lives: " + playerLives, 300, 18);

            // ✅ Continue drawing game elements
            g.setColor(Color.green);
            drawExplosions(g);
            drawPowreUps(g);
            drawAliens(g);
            drawPlayer(g);
            drawBossAndBullets(g);
            drawBombs(g);
            drawShot(g);

            // Draw the upgrade HUD overlay when collecting an upgrade
            if (showUpgradeHud) {
                try {
                    var hudIcon = new ImageIcon(IMG_UPGRADE_HUD);
                    var img = hudIcon.getImage();
                    int w = hudIcon.getIconWidth() * SCALE_FACTOR;
                    int h = hudIcon.getIconHeight() * SCALE_FACTOR;
                    var scaled = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
                    int x = (BOARD_WIDTH - w) / 2;
                    int y = (BOARD_HEIGHT - h) / 2;
                    g.drawImage(scaled, x, y, this);
                } catch (Exception ex) {
                    // Ignore missing HUD assets
                }
            }

        } else {
            // Game over; stop the timer and draw the game over screen
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(g);
        }
        if (showVictory){
            if (victoryImage != null) {
                g.drawImage(victoryImage, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, this);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Arial", Font.BOLD, 36));
                String msg = "Mission Completed!";
                int msgWidth = g.getFontMetrics().stringWidth(msg);
                g.drawString(msg, (BOARD_WIDTH - msgWidth) / 2, BOARD_HEIGHT / 2);
            }
            return;

        } else if (showMissionComplete) {
            g.setColor(new Color(0, 0, 0, 180)); // Dark transparent overlay
            g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String msg = "MISSION COMPLETED!";
            int msgWidth = g.getFontMetrics().stringWidth(msg);
            g.drawString(msg, (BOARD_WIDTH - msgWidth) / 2, BOARD_HEIGHT / 2);

            // New spacebar instruction text
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.WHITE);
            String msg2 = "Press SPACE to continue...";
            int msg2Width = g.getFontMetrics().stringWidth(msg2);
            g.drawString(msg2, (BOARD_WIDTH - msg2Width) / 2, (BOARD_HEIGHT / 2) + 50);


        }
        Toolkit.getDefaultToolkit().sync();
    }


    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }

    private void update() {
        boolean boundaryHit = false;

        // Remove any bullets or bombs that are no longer visible at the start of the update cycle.
        shots.removeIf(s -> !s.isVisible());
        bombs.removeIf(b -> !b.isVisible());
        if (bossActive && boss != null) {
            boss.getBullets().removeIf(bb -> !bb.isVisible());
        }

        // player
        player.act();

        // Power-ups
        for (PowerUp powerup : powerups) {
            if (powerup.isVisible()) {
                powerup.act();
                if (powerup.collidesWith(player)) {
                    powerup.upgrade(player);
                }
            }
        }

  
        for (Enemy enemy : enemies) {
            int ex = enemy.getX();
            // Check if any enemy has reached the right boundary
            if (ex >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
                boundaryHit = true;
                break;
            }
            // Check if any enemy has reached the left boundary
            if (ex <= BORDER_LEFT && direction != 1) {
                direction = 1;
                boundaryHit = true;
                break;
            }
        }
        // If a boundary was hit, drop all enemies down by GO_DOWN pixels
        if (boundaryHit) {
            for (Enemy enemy : enemies) {
                enemy.setY(enemy.getY() + GO_DOWN);
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.isVisible()) {
                int ey = enemy.getY();
                // If any enemy reaches the ground, trigger game over
                if (ey > GROUND - ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }
                enemy.act(direction);
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.isVisible() && player.isVisible() && enemy.collidesWith(player)) {

                var ii = new ImageIcon(IMG_EXPLOSION);
                explosions.add(new Explosion(player.getX(), player.getY()));
                playerLives--;
                if (playerLives > 0) {
                    // Preserve current speed and double shot state
                    int currentSpeed = player.getSpeed();
                    boolean hadDouble = player.hasDoubleShot();
                    // Create a new player at the starting position
                    player = new Player();
                    player.setSpeed(currentSpeed);
                    if (hadDouble) {
                        player.enableDoubleShot();
                        player.upgradePlayerSprite();
                    }
                } else {
                    inGame = false;
                    message = "Game Over";
                }
                break;
            }
        }

        // shot
        List<Shot> shotsToRemove = new ArrayList<>();
        for (Shot shot : shots) {

            if (shot.isVisible()) {
                int shotX = shot.getX();
                int shotY = shot.getY();

                for (Enemy enemy : enemies) {

                    if (enemy.isVisible() && shot.isVisible() && shot.collidesWith(enemy)) {
                        // Destroy the enemy and create an explosion
                        var ii = new ImageIcon(IMG_EXPLOSION);
                        enemy.setImage(ii.getImage());
                        enemy.setDying(true);
                        explosions.add(new Explosion(enemy.getX(), enemy.getY()));
                        deaths++;
                        enemiesKilled++;
                        stageKillCount++;
                        // Stage 2: Drop upgrade after first kill
                        if (stage == 2 && !stage2UpgradeDropped && stageKillCount == 1) {
                            upgradePowerUp = new UpgradePowerUp(enemy.getX(), enemy.getY());
                            upgradeActive = true;
                            stage2UpgradeDropped = true;
                        }

                        if (stage == 1) {
                            if (!firstSpeedUpDropped && stageKillCount == 3) {
                                powerups.add(new SpeedUp(enemy.getX(), enemy.getY()));
                                firstSpeedUpDropped = true;
                            } else if (!secondSpeedUpDropped && stageKillCount == 10) {
                                powerups.add(new SpeedUp(enemy.getX(), enemy.getY()));
                                secondSpeedUpDropped = true;
                            } else if (!secondSpeedUpDropped && stageKillCount == 20) {
                                powerups.add(new SpeedUp(enemy.getX(), enemy.getY()));
                                secondSpeedUpDropped = true;
                            }
                        } else if (stage == 2) {
                            if (!firstSpeedUpDropped && stageKillCount == 3) {
                                powerups.add(new SpeedUp(enemy.getX(), enemy.getY()));
                                firstSpeedUpDropped = true;
                            } else if (!secondSpeedUpDropped && stageKillCount == 10) {
                                powerups.add(new SpeedUp(enemy.getX(), enemy.getY()));
                                secondSpeedUpDropped = true;
                            } else if (!secondSpeedUpDropped && stageKillCount == 20) {
                                powerups.add(new SpeedUp(enemy.getX(), enemy.getY()));
                                secondSpeedUpDropped = true;
                            }
                        }

                        score += 10;
                        shot.die();
                        shotsToRemove.add(shot);
                    }
                }

                int y = shot.getY();
                y -= bulletSpeed;

                if (y < 0) {
                    shot.die();
                    shotsToRemove.add(shot);
                } else {
                    shot.setY(y);
                }
            }
        }
        shots.removeAll(shotsToRemove);


        spawnCounter++;

        if (stage == 1) {
            // Stage 1: spawn basic enemies until 50 kills, then boss 1
            if (!bossActive && stageKillCount < 50) {
                if (spawnCounter >= spawnIntervalFrames) {
                    spawnCounter = 0;
                    int spawnX = randomizer.nextInt(BOARD_WIDTH - BORDER_RIGHT - BORDER_LEFT - 40) + BORDER_LEFT;
                    int spawnY = 10;

                    Enemy normal = new Alien1(spawnX, spawnY);
                    enemies.add(normal);
                    enemyNextBombFrame.put(normal, frame);
                }
            }
            if (!bossActive && stageKillCount >= 50 && boss == null && !boss1Defeated) {
                int bossX = (BOARD_WIDTH - 100) / 2;
                int bossY = 50;
                boss = new Boss(bossX, bossY, IMG_BOSS1, IMG_BOSS_EXPLOSION1, 20);
                bossActive = true;
            }

        } else if (stage == 2) {
            // Stage 2: spawn tougher enemies until 50 kills, then boss 2
            if (!bossActive && stageKillCount < 70) {
                if (spawnCounter >= spawnIntervalFrames) {
                    spawnCounter = 0;
                    int spawnX = randomizer.nextInt(BOARD_WIDTH - BORDER_RIGHT - BORDER_LEFT - 40) + BORDER_LEFT;
                    int spawnY = 10;

                    if (randomizer.nextInt(100) < 70) {
                        ZigZagEnemy z = new ZigZagEnemy(spawnX, spawnY);
                        enemies.add(z);
                        enemyNextBombFrame.put(z, frame);
                    } else {
                        Enemy normal = new Alien1(spawnX, spawnY);
                        enemies.add(normal);
                        enemyNextBombFrame.put(normal, frame);
                    }
                }
            }

            if (!bossActive && stageKillCount >= 70 && boss == null && !boss2Defeated) {
                int bossX = (BOARD_WIDTH - 100) / 2;
                int bossY = 50;
                boss = new Boss(bossX, bossY, IMG_BOSS2, IMG_BOSS_EXPLOSION2, 40);
                bossActive = true;
            }

        } else if (stage == 3) {
            if (!bossActive && !finalBossSpawned) {
                    int bossX = (BOARD_WIDTH - 100) / 2;
                    int bossY = 50;
                    boss = new Boss(bossX, bossY, IMG_BOSS3, IMG_BOSS_EXPLOSION2, 100); // Set 100 HP
                    bossActive = true;
                    finalBossSpawned = true;
                    System.out.println("Boss 3 spawned with 100 HP");
                }
/* 
            if (!bossActive && stageKillCount < 25) {
                if (spawnCounter >= spawnIntervalFrames) {
                    spawnCounter = 0;
                    int spawnX = randomizer.nextInt(BOARD_WIDTH - BORDER_RIGHT - BORDER_LEFT - 40) + BORDER_LEFT;
                    int spawnY = 10;

                    int rand = randomizer.nextInt(100);
                    Enemy enemy;

                    if (rand < 30) {
                        enemy = new MiniBoss1(spawnX, spawnY);
                    } else if (rand < 60) {
                        enemy = new ShipEnemy(spawnX, spawnY);
                    } else {
                        enemy = new MiniBoss2(spawnX, spawnY);
                    }

                    enemies.add(enemy);
                    enemyNextBombFrame.put(enemy, frame);
                }
            }

            if (!bossActive && stageKillCount >= 25 && boss == null && !finalBossSpawned) {
                int bossX = (BOARD_WIDTH - 100) / 2;
                int bossY = 50;
                boss = new Boss(bossX, bossY, IMG_BOSS3, IMG_BOSS_EXPLOSION2, 40);
                bossActive = true;
                finalBossSpawned = true;
                System.out.println("Final Boss Spawned");
            }*/
        } 


        for (Enemy enemy : enemies) {
            if (!enemy.isVisible()) {
                continue;
            }

            if (!enemyNextBombFrame.containsKey(enemy)) {
                enemyNextBombFrame.put(enemy, frame);
            }
            // Only spawn a bomb if the global bomb limit is not exceeded and
            // the cooldown timer has expired
            Integer nextFrame = enemyNextBombFrame.get(enemy);
            if (nextFrame != null && frame >= nextFrame && bombs.size() < 7) {
                // Spawn a new bomb beneath the enemy
                Bomb b = new Bomb(enemy.getX(), enemy.getY());
                int enemyWidth = 0;
                if (enemy.getImage() != null) {
                    enemyWidth = enemy.getImage().getWidth(null);
                }
                int bombWidth = 0;
                if (b.getImage() != null) {
                    bombWidth = b.getImage().getWidth(null);
                }
                b.setX(enemy.getX() + (enemyWidth - bombWidth) / 2);
                int offsetY = 0;
                if (enemy.getImage() != null) {
                    offsetY = enemy.getImage().getHeight(null);
                }
                b.setY(enemy.getY() + offsetY);
                bombs.add(b);
                // Schedule the next bomb drop after a fixed cooldown period
                enemyNextBombFrame.put(enemy, frame + BOMB_COOLDOWN_FRAMES);
            }
        }

        // Update bombs: move them, detect collisions and mark for removal
        List<Bomb> bombsToRemove = new ArrayList<>();
        for (Bomb bomb : bombs) {
            if (!bomb.isVisible()) {
                bombsToRemove.add(bomb);
                continue;
            }
            // Move the bomb downward by its internal velocity
            bomb.act();

            // If bomb leaves the screen, mark it for removal
            if (bomb.getY() >= BOARD_HEIGHT) {
                bomb.die();
                bombsToRemove.add(bomb);
                continue;
            }

            if (player.isVisible() && bomb.collidesWith(player)) {
                // Player hit by a bomb.  Reduce lives and either respawn or end the game.
                explosions.add(new Explosion(player.getX(), player.getY()));
                playerLives--;
                if (playerLives > 0) {
                    int currentSpeed = player.getSpeed();
                    boolean hadDouble = player.hasDoubleShot();
                    player = new Player();
                    player.setSpeed(currentSpeed);
                    if (hadDouble) {
                        player.enableDoubleShot();
                        player.upgradePlayerSprite();
                    }
                } else {
                    inGame = false;
                    message = "Game Over";
                }
                // Remove the bomb that hit the player
                bomb.die();
                bombsToRemove.add(bomb);
            }
        }
        // Remove bombs flagged for deletion
        bombs.removeAll(bombsToRemove);

        // -----------------------------------------------------------------
        // Boss behaviour and boss bullets
        if (bossActive && boss != null) {
            // Move the boss horizontally
            boss.act();
            // Fire bullets at intervals
            boss.maybeShoot(frame);
            // Update existing boss bullets
            boss.updateBullets();
            // Check collisions between boss bullets and player
            List<BossBullet> bbToRemove = new ArrayList<>();
            for (BossBullet bBullet : boss.getBullets()) {
                // If the boss bullet hits the player, end the game
                if (player.isVisible() && bBullet.collidesWith(player)) {
                    // Player hit by a boss bullet.  Reduce lives and respawn or end the game.
                    explosions.add(new Explosion(player.getX(), player.getY()));
                    playerLives--;
                    if (playerLives > 0) {
                        int currentSpeed = player.getSpeed();
                        boolean hadDouble = player.hasDoubleShot();
                        player = new Player();
                        player.setSpeed(currentSpeed);
                        if (hadDouble) {
                            player.enableDoubleShot();
                            player.upgradePlayerSprite();
                        }
                    } else {
                        inGame = false;
                        message = "Game Over";
                    }
                    bbToRemove.add(bBullet);
                }
            }
            boss.getBullets().removeAll(bbToRemove);
        }

        // -----------------------------------------------------------------
        // Check player shots hitting the boss
        if (bossActive && boss != null) {
            List<Shot> shotsAgainstBoss = new ArrayList<>();
            for (Shot shot : shots) {
                if (shot.isVisible() && boss.collidesWith(shot)) {
                    shotsAgainstBoss.add(shot);
                    boss.decrementHealth();
                    // add a small explosion effect at the hit location
                    explosions.add(new Explosion(shot.getX(), shot.getY()));
                }
            }
            // Remove any shots that hit the boss
            for (Shot s : shotsAgainstBoss) {
                s.die();
                shots.remove(s);
            }
            // Check if boss is defeated
            if (boss.getHealth() <= 0) {
                bossDeathX = boss.getX();
                bossDeathY = boss.getY();

                var exIcon = new ImageIcon(boss.getExplosionPath());
                boss.setImage(exIcon.getImage());
                boss.setDying(true);
                explosions.add(new Explosion(bossDeathX, bossDeathY));
                bossActive = false;

                score += 200; // Award points for Boss 1

                // ✅ Trigger mission complete overlay instead of power-up or transition
                showMissionComplete = true;
                missionCompleteCounter = 180;

                boss = null; // Clear the boss reference
                boss1Defeated = true;
                
                
                showMissionComplete = true;

                missionCompleteCounter = 999999; // no timeout
                if (stage == 2) {
                    boss2Defeated = true;
                }
                if (stage == 3) {
                    showVictory = true;
                    inGame = false;
                    timer.stop();
                    
                } else {
                    // Only show mission complete for non-final stages
                    showMissionComplete = true;
                    missionCompleteCounter = 180;
                }


            }
        }


        if (upgradeActive && upgradePowerUp != null) {
            if (upgradePowerUp.isVisible()) {
                upgradePowerUp.act();
                if (upgradePowerUp.collidesWith(player)) {
                    // Apply the upgrade to the player (double shot and new sprite)
                    upgradePowerUp.upgrade(player);
                    upgradeActive = false;

                }
            } else {
                upgradeActive = false;
            }
        }


        if (stage2UpgradeActive && stage2UpgradePowerUp != null) {
            if (stage2UpgradePowerUp.isVisible()) {
                stage2UpgradePowerUp.act();
                if (stage2UpgradePowerUp.collidesWith(player)) {
                    // Remove the power‑up from the screen
                    stage2UpgradePowerUp.die();
                    stage2UpgradeActive = false;
                    // Increase bullet speed for stages beyond 2
                    bulletSpeed = Math.max(12, bulletSpeed + 5);
                    // Increase player speed by 1 to make movement snappier
                    player.setSpeed(player.getSpeed() + 1);
                    // Ensure double shot is enabled
                    player.enableDoubleShot();
   
                    
                }
            } else {
                stage2UpgradeActive = false;
            }
        }

        if (showMissionComplete) {
            missionCompleteCounter--;
            if (missionCompleteCounter <= 0) {
                inGame = false;
                message = "You Win!";
            }
        }
    }

    private void doGameCycle() {
        frame++;
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            // ✅ Handle SPACE key to proceed to next stage after mission complete
            if (showMissionComplete && key == KeyEvent.VK_SPACE) {
                startNextMission();
                return;
            }

            // If game is over or paused due to mission complete, ignore other keys
            if (!inGame || showMissionComplete) {
                return;
            }

            player.keyPressed(e);

            if (key == KeyEvent.VK_SPACE) {
                // Limit the number of active shots on screen to prevent spamming
                int maxShots = player.hasDoubleShot() ? 8 : 4;
                if (shots.size() < maxShots) {
                    int playerWidthOnScreen = 0;
                    if (player.getImage() != null) {
                        playerWidthOnScreen = player.getImage().getWidth(null);
                    }
                    if (player.hasDoubleShot()) {
                        // Fire two bullets symmetrically around the centre of the player
                        int bulletWidth;
                        try {
                            var ii = new ImageIcon(IMG_SHOT2);
                            bulletWidth = ii.getIconWidth() * SCALE_FACTOR / 2;
                        } catch (Exception ex) {
                            bulletWidth = 5 * SCALE_FACTOR;
                        }
                        int halfGap = bulletWidth / 2;
                        Shot2 shotLeft = new Shot2(player.getX() - halfGap, player.getY(), playerWidthOnScreen);
                        Shot2 shotRight = new Shot2(player.getX() + halfGap, player.getY(), playerWidthOnScreen);
                        shots.add(shotLeft);
                        shots.add(shotRight);
                    } else {
                        Shot shot = new Shot(player.getX(), player.getY(), playerWidthOnScreen);
                        shots.add(shot);
                    }
                }
            }
        }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }
        }
}
 