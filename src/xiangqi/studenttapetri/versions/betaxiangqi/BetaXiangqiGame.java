/**
 * 
 */
package xiangqi.studenttapetri.versions.betaxiangqi;

import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.*;



/**
 * Implementation of the Beta version of XiangqiGame.
 * 
 * @author Tim Petri
 * @version Feb 7, 2017
 *
 */
public class BetaXiangqiGame implements XiangqiGame
{
	
	private static String messageInvalidCoordinates = "The provided coordinates were invalid";
	private static String messageNoPieceAtProvidedSource = "There is no piece at the provided source coordinate";
	private static String messageNotAllowedToMovePiece = "The player can not move this piece";
	private static String messageMoveNotValidForPiece = "The given move was not valid for this piece";
	private static String messageGameWon = "The given move resulted in a win.";
	private static String messageGameDraw = "The given more resulted in a draw.";
	private static String messageGameAlreadyCompleted = "The game has already been completed.";
	
	
	private static XiangqiColor STARTING_COLOR = XiangqiColor.RED;
	private static int MAX_ROUNDS = 10;
	
	private int roundCount;
	private boolean gameCompleted;
	private String lastMoveMessage;
	private XiangqiColor activeColor;
	
	private BetaXiangqiBoard board;
	
	public BetaXiangqiGame()
	{
		gameCompleted = false;
		roundCount = 1;
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
		
		if(gameCompleted) {
			setMoveMessage(messageGameAlreadyCompleted);
			return MoveResult.ILLEGAL;
		}
		
		if (!areCoordinatesValid(source, destination)) {
			setMoveMessage(messageInvalidCoordinates);
			return MoveResult.ILLEGAL;
		}
		
		if (!sourceHasAPiece(source)) {
			setMoveMessage(messageNoPieceAtProvidedSource);
			return MoveResult.ILLEGAL;
		}
		
		if (!sourcePieceMatchesActiveColor(source)) {
			setMoveMessage(messageNotAllowedToMovePiece);
			return MoveResult.ILLEGAL;
		}
		
		if (!moveisValid(source, destination)) {
			setMoveMessage(messageMoveNotValidForPiece);
			return MoveResult.ILLEGAL;
		}
		
		board.movePiece(source, destination, activeColor);
		
		if (wasGeneralCaptured() || wasGeneralPutInCheckMate()) {
			
			setMoveMessage(messageGameWon);
			gameCompleted = true;
			return (activeColor == XiangqiColor.RED) ? MoveResult.RED_WINS :
				MoveResult.BLACK_WINS;
		}
		
		// check if game is over
		if (isEndOfFinalRound()) {
			setMoveMessage(messageGameDraw);
			gameCompleted = true;
			return MoveResult.DRAW;
		}
		
		// update round count and active color
		updateGameStats();
		
		return MoveResult.OK;
	}
	
	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#getPieceAt(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiColor)
	 */
	@Override
	public XiangqiPiece getPieceAt(XiangqiCoordinate where, XiangqiColor aspect)
	{
		return board.getPieceAt(where, aspect);
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
	
	private XiangqiColor opponentColor()
	{
		return (activeColor == XiangqiColor.RED) ? XiangqiColor.BLACK : XiangqiColor.RED;
	}	
	
	private boolean wasGeneralPutInCheckMate()
	{
		return board.isGeneralInCheckMate(opponentColor());
	}

	private boolean wasGeneralCaptured()
	{
		return board.isGeneralCaptured(opponentColor());
	}

	private boolean moveisValid(XiangqiCoordinate source, XiangqiCoordinate destination)
	{
		return board.getPieceAt(source, activeColor).isValidMove(source, destination);
	}

	private boolean sourcePieceMatchesActiveColor(XiangqiCoordinate source)
	{
		return board.getPieceAt(source, activeColor).getColor() == activeColor;
	}

	private boolean sourceHasAPiece(XiangqiCoordinate source)
	{
		return board.getPieceAt(source, activeColor).getPieceType() != XiangqiPieceType.NONE;
	}

	private boolean areCoordinatesValid(XiangqiCoordinate source, XiangqiCoordinate destination)
	{
		return board.isValidCoordinate(source) && board.isValidCoordinate(destination);
	}

	private boolean isEndOfFinalRound()
	{
		return (roundCount >= MAX_ROUNDS && activeColor != STARTING_COLOR);
	}

	private void updateGameStats()
	{
		roundCount += (activeColor == STARTING_COLOR) ? 0 : 1;	
		activeColor = opponentColor();
	}
	
	// Initializes the game with pieces according to game manual.
	private void placeStartingPieces()
	{
		// move rules
		MoveValidator soldierRules = new SoldierMoveValidator(board);
		MoveValidator chariotRules = new ChariotMoveValidator(board);
		MoveValidator advisorRules = new AdvisorMoveValidator(board);
		MoveValidator generalRules = new GeneralMoveValidator(board);
		
		// red pieces
		XiangqiPieceImpl redGeneral = XiangqiPieceImpl.makePiece(XiangqiPieceType.GENERAL, XiangqiColor.RED, generalRules);
		XiangqiPieceImpl redSoldier = XiangqiPieceImpl.makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.RED, soldierRules);
		XiangqiPieceImpl redAdvisorLeft = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.RED, advisorRules);
		XiangqiPieceImpl redAdvisorRight = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.RED, advisorRules);
		XiangqiPieceImpl redChariotLeft = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.RED, chariotRules);
		XiangqiPieceImpl redChariotRight = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.RED, chariotRules);		
		
		// black pieces
		XiangqiPieceImpl blackGeneral = XiangqiPieceImpl.makePiece(XiangqiPieceType.GENERAL, XiangqiColor.BLACK, generalRules);
		XiangqiPieceImpl blackSoldier = XiangqiPieceImpl.makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.BLACK, soldierRules);
		XiangqiPieceImpl blackAdvisorLeft = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.BLACK, advisorRules);
		XiangqiPieceImpl blackAdvisorRight = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.BLACK, advisorRules);
		XiangqiPieceImpl blackChariotLeft = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.BLACK, chariotRules);
		XiangqiPieceImpl blackChariotRight = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.BLACK, chariotRules);	
		
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
