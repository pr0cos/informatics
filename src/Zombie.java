import java.awt.*;
import java.io.IOException;

public class Zombie extends Enemy{


    public Zombie(double x, double y, Rectangle room) throws IOException {
        super(x, y, 6, 3, room, 50, "data\\zombie.png", "data\\zombie_rotated.png");
    }
}
