public class Move {
    //the row, the column and the value of the move
    private int row;
    private int col;
    private int value;

    //Constructor
    public Move(){
        row = -1;
        col = -1;
    }

    //Constructor
    public Move(int i, int j){
        row = i;
        col = j;
    }

    //Constructor
    public Move(int value)
    {
        row = -1;
        col = -1;
        this.value = value;
    }

    //Constructor
    public Move(int row, int col, int value)
    {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    //Return the move's row
    public int getRow(){
        return row;
    }


    //Returns the move's column
    public int getCol(){
        return col;
    }

    //Returns the value of the move
    public int getValue() {
        return value;
    }

    //Sets the value of the variable row
    public void setRow(int i){
        row = i;
    }

    //Sets the value of variable column
    public void setCol(int j){
        col = j;
    }

    //Sets the value of the variable value
    public void setValue(int value) {
        this.value = value;
    }
}
