package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;
import java.awt.Image;

public class MiniBoss1 extends Enemy {

    public MiniBoss1(int x, int y) {
        super(x, y);

        ImageIcon ii = new ImageIcon(IMG_BOSS1);
        Image image = ii.getImage();
        setImage(image);
        setImageDimensions(image.getWidth(null), image.getHeight(null));
        setHp(2); // 2 HP
        setVisible(true);
    }

    @Override
    public void act(int direction) {
        this.y += 1; // slow downward movement
    }
}
