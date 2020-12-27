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

        String[][] board = createBoard(i+1);
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
                        System.out.println("Enter a row(1 to " + (i) + "): ");
                        row = scanner.nextInt();
                        System.out.println("Enter a column(1 to " + (i) + "): ");
                        column = scanner.nextInt();

                        if (row < 1 || row > i || column < 1 || column > i) {
                            System.out.println("Your row or column doesn't exist.");
                        } else if (!board[row][column].equals("-")) {
                            System.out.println("Row or column already use.");
                        } else {
                            board[row][column] = String.valueOf(symbol1);

                            int checkedScore = checkScore(board, column, row, String.valueOf(symbol1), playerScore);
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
                    row = random.nextInt(i+1);
                    column = random.nextInt(i+1);
                    while (!board[row][column].equals("-")) {
                        row = random.nextInt(i+1);
                        column = random.nextInt(i+1);
                    }
                    board[row][column] = String.valueOf(symbol2);
                    int checkedScore = checkScore(board, column, row, String.valueOf(symbol2), computerScore);
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
            System.out.println(player1+ " score: " + playerScore + ", Computer Score" + computerScore);
        }
    }

    private static void boardBeforePlay(String[][] board) {
        System.out.println("*********BOARD*************");
        for (String[] string : board) {
            for (int k = 0; k < board.length; k++) {
                System.out.print(string[k]);
            }
            System.out.println();
        }
    }

    private static int checkScore(String[][] board, int column, int row, String symbol, int score) {
        if (symbol.equals("S")) {
            if (board.length - column > 2 && board[row][column + 2].equals("S") && board[row][column + 1].equals("O")) {
                score++;
            }
            if (column >= 3 && board[row][column - 2].equals("S") && board[row][column - 1].equals("O")) {
                score++;
            }
            if (board.length - row > 2 && board[row + 2][column].equals("S") && board[row + 1][column].equals("O")) {
                score++;
            }
            if (row >= 3 && board[row - 2][column].equals("S") && board[row - 1][column].equals("O")) {
                score++;
            }
            if (board.length - column > 2 && row >= 3 && board[row - 1][column + 1].equals("O") && board[row - 2][column + 2].equals("S")) {
                score++;
            }
            if (column >= 3 && row >= 3 && board[row - 1][column - 1].equals("O") && board[row - 2][column - 2].equals("S")) {
                score++;
            }
            if (board.length - column > 2 && board.length - row > 2 && board[row + 1][column + 1].equals("O") && board[row + 2][column + 2].equals("S")) {
                score++;
            }
            if (board.length - row > 2 && column >= 3 && board[row + 1][column - 1].equals("O") && board[row + 2][column - 2].equals("S")) {
                score++;
            }
        } else if (symbol.equals("O")) {
            if (column >= 2 && board.length - column > 1 && board[row][column - 1].equals("S") && board[row][column + 1].equals("S")) {
                score++;
            }
            if (row >= 2 && board.length - row > 1 && board[row - 1][column].equals("S") && board[row + 1][column].equals("S")) {
                score++;
            }
            if (row >= 2 && column >= 2 && board.length - column > 1 && board.length - row > 1) {
                if (board[row - 1][column - 1].equals("S") && board[row + 1][column + 1].equals("S")) {
                    score++;
                }
                if (board[row - 1][column + 1].equals("S") && board[row + 1][column - 1].equals("S")) {
                    score++;
                }
            }
        }
        return score;
    }

    private static boolean checkBoard(String[][] board) {
        for (String[] string : board) {
            for (int k = 0; k < board.length; k++) {
                if (string[k].equals("-")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String[][] createBoard(int i) {
        String[][] board = new String[i][i];
        for (int j = 0; j < board.length; j++) {
            for (int k = 0; k < board.length; k++) {
                if (j==0){
                    board[0][k]= String.valueOf(k);
                }
                else if (k==0){
                    board[j][0]= String.valueOf(j);
                }
                else {
                    board[j][k] = "-";
                }
            }
        }
        return board;
    }

    private static void drawBoard(String[][] board, String playerName, int playerScore, int computerScore) {
        System.out.println("*********LAST SITUATION*************");
        System.out.println(playerName + " Score: " + playerScore);
        System.out.println("Computer Score: " + computerScore);
        for (String[] chars : board) {
            for (int k = 0; k < board.length; k++) {
                System.out.print(chars[k]);
            }
            System.out.println();
        }
    }
}