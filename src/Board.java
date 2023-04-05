import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
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
    Rectangle last_room;
    ArrayList<Enemy> enemies;
    ArrayList<Rectangle> rooms;
    ArrayList<PlayerBullet> playerBullets;
    ArrayList<EnemyBullet> enemyBullets;
    ArrayList<ArrayList<Integer>> graph;

    public Board(int x, int y, int cellSize) {
        this.x = x;
        this.y = y;
        this.width = 42;
        this.height = 42;
        this.cellSize = cellSize;
        x_direction = 0;
        y_direction = 0;
        dx = 5;
        dy = 5;
        enemies = new ArrayList<>();
        rooms = new ArrayList<>();
        playerBullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
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
        last_room = null;
        generate();
    }
    void generate(){
        Random r = new Random();
        int n = r.nextInt(1, 7);
//        n = 2;
        String[] start = new Rooms().start_room();
        String[] final_room = new Rooms().final_room();
        String[] chest_room = new Rooms().chest_room();
        Rectangle final_room_rect;
        Rectangle chest_room_rect;
        if(n == 1){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            Rectangle room = new Rectangle(1, 1, 10, 10);
            rooms.add(room);
            this.x = (1920 - 45) / 2 - 300;
            this.y = (1080 - 45) / 2 - 300;
            this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
            int fina_locus = r.nextInt(1, 5);
            if(fina_locus == 1){
                final_room_rect = new Rectangle(1, 16, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 31, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
            } else if(fina_locus == 2){
                final_room_rect = new Rectangle(31, 16, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 31, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 3){
                final_room_rect = new Rectangle(1, 31, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 16, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 4){
                final_room_rect = new Rectangle(31, 31, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 16, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
            }
        }else if(n == 2){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            Rectangle room = new Rectangle(1, 16, 10, 10);
            rooms.add(room);
            this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
            this.x = (1920 - 45) / 2 - 300;
            this.y = (1080 - 45) / 2 - 2600;
            int fina_locus = r.nextInt(1, 5);
            if(fina_locus == 1){
                final_room_rect = new Rectangle(1, 1, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 31, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
            } else if(fina_locus == 2){
                final_room_rect = new Rectangle(31, 1, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 31, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 3){
                final_room_rect = new Rectangle(1, 31, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 1, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 4){
                final_room_rect = new Rectangle(31, 31, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 1, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
            }
        }else if(n == 3){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            Rectangle room = new Rectangle(1, 31, 10, 10);
            rooms.add(room);
            this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
            this.x = (1920 - 45) / 2 - 300;
            this.y = (1080 - 45) / 2 - 4800;
            int fina_locus = r.nextInt(1, 5);
            if(fina_locus == 1){
                final_room_rect = new Rectangle(1, 16, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 1, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
            } else if(fina_locus == 2){
                final_room_rect = new Rectangle(31, 16, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 1, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 3){
                final_room_rect = new Rectangle(1, 1, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 16, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 4){
                final_room_rect = new Rectangle(31, 1, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 16, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
            }
        }else if(n == 4){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            Rectangle room = new Rectangle(31, 1, 10, 10);
            rooms.add(room);
            this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
            this.x = (1920 - 45) / 2 - 4800;
            this.y = (1080 - 45) / 2 - 300;
            int fina_locus = r.nextInt(1, 5);
//            fina_locus = 4;
            if(fina_locus == 1){
                final_room_rect = new Rectangle(1, 16, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 31, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
            } else if(fina_locus == 2){
                final_room_rect = new Rectangle(31, 16, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 31, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 3){
                final_room_rect = new Rectangle(1, 31, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 16, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 4){
                final_room_rect = new Rectangle(31, 31, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 16, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
            }
        }else if(n == 5){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            Rectangle room = new Rectangle(31, 16, 10, 10);
            rooms.add(room);
            this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
            this.x = (1920 - 45) / 2 - 4800;
            this.y = (1080 - 45) / 2 - 2700;
            int fina_locus = r.nextInt(1, 5);
            if(fina_locus == 1){
                final_room_rect = new Rectangle(1, 1, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 31, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
            } else if(fina_locus == 2){
                final_room_rect = new Rectangle(31, 1, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 31, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 3){
                final_room_rect = new Rectangle(1, 31, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 31 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 35, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 1, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 4){
                final_room_rect = new Rectangle(31, 31, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 1, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
            }
        }else if(n == 6){
            for(int i = 0; i < start.length; i++){
                for(int j = 0; j < start[i].length(); j++){
                    if(start[i].charAt(j) == '.'){
                        this.paintRectangle(new Rectangle(31 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                    }
                }
            }
            Rectangle room = new Rectangle(31, 31, 10, 10);
            rooms.add(room);
            this.paintRectangle(new Rectangle(26, 35, 5, 2), new Color(86, 15, 15));
            this.x = (1920 - 45) / 2 - 4800;
            this.y = (1080 - 45) / 2 - 4800;
            int fina_locus = r.nextInt(1, 5);
            if(fina_locus == 1){
                final_room_rect = new Rectangle(1, 16, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 1, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
            } else if(fina_locus == 2){
                final_room_rect = new Rectangle(31, 16, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 1, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 3){
                final_room_rect = new Rectangle(1, 1, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(1 + j, 1 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(31, 16, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(26, 20, 5, 2), new Color(86, 15, 15));
            }else if(fina_locus == 4){
                final_room_rect = new Rectangle(31, 1, 10, 10);
                for(int i = 0; i < final_room.length; i++){
                    for(int j = 0; j < final_room[i].length(); j++){
                        if(final_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                        }else if(final_room[i].charAt(j) == 'f'){
                            this.paintRectangle(new Rectangle(31 + j, 1 + i, 1, 1), new Color(36, 62, 168));
                        }
                    }
                }
                rooms.add(final_room_rect);
                this.paintRectangle(new Rectangle(26, 5, 5, 2), new Color(86, 15, 15));
                chest_room_rect = new Rectangle(1, 16, 10, 10);
                for(int i = 0; i < chest_room.length; i++){
                    for(int j = 0; j < chest_room[i].length(); j++){
                        if(chest_room[i].charAt(j) == '.'){
                            this.paintRectangle(new Rectangle(1 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                        }
                    }
                }
                rooms.add(chest_room_rect);
                this.paintRectangle(new Rectangle(11, 20, 5, 2), new Color(86, 15, 15));
            }
        }
        String[] room1 = new Rooms().enemy_room();
        for(int i = 0; i < room1.length; i++){
            for(int j = 0; j < room1[i].length(); j++){
                if(room1[i].charAt(j) == '.'){
                    this.paintRectangle(new Rectangle(16 + j, 1 + i, 1, 1), new Color(36, 152, 168));
                }
            }
        }
        Rectangle enemy_room_1 = new Rectangle(16, 1, 10, 10);
        rooms.add(enemy_room_1);
        this.paintRectangle(new Rectangle(20, 11, 2, 5), new Color(86, 15, 15));
        enemies.add(new Rifler(2550 + this.x, 450 + this.y, enemy_room_1));
        String[] room2 = new Rooms().enemy_room();
        for(int i = 0; i < room2.length; i++){
            for(int j = 0; j < room2[i].length(); j++){
                if(room2[i].charAt(j) == '.'){
                    this.paintRectangle(new Rectangle(16 + j, 16 + i, 1, 1), new Color(36, 152, 168));
                }
            }
        }
        Rectangle enemy_room_2 = new Rectangle(16, 16, 10, 10);
        rooms.add(enemy_room_2);
        this.paintRectangle(new Rectangle(20, 26, 2, 5), new Color(86, 15, 15));
        String[] room3 = new Rooms().enemy_room();
        for(int i = 0; i < room3.length; i++){
            for(int j = 0; j < room3[i].length(); j++){
                if(room3[i].charAt(j) == '.'){
                    this.paintRectangle(new Rectangle(16 + j, 31 + i, 1, 1), new Color(36, 152, 168));
                }
            }
        }
        Rectangle enemy_room_3 = new Rectangle(16, 31, 10, 10);
        rooms.add(enemy_room_3);
    }

    void generate_enemies(Rectangle room){

    }

    void update_enemies(Player player){
        Rectangle room = get_room_screen(player.x, player.y);
        if(room == null){
            return;
        }
        ArrayList<Rectangle>rects = new ArrayList<>();
        for(int i = room.y; i < room.y + room.height; i++){
            for(int j = room.x; j < room.x + room.width; j++){
                if(!board.get(i).get(j).status){
                    rects.add(new Rectangle(j * cellSize, i * cellSize, cellSize, cellSize));
                }
            }
        }
        Cell player_cell = get_cell_screen(player.x, player.y);
        ArrayList<ArrayList<Integer>> paths = shortestPath((player_cell.x - room.x) + (player_cell.y - room.y) * 10);
//        System.out.println((player_cell.x - room.x) + (player_cell.y - room.y) * 10);
        for(Enemy enemy : enemies){
            if(enemy.room == room) {
                Cell enemy_cell = get_cell_screen((int) enemy.x + enemy.size / 2, (int) enemy.y + enemy.size / 2);
                ArrayList<Integer> path = paths.get((enemy_cell.x - room.x) + (enemy_cell.y - room.y) * 10);
                Collections.reverse(path);
                System.out.println(path);
                if(path.size() <= 1){
                    Vector2D v = new Vector2D((player.x + player.size / 2.0) - (enemy.x + enemy.size / 2.0), (player.y + player.size / 2.0) - (enemy.y + enemy.size / 2.0));
                    v.normalize();
                    enemy.update(v.x, v.y);
                }else {
                    double x2_ans = (room.x + (path.get(0) % 10) + 0.5) * cellSize + this.x;
                    double y2_ans = (room.y + (path.get(0) / 10) + 0.5) * cellSize + this.y;
                    boolean flag = false;
                    for(int vertex : path){
                        double x1 = (room.x + (vertex % 10) + 0.5) * cellSize;
                        double y1 = (room.y + (vertex / 10) + 0.5) * cellSize;
                        Line2D project1 = new Line2D.Double(x1 - enemy.size / 2, y1 - enemy.size / 2, enemy.x - this.x, enemy.y - this.y);
                        Line2D project2 = new Line2D.Double(x1 + enemy.size / 2, y1 + enemy.size / 2, enemy.x + enemy.size - this.x, enemy.y + enemy.size - this.y);
                        Line2D project3 = new Line2D.Double(x1 + enemy.size / 2, y1 - enemy.size / 2, enemy.x + enemy.size - this.x, enemy.y - this.y);
                        Line2D project4 = new Line2D.Double(x1 - enemy.size / 2, y1 + enemy.size / 2, enemy.x - this.x, enemy.y + enemy.size - this.y);
                        for(Rectangle rect : rects){
                            if(project1.intersects(rect) || project2.intersects(rect) || project3.intersects(rect) || project4.intersects(rect)){
                                flag = true;
                                break;
                            }
                        }
                        if(!flag){
                            x2_ans = x1 + this.x;
                            y2_ans = y1 + this.y;
                        }else{
                            break;
                        }
                    }
                    if(enemy instanceof Rifler) {
                        if (x2_ans == (room.x + (path.get(path.size() - 1) % 10) + 0.5) * cellSize + this.x) {
                            enemyBullets.add(((Rifler) enemy).shoot(player.x + player.size / 2, player.y + player.size / 2));
                        }
                        else{
                            Vector2D v = new Vector2D(x2_ans - (enemy.x + enemy.size / 2.0), y2_ans - (enemy.y + enemy.size / 2.0));
                            v.normalize();
                            enemy.update(v.x, v.y);
                        }
                    }else {
                        Vector2D v = new Vector2D(x2_ans - (enemy.x + enemy.size / 2.0), y2_ans - (enemy.y + enemy.size / 2.0));
                        v.normalize();
                        enemy.update(v.x, v.y);
                    }
                }
            }
        }
    }

    public ArrayList<ArrayList<Integer>> shortestPath(int i) {
        double inf = 1000000000.0;
        ArrayList<Double> d = new ArrayList<Double>();
        ArrayList<ArrayList<Integer>> p = new ArrayList<>();
        ArrayList<Integer> A = new ArrayList<Integer>();
        ArrayList<Integer> B = new ArrayList<Integer>();
        for (int k = 0; k < graph.size(); k++) {
            d.add(0.0);
            p.add(new ArrayList<Integer>());
            if (k == i) {
                d.set(k, 0.0);
                ArrayList<Integer> a = new ArrayList<Integer>();
                a.add(k);
                p.set(k, a);
                B.add(i);
            } else {
                d.set(k, inf);
                B.add(k);
            }
        }
        while (B.size() > 0) {
            int V = 0;
            double d_v = inf + 1;
            if (B.size() > 1) {
                for (int v : B) {
                    if (d.get(v) < d_v) {
                        V = v;
                        d_v = d.get(v);
                    }
                }
            } else {
                V = B.get(0);
                d_v = d.get(V);
            }
            for (int U = 0; U < graph.size(); U++) {
                double a = graph.get(V).get(U);
                if (a > 0) {
                    if (d.get(U) < d.get(V) + graph.get(V).get(U)) {
                        d.set(U, d.get(U));
                    } else {
                        d.set(U, d.get(V) + graph.get(V).get(U));
                        ArrayList<Integer> c = (ArrayList<Integer>) p.get(V).clone();
                        c.add(U);
                        p.set(U, c);
                    }
                }
            }
            A.add(V);
            B.remove((Integer) V);
        }
        return p;
    }



    void make_graph(Rectangle room){
        if(room == null || room == last_room){
            return;
        }
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
        System.out.println(graph);
        last_room = room;
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
        for(EnemyBullet bullet : enemyBullets){
            bullet.paint(g);
        }
        for(Enemy enemy : enemies){
            enemy.paint(g);
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
//        System.out.println(x);
//        System.out.println(y);
        this.x += x_direction * dx;
        this.y += y_direction * dy;
        make_graph(get_room_screen(player.x, player.y));
        ArrayList<PlayerBullet> delete_player_bullets = new ArrayList<>();
        ArrayList<EnemyBullet> delete_enemy_bullets = new ArrayList<>();
        ArrayList<Enemy> delete_enemies = new ArrayList<>();
        for(PlayerBullet bullet : playerBullets){
            bullet.x += x_direction * dx;
            bullet.y += y_direction * dy;
            bullet.update();
            if(!get_cell_screen((int)bullet.x, (int)bullet.y).status || (bullet.dist_max != -1 && bullet.dist >= bullet.dist_max)){
                delete_player_bullets.add(bullet);
            }
            for(Enemy enemy : enemies){
                Line2D l = new Line2D.Float((int) bullet.x, (int)bullet.y, (int) (bullet.x + 2 * bullet.dx), (int) (bullet.y + 2 * bullet.dy));
                Rectangle2D r1 = new Rectangle2D.Float((int)enemy.x, (int)enemy.y, enemy.getSize(), enemy.getSize());
                System.out.println(enemy.getSize());
                if(l.intersects(r1)){
                    delete_player_bullets.add(bullet);
                    enemy.hp -= bullet.damage;
                }
            }
        }
        for(EnemyBullet bullet : enemyBullets){
            bullet.x += x_direction * dx;
            bullet.y += y_direction * dy;
            bullet.update();
            if(!get_cell_screen((int)bullet.x, (int)bullet.y).status){
                delete_enemy_bullets.add(bullet);
            }
            Rectangle2D bul = new Rectangle2D.Float((int) bullet.x, (int)bullet.y, 20, 20);
            Rectangle2D r1 = new Rectangle2D.Float(player.x, player.y, player.size, player.size);
            if(bul.intersects(r1)){
                delete_enemy_bullets.add(bullet);
                player.damage(bullet.damage);
            }
        }
        for(Enemy enemy : enemies){
            System.out.println(enemy.x - this.x);
            System.out.println(enemy.y - this.y);
            enemy.update(x_direction * dx, y_direction * dy);
            Rectangle room = get_room_screen(player.x, player.y);
            Rectangle2D player_rectangle = new Rectangle2D.Float(player.x, player.y, player.size, player.size);
            Rectangle2D enemy_rectangle = new Rectangle2D.Float((float)enemy.x, (float)enemy.y, enemy.size, enemy.size);
            if(player_rectangle.intersects(enemy_rectangle)){
                player.damage(enemy.damage);
            }
            if(enemy.hp <= 0){
                delete_enemies.add(enemy);
            }
        }
        update_enemies(player);
        for(PlayerBullet bullet : delete_player_bullets){
            playerBullets.remove(bullet);
        }
        for(EnemyBullet bullet : delete_enemy_bullets){
            enemyBullets.remove(bullet);
        }
        for(Enemy enemy : delete_enemies){
            enemies.remove(enemy);
        }
    }

    public void bug_update(int x_d, int y_d){
        x += x_d * (dx);
        y += y_d * (dy);
        for(Enemy enemy : enemies){
            enemy.x += x_d * dx;
            enemy.y += y_d * dy;
        }
        for(PlayerBullet bullet : playerBullets){
            bullet.x += x_d * dx;
            bullet.y += y_d * dy;
        }
        for(EnemyBullet bullet : enemyBullets){
            bullet.x += x_d * dx;
            bullet.y += y_d * dy;
        }
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
            if(room.x <= cell.x && cell.x < room.x + room.width && room.y <= cell.y && cell.y < room.y + room.height){
                return room;
            }
        }
        return null;
    }

    public static int booleanObjectToInt(boolean foo) {
        return Boolean.compare(foo, false);
    }

    void invertUsingFor(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }
}
