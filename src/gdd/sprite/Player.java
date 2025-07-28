package gdd.sprite;

import static gdd.Global.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;

public class Player extends Sprite {

    private static final int START_X = 270;
    private static final int START_Y = 540;
    // Removed unused width field. The player's sprite size is derived from its image.
    private int currentSpeed = 2;

    // When true, the player fires two bullets with each shot
    private boolean doubleShot = false;

    /**
     * Vertical movement velocity.  Unlike dx which is defined in the
     * Sprite base class, dy is maintained here so that the player can
     * move up and down independently of other sprites.  A positive dy
     * moves the player downward, negative dy moves upward.  dy is set
     * when the player presses the up or down arrow keys.
     */
    private int dy = 0;

    private Rectangle bounds = new Rectangle(175,135,17,32);

    public Player() {
        initPlayer();
    }

    private void initPlayer() {
        // Load the default player sprite
        var ii = new ImageIcon(IMG_PLAYER);
        var scaledImage = ii.getImage().getScaledInstance(
                ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);

        setX(START_X);
        setY(START_Y);
    }

    public int getSpeed() {
        return currentSpeed;
    }

    public int setSpeed(int speed) {
        if (speed < 1) {
            speed = 1; // Ensure speed is at least 1
        }
        this.currentSpeed = speed;
        return currentSpeed;
    }
    public void setDx(int dx) {
        this.dx = dx;
    }

    public void shoot(List<Shot> shots) {
        if (canShoot()) {
            if (doubleShot) {
                // Shoot with upgraded shot image (IMG_SHOT2) but still only one bullet
                shots.add(new Shot(x + getWidth() / 2, y, IMG_SHOT2));
            } else {
                // Normal shot
                shots.add(new Shot(x + getWidth() / 2, y, IMG_SHOT));
            }
        }
    }
 
    public void act() {
        // Update the player's horizontal position based on the current velocity.
        x += dx;

        // Update the player's vertical position.  Add dy to y to move
        // the player up or down.  Without this the player is limited to
        // horizontal movement only.  Vertical movement is clamped to the
        // screen boundaries below.
        y += dy;

        // Clamp the player's position so it stays within the visible board area.
        // Determine the width of the player's sprite from its image.  If the image
        // has not yet been initialised, assume zero width to avoid null
        // pointer exceptions.
        int imageWidth = 0;
        if (getImage() != null) {
            imageWidth = getImage().getWidth(null);
        }
        // Prevent the player from moving past the left edge
        if (x < 0) {
            x = 0;
        }
        // Prevent the player from moving past the right edge
        int maxX = BOARD_WIDTH - imageWidth;
        if (x > maxX) {
            x = maxX;
        }

        // Clamp the player's vertical position so it remains within the
        // screen.  Use the height of the player's sprite to prevent the
        // image from partially leaving the play field.  This allows the
        // player to move forward and backward while keeping the entire
        // sprite visible.
        int imageHeight = 0;
        if (getImage() != null) {
            imageHeight = getImage().getHeight(null);
        }
        // Top boundary
        if (y < 0) {
            y = 0;
        }
        // Bottom boundary
        int maxY = BOARD_HEIGHT - imageHeight;
        if (y > maxY) {
            y = maxY;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -currentSpeed;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = currentSpeed;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -currentSpeed;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = currentSpeed;
        }
    }

    /**
     * Enable the player's double shot capability.  After calling this
     * method the scene should spawn two bullets for each shot fired.
     */
    public void enableDoubleShot() {
        this.doubleShot = true;
    }

    public boolean hasDoubleShot() {
        return doubleShot;
    }

    /**
     * Upgrade the player's sprite to the upgraded version defined by
     * IMG_PLAYER.  This is called when the player collects the upgrade
     * power‑up.
     */
    /**
     * Replace the player's sprite with the upgraded image.  This method
     * should be called when the player collects the upgrade power‑up in
     * stage one.  It loads the image defined by IMG_PLAYER_UPGRADE and
     * scales it using the global scaling factor.  Using a separate
     * constant allows the default sprite (player1.png) to remain
     * untouched.
     */
    public void upgradePlayerSprite() {
        // Load the upgraded sprite instead of the default player image
        var ii = new ImageIcon(IMG_PLAYER_UPGRADE);
        var scaled = ii.getImage().getScaledInstance(
                ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaled);
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}