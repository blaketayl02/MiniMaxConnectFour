import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        char board[][] = {{'_', '_', '_', '_', '_', '_', '_'},
                          {'_', '_', '_', '_', '_', '_', '_'},
                          {'_', '_', '_', '_', '_', '_', '_'},
                          {'_', '_', '_', '_', '_', '_', '_'},
                          {'_', '_', '_', '_', '_', '_', '_'},
                          {'_', '_', '_', '_', '_', '_', '_'}};


        // goal to make X win
        while (Move.movesLeft(board)) {
            System.out.println("=============================");
            System.out.println("1   2   3   4   5   6   7");
            Move.printBoard(board);
            if (Move.evaluate(board) == 10) {
                System.out.println("Sorry Computer Won!");
                break;
            } else if (Move.evaluate(board) == -10) {
                System.out.println("What? That's Impossible... You Won!");
                break;
            }
            System.out.println("Where would you like to play? Col:");
            Scanner scanner = new Scanner(System.in);
            try {
                int col = scanner.nextInt();
                Move.movePiece(board, col - 1, 'o');
                Move bestMove = Move.findBestMove(board);
                Move.movePiece(board, bestMove.col, 'x');
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (Move.evaluate(board) == 0) {
            System.out.println("Looks like a draw...");
        }

    }
}