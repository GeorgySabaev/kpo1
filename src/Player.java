import java.io.Console;
import java.util.Scanner;
public class Player extends MoveMaker {
    public Player(Board board){
        System.out.println("Введите имя игрока:");
        Scanner input = new Scanner( System.in );
        name = input.nextLine();
        this.board = board;
    }
    public MoveData move(){
        System.out.println("Введите координаты хода (столбец, затем строку), либо -1 чтобы взять последний ход назад.");
        Scanner input = new Scanner( System.in );
        int x = input.nextInt();
        if (x == -1){
            return new MoveData(true);
        }
        int y = input.nextInt();
        return new MoveData(new Vector2(x, y));
    }
}
