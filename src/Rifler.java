import java.awt.*;

public class Rifler extends Enemy{
    public Rifler(double x, double y, Rectangle room) {
        super(x, y, 2, 5, room, 30);
    }

    EnemyBullet shoot(int x, int y){
        return new EnemyBullet(x - (this.x + size / 2.0), y - (this.y + size / 2.0), damage, (this.x + size / 2.0), (this.y + size / 2.0));
    }
}
