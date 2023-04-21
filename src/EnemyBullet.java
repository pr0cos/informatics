import java.awt.*;

public class EnemyBullet extends Bullet{
    public EnemyBullet(double dx, double dy, int damage, double x, double y, int dist_max) {
        super(dx, dy, damage, x, y, Color.red, dist_max);
    }

    public EnemyBullet(Vector2D v, int damage, double x, double y, int dist_max) {
        super(v.x, v.y, damage, x, y, Color.red, dist_max);
    }

    @Override
    public void paint(Graphics g){
        g.setColor(color);

        g.fillOval((int)x, (int)y, 20, 20);
    }
}
