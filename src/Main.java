import java.awt.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MyPanel panel = new MyPanel(new Board(0,0,100), new Player(10, 10, 975, 525));
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();   // менеджер по трудоустройству слушателей клавиатуры
        manager.addKeyEventDispatcher(panel);    // подключаем нашу панель к прослушиванию клавиатуры
        manager.addKeyEventDispatcher(panel);
        while(true) {
            panel.repaint();
            Thread.sleep(10);
        }
    }
}
