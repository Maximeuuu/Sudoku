package sudoku.view;
import sudoku.Controller;

import javax.swing.*;
import java.awt.BorderLayout;

public class FrameSudoku extends JFrame
{
	private Controller ctrl;
	private PanelVerification panelVerif;
	private PanelGrid panelGrid;
	private MenuBarSudoku menuBar;

	public FrameSudoku( Controller ctrl )
	{
		this.setTitle   ( "Sudoku");
		this.setSize    ( 660,660 );
		this.setLocation( 100, 100 );
		this.setLayout  ( new BorderLayout() );

		this.ctrl = ctrl;

		this.menuBar    = new MenuBarSudoku( this.ctrl );
		this.panelVerif = new PanelVerification( this.ctrl );
		this.panelGrid  = new PanelGrid( this.ctrl );

		this.setJMenuBar( this.menuBar );
		this.add        ( this.panelVerif, BorderLayout.NORTH );
		this.add        ( this.panelGrid, BorderLayout.CENTER );

		// Gestion de la fermeture de la fenêtre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public void majButtons(){ this.panelGrid.majButtons(); }
	public void resetInfo() { this.panelVerif.resetCountDownInfo(); }
}
