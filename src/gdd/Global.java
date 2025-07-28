package gdd;

public class Global {
    private Global() {
        // Prevent instantiation
    }

    public static final int SCALE_FACTOR = 3; // Scaling factor for sprites

    public static final int BOARD_WIDTH = 512; // Doubled from 358
    public static final int BOARD_HEIGHT = 716; // Doubled from 350
    public static final int BORDER_RIGHT = 60; // Doubled from 30
    public static final int BORDER_LEFT = 10; // Doubled from 5

    public static final int GROUND = 580; // Doubled from 290
    public static final int BOMB_HEIGHT = 10; // Doubled from 5

    public static final int ALIEN_HEIGHT = 24; // Doubled from 12
    public static final int ALIEN_WIDTH = 24; // Doubled from 12
    public static final int ALIEN_INIT_X = 300; // Doubled from 150
    public static final int ALIEN_INIT_Y = 10; // Doubled from 5
    public static final int ALIEN_GAP = 30; // Gap between aliens

    public static final int GO_DOWN = 30; // Doubled from 15
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int PLAYER_WIDTH = 30; // Doubled from 15
    public static final int PLAYER_HEIGHT = 20; // Doubled from 10

    // Images
    public static final String IMG_ENEMY = "gdd-space-invaders-project\\src\\images\\alien1.png";
    public static final String IMG_PLAYER = "gdd-space-invaders-project\\src\\images\\player1.png";
    public static final String IMG_SHOT = "gdd-space-invaders-project\\src\\images\\shot1.png";
    public static final String IMG_EXPLOSION = "gdd-space-invaders-project\\src\\images\\explosion1.png";
    public static final String IMG_TITLE = "gdd-space-invaders-project\\src\\images\\title1.png";
    public static final String IMG_POWERUP_SPEEDUP = "gdd-space-invaders-project\\src\\images\\powerup-s.png";
    public static final String IMG_BG_TILE = "images/b1.png";

    // Additional assets for upgraded gameplay
    public static final String IMG_ZIGZAG_RIGHT = "gdd-space-invaders-project\\src\\images\\zigzag_right.png";
    public static final String IMG_ZIGZAG_LEFT  = "gdd-space-invaders-project\\src\\images\\zigzag_left.png";
    public static final String IMG_BOSS1        = "gdd-space-invaders-project\\src\\images\\boss1.png";
    public static final String IMG_BOSS2        = "gdd-space-invaders-project\\src\\images\\boss2.png";
    public static final String IMG_BOSS_BULLET  = "gdd-space-invaders-project\\src\\images\\boss_bullet.png";
    public static final String IMG_BOSS_EXPLOSION1 = "gdd-space-invaders-project\\src\\images\\boss_explosion1.png";
    public static final String IMG_BOSS_EXPLOSION2 = "gdd-space-invaders-project\\src\\images\\boss_explosion2.png";
    public static final String IMG_BOSS_EXPLOSION3 = "gdd-space-invaders-project\\src\\images\\boss_explosion3.png";
    public static final String IMG_POWERUP_UPGRADE = "gdd-space-invaders-project\\src\\images\\powerup.png";
    public static final String IMG_UPGRADE_HUD   = "gdd-space-invaders-project\\src\\images\\upgrade.png";
    public static final String IMG_SHOT2         = "gdd-space-invaders-project\\src\\images\\shot2.png";

    /**
     * Additional assets introduced for the multi‑stage refactor.  IMG_BOSS3
     * represents the final boss sprite used in stage three.  IMG_SHIP
     * represents the large ship enemy also used in the final stage.
     */
    public static final String IMG_BOSS3 = "gdd-space-invaders-project\\src\\images\\boss3.png";
    public static final String IMG_SHIP  = "gdd-space-invaders-project\\src\\images\\ship.png";

    /**
     * The upgraded player sprite.  When the player collects the upgrade
     * power‑up in stage one, their ship changes to use this image.  Do not
     * overwrite IMG_PLAYER so that the default sprite remains player1.png.
     */
    public static final String IMG_PLAYER_UPGRADE = "gdd-space-invaders-project\\src\\images\\player2.png";
    public static final String IMG_MISSION_COMPLETED = "gdd-space-invaders-project\\src\\images\\mission_completed.png";


    
}
