import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SniperRifle extends Gun{
    public SniperRifle() throws IOException {
        super(10, new Color(152, 231, 122), 1000, ImageIO.read(MyPanel.class.getResourceAsStream("sniper_rifle.png")));
    }
}
