import java.awt.*;

public class PlayerBullet extends Bullet{

    public PlayerBullet(double dx, double dy, int damage, double x, double y, Color color, int max_dist) {
        super(dx, dy, damage, x, y, color, max_dist);
    }
}
