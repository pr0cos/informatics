import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Boss extends Enemy{

    BufferedImage boss_angry;
    boolean attack_1;
    boolean attack_2;
    boolean attack_3;
    int attack_itterations;
    long t;
    double cd_1;
    double cd_2;
    double cd_3;
    boolean is_angry;

    public Boss(double x, double y, Rectangle room) throws IOException {
        super(x, y, 300, 3, room, 600, "data\\boss.png", "data\\boss.png");
        boss_angry = ImageIO.read(new File("data\\boss_angry.png"));
        attack_1 = false;
        cd_1 = 1000;
        attack_2 = false;
        cd_2 = 200;
        attack_3 = false;
        cd_3 = 15;
        attack_itterations = 0;
        is_angry = false;
        to_draw = image;
        t = System.currentTimeMillis();
    }

    @Override
    public void paint(Graphics g){
        if(!is_angry && hp <= 150){
            System.out.println("ss");
            is_angry = true;
            to_draw = boss_angry;
            cd_1 *= 0.75;
            cd_2 *= 0.75;
            cd_3 *= 0.75;

        }
        g.drawImage(to_draw, (int)x, (int)y, size, size, null);
        g.setColor(Color.RED);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        g.drawOval((int)x, (int)y, size, size);
    }

    @Override
    public void update(double dx, double dy){
        x += dx;
        y += dy;
    }

    public ArrayList<EnemyBullet> shoot(int x_player, int y_player){
        Random r = new Random();
        ArrayList<EnemyBullet> to_return = new ArrayList<>();
        long time = System.currentTimeMillis();
        if(!attack_1 && !attack_2 && !attack_3){
            int n = r.nextInt(1, 4);
//            n = 3;
            if(n == 1){
                attack_1 = true;
            }else if(n == 2){
                attack_2 = true;
            }else{
                attack_3 = true;
            }
        }
        double center_x = x + size / 2;
        double center_y = y + size / 2;
        if(attack_1 && time - t > cd_1){
            if(attack_itterations >= 11){
                attack_1 = false;
                attack_itterations = 0;
            }
            if(attack_itterations % 4 == 0){
                Vector2D v = new Vector2D(size / Math.sqrt(2), 0);
                to_return.add(new EnemyBullet(v.norm().mult(15), damage, center_x, center_y, -1));
                for(int i = 1; i <= 20; i++){
                    to_return.add(new EnemyBullet(v.rotated(Math.PI * i / 20).norm().mult(15), damage, center_x, center_y, -1));
                }
            }
            if(attack_itterations % 4 == 1){
                Vector2D v = new Vector2D( 0,size / Math.sqrt(2));
                to_return.add(new EnemyBullet(v.norm().mult(15), damage, center_x, center_y, -1));
                for(int i = 1; i <= 20; i++){
                    to_return.add(new EnemyBullet(v.rotated(Math.PI * i / 20).norm().mult(15), damage, center_x, center_y, -1));
                }
            }
            if(attack_itterations % 4 == 2){
                Vector2D v = new Vector2D(size / Math.sqrt(2), 0);
                to_return.add(new EnemyBullet(v.norm().mult(15), damage, center_x, center_y, -1));
                for(int i = 1; i <= 20; i++){
                    to_return.add(new EnemyBullet(v.rotated(-Math.PI * i / 20).norm().mult(15), damage, center_x, center_y, -1));
                }
            }
            if(attack_itterations % 4 == 3){
                Vector2D v = new Vector2D( 0,size / Math.sqrt(2));
                to_return.add(new EnemyBullet(v.norm().mult(15), damage, center_x, center_y, -1));
                for(int i = 1; i <= 20; i++){
                    to_return.add(new EnemyBullet(v.rotated(-Math.PI * i / 20).norm().mult(15), damage, center_x, center_y, -1));
                }
            }
            t = System.currentTimeMillis();
            attack_itterations += 1;
        }else if(attack_2){
            attack_2 = false;
        } else if (attack_3) {
            Vector2D v = new Vector2D( x_player - center_x,y_player - center_y);
            to_return.add(new EnemyBullet(v.norm().mult(15), damage, center_x, center_y, -1));
            to_return.add(new EnemyBullet(v.norm().rotated(Math.PI / 2).mult(15), damage, center_x, center_y, -1));
            to_return.add(new EnemyBullet(v.norm().rotated(-Math.PI / 2).mult(15), damage, center_x, center_y, -1));
            to_return.add(new EnemyBullet(v.norm().rotated(Math.PI).mult(15), damage, center_x, center_y, -1));
            attack_itterations += 1;
            if(attack_itterations >= 150){
                attack_3 = false;
                attack_itterations = 0;
            }
        }
        return to_return;
    }
}
