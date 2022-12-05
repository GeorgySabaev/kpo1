abstract class MoveMaker {
    public String name;
    Board board;
    public abstract MoveData move();
}

class MoveData {
    Vector2 coords;
    boolean isTakeback;
    public MoveData(boolean isTakeback, Vector2 coords){
        this.coords = coords;
        this.isTakeback = isTakeback;
    }
    public MoveData(Vector2 coords){
        this(false, coords);
    }
    public MoveData(boolean isTakeback){
        this(isTakeback, new Vector2(0,0));
    }
}