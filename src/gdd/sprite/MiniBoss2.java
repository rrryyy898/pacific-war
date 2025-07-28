package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;
import java.awt.Image;

public class MiniBoss2 extends Enemy {

    public MiniBoss2(int x, int y) {
        super(x, y);

        ImageIcon ii = new ImageIcon(IMG_BOSS2);
        Image image = ii.getImage();
        setImage(image);
        setImageDimensions(image.getWidth(null), image.getHeight(null));
        setHp(5); // 5 HP
        setVisible(true);
    }

    @Override
    public void act(int direction) {
        this.y += 1;
    }
}
