import java.util.Stack;

public class Board {
    Stack<BoardSnapshot> timeline = new Stack<BoardSnapshot>();
    int size = 8;

    Board() {
        timeline.push(new BoardSnapshot(size));
    }
    public boolean isFinished(){
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (top().isMoveValid(new Vector2(j, i))){
                    return false;
                }
            }
        }
        return true;
    }
    public BoardSnapshot top(){
        return timeline.peek();
    }
    public String toString() {
        return top().toString();
    }
    public boolean tryMove(Vector2 move){
        if (!top().isMoveValid(move)){
            return false;
        }
        BoardSnapshot newBoard = new BoardSnapshot(top());
        for (int shiftX = -1; shiftX <= 1; shiftX++) {
            for (int shiftY = -1; shiftY <= 1; shiftY++) {
                if (shiftX == 0 && shiftY == 0) {
                    continue;
                }
                Vector2 shift = new Vector2(shiftX, shiftY);
                Vector2 i = new Vector2(move.x, move.y);
                i.Add(shift);
                if (!i.isWithinBox(size) || newBoard.at(i) != newBoard.currentTurn.invert()){
                    continue;
                }
                i.Add(shift);
                boolean isConnected = false;
                while (i.isWithinBox(size)) {
                    if (newBoard.at(i) == newBoard.currentTurn){
                        isConnected = true;
                        break;
                    }
                    if (newBoard.at(i) == Cell.empty){
                        break;
                    }
                    i.Add(shift);
                }
                if (isConnected){
                    Vector2 negShift = new Vector2(-shiftX, -shiftY);
                    i.Add(negShift);
                    while (newBoard.at(i) == newBoard.currentTurn.invert()){
                        newBoard.writeAt(i, newBoard.currentTurn);
                        i.Add(negShift);
                    }
                }
            }
        }
        newBoard.writeAt(move, newBoard.currentTurn);
        newBoard.currentTurn = newBoard.currentTurn.invert();
        timeline.push(newBoard);
        return true;
    }
}

class BoardSnapshot {
    Cell[][] board;
    Cell currentTurn;
    int size;

    public BoardSnapshot(int size) {
        this.size = size;
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = Cell.empty;
            }
        }
        board[3][3] = board[4][4] = Cell.first;
        board[3][4] = board[4][3] = Cell.second;
        currentTurn = Cell.first;
    }

    public BoardSnapshot(BoardSnapshot previous) {
        this.size = previous.size;
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            if (size >= 0) System.arraycopy(previous.board[i], 0, board[i], 0, size);
        }
        currentTurn = previous.currentTurn;
    }

    public Cell at(Vector2 coords){
        return board[coords.x][coords.y];
    }

    public void writeAt(Vector2 coords, Cell value){
        board[coords.x][coords.y] = value;
    }

    public String toString() {
        StringBuilder retval = new StringBuilder();
        retval.append("  0 1 2 3 4 5 6 7\n");
        for (int i = 0; i < 8; ++i) {
            retval.append(Integer.toString(i)).append(" ");
            for (int j = 0; j < 8; ++j) {
                String c = "- ";
                if (board[j][i] == Cell.first){
                    c = "▓ ";
                }
                if (board[j][i] == Cell.second){
                    c = "▒ ";
                }
                if (isMoveValid(new Vector2(j, i))){
                    c = "X ";
                }
                retval.append(c);
            }
            retval.append("\n");
        }
        return retval.toString();
    }

    boolean isMoveValid(Vector2 move) {
        if (!move.isWithinBox(size)) {
            return false;
        }
        if (at(move) != Cell.empty) {
            return false;
        }

        for (int shiftX = -1; shiftX <= 1; shiftX++) {
            for (int shiftY = -1; shiftY <= 1; shiftY++) {
                if (shiftX == 0 && shiftY == 0) {
                    continue;
                }
                Vector2 shift = new Vector2(shiftX, shiftY);
                Vector2 i = new Vector2(move.x, move.y);
                i.Add(shift);
                if (!i.isWithinBox(size) || at(i) != currentTurn.invert()){
                    continue;
                }
                i.Add(shift);
                while (i.isWithinBox(size)) {
                    if (at(i) == currentTurn){
                        return true;
                    }
                    if (at(i) == Cell.empty){
                        break;
                    }
                    i.Add(shift);
                }
            }
        }
        return false;
    }
}