package sudoku.model;

import java.util.Random;
import java.util.Timer;

public class Sudoku
{
	private static final int SIZE = 9;
	private static final int SUM = ( SIZE*(SIZE+1) )/2;
	private static final Value EMPTY = new Value(0, false);

	private long startTime;
	private long currentTime;
	private boolean isPlaying;

	private int difficulty;

	private Value[][] grid;

	public static void main( String[] agrs )
	{
		Random random = new Random();

		Sudoku s = new Sudoku( 1 );
		s.fillRandomSudoku();
		String init = s.toString();
		System.out.println( init );
		System.out.println( s.isValidSolution() );

		s.removeNumbers();
		System.out.println( s.toString() );
		System.out.println( s.isValidSolution() );

		s.solveSudoku();
		String end = s.toString();
		System.out.println( end );
		System.out.println( s.isValidSolution() );
		System.out.println( "test : " + end.equals(init) );

	}

	public Sudoku( int difficulty )
	{
		this.grid = new Value[SIZE][SIZE];

		for( int row = 0; row < SIZE; row++ )
		{
			for( int col = 0; col < SIZE; col++ )
			{
				this.grid[row][col] = new Value(0, false);
			}
		}

		this.difficulty = difficulty;
	}

	public void clearGrid()
	{
		for( int row=0; row<SIZE; row++ )
		{
			for( int col=0; col<SIZE; col++ )
			{
				if( this.grid[row][col].isEditable() )
				{
					this.grid[row][col].setDigit( 0 );
				}
			}
		}
	}

	public boolean startTimer()
	{
		this.isPlaying = true;
		this.startTime = System.currentTimeMillis();
		return true;
	}

	public boolean stopTimer()
	{
		this.isPlaying = false;
		return false;
	}

	public boolean refreshTimer()
	{
		if( this.isPlaying )
		{
			this.currentTime = System.currentTimeMillis() - this.startTime;
		}
		return this.isPlaying;
	}

	public long getCurrentTime()
	{
		return this.currentTime/1000;
	}

	public final int getSize()
	{
	    return Sudoku.SIZE;
	}

	public final Value getValue( int row, int col )
	{
		return this.grid[row][col];
	}

	public final int getDigit( int row, int col )
	{
		return this.grid[row][col].getDigit();
	}

	public void setValue( int row, int col, int value )
	{
		this.grid[row][col].setDigit(value);
	}

	public void fillRandomSudoku()
	{
		this.fillDiagonalRegions();
		this.solveSudoku(); // Remplir complètement la grille
	}

	// Remplit les régions diagonales de la grille (3x3)
	public void fillDiagonalRegions()
	{
		Random random = new Random();
		int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		Sudoku.shuffleArray(values, random);

		for (int i = 0; i < SIZE; i += 3)
		{
			this.fillRegion(i, i, values);
		}
	}

	// Remplit une région 3x3 de la grille de manière aléatoire
	public void fillRegion( int startRow, int startCol, int[] values )
	{
		Random random = new Random();
		Sudoku.shuffleArray(values, random);
		int index = 0;
		for (int i = startRow; i < startRow + 3; i++)
		{
			for (int j = startCol; j < startCol + 3; j++)
			{
				this.grid[i][j].setDigit(values[index++]);
			}
		}
	}

	public final boolean isValidSolution()
	{
		for( int row = 0; row < SIZE; row++ )
		{
			int colValue=0;
			int rowValue=0;
			for( int col = 0; col < SIZE; col++ )
			{
				rowValue += this.grid[row][col].getDigit();
				colValue += this.grid[col][row].getDigit();
			}

			if( rowValue != SUM ){ return false; }
			if( colValue != SUM ){ return false; }

		}
		return true;
	}

	// Résout la grille de Sudoku complètement
	public boolean solveSudoku()
	{
		for( int row = 0; row < SIZE; row++ )
		{
			for( int col = 0; col < SIZE; col++ )
			{
				if( this.grid[row][col].equals(EMPTY) )
				{
					for( int num = 1; num <= SIZE; num++ )
					{
						if( Sudoku.isValidMove( this.grid, row, col, num ) )
						{
							this.grid[row][col].setDigit(num);
							if( this.solveSudoku() )
							{
								return true;
							}
							this.grid[row][col].setDigit(0); //A CHANGER ?
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	// Vérifie si un chiffre peut être placé dans une case donnée
	public static boolean isValidMove( Value[][] grid, int row, int col, int value )
	{
		return !Sudoku.usedInRow(grid, row, value) && !Sudoku.usedInCol(grid, col, value) && !Sudoku.usedInRegion(grid, row - row % 3, col - col % 3, value);
	}

	// Vérifie si un chiffre est déjà utilisé dans une ligne donnée
	public static boolean usedInRow( Value[][] grid, int row, int value )
	{
		for( int col = 0; col < SIZE; col++ )
		{
			if( grid[row][col].equals( new Value(value) ) )
			{
				return true;
			}
		}
		return false;
	}

	// Vérifie si un chiffre est déjà utilisé dans une colonne donnée
	public static boolean usedInCol( Value[][] grid, int col, int value )
	{
		for( int row = 0; row < SIZE; row++ )
		{
			if( grid[row][col].equals( new Value(value) ) )
			{
				return true;
			}
		}
		return false;
	}

	// Vérifie si un chiffre est déjà utilisé dans une région 3x3 donnée
	public static boolean usedInRegion( Value[][] grid, int startRow, int startCol, int value )
	{
		for( int row = 0; row < 3; row++ )
		{
			for( int col = 0; col < 3; col++ )
			{
				if( grid[row + startRow][col + startCol].equals( new Value(value) ) )
				{
					return true;
				}
			}
		}
		return false;
	}

	// Supprime un nombre donné de chiffres de la grille
	public void removeNumbers()
	{
		Random random = new Random();
		int count=1;

		switch( this.difficulty )
		{
			case 0 -> count = random.nextInt(5) + 5;
			case 1 -> count = random.nextInt(30) + 15; // Supprimer certains chiffres aléatoirement
			case 2 -> count = random.nextInt(15) + 30; // En test
			case 3 -> count = random.nextInt(5) + 45; // Peut ne pas marcher car plusieurs soltions possibles
		}

		while( count > 0 )
		{
			int row = random.nextInt(SIZE);
			int col = random.nextInt(SIZE);
			if( !this.grid[row][col].equals(EMPTY) )
			{
				this.grid[row][col] = new Value(0, true); //A CHANGER ?
				count--;
			}
		}
	}

	// Mélange un tableau d'entiers de manière aléatoire
	public static void shuffleArray(int[] arr, Random random)
	{
		for (int i = arr.length - 1; i > 0; i--)
		{
			int index = random.nextInt(i + 1);
			int temp = arr[index];
			arr[index] = arr[i];
			arr[i] = temp;
		}
	}

	// Affiche la grille de Sudoku
	public String toString()
	{
		String s="";

		for( int row = 0; row < SIZE; row++ )
		{
			for( int col = 0; col < SIZE; col++ )
			{
				s += this.getDigit(row, col) + " ";
			}
			s+="\n";
		}
		return s;
	}
}
