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
    int iterations;
    BossBoard boss_board;

    public MyPanel(Board board, Player player) throws IOException {
        this.board = board;
        this.player = player;
        this.pause = true;
        boss_board = new BossBoard(0, 0, 150);
        background = ImageIO.read(MyPanel.class.getResourceAsStream("background.png"));
        t = System.currentTimeMillis();
        t_pause = System.currentTimeMillis();
        setSize(1920, 1080);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(this);
        this.player.x = (getWidth() - player.size) / 2;
        this.player.y = (getHeight() - player.size) / 2;
        iterations = 1;
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
            g.drawImage(player.gun.image, 50, 955, 200, 75, null);
            g.fillRect(25, 25, 25 * (10 + player.extra_hp), 50);
            g.setColor(Color.red);
            g.fillRect(25, 25, 25 * (player.hp + player.extra_hp), 50);
            if(!(board instanceof BossBoard)) {
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
                if (board.new_gun.on_gun(player.x + player.size / 2 - board.x, player.y + player.size / 2 - board.y)) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                    g.drawString("Press E to swap weapon", 800, 900);
                }
            }else{
                if(((BossBoard) board).boss != null && ((BossBoard) board).boss.room == board.get_room_screen(player.x, player.y)){
                    g.setColor(new Color(100, 100, 100));
                    g.fillRect(1850 - 5, 85 - 5, 50 + 10, 900 + 10);
                    g.setColor(Color.red);
                    g.fillRect(1850, 85, 50, 3 * ((BossBoard) board).boss.hp);
                }else if(((BossBoard) board).boss == null){
                    g.setFont(new Font("SansSerif", Font.PLAIN, 50));
                    g.drawString("YOU WIN!", 800, 500);
                }
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
            Point e = MouseInfo.getPointerInfo().getLocation();
            if(e.getX() - player.x - player.size / 2 > 0){
                player.normalize();
            }else{
                player.rotate();
            }
            if (System.currentTimeMillis() - t > player.gun.cooldown && mouse_left_flag && (player.gun instanceof MachineGun)) {
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
            if(player.hp + player.extra_hp <= 0){
                try {
                    if(board instanceof BossBoard){
                        boss_board = new BossBoard(0, 0, 150);
                    }
                    board = new Board(0, 0, 150);
                    player = new Player(10, 10, (getWidth() - player.size) / 2, (getHeight() - player.size) / 2);
                    iterations = 1;
                } catch (IOException x) {
                    throw new RuntimeException(x);
                }
            }
        }else{
            board.paint(g);
            player.paint(g);
            g.setColor(new Color(100, 100, 100));
            g.fillRect(25, 25, 25 * (10 + player.extra_hp), 50);
            g.setColor(Color.red);
            g.fillRect(25, 25, 25 * (player.hp + player.extra_hp), 50);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.PLAIN, 50));
            g.drawString("PAUSE", 800, 500);
        }
        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_E && board.new_gun.on_gun(player.x + player.size / 2 - board.x, player.y + player.size / 2 - board.y)){
            System.out.println("asd");
            if(player.extra_gun == null){
                player.extra_gun = board.new_gun.pick(null);
            }else {
                player.gun = board.new_gun.pick(player.gun);
            }
        }
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_SPACE && board.damage_portal.in_portal(player.x + player.size / 2 - board.x, player.y + player.size / 2 - board.y)){
            try {
                player.extra_damage += 1;
                if(iterations >= 2){
                    board = boss_board;
                }else {
                    iterations += 1;
                    board = new Board(0, 0, 150);
                }
                player.hp = 10;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_SPACE && board.hp_portal.in_portal(player.x + player.size / 2 - board.x, player.y + player.size / 2 - board.y)){
            try {
                player.extra_hp += 1;
                if(iterations >= 2){
                    board = boss_board;
                }else {
                    iterations += 1;
                    board = new Board(0, 0, 150);
                }
                player.hp = 10;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_X){
            if(player.extra_gun != null){
                player.swap();
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
        if(e.getX() == 0 && e.getY() == 0){
            player.hp = 10;
            board = boss_board;
        }
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
