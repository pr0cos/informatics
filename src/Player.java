
import javax.sound.midi.SysexMessage;
import java.awt.*;

public class Player {

    int hp;
    int x;
    int y;
    int size;
    long t;
    long t_shield;
    boolean is_damaged;
    boolean shield;
    int damage;
    int extra_damage;
    int extra_hp;
    Gun gun;

    public Player(int hp, int damage, int x, int y) {
        this.hp = hp;
        this.size = 45;
        this.damage = damage;
        this.x = x;
        this.y = y;
        is_damaged = false;
        gun = new MachineGun();
        extra_hp = 0;
        extra_damage = 0;
        shield = false;
//        gun = new Shotgun();
    }
    public void paint(Graphics g){
        if(System.currentTimeMillis() - t > 300){
            is_damaged = false;
        }
        if(System.currentTimeMillis() - t_shield > 1000){
            shield = false;
        }
        if(shield){
            g.setColor(Color.CYAN);
        }else if(is_damaged){
            g.setColor(Color.RED);
        }
        else{
            g.setColor(Color.GREEN);
        }
        g.fillRect(x, y, size, size);
    }

    public void damage(int damage){
        if(!is_damaged && !shield){
            hp -= damage;
            t = System.currentTimeMillis();
            is_damaged = true;
        }
    }

    public void shield(){
        if(System.currentTimeMillis() - t_shield > 2000){
            shield = true;
            t_shield = System.currentTimeMillis();
        }
    }
}

