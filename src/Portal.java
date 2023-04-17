import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Portal {
    int type;
    int x;
    int y;
    int width;
    int height;
    BufferedImage portal1;
    BufferedImage portal2;

    public Portal(int type, int x, int y, int width, int height) throws IOException {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        portal1 = ImageIO.read(new File("data\\damage_portal.png"));
        portal2 = ImageIO.read(new File("data\\hp_portal.png"));
    }

    public boolean in_portal(int x1, int y1){
        if(x <= x1 && x1 <= x + width && y <= y1 && y1 <= y + height){
            return true;
        }
        return false;
    }

    public void paint(Graphics g, int x1, int y1){
        if(type == 0){
            g.drawImage(portal1, x + x1, y + y1, width, height, null);
        }else{
            g.drawImage(portal2, x + x1, y + y1, width, height, null);
        }

    }
}
