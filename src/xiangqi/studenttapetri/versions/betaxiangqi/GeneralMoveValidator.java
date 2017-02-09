package xiangqi.studenttapetri.versions.betaxiangqi;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenttapetri.common.MoveValidator;

public class GeneralMoveValidator implements MoveValidator
{
	
	BetaXiangqiBoard board;

	public GeneralMoveValidator(BetaXiangqiBoard board)
	{
		this.board = board;
	}

	@Override
	public boolean isValid(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		final XiangqiColor ownColor = piece.getColor();
		
		// cannot capture own piece
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
		
		// can only move in palace
		if (destination.getRank() != 1 || destination.getFile() == 1 || destination.getFile() == 5) return false;
		
		// can only move one step
		return (Math.abs(destination.getFile() - source.getFile()) == 1);
	
	}

}
