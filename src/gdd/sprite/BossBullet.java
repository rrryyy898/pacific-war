package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

/**
 * A projectile fired by a boss enemy.  Boss bullets fall towards the
 * player and can damage or kill the player upon collision.  They are
 * visually distinct from regular bombs to signal increased threat.
 */
public class BossBullet extends Sprite {

    // Speed at which the boss bullet descends.  Increase this slightly
    // compared to regular bombs so boss attacks feel more challenging.
    private static final int SPEED = 5;

    public BossBullet(int x, int y) {
        this.x = x;
        this.y = y;
        // Load boss bullet image
        var ii = new ImageIcon(IMG_BOSS_BULLET);
        // Scale using the global SCALE_FACTOR to maintain consistency
        var scaled = ii.getImage().getScaledInstance(
                ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaled);
    }

    @Override
    public void act() {
        // Move downward towards the player
        y += SPEED;
    }

    /**
     * Override collision detection to shrink the hit box of the boss bullet.
     * Without this, near‑misses can still register as hits, resulting in
     * frustrating deaths when the projectile appears not to touch the player.
     * The bounding box is reduced by one third in both dimensions, similar
     * to the Bomb class.
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