import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SniperRifle extends Gun{
    public SniperRifle() throws IOException {
        super(10, new Color(152, 231, 122), 1000, ImageIO.read(new File("data\\sniper_rifle.png")));
    }
}
