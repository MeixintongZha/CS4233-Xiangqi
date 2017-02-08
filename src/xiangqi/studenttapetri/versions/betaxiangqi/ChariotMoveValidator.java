package xiangqi.studenttapetri.versions.betaxiangqi;

import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.MoveValidator;
import xiangqi.studenttapetri.common.XiangqiCoordinateImpl;

public class ChariotMoveValidator implements MoveValidator
{
	// context
	private BetaXiangqiBoard board;
	
	public ChariotMoveValidator(BetaXiangqiBoard board)
	{
		this.board = board;
	}
	
	@Override
	public boolean isValid(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		
		
		
		final int sourceRank = source.getRank();
		final int sourceFile = source.getFile();
		final int destRank = destination.getRank();
		final int destFile = destination.getFile();
		
//		System.out.println("Source: " + sourceRank + ", " + sourceFile);
//		System.out.println("Destin: " + destRank + ", " + destFile);
		
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
			
			// System.out.println("walking from " + smallerRank + " to " + largerRank);
			
			// check that there is no piece in between source and destination
			for (int i = smallerRank + 1; i < largerRank; i++) {
				if (board.getPieceAt(XiangqiCoordinateImpl.makeCoordinate(i, sourceRank), 
						piece.getColor()).getPieceType() != XiangqiPieceType.NONE)
					return false;
			}
			
			return true;
		}
		
		// diagonal/irregular movement
		return false;
	}
	

}
