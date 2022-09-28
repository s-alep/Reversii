import java.util.ArrayList;

public class Board {
    public int dimension;

    // static variables
    public static final int black  = -1;
    public static final int white = 1;
    public static final int empty = 0;

    //The last move played
    public Move lastMove;
    //The last player -1 for black 1 for white
    public int lastPlayer;
    //array that holds the gameboard
    public int[][] gameBoard;

    //Constructor
    public Board(){
        lastMove  = new Move();
        lastPlayer = white;
        dimension = 8;

        gameBoard = new int[dimension][dimension];
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                gameBoard[i][j] = empty;
            }
        }

        gameBoard[3][3] = black;
        gameBoard[3][4] = white;
        gameBoard[4][3] = white;
        gameBoard[4][4] = black;

    }

    //Constructor
    public Board (Board b){
        lastMove = b.lastMove;
        lastPlayer = b.lastPlayer;
        dimension = b.dimension;

        gameBoard = new int[dimension][dimension];
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                gameBoard[i][j] = b.gameBoard[i][j];
            }
        }
    }

    //Returns the last move that got played
    public Move getLastMove(){
        return lastMove;
    }

    //Return the player who played last
    public int getLastPlayer(){
        return lastPlayer;
    }

    //Returns the game board array
    public int[][] getGameBoard() {
        return gameBoard;
    }

    //Set the last move played
    public void setLastMove(Move m){
        lastMove = m;
    }

    //Sets the last player
    public void setLastPlayer(int p){
        lastPlayer = p;
    }

    //Sets the gameboard
    public void setGameBoard(int [][] gb){
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                gameBoard[i][j] = gb[i][j];
            }
        }
    }

    //A valid move is played and the board changes accordingly
    public void makeMove(int i, int j, int colour){
       //An empty tile is filled
        gameBoard[i][j] = colour;

        //Temp_c holds the opposite colour
        int temp_c;

        if(colour == black) temp_c = white;
        else temp_c = black;

        //change the colour of the conquered pieces
        //upwards
        for (int row = i; row >= 0 ; --row ){
           // System.out.println("1 " + row + "-" +j);
            if (gameBoard[row][j] == colour && (row != i)){
                for(int k = row + 1; k != i; k++){
                    //System.out.print("1a " + k + "-" + j + "  ");
                    if (gameBoard[k][j] == temp_c){
                        //System.out.println("1b");
                        gameBoard[k][j] = colour;
                    }
                }
                break;
            }
        }
        //downwards
        for (int row = i; row <= 7 ; row ++){
            if (gameBoard[row][j] == colour && row != i){
                for(int k = row - 1; k != i; k--){
                    if (gameBoard[k][j] == temp_c) {
                        gameBoard[k][j] = colour;
                    }
                }
                break;
            }
        }
        //right
        for (int col = j; col <= 7 ; col ++){
            if (gameBoard[i][col] == colour && col != j){
                for(int k = col - 1; k != j; k--){
                    if (gameBoard[i][k] == temp_c) gameBoard[i][k] = colour;
                }
                break;
            }
        }
        //left
        for (int col = j; col >= 0 ; col --){
            if (gameBoard[i][col] == colour && col != j){
                for(int k = col + 1; k != j; k++){
                    if (gameBoard[i][k] == temp_c) gameBoard[i][k] = colour;
                }
                break;
            }
        }

       int row, col;
        //up-right
        row = i;
        col = j;
        row--;
        col++;
        while (row >= 0 && col <= 7){
            if(gameBoard[row][col] == colour){
                while (row != i && col != j){
                    row++;
                    col--;
                    if(gameBoard[row][col] == temp_c){
                        gameBoard[row][col] = colour;
                    }
                }
                break;
            }
            row--;
            col++;
        }
        //down-right
        row = i+1;
        col = j+1;
        while (row <=7  && col <= 7){

            if(gameBoard[row][col] == colour){
                while (row != i && col != j){
                    row--;
                    col--;
                    if(gameBoard[row][col] == temp_c){
                        gameBoard[row][col] = colour;
                    }
                }
                break;
            }
            row++;
            col++;
        }
        //down-left
        row = i+1;
        col = j-1;
        while (row <=7  && col >= 0){

            if(gameBoard[row][col] == colour){
                while (row != i && col != j){
                    row--;
                    col++;
                    if(gameBoard[row][col] == temp_c){
                        gameBoard[row][col] = colour;
                    }
                }
                break;
            }
            row++;
            col--;
        }
        //up-left
        row = i-1;
        col = j-1;
        while (row >=0  && col >= 0){

            if(gameBoard[row][col] == colour){
                while (row != i && col != j){
                    row++;
                    col++;
                    if(gameBoard[row][col] == temp_c){
                        gameBoard[row][col] = colour;
                    }
                }
                break;
            }
            row--;
            col--;
        }


        lastMove = new Move(i,j);
        lastPlayer = colour;
    }

    //Returns TRUE of FALSE depending of the validity of the move given
    public boolean isValidMove(int i, int j, int colour){
        //Checks the colour
        if(colour != white && colour != black) return false;
        //Checks if the indexes are inside the bounds.
        if (i < 0 || i > 7 || j < 0 || j > 7) return false;
        //Checks if the tile of the move is available
        if(gameBoard[i][j] != empty) return false;
        //Checks if the tile has opposing tiles adjacent to it.
        if (!hasOppositeAdjacent(i,j, colour)) return false;
        return true;
    }

    //Returns true or false depending on the existence of tiles with the opposing colour adjacent to the tile.
    //A tile can only be placed next to a tile with the opposing colour
    private boolean hasOppositeAdjacent(int i, int j, int colour) {
        //Total sum of opposing tiles surrounding  the tile
       int n = 0;
       //Checks if the tile is available
       if (gameBoard[i][j] != empty) return false;

       //Checks the three tiles (i-1,j-1), (i-1,j), (i-1, j+1) above the tile i,j
        if( (i-1) >= 0){
            if(j-1 >= 0) {
                if( gameBoard[i-1][j-1] != colour && gameBoard[i-1][j-1] != empty){
                    n++;
                }
            }

            if( gameBoard[i-1][j] != colour && gameBoard[i-1][j] != empty){
                n++;
            }

            if(j+1 <=7) {
                if( gameBoard[i-1][j+1] != colour && gameBoard[i-1][j+1] != empty){
                    n++;
                }
            }
        }
        //Checks the three tiles (i+1,j-1), (i+1,j), (i+1, j+1) below the tile i,j
        if( (i+1) <= 7){
            if(j-1 >= 0) {
                if( gameBoard[i+1][j-1] != colour && gameBoard[i+1][j-1] != empty){
                    n++;
                }
            }

            if( gameBoard[i+1][j] != colour && gameBoard[i+1][j] != empty){
                n++;
            }

            if(j+1 <=7) {
                if( gameBoard[i+1][j+1] != colour && gameBoard[i+1][j+1] != empty){
                    n++;
                }
            }
        }

        //Checks the three tiles (i-1,j-1), (i,j-1), (i+1, j-1) to the left of the tile i,j
        if( (j-1) >= 0){
            if(i-1 >= 0) {
                if( gameBoard[i-1][j-1] != colour && gameBoard[i-1][j-1] != empty){
                    n++;
                }
            }

            if( gameBoard[i][j-1] != colour && gameBoard[i][j-1] != empty){
                n++;
            }

            if(i+1 <=7) {
                if( gameBoard[i+1][j-1] != colour && gameBoard[i+1][j-1] != empty){
                    n++;
                }
            }

        }

        //Checks the three tiles (i-1,j+1), (i,j+1), (i+1, j+1) to the right of the tile i,j
        if( (j+1) <= 7){
            if(i-1 >= 0) {
                if( gameBoard[i-1][j+1] != colour && gameBoard[i-1][j+1] != empty){
                    n++;
                }
            }

            if( gameBoard[i][j+1] != colour && gameBoard[i][j+1] != empty){
                n++;
            }

            if(i+1 <=7) {
                if( gameBoard[i+1][j+1] != colour && gameBoard[i+1][j+1] != empty){
                    n++;
                }
            }

        }
        return n!=0;
    }

    // Returns and ArrayList that holds the boards of all the available moves
    public ArrayList<Board> getChildren(int colour){
        ArrayList<Board> children = new ArrayList<Board>();
        for (int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(isValidMove(i,j,colour)){

                    Board child = new Board(this);
                    child.makeMove(i,j,colour);
                    children.add(child);

                }
            }
        }
        return children;
    }

    //Evaluates each move. The program prioritizes a move if it gives more tiles, gets a wall-tile or a corner tile
    // value = tiles + walls + 3*corners
    public int evaluate(){
        //Number of disks
        int disks, b_disks = 0, w_disks = 0;
        //Number of corners
        int corners, b_corners = 0, w_corners = 0;
        //Number of stable disks (the opponent cant move)
        int stable, b_stable =0, w_stable = 0;
        //Number of wall pieces
        int wall , b_wall=0, w_wall = 0;


        int value = 0;


        int it ,c,row, col;
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                if(gameBoard[i][j] == black) b_disks++;
                if(gameBoard[i][j] == white) w_disks++;

                if(i == 0 ){
                    if (j == 0){
                        if(gameBoard[i][j] == black){
                            b_corners++;
                        }
                        if(gameBoard[i][j] == white){
                            w_corners++;

                        }
                    }else if(j == 7){
                        if(gameBoard[i][j] == black) {
                            b_corners++;

                        }
                        if(gameBoard[i][j] == white) {
                            w_corners++;

                        }
                    }else{
                        if(gameBoard[i][j] == black) b_wall++;
                        if(gameBoard[i][j] == white) w_wall++;
                    }
                }if(i == 7){
                    if(j == 0){
                        if(gameBoard[i][j] == black) {
                            b_corners++;

                        }
                        if(gameBoard[i][j] == white) {
                            w_corners++;

                        }
                    }
                    else if(j == 7){
                        if(gameBoard[i][j] == black) {
                            b_corners++;

                        }
                        if(gameBoard[i][j] == white) {
                            w_corners++;

                        }
                    }else{
                        if(gameBoard[i][j] == black) b_wall++;
                        if(gameBoard[i][j] == white) w_wall++;
                    }
                }if(j == 0){
                    if(i == 0 || i ==7) continue;
                    else {
                        if(gameBoard[i][j] == black) b_wall++;
                        if(gameBoard[i][j] == white) w_wall++;
                    }
                }if(j == 7) {
                    if (i == 0 || i == 7) {}
                    else {
                        if (gameBoard[i][j] == black) b_wall++;
                        if (gameBoard[i][j] == white) w_wall++;
                    }
                }
            }
        }

        disks = b_disks - w_disks;
        corners = b_corners - w_corners;
        wall = b_wall - w_wall;


        return disks + 3*corners  + wall ;

    }


    //Returns true or false depending on the amount of moves. If there are no moves left returns TRUE.
    public boolean isTerminal(){
        if (this.lastPlayer == white){
            if (this.getChildren(black).size() == 0
                    && this.getChildren(white).size() == 0) return true;
        }else if (this.lastPlayer == black){
            if (this.getChildren(white).size() == 0
                    && this.getChildren(black).size() == 0) return true;
        }
        return false;
    }

    //Prints the game board and the score.
    public void print(){
        int b_score = 0, w_score = 0;
        System.out.println("***1*2*3*4*5*6*7*8**");
        for(int row=0; row<dimension; row++)
        {
            System.out.print("*" + (row+1) + " ");
            for(int col=0; col<dimension; col++)
            {
                switch (gameBoard[row][col])
                {
                    case black:
                        System.out.print("X ");
                        b_score++;
                        break;
                    case white:
                        System.out.print("O ");
                        w_score++;
                        break;
                    case empty:
                        System.out.print("- ");
                        break;
                    default:
                        break;
                }
            }
            System.out.println("*");
        }
        System.out.println("********************");
        System.out.println("Score: B: " + b_score + " - W: " + w_score );
        System.out.println("********************");
    }


}
