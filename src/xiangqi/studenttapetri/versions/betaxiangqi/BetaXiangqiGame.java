/**
 * 
 */
package xiangqi.studenttapetri.versions.betaxiangqi;

import java.util.HashMap;
import java.util.Map;

import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.*;



/**
 * Implementation of the Beta version of Xiangqi.
 * 
 * This class handles the messaging of invalid moves, move counts, and overall
 * control of board.
 * @author timpetri
 *
 */
public class BetaXiangqiGame implements XiangqiGame
{
	
	private static String messageInvalidCoordinates = "The provided coordinates were invalid";
	private static String messageNoPieceAtProvidedSource = "There is no piece at the provided source coordinate";
	private static String messageNotAllowedToMovePiece = "The player can not move this piece";
	
	
	private static XiangqiColor STARTING_COLOR = XiangqiColor.RED;
	private static int MAX_MOVES = 10;
	
	private int moveCount;
	private String lastMoveMessage;
	private XiangqiColor activeColor;
	
	// TODO: change to implementation
	private BetaXiangqiBoard board;
	
	public BetaXiangqiGame()
	{
		moveCount = 0;
		lastMoveMessage = "";
		
		activeColor = STARTING_COLOR;
		board = new BetaXiangqiBoard();
		placeStartingPieces();
	}



	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#makeMove(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public MoveResult makeMove(XiangqiCoordinate source, XiangqiCoordinate destination)
	{
		// are coordinates within boounds
		if (!board.isValidCoordinate(source) || !board.isValidCoordinate(destination)) {
			setMoveMessage(messageInvalidCoordinates);
			return MoveResult.ILLEGAL;
		}
		
		// does source have a piece with
		if (board.getPieceAt(source, activeColor).getPieceType() == XiangqiPieceType.NONE) {
			setMoveMessage(messageNoPieceAtProvidedSource);
			return MoveResult.ILLEGAL;
		}
		
		// is color at source equal to playing color
		if (board.getPieceAt(source, activeColor).getColor() != activeColor) {
			setMoveMessage(messageNotAllowedToMovePiece);
			return MoveResult.ILLEGAL;
		}
		
		// is move valid (piece it self can figure this out using board, src, dest)
		
		// move piece to location (replace what ever is there)
		
		// check for game winning conditions
		
		// check for movecount
		moveCount++;
		activeColor = (activeColor == XiangqiColor.RED) ? XiangqiColor.BLACK : XiangqiColor.RED;
		
		return MoveResult.OK;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#getMoveMessage()
	 */
	@Override
	public String getMoveMessage()
	{
		return lastMoveMessage;
	}
	
	private void setMoveMessage(String msg)
	{
		lastMoveMessage = msg;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#getPieceAt(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiColor)
	 */
	@Override
	public XiangqiPiece getPieceAt(XiangqiCoordinate where, XiangqiColor aspect)
	{
		return board.getPieceAt(where, aspect);
	}
	
	// TODO: call initializeGame?
	private void placeStartingPieces()
	{
		// red pieces
		XiangqiPiece redGeneral = XiangqiPieceImpl.makePiece(XiangqiPieceType.GENERAL, XiangqiColor.RED);
		XiangqiPiece redSoldier = XiangqiPieceImpl.makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.RED);
		XiangqiPiece redAdvisorLeft = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.RED);
		XiangqiPiece redAdvisorRight = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.RED);
		XiangqiPiece redChariotLeft = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.RED);
		XiangqiPiece redChariotRight = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.RED);		
		
		// black pieces
		XiangqiPiece blackGeneral = XiangqiPieceImpl.makePiece(XiangqiPieceType.GENERAL, XiangqiColor.BLACK);
		XiangqiPiece blackSoldier = XiangqiPieceImpl.makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.BLACK);
		XiangqiPiece blackAdvisorLeft = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.BLACK);
		XiangqiPiece blackAdvisorRight = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.BLACK);
		XiangqiPiece blackChariotLeft = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.BLACK);
		XiangqiPiece blackChariotRight = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.BLACK);	
		
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 3), redGeneral, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(2, 3), redSoldier, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 2), redAdvisorLeft, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 4), redAdvisorRight, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 1), redChariotLeft, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 5), redChariotRight, XiangqiColor.RED);

		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 3), blackGeneral, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(2, 3), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 2), blackAdvisorLeft, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 4), blackAdvisorRight, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 1), blackChariotLeft, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 5), blackChariotRight, XiangqiColor.BLACK);	
	}

}
