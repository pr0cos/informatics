import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class Board{

    int x;
    int y;
    int width;
    int height;
    ArrayList<ArrayList<Cell>> board;
    int cellSize;
    int x_direction;
    int y_direction;
    int dx;
    int dy;
    ArrayList<Rectangle> rooms;
    ArrayList<PlayerBullet> playerBullets;
    ArrayList<ArrayList<Integer>> graph;

    public Board(int x, int y, int cellSize) {
        this.x = x;
        this.y = y;
        this.width = 42;
        this.height = 42;
        this.cellSize = cellSize;
        x_direction = 0;
        y_direction = 0;
        dx = 2;
        dy = 2;
        rooms = new ArrayList<>();
        playerBullets = new ArrayList<>();
        board = new ArrayList<>();
        for(int i = 0; i < this.height; i++){
            board.add(new ArrayList<Cell>());
            for(int j = 0; j < this.width; j++){
                board.get(i).add(new Cell(j, i, false, cellSize));
            }
        }
        graph = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            graph.add(new ArrayList<Integer>());
            for(int j = 0; j < 100; j++){
                graph.get(i).add(0);
            }
        }
        generate();
    }
    void generate(){
        Random r = new Random();
//        int n = r.nextInt(1, 7);
        int n = 1;
        String[] start = new Rooms().start_room();
        if(n == 1){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            rooms.add(new Rectangle(1, 1, 10, 10));
            this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
        }else if(n == 2){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            rooms.add(new Rectangle(1, 16, 10, 10));
            this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
        }else if(n == 3){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            rooms.add(new Rectangle(1, 31, 10, 10));
            this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
        }else if(n == 4){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            rooms.add(new Rectangle(31, 1, 10, 10));
            this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
        }else if(n == 5){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            rooms.add(new Rectangle(31, 16, 10, 10));
            this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
        }else if(n == 6){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            rooms.add(new Rectangle(31, 31, 10, 10));
            this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
        }
        String[] room1 = new Rooms().enemy_room();
        for(int i = 0; i < room1.length; i++){
            for(int j = 0; j < room1[i].length(); j++){
                if(room1[i].charAt(j) == '.'){
                    this.paintRectangle(new Rectangle(16 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                }
            }
        }
        rooms.add(new Rectangle(16, 1, 10, 10));
        this.paintRectangle(new Rectangle(20, 11, 2, 5), new Color(86, 15, 15));
        String[] room2 = new Rooms().enemy_room();
        for(int i = 0; i < room2.length; i++){
            for(int j = 0; j < room2[i].length(); j++){
                if(room2[i].charAt(j) == '.'){
                    this.paintRectangle(new Rectangle(16 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                }
            }
        }
        rooms.add(new Rectangle(16, 16, 10, 10));
        this.paintRectangle(new Rectangle(20, 26, 2, 5), new Color(86, 15, 15));
        String[] room3 = new Rooms().enemy_room();
        for(int i = 0; i < room3.length; i++){
            for(int j = 0; j < room3[i].length(); j++){
                if(room3[i].charAt(j) == '.'){
                    this.paintRectangle(new Rectangle(16 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                }
            }
        }
        rooms.add(new Rectangle(16, 31, 10, 10));
    }

    void make_graph(Rectangle room){
        for(int i = 0; i < room.height; i++){
            for(int j = 0; j < room.width; j++){
                if(board.get(i + room.y).get(room.x + j).status){
                    if(j >= 1) {
                        graph.get(10 * i + j).set(10 * i + j - 1, booleanObjectToInt(board.get(i + room.y).get(room.x + j - 1).status));
                    }
                    if(j < room.width - 1) {
                        graph.get(10 * i + j).set(10 * i + j + 1, booleanObjectToInt(board.get(i + room.y).get(room.x + j + 1).status));
                    }
                    if(i >= 1) {
                        graph.get(10 * i + j).set(10 * (i - 1) + j, booleanObjectToInt(board.get(i - 1 + room.y).get(room.x + j).status));
                    }
                    if(i < room.height - 1) {
                        graph.get(10 * i + j).set(10 * (i + 1) + j, booleanObjectToInt(board.get(i + 1 + room.y).get(room.x + j).status));
                    }
                }
            }
        }
    }
    void paint(Graphics g){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                board.get(i).get(j).paint(g, x, y);
            }
        }
        for(PlayerBullet bullet : playerBullets){
            bullet.paint(g);
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

    public void setX_direction(int x_direction) {
        this.x_direction = x_direction;
    }

    public void setY_direction(int y_direction) {
        this.y_direction = y_direction;
    }

    public void update(Player player){
        this.x += x_direction * dx;
        this.y += y_direction * dy;
        ArrayList<PlayerBullet> delete_player_bullets = new ArrayList<>();
        for(PlayerBullet bullet : playerBullets){
            bullet.x += x_direction * dx;
            bullet.y += y_direction * dy;
            bullet.update();
            if(!get_cell_screen((int)bullet.x, (int)bullet.y).status){
                delete_player_bullets.add(bullet);
            }
        }
        for(PlayerBullet bullet : delete_player_bullets){
            playerBullets.remove(bullet);
        }
        make_graph(get_room_screen(player.x, player.y));
        System.out.println(graph);
    }

    public void bug_update(int x_d, int y_d){
        x += x_d * (dx);
        y += y_d * (dy);
    }
    public Cell get_cell_world(int x, int y){
        int cell_x = x / cellSize;
        int cell_y = y / cellSize;
        return board.get(cell_y).get(cell_x);
    }
    public Cell get_cell_screen(int x, int y){
        int x1 = x - this.x;
        int y1 = y - this.y;
        return get_cell_world(x1, y1);
    }

    public Rectangle get_room_screen(int x, int y){
        Cell cell = get_cell_screen(x, y);
        for(Rectangle room : rooms){
            if(room.x <= cell.x && cell.x <= room.x + room.width){
                return room;
            }
        }
        return null;
    }

    public static int booleanObjectToInt(boolean foo) {
        return Boolean.compare(foo, false);
    }
}
