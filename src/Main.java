import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean keepRunning = true;
        int highScore = -1;
        String highScoreHolder = "";
        while (keepRunning) {
            System.out.println("РЕВЕРСИ");
            System.out.println("Введите число от 1 до 3:");
            System.out.println("1: Начать игру.");
            System.out.println("2: Вывести рекорд.");
            System.out.println("3: Выйти из игры.");
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            while (option > 3 || option < 0) {
                System.out.println("Введите число от 1 до 3.");
                option = input.nextInt();
            }
            if (option == 1) {
                Game g = new Game();
                GameResults results = g.play();
                if (results.isTie) {
                    System.out.println("Ничья со счетом" + results.score + ".");
                } else {
                    System.out.println("Победа игрока " + results.winner + " со счетом " + results.score + ".");
                    if (results.isRanked && results.score > highScore){
                        System.out.println("Новый рекорд!");
                        highScore = results.score;
                        highScoreHolder = results.winner;
                    }
                }
            } else if (option == 2) {
                if (highScore == -1){
                    System.out.println("Игроки еще ни разу не победили ИИ!");
                } else {
                    System.out.println("Рекорд занят " + highScoreHolder + " со счетом " + highScore + ".");
                }
            } else {
                System.out.println("КОНЕЦ ИГРЫ.");
                keepRunning = false;
            }
        }
    }
}

