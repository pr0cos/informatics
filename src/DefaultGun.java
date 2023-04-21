import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DefaultGun extends Gun{
    public DefaultGun() throws IOException {
        super(3, Color.YELLOW, 400, ImageIO.read(MyPanel.class.getResourceAsStream("default_gun.png")));
    }
}
