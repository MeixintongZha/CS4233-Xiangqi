/**
 * 
 */
package xiangqi.studenttapetri.versions.gammaxiangqi;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.AbstractXiangqiGame;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.XiangqiPieceImpl;
import xiangqi.studenttapetri.common.movement.MoveValidator;
import xiangqi.studenttapetri.common.movement.SoldierMoveValidator;
import xiangqi.studenttapetri.common.movement.AdvisorMoveValidator;
import xiangqi.studenttapetri.common.movement.ChariotMoveValidator;
import xiangqi.studenttapetri.common.movement.ElephantMoveValidator;
import xiangqi.studenttapetri.common.movement.GeneralMoveValidator;

import static xiangqi.studenttapetri.common.XiangqiPieceImpl.makePiece;

import java.util.concurrent.CompletionException;

import static xiangqi.studenttapetri.common.XiangqiCoordinateImpl.makeCoordinate;

/**
 * Implementation of the Gamma version of XiangqiGame.
 * 
 * @author Tim Petri
 * @version Feb 20, 2017
 */
public class GammaXiangqiGame extends AbstractXiangqiGame
{

	public GammaXiangqiGame()
	{
		super(25);
	}

	/* 
	 * @see xiangqi.studenttapetri.common.AbstractXiangqiGame#addBoard()
	 */
	@Override
	protected void addBoard()
	{
		this.board = new XiangqiBoard(10, 9);

	}
	
	/*
	 * @see xiangqi.studenttapetri.common.AbstractXiangqiGame#getPieceAt(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiColor)
	 */
	@Override
	public XiangqiPiece getPieceAt(XiangqiCoordinate where, XiangqiColor aspect)
	{
		if (!board.isValidCoordinate(where)) throw new CompletionException(new Throwable("Coordinate is invalid"));
		return board.getPieceAt(where, aspect);
	}

	/* 
	 * @see xiangqi.studenttapetri.common.AbstractXiangqiGame#placeStartingPieces()
	 */
	@Override
	protected void placeStartingPieces()
	{
		MoveValidator chariotMovementRules = new ChariotMoveValidator(this.board);
		MoveValidator advisorMovementRules = new AdvisorMoveValidator(this.board);
		MoveValidator generalMovementRules = new GeneralMoveValidator(this.board);
		MoveValidator soliderMovementRules = new SoldierMoveValidator(this.board);
		MoveValidator elephantMovementRules = new ElephantMoveValidator(this.board);
		
		// red pieces
		XiangqiPieceImpl redGeneral = makePiece(XiangqiPieceType.GENERAL, XiangqiColor.RED, generalMovementRules);
		XiangqiPieceImpl redSoldier = makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.RED, soliderMovementRules);
		XiangqiPieceImpl redAdvisor = makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.RED, advisorMovementRules);
		XiangqiPieceImpl redChariot = makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.RED, chariotMovementRules);
		XiangqiPieceImpl redElephant = makePiece(XiangqiPieceType.ELEPHANT, XiangqiColor.RED, elephantMovementRules);
		
		// black pieces
		XiangqiPieceImpl blackGeneral = makePiece(XiangqiPieceType.GENERAL, XiangqiColor.BLACK, generalMovementRules);
		XiangqiPieceImpl blackSoldier = makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.BLACK, soliderMovementRules);
		XiangqiPieceImpl blackAdvisor = makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.BLACK, advisorMovementRules);
		XiangqiPieceImpl blackChariot = makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.BLACK, chariotMovementRules);
		XiangqiPieceImpl blackElephant = makePiece(XiangqiPieceType.ELEPHANT, XiangqiColor.BLACK, elephantMovementRules);
		
		board.putPiece(makeCoordinate(1, 5), redGeneral, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 4), redAdvisor, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 6), redAdvisor, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 3), redElephant, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 7), redElephant, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 1), redChariot, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 9), redChariot, XiangqiColor.RED);
		
		board.putPiece(makeCoordinate(4, 1), redSoldier, XiangqiColor.RED);
		board.putPiece(makeCoordinate(4, 3), redSoldier, XiangqiColor.RED);
		board.putPiece(makeCoordinate(4, 5), redSoldier, XiangqiColor.RED);
		board.putPiece(makeCoordinate(4, 7), redSoldier, XiangqiColor.RED);
		board.putPiece(makeCoordinate(4, 9), redSoldier, XiangqiColor.RED);
		
		board.putPiece(makeCoordinate(1, 5), blackGeneral, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 4), blackAdvisor, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 6), blackAdvisor, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 3), blackElephant, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 7), blackElephant, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 1), blackChariot, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 9), blackChariot, XiangqiColor.BLACK);
		
		board.putPiece(makeCoordinate(4, 1), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(4, 3), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(4, 5), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(4, 7), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(4, 9), blackSoldier, XiangqiColor.BLACK);
	}
	
	// used for debug
	public XiangqiBoard getBoard() {
		return this.board;
	}
}
