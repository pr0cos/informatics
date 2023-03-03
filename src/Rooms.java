import java.util.Random;

public class Rooms{

    String[] enemy_room_1;
    String[] enemy_room_2;
    String[] start_room;

    public Rooms() {
        this.enemy_room_1 = new String[]   {"..........",
                "..........",
                "..**..**..",
                "..**..**..",
                "..**..**..",
                "..........",
                ".*......*.",
                "..******..",
                "..........",
                ".........."};
        this.enemy_room_2 = new String[]   {"..........",
                "..........",
                ".*...****.",
                ".*......*.",
                ".*......*.",
                ".*......*.",
                ".*......*.",
                ".****...*.",
                "..........",
                ".........."};
        this.start_room = new String[]     {"..........",
                "..........",
                "..........",
                "..........",
                "..........",
                "..........",
                "..........",
                "..........",
                "..........",
                ".........."};
    }

    String[] enemy_room(){
        Random r = new Random();
        int room = r.nextInt(1, 3);
        if(room == 1){
            return enemy_room_1;
        }else if(room == 2){
            return enemy_room_2;
        }else{
            return null;
        }
    }

    String[] start_room(){
        return start_room;
    }
}