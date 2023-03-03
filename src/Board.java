import java.awt.*;
import java.util.ArrayList;

public class Board{

    int x;
    int y;
    int width;
    int height;
    ArrayList<ArrayList<Cell>> board;
    int cellSize;

    public Board(int x, int y, int cellSize) {
        this.x = x;
        this.y = y;
        this.width =112;
        this.height = 112;
        this.cellSize = cellSize;
        board = new ArrayList<>();
        for(int i = 0; i < this.height; i++){
            board.add(new ArrayList<Cell>());
            for(int j = 0; j < this.width; j++){
                board.get(i).add(new Cell(j, i, false, cellSize));
            }
        }
    }

    void paintRectangle(Rectangle rect, Color c){
        for(int i = rect.y; i < rect.y + rect.height; i++){
            for(int j = rect.x; j < rect.x + rect.width; j++){
                board.get(i).get(j).setStatus(true);
                board.get(i).get(j).setC(c);
            }
        }
    }
}
