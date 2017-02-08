/**
 * 
 */
package xiangqi.studenttapetri.common;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;

/**
 * 
 * Data structure for the Xiangqi piece.
 * @author Tim Petri
 * @version Feb 4, 2017
 *
 */
public class XiangqiPieceImpl implements XiangqiPiece
{
	
	private final XiangqiColor color;
	private final XiangqiPieceType pieceType;
	private final MoveValidator moveValidator;
	
	public static XiangqiPieceImpl makePiece(XiangqiPieceType pieceType, XiangqiColor color)
	{
		return new XiangqiPieceImpl(pieceType, color, null);
	}
	
	public static XiangqiPieceImpl makePiece(XiangqiPieceType pieceType, XiangqiColor color, MoveValidator mv)
	{
		return new XiangqiPieceImpl(pieceType, color, mv);
	}
	
	private XiangqiPieceImpl(XiangqiPieceType pieceType, XiangqiColor color, MoveValidator mv)
	{
		this.pieceType = pieceType;
		this.color = color;
		this.moveValidator = mv;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiPiece#getColor()
	 */
	@Override
	public XiangqiColor getColor()
	{
		return color;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiPiece#getPieceType()
	 */
	@Override
	public XiangqiPieceType getPieceType()
	{
		return pieceType;
	}
	
	public boolean isValidMove(XiangqiCoordinate source, XiangqiCoordinate destination) {
		
		return moveValidator.isValid(source, destination, this);
	}

}
