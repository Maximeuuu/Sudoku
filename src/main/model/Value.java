package sudoku.model;

public class Value extends Object
{
	private int digit;
	private boolean editable;

	public Value( int digit )
	{
		this( digit, true );
	}

	public Value( int digit, boolean editable )
	{
		if( digit <= 0 || digit > 9 ){ digit = 0; }

		this.digit = digit;
		this.editable = editable;
	}

	public void setEditable( boolean bool )
	{
		this.editable = bool;
	}

	public void setDigit( int digit )
	{
		if( digit <= 0 || digit > 9 ){ digit = 0; }
		this.digit = digit;
	}

	public final boolean isEditable()
	{
		return this.editable;
	}

	public final int getDigit()
	{
		return this.digit;
	}

	public boolean equals( Value value )
	{
		return this.digit == value.digit;
	}
}
