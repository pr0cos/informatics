import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

public class MyPanel extends JFrame implements KeyEventDispatcher, MouseListener, MouseMotionListener {

    Board board;
    boolean w_flag;
    boolean s_flag;
    boolean a_flag;
    boolean d_flag;
    Player player;

    long t;

    public MyPanel(Board board, Player player) {
        this.board = board;
        this.player = player;
        boolean w_flag = false;
        boolean s_flag = false;
        boolean a_flag = false;
        boolean d_flag = false;
        t = System.currentTimeMillis();
        setSize(1920, 1080);
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
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        board.paint(g);
        player.paint(g);
        board.update();
        boolean left_up = board.get_cell_screen(player.x, player.y).status;
        boolean left_down = board.get_cell_screen(player.x, player.y + player.size).status;
        boolean right_up = board.get_cell_screen(player.x + player.size, player.y).status;
        boolean right_down = board.get_cell_screen(player.x + player.size, player.y + player.size).status;
        if(!(left_up && left_down)){
            board.bug_update(-1, 0);
        }
        if(!(right_up && right_down)){
            board.bug_update(1, 0);
        }
        if(!(left_up && right_up)){
            board.bug_update(0, -1);
        }
        if(!(left_down && right_down)){
            board.bug_update(0, 1);
        }
        if(!(left_down || right_down || left_up)){
            board.bug_update(-1, 1);
        }
        if(!(left_down || right_down || right_up)){
            board.bug_update(1, 1);
        }
        if(!(left_up || right_up || right_down)){
            board.bug_update(1, -1);
        }
        if(!(left_up || right_up || left_down)){
            board.bug_update(-1, -1);
        }

        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
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
        if(!(e.getButton() == MouseEvent.BUTTON1)){
            return;
        }
        if(player.gun instanceof DefaultGun && System.currentTimeMillis() - t > player.gun.cooldown){
            t = System.currentTimeMillis();
            double x1 = e.getX() - (player.x + player.size / 2.0);
            double y1 = e.getY() - (player.y + player.size / 2.0);
            Vector2D vel = new Vector2D(x1, y1);
            vel.normalize();
            board.playerBullets.add(new PlayerBullet(10 * vel.x, 10 * vel.y, player.gun.damage, player.x + player.size / 2.0, player.y + player.size / 2.0, player.gun.c));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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
