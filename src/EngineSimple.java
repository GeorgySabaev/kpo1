public class EngineSimple extends Engine {
    public EngineSimple(Board board) {
        this.board = board;
        this.name = "ИИ (Легкий)";
    }

    public MoveData move() {
        int maxScore = 0;
        Vector2 maxMove = new Vector2(0, 0);
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Vector2 move = new Vector2(i, j);
                int score = calculateScore(move);
                if (score > maxScore){
                    maxScore = score;
                    maxMove = move;
                }
            }
        }
        return new MoveData(maxMove);
    }

    public int calculateScore(Vector2 move) {
        if (!board.top().isMoveValid(move)) {
            return 0;
        }
        int score;
        if (move.x % 7 == 0 && move.y % 7 == 0) {
            score = 8;
        } else if (move.x % 7 == 0 || move.y % 7 == 0) {
            score = 4;
        } else {
            score = 0;
        }
        for (int shiftX = -1; shiftX <= 1; shiftX++) {
            for (int shiftY = -1; shiftY <= 1; shiftY++) {
                if (shiftX == 0 && shiftY == 0) {
                    continue;
                }
                Vector2 shift = new Vector2(shiftX, shiftY);
                Vector2 i = new Vector2(move.x, move.y);
                i.Add(shift);
                if (!i.isWithinBox(board.size) || board.top().at(i) != board.top().currentTurn.invert()) {
                    continue;
                }
                i.Add(shift);
                boolean isConnected = false;
                while (i.isWithinBox(board.size)) {
                    if (board.top().at(i) == board.top().currentTurn) {
                        isConnected = true;
                        break;
                    }
                    if (board.top().at(i) == Cell.empty) {
                        break;
                    }
                    i.Add(shift);
                }
                if (isConnected) {
                    Vector2 negShift = new Vector2(-shiftX, -shiftY);
                    i.Add(negShift);
                    while (board.top().at(i) == board.top().currentTurn.invert()) {
                        if (i.x % 7 == 0 || i.y % 7 == 0) {
                            score += 20;
                        } else {
                            score += 10;
                        }
                        i.Add(negShift);
                    }
                }
            }
        }
        return score;
    }
}
