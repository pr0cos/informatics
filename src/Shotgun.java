import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Shotgun extends Gun{

    public Shotgun() throws IOException {
        super(3, new Color(78, 222, 11), 750, ImageIO.read(new File("data\\shotgun.png")));
    }
}
