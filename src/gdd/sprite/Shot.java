package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class Shot extends Sprite {

    private static final int V_SPACE = 1;

    public Shot() {}

    // Legacy constructor – still required!
    public Shot(int x, int y) {
        this(x, y, PLAYER_WIDTH * SCALE_FACTOR);
    }

    // Used by Scene1.java for better centering
    public Shot(int x, int y, int playerWidth) {
        initShot(x, y, playerWidth, IMG_SHOT); // Use default image
    }

    // New constructor for upgraded shot (shot2.png)
    public Shot(int x, int y, int playerWidth, String imagePath) {
        initShot(x, y, playerWidth, imagePath);
    }

    private void initShot(int x, int y, int playerWidth, String imagePath) {
        var ii = new ImageIcon(imagePath);

        int bulletWidth = ii.getIconWidth() * SCALE_FACTOR / 2;
        int bulletHeight = ii.getIconHeight() * SCALE_FACTOR / 2;
        var scaledImage = ii.getImage().getScaledInstance(bulletWidth, bulletHeight, java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);

        int offsetX = (playerWidth - bulletWidth) / 2;
        setX(x + offsetX);
        setY(y - V_SPACE);
    }
}
