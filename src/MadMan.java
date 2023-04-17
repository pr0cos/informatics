import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MadMan extends Enemy{
    long t;
    public MadMan(double x, double y, Rectangle room) throws IOException {
        super(x, y, 10, 2, room, 100, "data\\madman.png", "data\\madman_rotated.png");
        t = System.currentTimeMillis();
    }

    ArrayList<EnemyBullet> shoot(){
        long time = System.currentTimeMillis();
        if(time - t > 500) {
            ArrayList<EnemyBullet> bullets = new ArrayList<>();
            Random r = new Random();
            for(int i = 0; i < 5; i++){
                Vector2D v = new Vector2D(r.nextDouble(-1, 1), r.nextDouble(-1, 1));
                v.normalize();
                bullets.add(new EnemyBullet(10 * v.getX(), 10 * v.getY(), damage, x +size / 2, y + size / 2, -1));
            }
            t = time;
            return bullets;
        }else{
            return null;
        }
    }
}
