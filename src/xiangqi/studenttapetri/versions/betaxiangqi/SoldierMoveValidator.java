/**
 * 
 */
package xiangqi.studenttapetri.versions.betaxiangqi;

import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenttapetri.common.MoveValidator;

/**
 * @author timpetri
 * @version 1:42:11 PM
 */
public class SoldierMoveValidator implements MoveValidator
{
	private static BetaXiangqiBoard board;
	// TODO: need reference to itself?

	/* 
	 * @see xiangqi.studenttapetri.common.MoveValidator#isValid(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public boolean isValid(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		// soldier is only allowed to move one step forward
		return (source.getClass() == destination.getClass() &&
				source.getRank() == destination.getRank()-1);
		
	}
	
	public SoldierMoveValidator(BetaXiangqiBoard board) 
	{
		this.board = board;
	}
	
}
