package org.kodluyoruz;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your name: ");
        String player1 = scanner.nextLine();
        int i;
        do {
            System.out.println("Please enter the size of the board(3 to 7): ");
            i = scanner.nextInt();
        } while (i < 3 || i > 7);

        char[][] board = createBoard(i);
        boardBeforePlay(board);

        final String symbols = "SO";
        final int N = symbols.length();
        boolean isPlayer = random.nextBoolean();
        char symbol1 = symbols.charAt(random.nextInt(N));
        char symbol2;
        if (symbol1 == 'S') {
            symbol2 = 'O';
        } else {
            symbol2 = 'S';
        }

        int playerScore = 0;
        int computerScore = 0;
        int row, column;

        while (checkBoard(board)) {

            System.out.println("*******************************");
            if (isPlayer) {
                while (true) {
                    try {
                        System.out.println(player1 + "'s turn!");
                        System.out.println("Enter a row(0 to " + (i - 1) + "): ");
                        row = scanner.nextInt();
                        System.out.println("Enter a column(0 to " + (i - 1) + "): ");
                        column = scanner.nextInt();

                        if (row < 0 || row > (i - 1) || column < 0 || column > (i - 1)) {
                            System.out.println("Your row or column doesn't exist.");
                        } else if (board[row][column] != '-') {
                            System.out.println("Row or column already use.");
                        } else {
                            board[row][column] = symbol1;

                            int checkedScore = checkScore(board, column, row, symbol1, playerScore);
                            if (checkedScore != playerScore) {
                                playerScore = checkedScore;
                                drawBoard(board, player1, playerScore, computerScore);
                            } else {
                                drawBoard(board, player1, playerScore, computerScore);
                                isPlayer = false;

                            }
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid row or column");
                    }
                    scanner.nextLine();
                    break;
                }
            } else {
                while (true) {
                    System.out.println("Computer's turn!");
                    row = random.nextInt(i);
                    column = random.nextInt(i);
                    while (board[row][column] != '-') {
                        row = random.nextInt(i);
                        column = random.nextInt(i);
                    }
                    board[row][column] = symbol2;
                    int checkedScore = checkScore(board, column, row, symbol2, computerScore);
                    if (checkedScore != computerScore) {
                        computerScore = checkedScore;
                        drawBoard(board, player1, playerScore, computerScore);
                    } else {
                        drawBoard(board, player1, playerScore, computerScore);
                        isPlayer = true;
                    }
                    break;
                }
            }
        }
        hasWon(playerScore,computerScore, player1);

    }

    private static void hasWon(int playerScore, int computerScore, String player1) {
        System.out.println("*******************************");
        if (playerScore > computerScore) {
            System.out.println(player1+ " has won!");
            System.out.println("Score: " + playerScore);
        }
        else if (computerScore > playerScore) {
            System.out.println("Computer has won!");
            System.out.println("Score: " + computerScore);
        }
        else {
            System.out.println("It's a tie!");
            System.out.println(player1+ " score: " + playerScore + "Computer Score" + computerScore);
        }
    }

    private static void boardBeforePlay(char[][] board) {
        System.out.println("*********BOARD*************");
        for (char[] chars : board) {
            for (int k = 0; k < board.length; k++) {
                System.out.print(chars[k]);
            }
            System.out.println();
        }
    }

    private static int checkScore(char[][] board, int column, int row, char symbol, int score) {
        if (symbol == 'S') {
            if (board.length - column > 2 && board[row][column + 2] == 'S' && board[row][column + 1] == 'O') {
                score++;
            }
            if (column >= 2 && board[row][column - 2] == 'S' && board[row][column - 1] == 'O') {
                score++;
            }
            if (board.length - row > 2 && board[row + 2][column] == 'S' && board[row + 1][column] == 'O') {
                score++;
            }
            if (row >= 2 && board[row - 2][column] == 'S' && board[row - 1][column] == 'O') {
                score++;
            }
            if (board.length - column > 2 && row >= 2 && board[row - 1][column + 1] == 'O' && board[row - 2][column + 2] == 'S') {
                score++;
            }
            if (column >= 2 && row >= 2 && board[row - 1][column - 1] == 'O' && board[row - 2][column - 2] == 'S') {
                score++;
            }
            if (board.length - column > 2 && board.length - row > 2 && board[row + 1][column + 1] == 'O' && board[row + 2][column + 2] == 'S') {
                score++;
            }
            if (board.length - row > 2 && column >= 2 && board[row + 1][column - 1] == 'O' && board[row + 2][column - 2] == 'S') {
                score++;
            }
        } else if (symbol == 'O') {
            if (column >= 1 && board.length - column > 1 && board[row][column - 1] == 'S' && board[row][column + 1] == 'S') {
                score++;
            }
            if (row >= 1 && board.length - row > 1 && board[row - 1][column] == 'S' && board[row + 1][column] == 'S') {
                score++;
            }
            if (row >= 1 && column >= 1 && board.length - column > 1 && board.length - row > 1) {
                if (board[row - 1][column - 1] == 'S' && board[row + 1][column + 1] == 'S') {
                    score++;
                }
                if (board[row - 1][column + 1] == 'S' && board[row + 1][column - 1] == 'S') {
                    score++;
                }
            }
        }
        return score;
    }

    private static boolean checkBoard(char[][] board) {
        for (char[] chars : board) {
            for (int k = 0; k < board.length; k++) {
                if (chars[k] == '-') {
                    return true;
                }
            }
        }
        return false;
    }

    private static char[][] createBoard(int i) {
        char[][] board = new char[i][i];
        for (int j = 0; j < board.length; j++) {
            for (int k = 0; k < board.length; k++) {
                board[j][k] = '-';
            }
        }
        return board;
    }

    private static void drawBoard(char[][] board, String playerName, int playerScore, int computerScore) {
        System.out.println("*********LAST SITUATION*************");
        System.out.println(playerName + " Score: " + playerScore);
        System.out.println("Computer Score: " + computerScore);
        for (char[] chars : board) {
            for (int k = 0; k < board.length; k++) {
                System.out.print(chars[k]);
            }
            System.out.println();
        }
    }
}