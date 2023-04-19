
import javax.imageio.ImageIO;
import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
    BufferedImage image;
    BufferedImage image_rotated;
    BufferedImage to_draw;

    public Player(int hp, int damage, int x, int y) throws IOException {
        this.hp = hp;
        this.size = 80;
        this.damage = damage;
        this.x = x;
        this.y = y;
        is_damaged = false;
        gun = new DefaultGun();
        extra_gun = null;
        extra_hp = 0;
        extra_damage = 0;
        shield = false;
        t_swap = System.currentTimeMillis();
        image = ImageIO.read(new File("data\\player.png"));
        image_rotated = ImageIO.read(new File("data\\player_rotated.png"));
        to_draw = image;
    }
    public void paint(Graphics g){
        if(System.currentTimeMillis() - t > 300){
            is_damaged = false;
        }
        if(System.currentTimeMillis() - t_shield > 1000){
            shield = false;
        }
        g.drawImage(to_draw, x, y, size, size, null);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        if(shield){
            g.setColor(Color.CYAN);
            g.drawOval(x, y, size, size);
        }else if(is_damaged){
            g.setColor(Color.RED);
            g.drawOval(x, y, size, size);
        }
    }

    public void rotate(){
        to_draw = image_rotated;
    }

    public void normalize(){
        to_draw = image;
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

