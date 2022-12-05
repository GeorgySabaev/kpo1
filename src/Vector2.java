public class Vector2 {
    int x;
    int y;
    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean isWithinBox(int size){
        return !(x < 0 || x >= size || y < 0 || y >= size);
    }
    public void Add(Vector2 other){
        x += other.x;
        y += other.y;
    }
}
