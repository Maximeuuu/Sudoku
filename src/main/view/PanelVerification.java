package sudoku.view;
import sudoku.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.*;

public class PanelVerification extends JPanel implements ActionListener
{
	private Controller ctrl;
	private JLabel lblTimer;
	private JLabel lblInfo;
	private JButton btnVerif;
	private int countDown;

	public PanelVerification( Controller ctrl )
	{
		this.ctrl = ctrl;
		this.countDown = 0;

		this.setLayout( new GridLayout(1,3) );
		this.setBorder(new EmptyBorder(10, 10, 10, 10));

		//Creation des composants
		this.lblTimer = new JLabel( "Temps : " );
		this.lblInfo = new JLabel( "" );
		this.btnVerif = new JButton( "Valider" );

		//Placement des composants
		this.add( this.lblTimer );
		this.add( this.lblInfo );
		this.add( this.btnVerif );

		//Activation des composants
		this.btnVerif.addActionListener( this );

		// Créez un Timer pour rafraîchir le timer chaque seconde
        Timer timer = new Timer(1000, new ActionListener()
		{
            @Override
            public void actionPerformed(ActionEvent e)
			{
                PanelVerification.this.updateTimer(); // Met à jour le timer à chaque déclenchement
				PanelVerification.this.updateInfo();
            }
        });
        timer.start(); // Démarre le timer
	}

	public void updateInfo()
	{
		if( this.countDown > 0 )
		{
			this.countDown--;
		}
		else
		{
			this.lblInfo.setText( "" );
		}
	}

	public void resetCountDownInfo()
	{
		this.countDown = 0;
	}

	public void updateTimer()
	{
		this.ctrl.refreshTimer();
		this.lblTimer.setText( "Temps : "+this.ctrl.getCurrentTime()+"s" );
	}

	public void actionPerformed ( ActionEvent e)
	{
		if( e.getSource() == this.btnVerif )
		{
			if( this.ctrl.sudokuIsValid() )
			{
				this.countDown = 10;
				this.lblInfo.setText( "Partie terminée !" );
				this.ctrl.stopGame();
			}
			else
			{
				this.countDown = 4;
				this.lblInfo.setText( "Solution non valide." );
			}

		}
	}
}
