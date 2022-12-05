public enum Cell {
    empty,
    second,
    first;
    public Cell invert(){
        if (this == first){
            return second;
        }
        if (this == second){
            return first;
        }
        return empty;
    }
}
