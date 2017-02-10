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
 * 
 * The piece holds its type and color, as well as the move validator
 * used to verify any moves the piece is used in.
 * 
 * @author Tim Petri
 * @version Feb 6, 2017
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
	
	// private constructor
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
	
	/**
	 * Returns true if the move specified by the given source and destination is valid for this piece, 
	 * according to the rules specified by its move validator.
	 * 
	 * @param source
	 * @param destination
	 * @return whether the move is valid
	 */
	public boolean isValidMove(XiangqiCoordinate source, XiangqiCoordinate destination) {
		
		return moveValidator.isValid(source, destination, this);
	}
	
	@Override
	public String toString()
	{
		return "[" + getColor() + "," + getPieceType() + "]";
	}

}
