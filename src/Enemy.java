import java.awt.*;

public class Enemy {

    double x;
    double y;
    int hp;
    int damage;
    int size;
    Rectangle room;

    public Enemy(double x, double y, int hp, int damage, Rectangle room, int size) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.damage = damage;
        this.room = room;
        this.size = size;
    }

    public void update(double dx, double dy){
        x += dx;
        y += dy;
    }

    public void paint(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillRect((int)x, (int)y, size, size);
    }

    public int getSize() {
        return size;
    }
}