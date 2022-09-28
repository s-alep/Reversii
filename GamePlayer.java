import java.util.ArrayList;
import java.util.Random;

public class GamePlayer {

    //Variable playerColour reprents the tiles that the player is using, black or white
    private int playerColour;
    //Variable maxDepth is the maximum value the depth of the MiniMax algorithm can take.
    private int maxDepth;

    //Returns the value of the variable playerColour
    public int getPlayerColour() {
        return playerColour;
    }

    //Sets the value of variable playerColour
    public void setPlayerColour(int colour){
        if (colour != Board.black && colour != Board.white) playerColour = 0;
        else playerColour = colour;
    }

    //Sets the value of the variable maxDepth
    public void setMaxDepth(int d){
        if (d < 1) System.out.println("ERROR: Invalid depth value");
        else maxDepth = d;
    }

    //Constructor
    public GamePlayer(){
        maxDepth = 2;
        playerColour = Board.black;
    }

    //Constructor
    public GamePlayer(int colour, int d){
        if (d < 1) System.out.println("ERROR: Invalid depth value");
        else maxDepth = d;

        if (!(colour == Board.black || colour == Board.white)) {
            playerColour = 0;
            System.out.println("ERROR: Invalid player colour.");
        }
        else playerColour = colour;
    }

    //Returns the best Reversi move for the given board using the MiniMax algorithm.
    public Move MiniMax(Board board){
        if(playerColour == Board.black){
            return max(new Board(board), 0);
        }else return min(new Board(board), 0);
    }

    //The max portion of the MiniMax algorithm.
    public Move max(Board b, int depth){
        Random r = new Random();

        //If the board is terminal or the maximum depth has been reached returns the move.
        if((b.isTerminal()) || (depth == maxDepth))
        {
            Move lastMove = new Move(b.getLastMove().getRow(), b.getLastMove().getCol(), b.evaluate());
            return lastMove;
        }

        //Iterate through the children and find the one with the biggest value
        ArrayList<Board> children = new ArrayList<Board>(b.getChildren(Board.black));
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (Board child : children)
        {

            Move move = min(child, depth + 1);
           if(move.getValue() >= maxMove.getValue())
            {
                if ((move.getValue() == maxMove.getValue()))
                {

                    if (r.nextInt(2) == 0)
                    {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setCol(child.getLastMove().getCol());
                        maxMove.setValue(move.getValue());
                    }
                }
                else
                {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }
            }
        }
        return maxMove;
    }

    //The min portion of the MiniMax algorithm
    public Move min(Board b, int depth){
        Random r = new Random();

        //If the board is terminal or the maximum depth has been reached returns the move.
        if((b.isTerminal()) || (depth == maxDepth))
        {
            Move lastMove = new Move(b.getLastMove().getRow(), b.getLastMove().getCol(), b.evaluate());
            return lastMove;
        }

        //Iterate through the children and find the one with the smallest value
        ArrayList<Board> children = new ArrayList<Board>(b.getChildren(Board.white));

        Move minMove = new Move(Integer.MAX_VALUE);
        for (Board child : children)
        {
            Move move = max(child, depth + 1);

            if(move.getValue() <= minMove.getValue())
            {
                if ((move.getValue() == minMove.getValue()))
                {
                    if (r.nextInt(2) == 0)
                    {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                    }
                }
                else
                {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setCol(child.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                }
            }
        }
        return minMove;
    }
}
