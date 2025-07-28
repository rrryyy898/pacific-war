package gdd.powerup;

import gdd.sprite.Player;
import static gdd.Global.*;
import javax.swing.ImageIcon;

/**
 * A power‑up that upgrades the player's weapon and sprite.
 * It enables double bullets and drifts downward like SpeedUp.
 */
public class UpgradePowerUp extends PowerUp {

    public UpgradePowerUp(int x, int y) {
        super(x, y);

        ImageIcon ii = new ImageIcon(IMG_POWERUP_UPGRADE);
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() ,
                ii.getIconHeight() ,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act() {
        // Make it fall downward like SpeedUp
        y += 2;

        if (y > BOARD_HEIGHT) {
            die(); // Remove it if it goes off-screen
        }
    }

    @Override
    public void upgrade(Player player) {
        player.upgradePlayerSprite();
        player.enableDoubleShot();
        this.die();
    }
}
