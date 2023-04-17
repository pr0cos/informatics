import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MyPanel extends JFrame implements KeyEventDispatcher, MouseListener, MouseMotionListener {

    Board board;
    BufferedImage background;
    boolean w_flag;
    boolean s_flag;
    boolean a_flag;
    boolean d_flag;
    boolean mouse_left_flag;
    boolean pause;
    Player player;
    long t;
    long t_pause;

    public MyPanel(Board board, Player player) throws IOException {
        this.board = board;
        this.player = player;
        this.pause = true;
        background = ImageIO.read(new File("data\\background.png"));
        t = System.currentTimeMillis();
        t_pause = System.currentTimeMillis();
        setSize(1920, 1080);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(this);
        this.player.x = (getWidth() - player.size) / 2;
        this.player.y = (getHeight() - player.size) / 2;
    }

    @Override
    public void paint(Graphics g) {
        BufferStrategy bufferStrategy = getBufferStrategy();        // Обращаемся к стратегии буферизации
        if (bufferStrategy == null) {                               // Если она еще не создана
            createBufferStrategy(2);                                // то создаем ее
            bufferStrategy = getBufferStrategy();                   // и опять обращаемся к уже наверняка созданной стратегии
        }
        g = bufferStrategy.getDrawGraphics();                       // Достаем текущую графику (текущий буфер)
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        if(!pause) {
            board.paint(g);
            player.paint(g);
            g.setColor(new Color(100, 100, 100));
            g.fillRect(25, 25, 25 * (10 + player.extra_hp), 50);
            g.setColor(Color.red);
            g.fillRect(25, 25, 25 * (player.hp + player.extra_hp), 50);
            if (board.damage_portal.in_portal(player.x + player.size / 2 - board.x, player.y + player.size / 2 - board.y)) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                g.drawString("Press SPACE to get more damage", 800, 900);
            }
            if (board.hp_portal.in_portal(player.x + player.size / 2 - board.x, player.y + player.size / 2 - board.y)) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                g.drawString("Press SPACE to get more health points", 750, 900);
            }
            board.update(this.player);
            boolean left_up = board.get_cell_screen(player.x, player.y).status;
            boolean left_down = board.get_cell_screen(player.x, player.y + player.size).status;
            boolean right_up = board.get_cell_screen(player.x + player.size, player.y).status;
            boolean right_down = board.get_cell_screen(player.x + player.size, player.y + player.size).status;
            if (!(left_up && left_down)) {
                board.bug_update(-1, 0);
            }
            if (!(right_up && right_down)) {
                board.bug_update(1, 0);
            }
            if (!(left_up && right_up)) {
                board.bug_update(0, -1);
            }
            if (!(left_down && right_down)) {
                board.bug_update(0, 1);
            }
            if (!(left_down || right_down || left_up)) {
                board.bug_update(-1, 1);
            }
            if (!(left_down || right_down || right_up)) {
                board.bug_update(1, 1);
            }
            if (!(left_up || right_up || right_down)) {
                board.bug_update(1, -1);
            }
            if (!(left_up || right_up || left_down)) {
                board.bug_update(-1, -1);
            }

            if (System.currentTimeMillis() - t > player.gun.cooldown && mouse_left_flag && (player.gun instanceof MachineGun)) {
                Point e = MouseInfo.getPointerInfo().getLocation();
                double x1 = e.getX() - (player.x + player.size / 2.0);
                double y1 = e.getY() - (player.y + player.size / 2.0);
                Vector2D vel = new Vector2D(x1, y1);
                vel.normalize();
                t = System.currentTimeMillis();
                if (player.gun instanceof MachineGun) {
                    Random r = new Random();
                    vel.setX(vel.x + r.nextDouble(-0.2, 0.2));
                    vel.setY(vel.y + r.nextDouble(-0.2, 0.2));
                    vel.normalize();
                    board.playerBullets.add(new PlayerBullet(20 * vel.x, 20 * vel.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, -1));
                }
            }
            if(player.hp <= 0){
                try {
                    board = new Board(0, 0, 150);
                    player = new Player(10, 10, (getWidth() - player.size) / 2, (getHeight() - player.size) / 2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            board.paint(g);
            player.paint(g);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.PLAIN, 50));
            g.drawString("PAUSE", 800, 500);
        }
        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE && board.damage_portal.in_portal(player.x + player.size / 2 - board.x, player.y + player.size / 2 - board.y)){
            try {
                player.extra_damage += 1;
                board = new Board(0,0,150);
                player.hp = 10;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE && board.hp_portal.in_portal(player.x + player.size / 2 - board.x, player.y + player.size / 2 - board.y)){
            try {
                player.extra_hp += 1;
                board = new Board(0,0,150);
                player.hp = 10;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_F){
            player.shield();
        }
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE && System.currentTimeMillis() - t_pause > 100){
            System.out.println("pause");
            pause = !pause;
            t_pause = System.currentTimeMillis();
        }
        if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_A){
            board.setX_direction(1);
            a_flag = true;
        }
        if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_D) {
            board.setX_direction(-1);
            d_flag = true;
        }
        if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_W){
            board.setY_direction(1);
            w_flag = true;
        }
        if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_S) {
            board.setY_direction(-1);
            s_flag = true;
        }
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_A){
            a_flag = false;
            if(!d_flag){
                board.setX_direction(0);
            }else{
                board.setX_direction(-1);
            }
        }
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_D){
            d_flag = false;
            if(!a_flag){
                board.setX_direction(0);
            }else{
                board.setX_direction(1);
            }
        }
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_W){
            w_flag = false;
            if(!s_flag){
                board.setY_direction(0);
            }else{
                board.setY_direction(-1);
            }
        }
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_S){
            s_flag = false;
            if(!w_flag){
                board.setY_direction(0);
            }else{
                board.setY_direction(1);
            }
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if((e.getButton() == MouseEvent.BUTTON1)) {
            mouse_left_flag = true;
            if (player.gun instanceof DefaultGun) {
                double x1 = e.getX() - (player.x + player.size / 2.0);
                double y1 = e.getY() - (player.y + player.size / 2.0);
                Vector2D vel = new Vector2D(x1, y1);
                vel.normalize();
                board.playerBullets.add(new PlayerBullet(10 * vel.x, 10 * vel.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, -1));
            }
            if (System.currentTimeMillis() - t > player.gun.cooldown) {
                double x1 = e.getX() - (player.x + player.size / 2.0);
                double y1 = e.getY() - (player.y + player.size / 2.0);
                Vector2D vel = new Vector2D(x1, y1);
                vel.normalize();
                t = System.currentTimeMillis();
                if(player.gun instanceof MachineGun) {
                    Random r = new Random();
                    vel.setX(vel.x + r.nextDouble(-0.35, 0.35));
                    vel.setY(vel.y + r.nextDouble(-0.35, 0.35));
                    vel.normalize();
                    board.playerBullets.add(new PlayerBullet(20 * vel.x, 20 * vel.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, -1));
                }
                if(player.gun instanceof SniperRifle){
                    board.playerBullets.add(new PlayerBullet(40 * vel.x, 40 * vel.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, -1));
                }
                if (player.gun instanceof Shotgun) {
                    Vector2D vel1 = vel.rotated(Math.PI / 18);
                    Vector2D vel2 = vel.rotated(-Math.PI / 18);
                    Vector2D vel3 = vel.rotated(Math.PI / 9);
                    Vector2D vel4 = vel.rotated(-Math.PI / 9);
                    Vector2D vel5 = vel.rotated(Math.PI / 36);
                    Vector2D vel6 = vel.rotated(-Math.PI / 36);
                    board.playerBullets.add(new PlayerBullet(30 * vel.x, 30 * vel.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, 600));
                    board.playerBullets.add(new PlayerBullet(30 * vel1.x, 30 * vel1.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, 600));
                    board.playerBullets.add(new PlayerBullet(30 * vel2.x, 30 * vel2.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, 600));
                    board.playerBullets.add(new PlayerBullet(30 * vel3.x, 30 * vel3.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, 600));
                    board.playerBullets.add(new PlayerBullet(30 * vel4.x, 30 * vel4.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, 600));
                    board.playerBullets.add(new PlayerBullet(30 * vel5.x, 30 * vel5.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, 600));
                    board.playerBullets.add(new PlayerBullet(30 * vel6.x, 30 * vel6.y, player.gun.damage + player.extra_damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c, 600));
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if((e.getButton() == MouseEvent.BUTTON1)){
            mouse_left_flag = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
