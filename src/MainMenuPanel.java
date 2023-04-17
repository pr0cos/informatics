import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuPanel extends JFrame implements MouseListener, MouseMotionListener {

    Button play_button;
    BufferedImage background;
    public MainMenuPanel() throws IOException {
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        play_button = new Button(10, 400, 500, 200, "data\\play_button.png", new ButtonAction() {
            @Override
            public void onClick() throws IOException {
                Main.level();
            }
        });
        play_button.x = (getWidth() - play_button.width) / 2;
        background = ImageIO.read(new File("data\\background.png"));
        setVisible(true); // делаем окно видимым только после того как оно полностью готово
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferStrategy bufferStrategy = getBufferStrategy(); // Обращаемся к стратегии буферизации
        if (bufferStrategy == null) { // Если она еще не создана
            createBufferStrategy(2); // то создаем ее
            bufferStrategy = getBufferStrategy(); // и опять обращаемся к уже наверняка созданной стратегии
        }
        g = bufferStrategy.getDrawGraphics(); // Достаем текущую графику (текущий буфер) - это наш холст для рисования (спрятанный от глаз пользователя)
        g.clearRect(0, 0, getWidth(), getHeight()); // Очищаем наш холст (ведь там остался предыдущий кадр)

        // Выполняем рисование:
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        play_button.paint(g);

        g.dispose();                // Освободить все временные ресурсы графики (после этого в нее уже нельзя рисовать)
        bufferStrategy.show();      // Сказать буферизирующей стратегии отрисовать новый буфер (т.е. поменять показываемый и обновляемый буферы местами)
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            play_button.on_mouse_hit(e.getX(), e.getY());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
        play_button.on_mouse_move(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        play_button.on_mouse_move(e.getX(), e.getY());
    }
}