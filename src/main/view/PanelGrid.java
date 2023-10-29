package sudoku.view;
import sudoku.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.Color;

public class PanelGrid extends JPanel implements MouseListener
{
	private static Color GREY  = new Color(240, 240, 240);
	private static Color WHITE = new Color(255, 255, 255);

	private Controller ctrl;
	private int size;
	private int nbParts;
	private JButton[][] buttons;

	public PanelGrid( Controller ctrl )
	{
		this.ctrl = ctrl;
		this.size = ctrl.getSize();
		this.nbParts = (int)Math.sqrt(this.size);

		this.buttons = new JButton[this.size][this.size];

		this.setLayout( new GridLayout(this.nbParts, this.nbParts, 5, 5) );
		this.setBorder(new EmptyBorder(10, 10, 10, 10));

		this.generateButtons();
	}

	public void generateButtons()
	{
	    for( int cptRegX=0; cptRegX<this.nbParts; cptRegX++ )
	    {
	        for( int cptRegY=0; cptRegY<this.nbParts; cptRegY++ )
    	    {
                JPanel panelTemp = new JPanel( new GridLayout(this.nbParts, this.nbParts) );

	            for( int cptX=0; cptX<this.nbParts; cptX++ )
		        {
					int posX = cptRegX*this.nbParts+cptX;
			        for( int cptY=0; cptY<this.nbParts; cptY++ )
			        {
						int posY = cptRegY*this.nbParts+cptY;
				        int value = this.ctrl.getNumber(posX,posY);

				        if( value==0 )
				        {
					        this.buttons[posX][posY] = new JButton( "" );
							this.buttons[posX][posY].setBackground( PanelGrid.WHITE );
				        }
				        else
				        {
					        this.buttons[posX][posY] = new JButton( ""+value );
							this.buttons[posX][posY].setBackground( PanelGrid.WHITE );
							this.buttons[posX][posY].setEnabled(false);
				        }

				        panelTemp.add( this.buttons[posX][posY] );
				        this.buttons[posX][posY].addMouseListener( this );
			        }

		        }
		        this.add( panelTemp );
		    }
	    }
	}

	public void majButtons()
	{
		for( int cptX=0; cptX<this.size; cptX++ )
		{
			for( int cptY=0; cptY<this.size; cptY++ )
			{
				int value = this.ctrl.getNumber(cptX,cptY);

				if( value==0 )
				{
					this.buttons[cptX][cptY].setText( "" );
					this.buttons[cptX][cptY].setBackground( PanelGrid.WHITE );
					this.buttons[cptX][cptY].setEnabled(true);
				}
				else
				{
					this.buttons[cptX][cptY].setText( ""+value );
					this.buttons[cptX][cptY].setBackground( PanelGrid.WHITE );
					this.buttons[cptX][cptY].setEnabled(false);
				}
			}
		}
	}

	public void mouseExited( MouseEvent e ){}
	public void mouseEntered( MouseEvent e ){}
	public void mouseReleased( MouseEvent e ){}
	public void mouseClicked( MouseEvent e ){}
	public void mousePressed( MouseEvent e )
	{
		for( int cptX=0; cptX<this.size; cptX++ )
		{
			for( int cptY=0; cptY<this.size; cptY++ )
			{
				if( e.getSource() == this.buttons[cptX][cptY] )
				{
					if( this.ctrl.isEditable(cptX,cptY) )
					{

						if( e.getButton() == MouseEvent.BUTTON1 ) //clic gauche
						{
							this.ctrl.setNumber(cptX, cptY, (this.ctrl.getNumber(cptX,cptY))%9+1);
							this.buttons[cptX][cptY].setText( ""+this.ctrl.getNumber(cptX, cptY) );
						}
						if( e.getButton() == MouseEvent.BUTTON3 ) //clic droit
						{
							this.ctrl.setNumber(cptX, cptY, 0);
							this.buttons[cptX][cptY].setText( "" );
						}

					}

				}
			}
		}
	}
}
