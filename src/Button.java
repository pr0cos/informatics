import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Button{
    int x;
    int y;
    int width;
    int height;
    BufferedImage image;
    ButtonAction action;
    boolean is_mouse_over;

    public Button(int x, int y, int width, int height, String file_name, ButtonAction action) throws IOException {
        this.x = x;
        this.y = y;
        this.image = ImageIO.read(new File(file_name));
        this.width = width;
        this.height = height;
        this.action = action;
        this.is_mouse_over = false;
    }

    public void paint(Graphics g) {
        if (is_mouse_over) {
            g.drawImage(image, x - 10, y - 10, width + 20, height + 20, null);
        } else {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    public void on_mouse_hit(int x, int y) throws IOException {
        if(this.x <= x && x <= this.x + this.width && this.y <= y && y <= this.y + this.height){
            action.onClick();
        }
    }
    public void on_mouse_move(int mouse_x, int mouse_y){
        is_mouse_over = this.x <= mouse_x && mouse_x <= this.x + this.width && this.y <= mouse_y && mouse_y <= this.y + this.height;
    }

}
