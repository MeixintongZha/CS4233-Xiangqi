package xiangqi.studenttapetri.common.movement;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.XiangqiCoordinateImpl;

/**
 * This class contains the logic used for verifying that an Elephant piece moves correctly.
 * 
 * @author Tim Petri
 * @version Feb 20, 2017
 */
public class ElephantMoveValidator implements MoveValidator
{
	
	public ElephantMoveValidator(){}

	@Override
	public boolean isValid(XiangqiBoard board, XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{

		final XiangqiColor ownColor = piece.getColor();
		
		// may not capture own colored piece
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
		
		// may not cross river
		if (destination.getRank() > 5) return false;
		
		// may only 2 steps move diagonally
		final int deltaRank = destination.getRank() - source.getRank();
		final int deltaFile = destination.getFile() - source.getFile();
		if (Math.abs(deltaRank) != 2 || Math.abs(deltaFile) != 2) return false;
		
		// no piece can block elephant
		final int dr = (deltaRank > 1) ? 1 : -1, df = (deltaFile > 1) ? 1 : -1;
		XiangqiCoordinate between = XiangqiCoordinateImpl.makeCoordinate(source.getRank() + dr, source.getFile() + df);
		if (board.getPieceAt(between, ownColor).getPieceType() != XiangqiPieceType.NONE) return false;
		
		return true;
	}

}
