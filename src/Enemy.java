import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {

    double x;
    double y;
    int hp;
    int damage;
    int size;
    Rectangle room;
    BufferedImage image;
    BufferedImage image_rotated;
    BufferedImage to_draw;

    public Enemy(double x, double y, int hp, int damage, Rectangle room, int size, String image, String image_rotated) throws IOException {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.damage = damage;
        this.room = room;
        this.size = size;
        this.image = ImageIO.read(new File(image));
        this.image_rotated = ImageIO.read(new File(image_rotated));
        to_draw = this.image;
    }

    public void update(double dx, double dy){
        x += dx;
        y += dy;
        if(dx > 0){
            to_draw = image;
        }else if(dx < 0){
            to_draw = image_rotated;
        }
    }
    public void paint(Graphics g){
        g.drawImage(to_draw, (int)x, (int)y, size, size, null);
    }

    public int getSize() {
        return size;
    }
}