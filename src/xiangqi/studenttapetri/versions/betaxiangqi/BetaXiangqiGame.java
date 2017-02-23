/**
 * 
 */
package xiangqi.studenttapetri.versions.betaxiangqi;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.*;
import xiangqi.studenttapetri.common.movement.ChariotMoveValidator;
import xiangqi.studenttapetri.common.movement.MoveValidator;



/**
 * Implementation of the Beta version of XiangqiGame.
 * 
 * @author Tim Petri
 * @version Feb 7, 2017
 *
 */
public class BetaXiangqiGame extends AbstractXiangqiGame
{
	
	public BetaXiangqiGame()
	{
		super(10); // max 10 rounds
	}

	// Adds a board with 5 ranks and 5 files
	protected void addBoard()
	{
		this.board = new XiangqiBoard(5,5);
		
	}
	
	// Initializes the game with pieces according to game manual.
	protected void placeStartingPieces()
	{
		// move rules
		MoveValidator soldierRules = new BetaSoldierMoveValidator(board);
		MoveValidator chariotRules = new ChariotMoveValidator(board);
		MoveValidator advisorRules = new BetaAdvisorMoveValidator(board);
		MoveValidator generalRules = new BetaGeneralMoveValidator(board);
		
		// red pieces
		XiangqiPieceImpl redGeneral = XiangqiPieceImpl.makePiece(XiangqiPieceType.GENERAL, XiangqiColor.RED, generalRules);
		XiangqiPieceImpl redSoldier = XiangqiPieceImpl.makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.RED, soldierRules);
		XiangqiPieceImpl redAdvisor = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.RED, advisorRules);
		XiangqiPieceImpl redChariot = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.RED, chariotRules);	
		
		// black pieces
		XiangqiPieceImpl blackGeneral = XiangqiPieceImpl.makePiece(XiangqiPieceType.GENERAL, XiangqiColor.BLACK, generalRules);
		XiangqiPieceImpl blackSoldier = XiangqiPieceImpl.makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.BLACK, soldierRules);
		XiangqiPieceImpl blackAdvisor = XiangqiPieceImpl.makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.BLACK, advisorRules);
		XiangqiPieceImpl blackChariot = XiangqiPieceImpl.makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.BLACK, chariotRules);
		
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 3), redGeneral, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(2, 3), redSoldier, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 2), redAdvisor, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 4), redAdvisor, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 1), redChariot, XiangqiColor.RED);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 5), redChariot, XiangqiColor.RED);

		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 3), blackGeneral, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(2, 3), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 2), blackAdvisor, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 4), blackAdvisor, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 1), blackChariot, XiangqiColor.BLACK);
		board.putPiece(XiangqiCoordinateImpl.makeCoordinate(1, 5), blackChariot, XiangqiColor.BLACK);	
	}


}
