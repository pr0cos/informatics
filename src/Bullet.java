import java.awt.*;

public class Bullet {
    double dx;
    double dy;
    int damage;
    double x;
    double y;
    Color color;
    int dist_max;
    double dist;

    public Bullet(double dx, double dy, int damage, double x, double y, Color color, int dist_max) {
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.color = color;
        this.dist_max = dist_max;
        this.dist = 0;
    }

    public void update(){
        x += dx;
        y += dy;
        if(dist_max != -1) {
            dist += Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        }
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(15));
        g2.setColor(color);
        g2.drawLine((int) x,(int) y,(int) (x + dx),(int) (y + dy));
    }
}
