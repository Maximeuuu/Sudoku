package sudoku.view;
import sudoku.Controller;

import javax.swing.*;
import java.awt.event.*;

public class MenuBarSudoku extends JMenuBar implements ActionListener
{
	private JMenu sudokuMenu;
	private Controller ctrl;

	public MenuBarSudoku( Controller ctrl )
	{
		super(); // l'ensemble du menu
		this.sudokuMenu = null;
		this.ctrl = ctrl;

		final int TYPE = 0;
		final int NAME = 1;
		final int CHAR = 2;
		final int KEYS = 3;

		String[][] barModel = MenuBarSudoku.getBarModel();
		String hotkey;

		for( int cntRow=0; cntRow < barModel.length; cntRow++ )
		{
			if( barModel[cntRow][TYPE].equals( "M" ) )
			{
				this.sudokuMenu = this.createMenu( barModel[cntRow][NAME], barModel[cntRow][CHAR] );
				this.add( this.sudokuMenu );
			}

			if( barModel[cntRow][TYPE].equals( "I" ) )
			{
				if( barModel[cntRow].length == KEYS+1 ){ hotkey = barModel[cntRow][KEYS]; }
				else{ hotkey = null; }
				this.sudokuMenu.add( this.createMenui( barModel[cntRow][NAME], barModel[cntRow][CHAR], hotkey ) );
			}

			if( barModel[cntRow][TYPE].equals( "S" ) )
			{
				sudokuMenu.addSeparator();
			}
		}
	}

	/*METHODES - Creation*/
	public JMenu createMenu( String name, String mnemo )
	{
		JMenu menuTmp = new JMenu( name );
		menuTmp.setMnemonic( mnemo.charAt(0) );
		return menuTmp;
	}

	public JMenuItem createMenui( String name, String mnemo, String hotkey )
	{
		JMenuItem menui = new JMenuItem( name );
		menui.setMnemonic( mnemo.charAt(0) );

		if( hotkey != null )
		{
			menui.setAccelerator( getEquivalentKeyStroke(hotkey) );
		}

		menui.addActionListener ( this );
		return menui;
	}

	/*METHODE - listener*/
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() instanceof JMenuItem )
		{
			String name = ((JMenuItem) e.getSource()).getText();

			switch( name )
			{
				case "Quitter" 		-> this.ctrl.closeFrame();
				case "Rapide" 		-> this.ctrl.newGame( 0 );
				case "Facile" 		-> this.ctrl.newGame( 1 );
				case "Moyen" 		-> this.ctrl.newGame( 2 );
				case "Difficile" 	-> this.ctrl.newGame( 3 );
				case "Recommencer" 	-> this.ctrl.clearGrid();
			}
		}
	}

	/*METHODE - utilitaire*/
	public static KeyStroke getEquivalentKeyStroke( String hotkey )
	{
		String[] setTmp = hotkey.split("\\+");
		String sTmp="";

		for( int cnt=0; cnt<setTmp.length-1; cnt++ )
		{
			sTmp += setTmp[cnt].toLowerCase() + " ";
		}
		sTmp += setTmp[ setTmp.length-1 ];

		return KeyStroke.getKeyStroke( sTmp );
	}

	/*FORMAT DE LA BARRE*/
	public static String[][] getBarModel()
	{
		return new String[][] {
			{ "M", "Sudoku",           "P"                 },
			{ "I", "Sauvegarder",      "S", "CTRL+S"       },
			{ "I", "Charger" ,         "C", "CTRL+SHIFT+S" },
			{ "S"                                          },
			{ "I", "Statistiques",     "T"                 },
			{ "S"                                          },
			{ "I", "Quitter",          "Q", "ALT+F4"       },

			{ "M", "Difficulté",       "N", "CTRL+N"       },
			{ "I", "Rapide",           "R"                 },
			{ "I", "Facile",           "F"                 },
			{ "I", "Moyen",            "M"                 },
			{ "I", "Difficile",        "D",                },

			{ "M", "Partie",           "G"                 },
			{ "I", "Recommencer",      "R", "CTRL+Y"       },
			{ "I", "Indice",           "I"                 }
								};
	}

}
