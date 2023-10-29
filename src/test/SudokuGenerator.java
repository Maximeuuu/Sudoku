public class SudokuGenerator {
    private static final int SIZE = 9;
    private static final int EMPTY = 0;
    
    public static void main(String[] args) {
        int[][] sudoku = new int[SIZE][SIZE];
        if (generateSudoku(sudoku)) {
            printSudoku(sudoku);
        } else {
            System.out.println("Impossible de générer une grille de Sudoku.");
        }
    }

    // Génère une grille de Sudoku complète
    public static boolean generateSudoku(int[][] grid) {
        int row = 0;
        int col = 0;
        boolean isEmpty = true;

        // Trouver la prochaine case vide
        for (row = 0; row < SIZE; row++) {
            for (col = 0; col < SIZE; col++) {
                if (grid[row][col] == EMPTY) {
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        // Si aucune case n'est vide, la grille est complète
        if (isEmpty) {
            return true;
        }

        // Essayer de remplir la case vide avec les chiffres de 1 à 9
        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num;

                // Récursivement, essayer de remplir le reste de la grille
                if (generateSudoku(grid)) {
                    return true;
                }

                // Si la tentative actuelle ne mène pas à une solution, annuler
                grid[row][col] = EMPTY;
            }
        }

        // Aucune valeur possible pour cette case
        return false;
    }

    // Vérifie si un chiffre peut être placé dans une case donnée
    public static boolean isSafe(int[][] grid, int row, int col, int num) {
        // Vérifier la ligne et la colonne
        for (int x = 0; x < SIZE; x++) {
            if (grid[row][x] == num || grid[x][col] == num) {
                return false;
            }
        }

        // Vérifier la région 3x3
        int regionRow = row - row % 3;
        int regionCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[regionRow + i][regionCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // Affiche la grille de Sudoku
    public static void printSudoku(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }
}

