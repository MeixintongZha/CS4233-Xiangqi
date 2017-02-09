/**
 * 
 */
package xiangqi.studenttapetri.versions.betaxiangqi;

import xiangqi.common.XiangqiColor;
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
//		System.out.println("----");
//		board.printBoard();
		final XiangqiColor ownColor = piece.getColor();
//		System.out.println(ownColor + ": " + source.getRank() + ", " + source.getFile());
//		System.out.println(board.getPieceAt(destination, ownColor).getColor()  + ": " + destination.getRank() + ", " + destination.getFile());
		// cannot capture own piece
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
		
		// soldier is only allowed to move one step forward
		return (source.getClass() == destination.getClass() &&
				source.getRank() == destination.getRank()-1);
		
	}
	
	public SoldierMoveValidator(BetaXiangqiBoard board) 
	{
		this.board = board;
	}
	
}
