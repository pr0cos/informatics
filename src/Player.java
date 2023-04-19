
import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.io.IOException;

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
    Gun extra_gun;
    long t_swap;

    public Player(int hp, int damage, int x, int y) throws IOException {
        this.hp = hp;
        this.size = 45;
        this.damage = damage;
        this.x = x;
        this.y = y;
        is_damaged = false;
        gun = new DefaultGun();
        extra_gun = new MachineGun();
        extra_hp = 0;
        extra_damage = 0;
        shield = false;
        t_swap = System.currentTimeMillis();

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

    public void swap(){
        if(System.currentTimeMillis() - t_swap > 15) {
            Gun g = extra_gun;
            extra_gun = gun;
            gun = g;
            t_swap = System.currentTimeMillis();
            System.out.println("11");
        }
    }
}

