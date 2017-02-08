/**
 * 
 */
package xiangqi.studenttapetri.versions.alphaxiangqi;

import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.XiangqiPieceImpl;

/**
 * Implementation of the Alpha version of Xiangqi.
 * @author Tim Petri
 * @version Feb 4, 2017
 *
 */
public class AlphaXiangqiGame implements XiangqiGame
{
	
	private int moveCount;
	private String lastMoveMessage;
	
	
	public AlphaXiangqiGame()
	{
		moveCount = 0;
		lastMoveMessage = "";
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#makeMove(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public MoveResult makeMove(XiangqiCoordinate source, XiangqiCoordinate destination)
	{

		if (source.getRank() != 1) {
			lastMoveMessage = "Invalid source for move.";
			return MoveResult.ILLEGAL;	
		}
		else if (destination.getRank() != 1) {
			lastMoveMessage = "Invalid destination for move.";
			return MoveResult.ILLEGAL;
		}
		
		return moveCount++ == 0 ? MoveResult.OK : MoveResult.RED_WINS;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#getMoveMessage()
	 */
	@Override
	public String getMoveMessage()
	{
		return lastMoveMessage;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#getPieceAt(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiColor)
	 */
	@Override
	public XiangqiPiece getPieceAt(XiangqiCoordinate where, XiangqiColor aspect)
	{
		return XiangqiPieceImpl.makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE);
	}
	

}
