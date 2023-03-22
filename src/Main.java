import java.awt.*;
import java.awt.geom.Line2D;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MyPanel panel = new MyPanel(new Board(0,0,150), new Player(10, 10, 975, 525));
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();   // менеджер по трудоустройству слушателей клавиатуры
        manager.addKeyEventDispatcher(panel);    // подключаем нашу панель к прослушиванию клавиатуры
        manager.addKeyEventDispatcher(panel);
        Line2D a = new Line2D.Float(0, 0, 2, 2);
        Rectangle rect = new Rectangle(2, 2, 1, 1);
        System.out.println(a.intersects(rect));
        while(true) {
            panel.repaint();
            Thread.sleep(0);
        }
    }
}
