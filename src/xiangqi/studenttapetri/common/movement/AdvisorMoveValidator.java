package xiangqi.studenttapetri.common.movement;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenttapetri.common.XiangqiBoard;

/**
 * This class contains the logic used for verifying that an Advisor piece moves correctly.
 * 
 * @author Tim Petri
 * @version Feb 20, 2017
 */
public class AdvisorMoveValidator implements MoveValidator
{
	
	private XiangqiBoard board;

	/* 
	 * @see xiangqi.studenttapetri.common.MoveValidator#isValid(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiPiece)
	 */
	@Override
	public boolean isValid(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		final XiangqiColor ownColor = piece.getColor();
		
		// may not capture own colored piece
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
		
		final int sourceRank = source.getRank();
		final int sourceFile = source.getFile();
		final int destRank = destination.getRank();
		final int destFile = destination.getFile();
		
		// may not leave palace
		if (destRank > 3 || destFile < 4 || destFile > 6) {
			return false;
		}
		
		final int dr = 0, df = 1;
		final int dirs[][] = new int[][] {{1,-1}, {1,1}, {-1,1}, {-1,-1}};
		
		// loop through diagonals of source and see if they match destinations
		for (int i = 0; i < 4; i++) {
			if ( (sourceRank + dirs[i][dr] == destRank) && 
					(sourceFile + dirs[i][df] == destFile)) 
				return true;
		}
			
		return false;
	}
	
	public AdvisorMoveValidator(XiangqiBoard board) 
	{
		this.board = board;
	}

}
