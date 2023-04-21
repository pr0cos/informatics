import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class BossBoard extends Board{

    Boss boss;

    public BossBoard(int x, int y, int cellSize) throws IOException {
        super(x, y, cellSize);
        this.width = 50;
        this.height = 50;
        for(int i = 0; i < this.height; i++){
            board.add(new ArrayList<Cell>());
            for(int j = 0; j < this.width; j++){
                board.get(i).add(new Cell(j, i, false, cellSize));
            }
        }
    }

    @Override
    void generate() throws IOException{
        System.out.println("asdas");
        Rectangle boss_room = new Rectangle(16, 1, 15, 15);
        Rectangle start_room = new Rectangle(1, 1, 10, 10);
        this.paintRectangle(start_room, new Color(36, 152, 168));
        this.paintRectangle(boss_room, new Color(36, 152, 168));
        this.paintRectangle(new Rectangle(11, 5, 5, 2), new Color(86, 15, 15));
        rooms.add(boss_room);
        boss = new Boss((16 * cellSize + 31 * cellSize) / 2, (1 * cellSize + 16 * cellSize) / 2, boss_room);
        boss.x = boss.x - boss.size / 2;
        boss.y = boss.y - boss.size / 2;
        enemies.add(boss);
    }

    @Override
    void generate_enemies(Rectangle room){
        return;
    }

    @Override
    void make_graph(Rectangle room){
        return;
    }

    @Override
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

    @Override
    void update_enemies(Player player){
        if(boss != null && get_room_screen(player.x, player.y) == boss.room) {
            enemyBullets.addAll(boss.shoot(player.x + player.size / 2, player.y + player.size / 2));
        }

    }
    @Override
    public void update(Player player){
            this.x += x_direction * dx;
            this.y += y_direction * dy;
            ArrayList<PlayerBullet> delete_player_bullets = new ArrayList<>();
            ArrayList<EnemyBullet> delete_enemy_bullets = new ArrayList<>();
            ArrayList<Enemy> delete_enemies = new ArrayList<>();
        if(boss != null && boss.hp <= 0){
            boss = null;
            enemyBullets.clear();
            playerBullets.clear();
        }
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
}
