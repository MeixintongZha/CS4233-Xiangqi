/**
 * 
 */
package xiangqi.studenttapetri.versions.betaxiangqi;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.movement.MoveValidator;

/**
 * MoveValidator implementation for validating Soldier moves in Beta Xiangqi.
 * 
 * @author Tim Petri
 * @version Feb 7, 2017
 */
public class BetaSoldierMoveValidator implements MoveValidator
{

	public BetaSoldierMoveValidator() {}
	
	/* 
	 * @see xiangqi.studenttapetri.common.MoveValidator#isValid(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public boolean isValid(XiangqiBoard board, XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{

		final XiangqiColor ownColor = piece.getColor();
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
		
		// soldier is only allowed to move one step forward
		return (source.getClass() == destination.getClass() &&
				source.getRank() == destination.getRank()-1);
		
	}
	

	
}
