public class Move {
    int row, col;
    // X is computer trying to win
    // O is player
    static final String PLAYER_PIECE = "\u001B[33m\u25A0\u001B[0m"; // Yellow square
    static final String COMPUTER_PIECE = "\u001B[34m\u25A0\u001B[0m"; // Blue square
    static final int MAX_DEPTH = 8;
    static char oh = 'o';
    static char ex = 'x';

    static boolean movesLeft(char board[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == '_') {
                    return true;
                }
            }
        }
        return false;
    }

    static int minimax(char board[][], int depth, boolean isMax, int alpha, int beta) {
        int score = evaluate(board);

        if (score == 10 || score == -10 || !movesLeft(board) || depth >= MAX_DEPTH) {
            return score;
        }

        if (isMax) {
            int bestVal = -1000;
            for (int col = 0; col < 7; col++) {
                for (int row = board.length - 1; row >= 0; row--) {
                    if (board[row][col] == '_') {
                        board[row][col] = ex; //make move
                        int value = minimax(board, depth + 1, false, alpha, beta);
                        board[row][col] = '_'; //undo move
                        bestVal = Math.max(bestVal, value);
                        alpha = Math.max(alpha, bestVal);
                        if (beta <= alpha) {
                            break;
                        }
                        break; //break and move to next column
                    }
                }
            }
            return bestVal;
        } else {
            int bestVal = 1000;
            for (int col = 0; col < 7; col++) {
                for (int row = board.length - 1; row >= 0; row--) {
                    if (board[row][col] == '_') {
                        board[row][col] = oh;
                        int value = minimax(board, depth + 1, true, alpha, beta);
                        board[row][col] = '_';
                        bestVal = Math.min(bestVal, value);
                        beta = Math.min(beta, bestVal);
                        if (beta <= alpha) {
                            break;
                        }
                        break;
                    }
                }
            }
            return bestVal;
        }
    }

    static int evaluate(char board[][]) {
        int xCount, oCount;
        //check row
        for (int row = 0; row < board.length; row++) {
            xCount = 0;
            oCount = 0;
            for (int col = 0; col < 7; col++) {
                if (board[row][col] == ex) {
                    xCount++;
                    oCount = 0;
                } else if (board[row][col] == oh) {
                    oCount++;
                    xCount = 0;
                } else if (board[row][col] == '_') {
                    oCount = 0;
                    xCount = 0;
                }

                if (xCount == 4) {
                    return +10;
                } else if (oCount == 4) {
                    return -10;
                }
            }
        }

        // Check columns for win
        for (int col = 0; col < 7; col++) {
            xCount = 0;
            oCount = 0;
            for (int row = 0; row < board.length; row++) {
                if (board[row][col] == ex) {
                    xCount++;
                    oCount = 0;
                } else if (board[row][col] == oh) {
                    oCount++;
                    xCount = 0;
                } else if (board[row][col] == '_') {
                    oCount = 0;
                    xCount = 0;
                }

                if (xCount == 4) {
                    return +10;
                } else if (oCount == 4) {
                    return -10;
                }
            }
        }

        //check ascending diagonals
        for (int row = 3; row < board.length; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == ex && board[row - 1][col + 1] == ex && board[row - 2][col + 2] == ex && board[row - 3][col + 3] == ex) {
                    return 10;
                }
                if (board[row][col] == oh && board[row - 1][col + 1] == oh && board[row - 2][col + 2] == oh && board[row - 3][col + 3] == oh) {
                    return -10;
                }
            }
        }

        //check descending diagonals
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == ex && board[row + 1][col + 1] == ex && board[row + 2][col + 2] == ex && board[row + 3][col + 3] == ex) {
                    return 10;
                }
                if (board[row][col] == oh && board[row + 1][col + 1] == oh && board[row + 2][col + 2] == oh && board[row + 3][col + 3] == oh) {
                    return -10;
                }
            }
        }
        return 0; //tie
    }

    static Move findBestMove(char board[][]) {
        Move bestMove = new Move();
        int bestVal = -1000;

        for (int col = 0; col < 7; col++) {
            for (int row = board.length - 1; row >= 0; row--) {
                if (board[row][col] == '_') {
                    board[row][col] = ex;
                    int moveVal = minimax(board, 0, false, -10000, 10000);
                    board[row][col] = '_';

                    if (moveVal > bestVal) {
                        bestMove.row = row;
                        bestMove.col = col;
                        bestVal = moveVal;
                    }
                    break;
                }
            }
        }
        return bestMove;
    }

    static void movePiece(char[][] board, int col, char piece) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col] == '_') {
                board[row][col] = piece;
                break;
            }
        }
    }

    static void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == '_') {
                    System.out.print(board[i][j] + " : ");
                } else if (board[i][j] == ex) {
                    System.out.print(COMPUTER_PIECE + " : ");
                } else {
                    System.out.print(PLAYER_PIECE + " : ");
                }
            }
            System.out.println();
            System.out.println("...........................");
        }
        System.out.println();
    }
}