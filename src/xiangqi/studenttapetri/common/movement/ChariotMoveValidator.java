package xiangqi.studenttapetri.common.movement;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.XiangqiCoordinateImpl;

/**
 * This class contains the logic used for verifying that a Chariot piece moves correctly.
 * 
 * @author Tim Petri
 * @version Feb 19, 2017
 */
public class ChariotMoveValidator implements MoveValidator
{
	
	public ChariotMoveValidator() {}
	
	/* 
	 * @see xiangqi.studenttapetri.common.MoveValidator#isValid(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public boolean isValid(XiangqiBoard board, XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		
		final XiangqiColor ownColor = piece.getColor();
		
		// may not capture own colored piece
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
	
		final int sourceRank = source.getRank();
		final int sourceFile = source.getFile();
		final int destRank = destination.getRank();
		final int destFile = destination.getFile();

		// if chariot is moving horizontally
		if (sourceRank == destRank) {
			
			int smallerFile = sourceFile < destFile ? sourceFile : destFile;
			int largerFile = sourceFile < destFile ? destFile : sourceFile;
			
			// check that there is no piece in between source and destination
			for (int i = smallerFile + 1; i < largerFile; i++) {
				if (board.getPieceAt(XiangqiCoordinateImpl.makeCoordinate(sourceRank, i), 
						piece.getColor()).getPieceType() != XiangqiPieceType.NONE)
					return false;
			}
			
			return true;
		}
		// if chariot is moving vertically
		else if (sourceFile == destFile) {
			
			int smallerRank = sourceRank < destRank ? sourceRank : destRank;
			int largerRank = sourceRank < destRank ? destRank : sourceRank;
			
			// check that there is no piece in between source and destination
			for (int i = smallerRank + 1; i < largerRank; i++) {

				if (board.getPieceAt(XiangqiCoordinateImpl.makeCoordinate(i, sourceFile), 
						piece.getColor()).getPieceType() != XiangqiPieceType.NONE)
					return false;
			}
			
			return true;
		}
		
		// diagonal/irregular movement
		return false;
	}
	

}
