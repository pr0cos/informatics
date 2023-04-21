import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BigBro extends Enemy {

    long t;

    public BigBro(double x, double y, Rectangle room) throws IOException {
        super(x, y, 35, 4, room, 100, "bigbro.png", "bigbro_rotated.png");
        t = System.currentTimeMillis();
    }

    public ArrayList<EnemyBullet> shoot(int x_player, int y_player) {
        if (System.currentTimeMillis() - t > 2000) {
            t = System.currentTimeMillis();
            double x = (this.x + this.size / 2) - x_player;
            double y = (this.y + this.size / 2) - y_player;
            Vector2D velocity = new Vector2D(x, y);
            velocity.rotate(Math.PI / 4);
            int quarter = velocity.getQuarter();
            ArrayList<EnemyBullet> bullets = new ArrayList<>();
            if (quarter == 3) {
                for (int i = 0; i < 21; i++) {
                    double x1 = this.x + size - (Math.abs(i - 10) + 5) * (size / 20);
                    double y1 = this.y - size + (3 * size / 20) * i;
                    Vector2D v = new Vector2D(1, 0);
                    v.normalize();
                    bullets.add(new EnemyBullet(6 * v.getX(), 6 * v.getY(), damage, x1, y1, -1));
                }
            } else if (quarter == 2) {
                for (int i = 0; i < 21; i++) {
                    double x1 = this.x - size + (3 * size / 20) * i;
                    double y1 = this.y + (Math.abs(i - 10) + 5) * (size / 20);
                    Vector2D v = new Vector2D(0, -1);
                    v.normalize();
                    bullets.add(new EnemyBullet(6 * v.getX(), 6 * v.getY(), damage, x1, y1, -1));
                }
            } else if (quarter == 1) {
                for (int i = 0; i < 21; i++) {
                    double x1 = this.x + (Math.abs(i - 10) + 5) * (size / 20);
                    double y1 = this.y - size + (3 * size / 20) * i;
                    Vector2D v = new Vector2D(-1, 0);
                    v.normalize();
                    bullets.add(new EnemyBullet(6 * v.getX(), 6 * v.getY(), damage, x1, y1, -1));
                }
            } else if (quarter == 4) {
                for (int i = 0; i < 21; i++) {
                    double x1 = this.x - size + (3 * size / 20) * i;
                    double y1 = this.y + size - (Math.abs(i - 10) + 5) * (size / 20);
                    Vector2D v = new Vector2D(0, 1);
                    v.normalize();
                    bullets.add(new EnemyBullet(6 * v.getX(), 6 * v.getY(), damage, x1, y1, -1));
                }
            }
            return bullets;
        }
        return null;
    }
}