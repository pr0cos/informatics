
import java.awt.*;

public class Player {

    int hp;
    int x;
    int y;
    int size;
    long t;
    boolean hurts;
    int damage;
    Gun gun;

    public Player(int hp, int damage, int x, int y) {
        this.hp = hp;
        this.size = 45;
        this.damage = damage;
        this.x = x;
        this.y = y;
        hurts = false;
//        gun = new DefaultGun();
        gun = new MachineGun();
    }
    public void paint(Graphics g){
        if(System.currentTimeMillis() - t > 300){
            hurts = false;
        }
        if(hurts){
            g.setColor(Color.RED);
        }else{
            g.setColor(Color.GREEN);
        }
        g.fillRect(x, y, size, size);
    }

    public void damage(int damage){
        if(!hurts){
            hp -= damage;
            t = System.currentTimeMillis();
            hurts = true;
        }
    }
}

