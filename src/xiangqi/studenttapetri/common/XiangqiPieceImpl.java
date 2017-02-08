/**
 * 
 */
package xiangqi.studenttapetri.common;

import xiangqi.common.XiangqiColor;
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
	
	public static XiangqiPiece makePiece(XiangqiPieceType pieceType, XiangqiColor color)
	{
		return new XiangqiPieceImpl(pieceType, color);
	}
	
	private XiangqiPieceImpl(XiangqiPieceType pieceType, XiangqiColor color)
	{
		this.pieceType = pieceType;
		this.color = color;
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

}
