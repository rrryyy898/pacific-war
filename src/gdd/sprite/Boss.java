package gdd.sprite;

import static gdd.Global.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * Represents a boss enemy with its own health, movement and shooting
 * behaviour.  Bosses drift horizontally across the screen and periodically
 * shoot projectiles towards the player.  When a boss's health reaches
 * zero it should explode and be removed from the game.
 */
public class Boss extends Sprite {

    private int hp;
    private int maxHealth;
    private final String explosionPath;
    private int dx = 2;
    // Fire a bullet every 2 seconds (approx 120 frames at 60 FPS)
    private static final int BULLET_COOLDOWN_FRAMES = 120;
    private int nextBulletFrame = 0;
    private final List<BossBullet> bullets = new ArrayList<>();

    public Boss(int x, int y, String imagePath, String explosionPath, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.maxHealth = hp;
        this.explosionPath = explosionPath;
        // Load and scale boss sprite
        var ii = new ImageIcon(imagePath);
        var scaled = ii.getImage().getScaledInstance(
                ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaled);
    }
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Update the boss's position.  Bosses move horizontally and bounce off
     * screen edges similar to regular enemies.
     */
    @Override
    public void act() {
        x += dx;
        int spriteWidth = 0;
        if (getImage() != null) {
            spriteWidth = getImage().getWidth(null);
        }
        if (x <= BORDER_LEFT || x >= BOARD_WIDTH - BORDER_RIGHT - spriteWidth) {
            dx = -dx;
        }
    }

    /**
     * Attempt to fire a bullet if the cooldown has expired.  Bullets are
     * spawned from the bottom centre of the boss sprite.
     *
     * @param frame current frame count used to schedule bullet firing
     */
    public void maybeShoot(int frame) {
        if (frame >= nextBulletFrame) {
            // Determine position at which to spawn the bullet
            int spriteWidth = 0;
            int spriteHeight = 0;
            if (getImage() != null) {
                spriteWidth = getImage().getWidth(null);
                spriteHeight = getImage().getHeight(null);
            }
            int bulletX = x + (spriteWidth / 2);
            int bulletY = y + spriteHeight;
            bullets.add(new BossBullet(bulletX, bulletY));
            nextBulletFrame = frame + BULLET_COOLDOWN_FRAMES;
        }
    }

    /**
     * Update all bullets fired by this boss.  Bullets that move off screen are
     * removed from the internal list.
     */
    public void updateBullets() {
        var toRemove = new ArrayList<BossBullet>();
        for (BossBullet bullet : bullets) {
            bullet.act();
            if (bullet.getY() >= BOARD_HEIGHT) {
                bullet.die();
                toRemove.add(bullet);
            }
        }
        bullets.removeAll(toRemove);
    }

    /**
     * Access the current list of active bullets.
     */
    public List<BossBullet> getBullets() {
        return bullets;
    }

    /**
     * Reduce the boss's health by one.  If health reaches zero the caller
     * should handle destruction and explosions.
     */
    public void decrementHealth() {
        hp--;
    }

    public int getHealth() {
        return hp;
    }

    public String getExplosionPath() {
        return explosionPath;
    }
}