package xiangqi.studenttapetri.common.movement;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.XiangqiCoordinateImpl;

/**
 * This class contains the logic used for verifying that a Cannon piece moves correctly.
 * 
 * @author Tim Petri
 * @version Mar 2, 2017
 */
public class CannonMoveValidator implements MoveValidator
{
	
	public CannonMoveValidator() {}
	
	/* 
	 * @see xiangqi.studenttapetri.common.MoveValidator#isValid(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiPiece)
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
		
		// may only move orthogonally
		if (!(sourceRank == destRank || sourceFile == destFile)) return false;
		
		final boolean isCapturing = board.getPieceAt(destination, ownColor).getPieceType() != XiangqiPieceType.NONE;
		int numPiecesOnPath = 0;
		
		// if cannon is moving horizontally
		if (sourceRank == destRank) {
			
			int smallerFile = sourceFile < destFile ? sourceFile : destFile;
			int largerFile = sourceFile < destFile ? destFile : sourceFile;
			
			// count number of pieces between source and destination
			for (int i = smallerFile + 1; i < largerFile; i++) {
				if (board.getPieceAt(XiangqiCoordinateImpl.makeCoordinate(sourceRank, i), 
						piece.getColor()).getPieceType() != XiangqiPieceType.NONE)
					numPiecesOnPath++;
			}

		}
		// if cannon is moving vertically
		else if (sourceFile == destFile) {
			
			int smallerRank = sourceRank < destRank ? sourceRank : destRank;
			int largerRank = sourceRank < destRank ? destRank : sourceRank;
			
			// count number of pieces between source and destination
			for (int i = smallerRank + 1; i < largerRank; i++) {

				if (board.getPieceAt(XiangqiCoordinateImpl.makeCoordinate(i, sourceFile), 
						piece.getColor()).getPieceType() != XiangqiPieceType.NONE)
					numPiecesOnPath++;
			}
			
		}
		// may only capture if one piece is jumped
		if (isCapturing) return numPiecesOnPath == 1;
		

		return numPiecesOnPath == 0;
	}

}
