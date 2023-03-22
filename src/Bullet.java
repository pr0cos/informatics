import java.awt.*;

public class Bullet {
    double dx;
    double dy;
    int damage;
    double x;
    double y;
    Color color;

    public Bullet(double dx, double dy, int damage, double x, double y, Color color) {
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void update(){
        x += dx;
        y += dy;
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(15));
        g2.setColor(color);
        g2.drawLine((int) x,(int) y,(int) (x + dx),(int) (y + dy));
    }
}
