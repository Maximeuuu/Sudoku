package sudoku;
import sudoku.model.*;
import sudoku.view.*;

import java.util.Random;

public class Controller
{
	private Sudoku metier;
	private FrameSudoku ihm;
	private boolean isRunning;

	/**
	 * Initialisation de l'IHM et de la partie metier
	 */
	public Controller()
	{
		final int DIFFICULTY = 1;
		this.newSudoku( DIFFICULTY );
		this.ihm = new FrameSudoku( this );
		this.startGame();
	}

	/**
	 * Remplacer une partie actuelle par une nouvelle
	 */
	public void newGame( int difficulty )
	{
		this.ihm.resetInfo();
		this.newSudoku( difficulty );
		this.majGrid();
		this.startGame();
	}

	/**
	 * Creer un nouveau metier Sudoku
	 */
	private void newSudoku( int difficulty )
	{
		this.metier = new Sudoku( difficulty );
		this.metier.fillRandomSudoku();
		this.metier.removeNumbers();
	}

	/**
	 * Mettre a jour l'affichage de la grille
	 */
	private void majGrid()
	{
		this.ihm.majButtons();
	}

	/**
	 * Effacer ce qui a été saisie par l'utilisateur
	 */
	public void clearGrid()
	{
		this.ihm.resetInfo();
		this.metier.clearGrid();
		this.ihm.majButtons();
		this.startGame();
	}

	/**
	 * Ferme l'IHM et arrête le jeu
	 */
	public void closeFrame()
	{
		this.stopGame();
		this.ihm.dispose();
	}

	/**
	 * Vrai si la case est modifiable
	 */
	public boolean isEditable( int row, int col ){ return ( this.isRunning && this.metier.getValue(row, col).isEditable() ); }

	/**
	 * Recupere la valeur en x, y
	 */
	public int getNumber( int row, int col ){ return this.metier.getDigit(row, col); }

	/**
	 * Recupere la taille de la grille
	 */
	public int getSize(){ return this.metier.getSize(); }

	/**
	 * Change la valeur en x, y
	 */
	public void setNumber( int row, int col, int value ){ this.metier.setValue( row, col, value ); }

	/**
	 * Vrai si la grille completee est valide
	 */
	public boolean sudokuIsValid(){ return this.metier.isValidSolution(); }

	/**
	 * Initialise un nouveau chrono
	 */
	public void startGame(){ this.isRunning = this.metier.startTimer(); }

	/**
	 * Arrete le chrono et la partie
	 */
	public void stopGame(){ this.isRunning = this.metier.stopTimer(); }

	/**
	 * Actualise le chrono (est appele a chaque secondes)
	 */
	public void refreshTimer(){ this.isRunning = this.metier.refreshTimer(); }

	/**
	 * Recupere le temps de jeu
	 */
	public long getCurrentTime(){ return this.metier.getCurrentTime(); }

	/**
	 * Main
	 */
	public static void main( String[] args )
	{
		new Controller();
	}
}
