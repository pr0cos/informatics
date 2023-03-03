import java.awt.*;

public class Cell {

    int x;
    int y;
    boolean status;
    int size;

    Color c = new Color(0, 0, 0);

    public Cell(int x, int y, boolean status, int size) {
        this.x = x;
        this.y = y;
        this.status = status;
        this.size = size;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setC(Color c) {
        this.c = c;
    }

    void paint(Graphics g, int a, int b){
        g.setColor(c);
        if(status){
            g.fillRect(a + size * x, b + size * y, size, size);
        }
//        else{
//            g.drawRect(a + size * x, b + size * y, size, size);
//        }
    }
}
