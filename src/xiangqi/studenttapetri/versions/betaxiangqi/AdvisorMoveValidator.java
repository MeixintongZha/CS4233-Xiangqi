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
 * @version 10:29:51 PM
 */
public class AdvisorMoveValidator implements MoveValidator
{
	
	private BetaXiangqiBoard board;

	/* 
	 * @see xiangqi.studenttapetri.common.MoveValidator#isValid(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiPiece)
	 */
	@Override
	public boolean isValid(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		
		final XiangqiColor ownColor = piece.getColor();
		
		// cannot capture own piece
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
		
		final int sourceRank = source.getRank();
		final int sourceFile = source.getFile();
		final int destRank = destination.getRank();
		final int destFile = destination.getFile();
		
		final int dr = 0;
		final int df = 1;
		final int dirs[][] = new int[][] {{1,-1}, {1,1}, {-1,1}, {-1,1}};
		
		// loop through diagonals of source and see if they match destinations
		for (int i = 0; i < 4; i++)
			if ( (sourceRank + dirs[i][dr] == destRank) && 
					(sourceFile + dirs[i][df] == destFile)) 
				return true;
		
		return false;
	}
	
	public AdvisorMoveValidator(BetaXiangqiBoard board) 
	{
		this.board = board;
	}

}
