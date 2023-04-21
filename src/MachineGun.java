import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MachineGun extends Gun{
    public MachineGun() throws IOException {
        super(2, new Color(177, 60, 255), 75, ImageIO.read(MyPanel.class.getResourceAsStream("machine_gun.png")));
    }
}
