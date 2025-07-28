package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

/**
 * Represents a bomb dropped by an enemy.  A bomb is a simple sprite that
 * falls vertically down the screen at a fixed speed.  When it collides with
 * the player it should trigger the player’s death and be removed from the
 * scene.  Bombs are scaled using the same global SCALE_FACTOR that other
 * sprites use to maintain visual consistency.
 */
public class Bomb extends Sprite {

    /**
     * Vertical speed of the bomb in pixels per frame.  A small constant
     * value gives the falling animation a retro feel.  Adjust this if
     * bombs appear too slow or too fast compared to other game elements.
     */
    // Increase the vertical speed slightly so enemy bombs descend a bit faster.
    // Keeping this modest maintains the retro pacing while making projectiles
    // feel more threatening.  Adjust this value to tune difficulty.
    private static final int V_SPEED = 4;

    /**
     * Create a new bomb at the given starting coordinates.  The bomb
     * immediately loads and scales its image.  Caller is responsible for
     * positioning the bomb relative to the parent enemy before adding it
     * to the scene.
     *
     * @param x initial x coordinate (top‑left)
     * @param y initial y coordinate (top‑left)
     */
    public Bomb(int x, int y) {
        initBomb(x, y);
    }

    /**
     * Initialise the bomb sprite by loading its image from the project’s
     * resources and scaling it using the global SCALE_FACTOR.  The x and y
     * coordinates are stored directly on the base Sprite.  If the image
     * cannot be loaded the bomb will remain invisible.
     */
    private void initBomb(int x, int y) {
        this.x = x;
        this.y = y;
        // Hard‑code the path to the bomb sprite.  This mirrors the other
        // image constants in Global.java which reference the full project
        // directory.  Using ImageIcon allows Swing to locate the file from
        // within the working directory when running the game.
        var bombPath = "gdd-space-invaders-project\\src\\images\\bomb.png";
        var ii = new ImageIcon(bombPath);
        if (ii.getImage() != null) {
            // Scale the bomb to be smaller than other sprites.  Using half of
            // the global scale factor gives the bomb a compact appearance so
            // that it doesn’t overwhelm the player sprite.  Adjust this
            // multiplier if bombs still appear too large or too small.
            int scaledWidth = ii.getIconWidth() * SCALE_FACTOR / 2;
            int scaledHeight = ii.getIconHeight() * SCALE_FACTOR / 2;
            var scaled = ii.getImage().getScaledInstance(
                    scaledWidth,
                    scaledHeight,
                    java.awt.Image.SCALE_SMOOTH);
            setImage(scaled);
        }
    }

    /**
     * Update the bomb’s position.  Bombs simply fall downward by a fixed
     * number of pixels every frame until they leave the screen or collide
     * with the player.
     */
    @Override
    public void act() {
        this.y += V_SPEED;
    }

    /**
     * Override collision detection to shrink the bomb's collision box.  This
     * prevents near misses from counting as hits, making gameplay feel more
     * fair.  The bounding box is reduced by one third in both dimensions.
     */
    @Override
    public boolean collidesWith(Sprite other) {
        if (other == null || !this.isVisible() || !other.isVisible()) {
            return false;
        }
        int w = getImage().getWidth(null);
        int h = getImage().getHeight(null);
        int marginX = w / 3;
        int marginY = h / 3;
        int thisLeft = getX() + marginX / 2;
        int thisRight = getX() + w - marginX / 2;
        int thisTop = getY() + marginY / 2;
        int thisBottom = getY() + h - marginY / 2;
        int otherLeft = other.getX();
        int otherRight = other.getX() + other.getImage().getWidth(null);
        int otherTop = other.getY();
        int otherBottom = other.getY() + other.getImage().getHeight(null);
        return thisLeft < otherRight && thisRight > otherLeft && thisTop < otherBottom && thisBottom > otherTop;
    }
}