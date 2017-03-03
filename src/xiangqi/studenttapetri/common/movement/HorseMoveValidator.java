package xiangqi.studenttapetri.common.movement;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.XiangqiCoordinateImpl;

/**
 * This class contains the logic used for verifying that a Horse piece moves correctly.
 * 
 * @author Tim Petri
 * @version Mar 2, 2017
 */
public class HorseMoveValidator implements MoveValidator
{

	public HorseMoveValidator() {}

	@Override
	public boolean isValid(XiangqiBoard board, XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		final XiangqiColor ownColor = piece.getColor();
		
		// may not capture own colored piece
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
		
		final int deltaRank = destination.getRank() - source.getRank();
		final int deltaFile = destination.getFile() - source.getFile();
		
		// may only move one step orthogonal and one diagonal
		if (!(Math.abs(deltaRank) == 2 && Math.abs(deltaFile) == 1 ||
				Math.abs(deltaRank) == 1 && Math.abs(deltaFile) == 2 )) return false;
		
		// may not jump over a piece
		if (Math.abs(deltaRank) == 2) { // orthogonal step is left/right
			
			int dr = (deltaRank > 0) ? 1 : -1;
			if (board.getPieceAt(XiangqiCoordinateImpl.makeCoordinate(source.getRank()+dr, source.getFile()), 
					ownColor).getPieceType() != XiangqiPieceType.NONE)
				return false;
			
		} else { // orthogonal step is up/down
			
			int df = (deltaFile > 0) ? 1 : -1;
			if (board.getPieceAt(XiangqiCoordinateImpl.makeCoordinate(source.getRank(), source.getFile() + df), 
					ownColor).getPieceType() != XiangqiPieceType.NONE)
				return false;
		}
		
		
		return true;
	}

}
