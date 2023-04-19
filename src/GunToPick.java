import java.awt.*;
import java.awt.image.BufferedImage;

public class GunToPick{
    Gun gun;
    int x;
    int y;
    int width;
    int height;
    BufferedImage image;
    long t_pick;

    public GunToPick(Gun gun, int x, int y){
        this.gun = gun;
        this.x = x;
        this.y = y;
        image = gun.image;
        this.width = image != null ? image.getWidth() : 0;
        this.height = image != null ? image.getHeight() : 0;
        t_pick = System.currentTimeMillis();
    }

    public Gun pick(Gun g){
        if(System.currentTimeMillis() - t_pick > 15) {
            Gun to_return = gun;
            gun = g;
            t_pick = System.currentTimeMillis();
            if(g != null) {
                image = gun.image;
            }
            return to_return;
        }
        return g;
    }

    public void paint(Graphics g, int x_board, int y_board){
        if(gun != null) {
            g.drawImage(image, x - width / 2 + x_board, y - height / 2 + y_board, null);
        }
    }

    public boolean on_gun(int x1, int y1){
        if(x - width / 2 <= x1 && x1 <= x + width / 2 && y - height / 2 <= y1 && y1 <= y + height / 2){
            return true;
        }
        return false;
    }
}
