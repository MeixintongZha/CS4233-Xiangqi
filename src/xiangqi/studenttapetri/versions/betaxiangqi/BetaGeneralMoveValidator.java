package xiangqi.studenttapetri.versions.betaxiangqi;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.XiangqiCoordinateImpl;
import xiangqi.studenttapetri.common.movement.MoveValidator;

/**
 * MoveValidator implementation for validating General moves in Beta Xiangqi.
 * 
 * @author Tim Petri
 * @version Feb 8, 2017
 */
public class BetaGeneralMoveValidator implements MoveValidator
{

	public BetaGeneralMoveValidator() {}

	/* 
	 * @see xiangqi.studenttapetri.common.MoveValidator#isValid(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public boolean isValid(XiangqiBoard board, XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		final XiangqiColor ownColor = piece.getColor();
		
		// cannot capture own piece
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
		
		// can only move in palace
		if (destination.getFile() == 1 || destination.getFile() == 5) return false;
		
		// test for flying general case
		if (destination.getRank() == 5 && board.getPieceAt(destination, ownColor).getPieceType() == XiangqiPieceType.GENERAL
				 && source.getFile() == destination.getFile()) {
			
			// check that there is no piece in between source and destination
			for (int i = 2; i < 5; i++) {
				if (board.getPieceAt(XiangqiCoordinateImpl.makeCoordinate(i, source.getFile()), 
						piece.getColor()).getPieceType() != XiangqiPieceType.NONE)
					return false;
			}
			
			return true;
		}		

		
		return (destination.getRank() == 1 && Math.abs(destination.getFile() - source.getFile()) == 1);
	}

}
