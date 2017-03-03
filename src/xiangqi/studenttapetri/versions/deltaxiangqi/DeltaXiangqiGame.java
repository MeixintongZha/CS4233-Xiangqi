package xiangqi.studenttapetri.versions.deltaxiangqi;

import static xiangqi.studenttapetri.common.XiangqiCoordinateImpl.makeCoordinate;
import static xiangqi.studenttapetri.common.XiangqiPieceImpl.makePiece;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.CompletionException;

import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.AbstractXiangqiGame;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.XiangqiPieceImpl;
import xiangqi.studenttapetri.common.movement.AdvisorMoveValidator;
import xiangqi.studenttapetri.common.movement.CannonMoveValidator;
import xiangqi.studenttapetri.common.movement.ChariotMoveValidator;
import xiangqi.studenttapetri.common.movement.ElephantMoveValidator;
import xiangqi.studenttapetri.common.movement.GeneralMoveValidator;
import xiangqi.studenttapetri.common.movement.HorseMoveValidator;
import xiangqi.studenttapetri.common.movement.MoveValidator;
import xiangqi.studenttapetri.common.movement.SoldierMoveValidator;
import xiangqi.studenttapetri.versions.gammaxiangqi.AlwaysValidMoveValidator;

/**
 * Implementation of the Delta version of XiangqiGame.
 * 
 * @author Tim Petri
 * @version Mar 2, 2017
 */
public class DeltaXiangqiGame extends AbstractXiangqiGame
{

	public DeltaXiangqiGame()
	{
		super(Integer.MAX_VALUE); // no limit on number of moves
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
		MoveValidator chariotMovementRules = new ChariotMoveValidator();
		MoveValidator advisorMovementRules = new AdvisorMoveValidator();
		MoveValidator generalMovementRules = new GeneralMoveValidator();
		MoveValidator soliderMovementRules = new SoldierMoveValidator();
		MoveValidator elephantMovementRules = new ElephantMoveValidator();
		MoveValidator cannonMovementRules = new CannonMoveValidator();
		MoveValidator horseMoveMentRules = new HorseMoveValidator();
		
		// red pieces
		XiangqiPieceImpl redGeneral = makePiece(XiangqiPieceType.GENERAL, XiangqiColor.RED, generalMovementRules);
		XiangqiPieceImpl redSoldier = makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.RED, soliderMovementRules);
		XiangqiPieceImpl redAdvisor = makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.RED, advisorMovementRules);
		XiangqiPieceImpl redChariot = makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.RED, chariotMovementRules);
		XiangqiPieceImpl redElephant = makePiece(XiangqiPieceType.ELEPHANT, XiangqiColor.RED, elephantMovementRules);
		XiangqiPieceImpl redHorse = makePiece(XiangqiPieceType.HORSE, XiangqiColor.RED, horseMoveMentRules);
		XiangqiPieceImpl redCannon = makePiece(XiangqiPieceType.CANNON, XiangqiColor.RED, cannonMovementRules);
		
		// black pieces
		XiangqiPieceImpl blackGeneral = makePiece(XiangqiPieceType.GENERAL, XiangqiColor.BLACK, generalMovementRules);
		XiangqiPieceImpl blackSoldier = makePiece(XiangqiPieceType.SOLDIER, XiangqiColor.BLACK, soliderMovementRules);
		XiangqiPieceImpl blackAdvisor = makePiece(XiangqiPieceType.ADVISOR, XiangqiColor.BLACK, advisorMovementRules);
		XiangqiPieceImpl blackChariot = makePiece(XiangqiPieceType.CHARIOT, XiangqiColor.BLACK, chariotMovementRules);
		XiangqiPieceImpl blackElephant = makePiece(XiangqiPieceType.ELEPHANT, XiangqiColor.BLACK, elephantMovementRules);
		XiangqiPieceImpl blackHorse = makePiece(XiangqiPieceType.HORSE, XiangqiColor.BLACK, horseMoveMentRules);
		XiangqiPieceImpl blackCannon = makePiece(XiangqiPieceType.CANNON, XiangqiColor.BLACK, cannonMovementRules);
		
		
		board.putPiece(makeCoordinate(1, 5), redGeneral, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 4), redAdvisor, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 6), redAdvisor, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 3), redElephant, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 7), redElephant, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 2), redHorse, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 8), redHorse, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 1), redChariot, XiangqiColor.RED);
		board.putPiece(makeCoordinate(1, 9), redChariot, XiangqiColor.RED);
		
		board.putPiece(makeCoordinate(4, 1), redSoldier, XiangqiColor.RED);
		board.putPiece(makeCoordinate(4, 3), redSoldier, XiangqiColor.RED);
		board.putPiece(makeCoordinate(4, 5), redSoldier, XiangqiColor.RED);
		board.putPiece(makeCoordinate(4, 7), redSoldier, XiangqiColor.RED);
		board.putPiece(makeCoordinate(4, 9), redSoldier, XiangqiColor.RED);
		board.putPiece(makeCoordinate(3, 2), redCannon, XiangqiColor.RED);
		board.putPiece(makeCoordinate(3, 8), redCannon, XiangqiColor.RED);
		
		board.putPiece(makeCoordinate(1, 5), blackGeneral, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 4), blackAdvisor, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 6), blackAdvisor, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 3), blackElephant, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 7), blackElephant, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 2), blackHorse, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 8), blackHorse, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 1), blackChariot, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(1, 9), blackChariot, XiangqiColor.BLACK);
		
		board.putPiece(makeCoordinate(4, 1), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(4, 3), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(4, 5), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(4, 7), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(4, 9), blackSoldier, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(3, 2), blackCannon, XiangqiColor.BLACK);
		board.putPiece(makeCoordinate(3, 8), blackCannon, XiangqiColor.BLACK);
		
	}
	
	// used for debug
	public XiangqiBoard getBoard() {
		return this.board;
	}
}
