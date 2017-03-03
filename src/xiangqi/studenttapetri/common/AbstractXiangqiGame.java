package xiangqi.studenttapetri.common;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;

/**
 * This class implement the XiangqiGame interface and holds the logic common to several
 * versions (Beta and Gamma, currently) of the Xiangqi game. Each specific version of the game
 * adds their own boards (sizes) and place the corresponding starting pieces, but the interface logic
 * is mostly shared.
 * 
 * @author Tim Petri
 * @version Mar 2, 2017
 */
public abstract class AbstractXiangqiGame implements XiangqiGame
{
	private static String messageInvalidCoordinates = "The provided coordinates were invalid";
	private static String messageNoPieceAtProvidedSource = "There is no piece at the provided source coordinate";
	private static String messageNotAllowedToMovePiece = "The player can not move this piece";
	private static String messageMoveNotValidForPiece = "The given move was not valid for this piece";
	private static String messageGameWon = "The given move resulted in a win.";
	private static String messageGameDraw = "The given more resulted in a draw.";
	private static String messageGameAlreadyCompleted = "The game has already been completed.";
	private static String messageGeneralMayNotBeLeftInCheck = "Own general may not be left in check after round is over";
	
	private static XiangqiColor STARTING_COLOR = XiangqiColor.RED;
	
	private int maxRounds;
	private int roundCount;
	private boolean gameCompleted;
	private String lastMoveMessage;
	private XiangqiColor activeColor;

	private Deque<XiangqiBoard> pastBoards;
	protected XiangqiBoard board;
	
	
	public AbstractXiangqiGame(int maxRounds)
	{
		this.maxRounds = maxRounds;
		activeColor = STARTING_COLOR;
		gameCompleted = false;
		roundCount = 1;
		lastMoveMessage = "";
		
		pastBoards = new ArrayDeque<>();
		addBoard();
		placeStartingPieces();
	}

	/**
	 * This gets implemented by each specific game.
	 */
	abstract protected void addBoard();

	/**
	 * This gets implemented by each specific game.
	 */
	abstract protected void placeStartingPieces();

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
		
		if (!moveIsValid(source, destination)) {
			setMoveMessage(messageMoveNotValidForPiece);
			return MoveResult.ILLEGAL;
		}
		
		// save old board
		pastBoards.addLast(board.clone());
		
		// make move
		board.movePiece(source, destination, activeColor);
		
		// hook
		if (isOwnGeneralInCheck()) {
			revertMove();	
			setMoveMessage(messageGeneralMayNotBeLeftInCheck);
			return MoveResult.ILLEGAL;
		}
		
		// hook
		if (repetitionOccured()) {
			setMoveMessage(messageGameWon);
			gameCompleted = true;
			return (activeColor == XiangqiColor.RED) ? MoveResult.BLACK_WINS :
				MoveResult.RED_WINS;
		}
		
		if (wasOpponentGeneralCaptured() || wasOpponentGeneralPutInCheckMate()) {
			
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
	
	// Returns true if the first and third of the last 4 states were the same as this state
	protected boolean repetitionOccured()
	{
		if (roundCount < 5) return false;
		Iterator<XiangqiBoard> pbi = pastBoards.iterator();
		
		if (!this.board.equals(pbi.next())) {
			return false; // M1
		}
		pbi.next(); pbi.next(); pbi.next();
		if (!this.board.equals(pbi.next())) {
			return false; // M3
		}
		
		return true;
		
	}

	// revert last move using queue of old states
	private void revertMove()
	{
		this.board = pastBoards.removeLast();
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

	protected boolean isOwnGeneralInCheck()
	{
		return board.isGeneralInCheck(activeColor);
	}
	
	public XiangqiColor getActiveColor()
	{
		return activeColor;
	}
	
	private void setMoveMessage(String msg)
	{
		lastMoveMessage = msg;
	}
	
	private XiangqiColor opponentColor()
	{
		return (activeColor == XiangqiColor.RED) ? XiangqiColor.BLACK : XiangqiColor.RED;
	}	
	
	private boolean wasOpponentGeneralPutInCheckMate()
	{
		// return false;
		return board.isGeneralInCheckMate(opponentColor());
	}

	private boolean wasOpponentGeneralCaptured()
	{
		return board.isGeneralCaptured(opponentColor());
	}

	private boolean moveIsValid(XiangqiCoordinate source, XiangqiCoordinate destination)
	{
		return board.getPieceAt(source, activeColor).isValidMove(this.board, source, destination);
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
		return (roundCount >= this.maxRounds && activeColor != STARTING_COLOR);
	}

	private void updateGameStats()
	{
		roundCount += (activeColor == STARTING_COLOR) ? 0 : 1;	
		activeColor = opponentColor();
		if (pastBoards.size() >= 8) pastBoards.removeFirst();
	}

}
