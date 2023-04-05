import java.awt.*;

public class Rifler extends Enemy{
    public Rifler(double x, double y, Rectangle room) {
        super(x, y, 2, 5, room, 45);
    }

    EnemyBullet shoot(int x, int y){
        Vector2D v = new Vector2D(x - (this.x + size / 2.0), y - (this.y + size / 2.0));
        v.normalize();
        return new EnemyBullet(v.getX(), v.getY(), damage, (this.x + size / 2.0), (this.y + size / 2.0));
    }

    @Override
    public void paint(Graphics g){
        g.setColor(Color.pink);
        g.fillRect((int)x, (int)y, size, size);
    }
}
