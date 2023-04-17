import java.awt.*;
import java.io.IOException;

public class Rifler extends Enemy{
    long t;
    public Rifler(double x, double y, Rectangle room) throws IOException {
        super(x, y, 2, 3, room, 80, "data\\rifler.png", "data\\rifler_rotated.png");
        t = System.currentTimeMillis();
    }

    EnemyBullet shoot(int x, int y){
        long time = System.currentTimeMillis();
        if(time - t > 300) {
            Vector2D v = new Vector2D(x - (this.x + size / 2.0), y - (this.y + size / 2.0));
            v.normalize();
            t = time;
            return new EnemyBullet(15 * v.getX(), 15 * v.getY(), damage, (this.x + size / 2.0), (this.y + size / 2.0), -1);
        }else{
            return null;
        }
    }
}
