package xiangqi.studenttapetri.versions.gammaxiangqi;

import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.movement.MoveValidator;

// used for development
public class AlwaysValidMoveValidator implements MoveValidator
{
	
	public AlwaysValidMoveValidator() {}

	@Override
	public boolean isValid(XiangqiBoard board, XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		return true;
	}

}
