package xiangqi.studenttapetri.common.movement;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenttapetri.common.XiangqiBoard;

/**
 * This class contains the logic used for verifying that a Soldier piece moves correctly.
 * 
 * @author Tim Petri
 * @version Feb 20, 2017
 */
public class SoldierMoveValidator implements MoveValidator
{
	private static XiangqiBoard board;

	public SoldierMoveValidator(XiangqiBoard board) 
	{
		this.board = board;
	}
	
	/* 
	 * @see xiangqi.studenttapetri.common.MoveValidator#isValid(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public boolean isValid(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{

		final XiangqiColor ownColor = piece.getColor();
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;

		boolean pastRiver = source.getRank() > 5;
		
		// only allowed to move one step forward before river
		if (!pastRiver)
			return (source.getFile() == destination.getFile() && destination.getRank() == (source.getRank() + 1));
		
		// only allowed to move/capture one step sideways or forward after river
		else 
			return (source.getRank() == destination.getRank() && Math.abs(destination.getFile() - source.getFile()) == 1 ||
					source.getFile() == destination.getFile() && destination.getRank() == (source.getRank() + 1));
				
	}
	

	
}
