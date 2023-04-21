import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Hunter extends Enemy{
    long t;
    public Hunter(double x, double y, Rectangle room) throws IOException {
        super(x, y, 10, 2, room, 80, "hunter.png", "hunter_rotated.png");
        t = System.currentTimeMillis();
    }

    ArrayList<EnemyBullet> shoot(int x, int y){
        long time = System.currentTimeMillis();
        if(time - t > 500) {
            Vector2D vel = new Vector2D(x - (this.x + this.size / 2.0), y - this.y + this.size / 2.0);
            vel.normalize();
            ArrayList<EnemyBullet> bullets = new ArrayList<>();
            Vector2D vel1 = vel.rotated(Math.PI / 18);
            Vector2D vel2 = vel.rotated(-Math.PI / 18);
            bullets.add(new EnemyBullet(6 * vel.x, 6 * vel.y, damage, this.x + this.size / 2.0, this.y + this.size / 2.0, 600));
            bullets.add(new EnemyBullet(6 * vel1.x, 6 * vel1.y, damage, this.x + this.size / 2.0, this.y + this.size / 2.0, 600));
            bullets.add(new EnemyBullet(6 * vel2.x, 6 * vel2.y, damage, this.x + this.size / 2.0, this.y + this.size / 2.0, 600));
            t = time;
            return bullets;
        }else{
            return null;
        }
    }
}
