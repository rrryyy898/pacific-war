package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

/**
 * Represents an upgraded player projectile.  Shot2 uses a different
 * sprite image than the default shot and is intended to be used when
 * the player has collected the upgrade power‑up.  Positioning logic
 * mirrors that of the base Shot class so that bullets originate from
 * the middle of the player's sprite.
 */
public class Shot2 extends Shot {

    private static final int V_SPACE = 1;

    public Shot2(int x, int y) {
        super();
        initShot2(x, y, PLAYER_WIDTH * SCALE_FACTOR);
    }

    public Shot2(int x, int y, int playerWidth) {
        super();
        initShot2(x, y, playerWidth);
    }

    private void initShot2(int x, int y, int playerWidth) {
        var ii = new ImageIcon(IMG_SHOT2);
        // Scale similarly to the standard shot: half the global scale factor
        int bulletWidth = ii.getIconWidth() * SCALE_FACTOR / 2;
        int bulletHeight = ii.getIconHeight() * SCALE_FACTOR / 2;
        var scaledImage = ii.getImage().getScaledInstance(bulletWidth, bulletHeight,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
        // Centre horizontally relative to the player's sprite
        int offsetX = (playerWidth - bulletWidth) / 2;
        setX(x + offsetX);
        // Spawn just above the player's sprite
        setY(y - V_SPACE);
    }

    @Override
    public void act() {
        // Move upward like the standard shot
        y -= 10;
    }
}