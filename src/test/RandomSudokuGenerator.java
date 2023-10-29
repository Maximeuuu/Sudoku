import java.util.Random;

public class RandomSudokuGenerator {
    private static final int SIZE = 9;
    private static final int EMPTY = 0;

    public static void main(String[] args) {
        int[][] sudoku = generateRandomSudoku();
        printSudoku(sudoku);
    }

    // Génère une grille de Sudoku valide de manière aléatoire
    public static int[][] generateRandomSudoku() {
        int[][] sudoku = new int[SIZE][SIZE];
        fillRandomSudoku(sudoku);
        return sudoku;
    }

    // Remplit la grille de Sudoku de manière aléatoire tout en respectant les règles
    public static void fillRandomSudoku(int[][] sudoku) {
        Random random = new Random();
        fillDiagonalRegions(sudoku);
        solveSudoku(sudoku); // Remplir complètement la grille
        /*removeNumbers(sudoku, random.nextInt(30) + 15); // Supprimer certains chiffres aléatoirement*/
    }

    // Remplit les régions diagonales de la grille (3x3)
    public static void fillDiagonalRegions(int[][] sudoku) {
        Random random = new Random();
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(values, random);

        for (int i = 0; i < SIZE; i += 3) {
            fillRegion(sudoku, i, i, values);
        }
    }

    // Remplit une région 3x3 de la grille de manière aléatoire
    public static void fillRegion(int[][] sudoku, int startRow, int startCol, int[] values) {
        Random random = new Random();
        shuffleArray(values, random);
        int index = 0;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                sudoku[i][j] = values[index++];
            }
        }
    }

    // Résout la grille de Sudoku complètement
    public static boolean solveSudoku(int[][] sudoku) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (sudoku[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValidMove(sudoku, row, col, num)) {
                            sudoku[row][col] = num;
                            if (solveSudoku(sudoku)) {
                                return true;
                            }
                            sudoku[row][col] = EMPTY;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Vérifie si un chiffre peut être placé dans une case donnée
    public static boolean isValidMove(int[][] sudoku, int row, int col, int num) {
        return !usedInRow(sudoku, row, num) && !usedInCol(sudoku, col, num) && !usedInRegion(sudoku, row - row % 3, col - col % 3, num);
    }

    // Vérifie si un chiffre est déjà utilisé dans une ligne donnée
    public static boolean usedInRow(int[][] sudoku, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (sudoku[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    // Vérifie si un chiffre est déjà utilisé dans une colonne donnée
    public static boolean usedInCol(int[][] sudoku, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (sudoku[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    // Vérifie si un chiffre est déjà utilisé dans une région 3x3 donnée
    public static boolean usedInRegion(int[][] sudoku, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (sudoku[row + startRow][col + startCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    // Supprime un nombre donné de chiffres de la grille
    public static void removeNumbers(int[][] sudoku, int count) {
        Random random = new Random();
        while (count > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (sudoku[row][col] != EMPTY) {
                sudoku[row][col] = EMPTY;
                count--;
            }
        }
    }

    // Mélange un tableau d'entiers de manière aléatoire
    public static void shuffleArray(int[] arr, Random random) {
        for (int i = arr.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }

    // Affiche la grille de Sudoku
    public static void printSudoku(int[][] sudoku) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(sudoku[row][col] + " ");
            }
            System.out.println();
        }
    }
}

