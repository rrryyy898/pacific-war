package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

/**
 * A type of enemy that moves in a zig‑zag pattern across the screen while
 * descending.  Each instance alternates its initial horizontal direction
 * relative to the previous one to provide variety in movement.  The zig‑zag
 * enemy ignores the direction parameter supplied to the standard enemy
 * act method and instead controls its own horizontal velocity.
 */
public class ZigZagEnemy extends Enemy {

    private static boolean spawnDirectionRight = true;
    private int dx;

    public ZigZagEnemy(int x, int y) {
        super(x, y);
        // Determine initial horizontal direction based on static toggle
        dx = spawnDirectionRight ? 2 : -2;
        spawnDirectionRight = !spawnDirectionRight;
        // Load the appropriate sprite based on direction
        updateImageForDirection();
    }

    /**
     * Update the sprite image to reflect the current horizontal direction.
     */
    private void updateImageForDirection() {
        String path = dx > 0 ? IMG_ZIGZAG_RIGHT : IMG_ZIGZAG_LEFT;
        var ii = new ImageIcon(path);
        var scaled = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaled);
    }

    /**
     * Zig‑zag enemies move horizontally at a fixed speed while slowly
     * descending.  They reverse direction when they reach the screen
     * boundaries and swap their sprite to match their new heading.
     */
    @Override
    public void act(int ignoredDirection) {
        // Move horizontally and vertically
        x += dx;
        y += 1;
        // If we hit a horizontal boundary, reverse direction
        int spriteWidth = 0;
        if (getImage() != null) {
            spriteWidth = getImage().getWidth(null);
        }
        if (x <= BORDER_LEFT || x >= BOARD_WIDTH - BORDER_RIGHT - spriteWidth) {
            dx = -dx;
            updateImageForDirection();
        }
    }
}