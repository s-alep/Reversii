import java.util.Scanner;

public class Main {

    public static void main(String [] args){
        //Computer player
        GamePlayer computer = initialize();
        if(computer.getPlayerColour() != 0){
            //Board initialization
            Board b = new Board();

            Scanner sc = new Scanner(System.in);
            int row, col;
            long start, end, total = 0, rep = 0;
            while(!b.isTerminal()){
                b.print();
                //Last player initially by default white so black plays first
                switch(b.getLastPlayer()){
                    case Board.white:
                        System.out.println("Black player plays.");
                        //Checks if there are moves available
                        if(b.getChildren(Board.black).size() == 0){
                            System.out.println("Black player has no moves.");
                        }else {
                            //if computer is black make the MiniMax move
                            if (computer.getPlayerColour() == -1) {
                                start = System.currentTimeMillis();
                                Move m = computer.MiniMax(b);
                                b.makeMove(m.getRow(), m.getCol(), Board.black);
                                end = System.currentTimeMillis();
                                System.out.println("Computer plays " + (m.getRow()+1) + "-" + (m.getCol()+1) + ".");
                                System.out.println("Time to calculate: " + (end - start) + " ms.");
                                total+= end - start;
                                rep++;
                                System.out.println("Total computing time: " + total +" ms. Average time for " + rep + " repetitions: "+ total/rep +" ms.");
                            }
                            //If user is black get his move until it's valid and make it
                            else {
                                System.out.println("Enter your move: ");
                                System.out.print("row = ");
                                row = sc.nextInt();
                                System.out.print("column =  ");
                                col = sc.nextInt();
                                System.out.println("");
                                while (!b.isValidMove(row - 1, col - 1, Board.black)) {
                                    System.out.println("The move is not valid. row:[1,8] col:[1,8]");
                                    System.out.print("Enter your move: ");
                                    System.out.print("row = ");
                                    row = sc.nextInt();
                                    System.out.print("column =  ");
                                    col = sc.nextInt();
                                    System.out.println("");
                                }
                                b.makeMove(row - 1,col - 1,Board.black);
                            }
                        }
                        break;
                    case Board.black:
                        System.out.println("White player plays.");
                        //Checks if there are moves available
                        if(b.getChildren(Board.white).size() == 0){
                            System.out.println("White player has no moves.");
                        }else {
                            //if computer is white make the MiniMax move
                            if (computer.getPlayerColour() == 1) {
                                start = System.currentTimeMillis();
                                Move m = computer.MiniMax(b);
                                b.makeMove(m.getRow(), m.getCol(), Board.white);
                                System.out.println("Computer plays " + (m.getRow()+1) + "-" + (m.getCol()+1) + ".");
                                end = System.currentTimeMillis();
                                System.out.println("Time to calculate: " + (end - start) + " ms.");
                                total+= end - start;
                                rep++;
                                System.out.println("Total computing time: " + total +" ms. Average time for " + rep + " repetitions: "+ total/rep +" ms.");
                            }
                            //If user is white get his move until it's valid and make it
                            else {
                                System.out.print("Enter your move: ");
                                row = sc.nextInt();
                                System.out.print(" - ");
                                col = sc.nextInt();
                                System.out.println("");
                                while (!b.isValidMove(row - 1, col - 1, Board.white)) {
                                    System.out.println("The move is not valid. row:[1,8] col:[1,8]");
                                    System.out.print("Enter your move: ");
                                    row = sc.nextInt();
                                    System.out.print(" - ");
                                    col = sc.nextInt();
                                    System.out.println("");
                                }
                                b.makeMove(row-1,col-1,Board.white);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            //sc.close();
            //Print the last board and exit
            b.print();
            System.out.println("Goodbye.");

        }else {
            System.out.println("Goodbye");
        }
    }

    //Initializes the computer player
    public static GamePlayer initialize(){
        System.out.println("Welcome to Reversi.");
        //Get the maximum depth
        System.out.print("Choose MiniMax maximum depth (>=1): ");
        Scanner sc = new Scanner (System.in);
        int depth = sc.nextInt();

        //Get the human player side
        System.out.println("Choose black (X) or white (O) (black goes first):");
        String colour = sc.next();

        //Give the other side and the maxDepth to the computer player.
        int playerColour;

        if (colour.equalsIgnoreCase("X")  ) playerColour = Board.white;
        else if (colour.equalsIgnoreCase("Ο")) playerColour = Board.black;
        else playerColour = 0;

        //sc.close();
        return new GamePlayer(playerColour, depth);
    }
}
