import java.awt.*;
import java.awt.image.BufferedImage;

public class Gun {

    int damage;
    Color c;
    int cooldown;
    BufferedImage image;

    public Gun(int damage, Color c, int cooldown, BufferedImage image) {
        this.damage = damage;
        this.c = c;
        this.cooldown = cooldown;
        this.image = image;
    }
}