package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;
import java.awt.Image;

public class ShipEnemy extends Enemy {

    public ShipEnemy(int x, int y) {
        super(x, y);

        ImageIcon ii = new ImageIcon(IMG_SHIP);
        Image image = ii.getImage();
        setImage(image);
        setImageDimensions(image.getWidth(null), image.getHeight(null));
        setHp(2); // 2 HP
        setVisible(true);
    }

    @Override
    public void act(int direction) {
        this.y += 1;
    }
}
