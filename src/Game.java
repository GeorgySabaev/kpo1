import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class Game {
    private Board board;
    private MoveMaker moveMaker1;
    private MoveMaker moveMaker2;

    public GameResults play() {
        board = new Board();
        System.out.println("Выберите игрока 1:");
        moveMaker1 = getMoveMaker();
        System.out.println("Выберите игрока 2:");
        moveMaker2 = getMoveMaker();

        System.out.println(moveMaker1.name + " против " + moveMaker2.name + "!");

        while (!board.isFinished()) {
            MoveMaker current;
            if (board.top().currentTurn == Cell.first) {
                current = moveMaker1;
            } else {
                current = moveMaker2;
            }
            MoveData moveData;
            boolean isMoveMade = false;
            do {
                System.out.println();
                System.out.println("Ход игрока " + current.name + ":");
                System.out.println(board);
                moveData = current.move();
                if (moveData.isTakeback && board.timeline.size() < 3) {
                    System.out.println("Нельзя взять ход назад в свой первый ход!");
                    System.out.println("Сделайте ход снова.");
                    continue;
                }
                if (moveData.isTakeback) {
                    System.out.println("Игрок " + current.name + " берет ход назад!");
                    board.timeline.pop();
                    board.timeline.pop();
                    break;
                }
                if (!board.tryMove(moveData.coords)) {
                    System.out.println("Нельзя поставить фишку в эту клетку!");
                    System.out.println("Сделайте ход снова.");
                    continue;
                }
                isMoveMade = true;
            } while (!isMoveMade);
        }
        System.out.println("Конец игры!");
        System.out.println(board);
        int firstCount = 0;
        int secondCount = 0;
        int winScore = 0;
        MoveMaker winner;
        MoveMaker loser;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {

            }
        }
        if (firstCount == secondCount) {
            return new GameResults("", firstCount, false, true);
        } else if (firstCount > secondCount) {
            winner = moveMaker1;
            loser = moveMaker2;

        } else {
            winner = moveMaker2;
            loser = moveMaker1;
        }
        return new GameResults(winner.name, winScore, winner instanceof Player && loser instanceof Engine, false);
    }

    private MoveMaker getMoveMaker() {
        System.out.println("1: Человек");
        System.out.println("2: ИИ (легкий)");
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        while (option > 2 || option < 0) {
            System.out.println("Введите число от 1 до 2.");
            option = input.nextInt();
        }
        if (option == 1) {
            return new Player(board);
        } else {
            return new EngineSimple(board);
        }
    }
}

class GameResults{
    String winner;
    int score;
    boolean isRanked;
    boolean isTie;
    public GameResults(String winner, int score, boolean isRanked, boolean isTie){
        this.winner = winner;
        this.isRanked = isRanked;
        this.score = score;
        this.isTie = isTie;
    }
}
