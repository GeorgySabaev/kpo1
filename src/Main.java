public class Main {
    public static void main(String[] args) {
        Game g = new Game();
        GameResults results = g.play();
        if (results.isTie){
            System.out.println("Ничья со счетом" + results.score + ".");
        }else{
            System.out.println("Победа игрока " + results.winner + " со счетом " + results.score + ".");
        }
    }
}

