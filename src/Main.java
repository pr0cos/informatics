import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        main_menu();
        level();
    }

    public static void level() throws IOException {
        MyPanel panel = new MyPanel(new Board(0,0,150), new Player(10, 10, 975, 525));
//        MyPanel panel = new MyPanel(new BossBoard(0,0,150), new Player(10, 10, 975, 525));
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();   // менеджер по трудоустройству слушателей клавиатуры
        manager.addKeyEventDispatcher(panel);    // подключаем нашу панель к прослушиванию клавиатуры
        manager.addKeyEventDispatcher(panel);
        while(true) {
            panel.repaint();
        }
    }
}
