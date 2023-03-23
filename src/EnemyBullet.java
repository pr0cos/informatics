import java.awt.*;

public class EnemyBullet extends Bullet{
    public EnemyBullet(double dx, double dy, int damage, double x, double y) {
        super(dx, dy, damage, x, y, Color.red);
    }

    @Override
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval((int)x, (int)y, 20, 20);
    }
}
