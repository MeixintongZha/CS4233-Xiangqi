package xiangqi.studenttapetri.common;

import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;


// TODO: Write comments
public interface MoveValidator
{

	/*
	 * This method returns true if a move between source and destination is considered
	 * valid based on the implementation.
	 */
	boolean isValid(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece);

}
