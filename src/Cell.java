import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Random;

public class Cell {

    int x;
    int y;
    boolean status;
    int size;
    boolean is_wall;
    BufferedImage floor;
    BufferedImage wall;

    Color c = new Color(0, 0, 0);

    public Cell(int x, int y, boolean status, int size) throws IOException {
        this.x = x;
        this.y = y;
        this.status = status;
        this.size = size;
        this.is_wall = false;
        Random r = new Random();
        if(r.nextBoolean()){
            floor = ImageIO.read(new File("data\\floor1.png"));
        }else{
            floor = ImageIO.read(new File("data\\floor2.png"));
        }
        wall = ImageIO.read(new File("data\\wall.png"));
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setC(Color c) {
        this.c = c;
    }

    void paint(Graphics g, int a, int b){
        g.setColor(c);
        if(status){
            g.drawImage(floor, a + size * x, b + size * y, size, size, null);
        }
        if(is_wall){
            g.drawImage(wall, a + size * x, b + size * y, size, size, null);
        }
    }
}
