package xiangqi.studenttapetri.versions.deltaxiangqi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static testutil.TestPiece.makePiece;
import static xiangqi.common.MoveResult.ILLEGAL;
import static xiangqi.common.MoveResult.OK;
import static xiangqi.common.MoveResult.RED_WINS;
import static xiangqi.common.XiangqiColor.BLACK;
import static xiangqi.common.XiangqiColor.RED;
import static xiangqi.common.XiangqiPieceType.ADVISOR;
import static xiangqi.common.XiangqiPieceType.CHARIOT;
import static xiangqi.common.XiangqiPieceType.ELEPHANT;
import static xiangqi.common.XiangqiPieceType.GENERAL;
import static xiangqi.common.XiangqiPieceType.SOLDIER;

import java.util.concurrent.CompletionException;

import static xiangqi.common.XiangqiPieceType.HORSE;
import static xiangqi.common.XiangqiPieceType.CANNON;

import org.junit.Before;
import org.junit.Test;

import testutil.TestCoordinate;
import xiangqi.XiangqiGameFactory;
import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiGameVersion;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;

public class DeltaXiangqiTestCases
{

	private XiangqiGame game;
	private DeltaXiangqiGame delta;

	private static XiangqiPiece nonePiece = 
			makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE), 
			redChariot = makePiece(CHARIOT, RED),
			redAdvisor = makePiece(ADVISOR, RED),
			redGeneral = makePiece(GENERAL, RED),
			redSoldier = makePiece(SOLDIER, RED),
			redElephant = makePiece(ELEPHANT, RED),
			redHorse = makePiece(HORSE, RED),
			redCannon = makePiece(CANNON, RED),
			blackChariot = makePiece(CHARIOT, BLACK),
			blackAdvisor = makePiece(ADVISOR, BLACK),
			blackGeneral = makePiece(GENERAL, BLACK),
			blackSoldier = makePiece(SOLDIER, BLACK),
			blackElephant = makePiece(ELEPHANT, BLACK),
			blackHorse = makePiece(HORSE, BLACK),
			blackCannon = makePiece(CANNON, BLACK);

	@Before
	public void setup()
	{
		game = XiangqiGameFactory.makeXiangqiGame(XiangqiGameVersion.DELTA_XQ);
		delta = (DeltaXiangqiGame) game; // used for debug
	}
	
	// Note: Like Prof. Pollice suggested, I copied in 42 tests from Gamma.
	// After that, tests start counting from 1 again with factoryProducesDeltaXiangqiGame()
	// These tests correspond to my TDD development/refactoring process for Delta.
	
	@Test // 0
	public void factoryProducesDeltaXiangqiGame()
	{
		assertNotNull(game);
	}
	
	@Test // 1
	public void correctInitialPositions()
	{
		assertEquals(redGeneral, game.getPieceAt(mkc(1,5), RED));
		assertEquals(redAdvisor, game.getPieceAt(mkc(1,4), RED));
		assertEquals(redAdvisor, game.getPieceAt(mkc(1,6), RED));
		assertEquals(redElephant, game.getPieceAt(mkc(1,3), RED));
		assertEquals(redElephant, game.getPieceAt(mkc(1,7), RED));
		assertEquals(redHorse, game.getPieceAt(mkc(1,2), RED));
		assertEquals(redHorse, game.getPieceAt(mkc(1,8), RED));
		
		assertEquals(redChariot, game.getPieceAt(mkc(1,1), RED));
		assertEquals(redChariot, game.getPieceAt(mkc(1,9), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,1), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,3), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,5), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,7), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,9), RED));
		assertEquals(redCannon, game.getPieceAt(mkc(3,2), RED));
		assertEquals(redCannon, game.getPieceAt(mkc(3,8), RED));
		
		assertEquals(blackGeneral, game.getPieceAt(mkc(1,5), BLACK));
		assertEquals(blackAdvisor, game.getPieceAt(mkc(1,4), BLACK));
		assertEquals(blackAdvisor, game.getPieceAt(mkc(1,6), BLACK));
		assertEquals(blackElephant, game.getPieceAt(mkc(1,3), BLACK));
		assertEquals(blackElephant, game.getPieceAt(mkc(1,7), BLACK));
		assertEquals(blackHorse, game.getPieceAt(mkc(1,2), BLACK));
		assertEquals(blackHorse, game.getPieceAt(mkc(1,8), BLACK));
		
		assertEquals(blackChariot, game.getPieceAt(mkc(1,1), BLACK));
		assertEquals(blackChariot, game.getPieceAt(mkc(1,9), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,1), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,3), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,5), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,7), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,9), BLACK));
		assertEquals(blackCannon, game.getPieceAt(mkc(3,2), BLACK));
		assertEquals(blackCannon, game.getPieceAt(mkc(3,8), BLACK));
	}
	
	@Test // 2
	public void queryAnEmptyLocation()
	{
		assertEquals(nonePiece, game.getPieceAt(mkc(5,6), RED));
		assertEquals(nonePiece, game.getPieceAt(mkc(5,1), BLACK));
	}
	
	@Test // 3
	public void queryAnInvalidLocation()
	{
		try {
			game.getPieceAt(mkc(-1,-1), RED);
			fail();
		} catch (CompletionException e) {
			
			// exception was throw
			assertTrue(e.getMessage().contains("invalid"));	
		}
	}
	
	@Test // 4
	public void outOfBoundsCoordinatesMoveReturnIllegalMove()
	{
		// all invalid source + valid destination
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(-1, -1), mkc(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(-1, 6), mkc(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(-1, 10), mkc(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(5, 10), mkc(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(11, 10), mkc(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(11, 5), mkc(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(11, -1), mkc(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(5, -1), mkc(3, 3)));
		
		// all valid source + invalid destination
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3, 3), mkc(-1, -1)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3, 3), mkc(-1, 6)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3, 3), mkc(-1, 10)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3, 3), mkc(5, 10)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3, 3), mkc(11, 10)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3, 3), mkc(11, 5)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3, 3), mkc(11, -1)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3, 3), mkc(5, -1)));
	}
	
	@Test // 5
	public void outOfBoundsCoordinatesResultInAppropriateMessage()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(11, 10), mkc(3, 3)));
		assertTrue(game.getMoveMessage().contains("invalid"));
	}
	
	@Test  // 6
	public void moveFromEmptyCoordinateIsIllegal()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(7,4), mkc(3,4)));
		assertTrue(game.getMoveMessage().contains("no piece"));
	}
	
	@Test // 7
	public void redIsAllowedToMoveRedPiece() 
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(4,1), mkc(5,1)));
	}
	
	@Test // 8
	public void redIsNotAllowedToMoveBlackPiece()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(10,1), mkc(9,1)));
		assertTrue(game.getMoveMessage().contains("can not move this piece"));
	}
	
	@Test // 9
	public void blackIsAllowedToMoveBlackPiece() 
	{
		// red dummy move
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); 
		
		assertEquals(MoveResult.OK, game.makeMove(mkc(4,1), mkc(5,1)));
	}
	
	@Test // 10
	public void blackIsNotAllowedToMoveRedPiece()
	{
		// red dummy move
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); 
		
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(10,1), mkc(9,1)));
		assertTrue(game.getMoveMessage().contains("can not move this piece"));
	}
	
	@Test // 11
	public void illegalMoveLetsPlayerRetry()
	{
		assertEquals(XiangqiColor.RED, delta.getActiveColor());
		
		// red tries to make illegal move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,1), mkc(-1,-1)));
		
		// red can then retry
		assertEquals(XiangqiColor.RED, delta.getActiveColor());	
	}
	
	@Test // 12
	public void validMoveSwitchesActiveColor()
	{
		assertEquals(XiangqiColor.RED, delta.getActiveColor());
		
		// red tries to make illegal move
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		
		// black is now up
		assertEquals(XiangqiColor.BLACK, delta.getActiveColor());	
	}
	
	@Test // 13
	public void chariotCanMoveVertically() 
	{
		// one forward
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(redChariot, game.getPieceAt(mkc(2,1), RED));
		
		// two forward
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(3,1))); 
		assertEquals(blackChariot, game.getPieceAt(mkc(3,1), BLACK));
		
		// one backward
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(redChariot, game.getPieceAt(mkc(1,1), RED));
		
		// two backward
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,1), mkc(1,1))); 
		assertEquals(blackChariot, game.getPieceAt(mkc(1,1), BLACK));
	}
	
	@Test // 14
	public void chariotCanMoveHorizontall()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // move red into place
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // move black into place
		
		// three right
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(2,3)));
		assertEquals(redChariot, game.getPieceAt(mkc(2,3), RED));
		
		// five right
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(2,6))); 
		assertEquals(blackChariot, game.getPieceAt(mkc(2,6), BLACK));
		
		// one left
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,3), mkc(2,2)));
		assertEquals(redChariot, game.getPieceAt(mkc(2,2), RED));
		
		// four left
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,6), mkc(2,2))); 
		assertEquals(blackChariot, game.getPieceAt(mkc(2,2), BLACK));
	}
	
	@Test // 15
	public void chariotMayNotMoveDiagonally() 
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,1), mkc(2,2)));
	}
	
	@Test // 16
	public void chariotMayNotJump() 
	{	
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,1), mkc(7,1)));
	}
	
	@Test // 17
	public void advisorCanMoveDiagonally()
	{
		// tests all four directions of diagnoal movement
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,4), mkc(2,5)));
		assertEquals(redAdvisor, game.getPieceAt(mkc(2,5), RED));
		
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,6), mkc(2,5)));
		assertEquals(blackAdvisor, game.getPieceAt(mkc(2,5), BLACK));
		
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(1,4)));
		assertEquals(redAdvisor, game.getPieceAt(mkc(1,4), RED));
		
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(1,6)));
		assertEquals(blackAdvisor, game.getPieceAt(mkc(1,6), BLACK));
	}
	
	@Test // 18
	public void advisorMayNotMoveMoreThanOneStep()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,4), mkc(3,6)));
	}
	
	@Test // 19
	public void advisorMayNotLeavePalace()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,4), mkc(2,3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,6), mkc(2,7)));
		
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,4), mkc(2,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,4), mkc(2,5))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(3,4)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(1,4))); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,4), mkc(4,2)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,4), mkc(4,5)));
	}
	
	@Test // 20
	public void generalCanVertically()
	{
		// forward
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,5), mkc(2,5)));
		assertEquals(redGeneral, game.getPieceAt(mkc(2,5), RED));
		
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		
		// backward
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(1,5)));
		assertEquals(redGeneral, game.getPieceAt(mkc(1,5), RED));
		
	}
	
	@Test // 21
	public void generaCanMoveHorizontally()
	{
		// forward
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,5), mkc(2,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		
		// right
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(2,6)));
		assertEquals(redGeneral, game.getPieceAt(mkc(2,6), RED));
		
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1),	mkc(1,1))); // dummy black move
		
		// left 
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,6), mkc(2,5)));
		assertEquals(redGeneral, game.getPieceAt(mkc(2,5), RED));
	}
	
	@Test //22
	public void generalMayNotMoveMoreThanOneStep()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,5), mkc(3,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,5), mkc(2,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(2,4)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1),	mkc(1,1))); // dummy black move
		
		assertEquals(redGeneral, game.getPieceAt(mkc(2,4), RED));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(2,4), mkc(2,6)));
	}
	
	@Test //23
	public void generalMayNotLeavePalace()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,5), mkc(2,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(3,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1),	mkc(1,1))); // dummy black move
		
		assertEquals(redGeneral, game.getPieceAt(mkc(3,5), RED));
		assertEquals(ILLEGAL, game.makeMove(mkc(3,5), mkc(4,5)));
	}
	
	@Test // 24
	public void soldierCanMoveForwardOneStepBeforeRiver() 
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(4,5), mkc(5,5)));
		assertEquals(redSoldier, game.getPieceAt(mkc(5,5), RED));
	}
	
	@Test // 25
	public void soliderMayNotMoveBackward()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(4,5), mkc(3,5)));
	}
	
	@Test // 26
	public void soldierMayNotMoveHorizontallyBeforeRiver()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(4,5), mkc(4,6)));
	}
	
	@Test //27
	public void soldierMayNotMoveMoreThanOneStepForward()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(4,5), mkc(6,5)));
	}
	
	@Test // 28
	public void soldierCanMoveHorizontallyPastRiver()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(4,5), mkc(5,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(5,5), mkc(6,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1),	mkc(1,1))); // dummy black move
		
		// left
		assertEquals(MoveResult.OK, game.makeMove(mkc(6,5), mkc(6,4)));
		assertEquals(redSoldier, game.getPieceAt(mkc(6,4), RED));
		
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		
		// right
		assertEquals(MoveResult.OK, game.makeMove(mkc(6,4), mkc(6,5)));
		assertEquals(redSoldier, game.getPieceAt(mkc(6,5), RED));
	}
	
	@Test //29
	public void soldierCanMoveVerticallyPastRiver()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(4,5), mkc(5,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(5,5), mkc(6,5)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1),	mkc(1,1))); // dummy black move
		
		assertEquals(MoveResult.OK, game.makeMove(mkc(6,5), mkc(7,5)));
	}
	
	@Test //30
	public void elephantCanMoveTwoStepsDiagonally()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,3), mkc(3,1)));
		assertEquals(redElephant, game.getPieceAt(mkc(3,1), RED));
	}
	
	@Test //31
	public void elephantMayNotMoveOneStepDiagonally()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,3), mkc(2,2)));
	}
	
	@Test //32
	public void elephantMayNotMoveMoreThanTwoStepsDiagonally()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,3), mkc(4,6)));
	}
	
	@Test //33
	public void elephantMayNotMoveVertically()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,3), mkc(2,3)));
	}
	
	@Test //34
	public void elephantMayMotMoveHorizontally()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,3), mkc(3,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,1), mkc(3,2)));
	}
	
	@Test //35
	public void elephantMayNotJumpOverAPiece()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(2,2))); // red chariot blocks elephant move (1,3) -> (3,1)
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1),	mkc(1,1))); // dummy black move
		
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,3), mkc(3,1)));
	}
	
	@Test //36
	public void capturingOwnPieceIsIllegal()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,1), mkc(1,3)));
		assertTrue(game.getMoveMessage().contains("not valid for this piece"));
	}
	
	@Test //37
	public void soliderCanCapturePiece()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(4,1), mkc(5,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(4,9), mkc(5,9)));
		
		assertEquals(blackSoldier, game.getPieceAt(mkc(6,1), RED));
		assertEquals(MoveResult.OK, game.makeMove(mkc(5,1), mkc(6,1)));
		assertEquals(redSoldier, game.getPieceAt(mkc(6,1), RED));
		
	}
	
	@Test //38
	public void chariotCanCapturePiece()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(2,4))); // red chariot at (2,4)
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1),	mkc(1,1))); // dummy black move
		
		assertEquals(blackAdvisor, game.getPieceAt(mkc(10,4), RED));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,4), mkc(10,4)));
		assertEquals(redChariot, game.getPieceAt(mkc(10,4), RED));
	}
	
	@Test //39
	public void elephantCanCapturePiece()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(4,3), mkc(5,3)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,7),	mkc(3,9))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(5,3), mkc(6,3))); // red chariot at (2,4)
		
		assertEquals(redSoldier, game.getPieceAt(mkc(5,7), BLACK));
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,9),	mkc(5,7)));
		assertEquals(blackElephant, game.getPieceAt(mkc(5,7), BLACK));
	}
	
	@Test //40
	public void advisorCanCapturePiece()
	{
		// red chariot moves to position to be capture by advisor in palace
		// black advisor captures it
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1),	mkc(2,1))); 
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(2,4))); 
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,4),	mkc(2,5))); 
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,4), mkc(8,4)));
		
		assertEquals(redChariot, game.getPieceAt(mkc(3,6), BLACK));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5),	mkc(3,6)));
		assertEquals(blackAdvisor, game.getPieceAt(mkc(3,6), BLACK));
	}
	
	@Test //42
	public void completeGame()
	{
		makeValidMoves(mkc(1,1), mkc(2,1), mkc(1,9), mkc(2,9),
				mkc(2,1), mkc(2,4), mkc(2,9), mkc(1,9),
				mkc(2,4), mkc(9,4), mkc(1,9), mkc(2,9),
				mkc(9,4), mkc(9,3), mkc(2,9), mkc(1,9),
				mkc(1,9), mkc(2,9), mkc(1,3), mkc(3,1),
				mkc(2,9), mkc(2,4), mkc(1,7), mkc(3,9),
				mkc(9,3), mkc(10,3), mkc(3,9), mkc(5,7),
				mkc(2,4), mkc(9,4), mkc(1,9), mkc(2,9));
		assertEquals(RED_WINS, game.makeMove(mkc(10,3), mkc(10,4)));
	}
	
	@Test // 1 (TDD for Delta game started here)
	public void cannonCanMoveVertically()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(4,2))); // 1 up
		assertEquals(redCannon, game.getPieceAt(mkc(4,2), RED));
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(2,2))); // 1 down
		assertEquals(blackCannon, game.getPieceAt(mkc(2,2), BLACK));
		assertEquals(MoveResult.OK, game.makeMove(mkc(4,2), mkc(2,2))); // 2 down
		assertEquals(redCannon, game.getPieceAt(mkc(2,2), RED));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,2), mkc(4,2))); // 2 up	
		assertEquals(blackCannon, game.getPieceAt(mkc(4,2), BLACK));
	}
	
	@Test // 2
	public void cannonCanMoveHorizontally()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(3,1))); // 1 left
		assertEquals(redCannon, game.getPieceAt(mkc(3,1), RED));
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(3,3))); // 1 right
		assertEquals(blackCannon, game.getPieceAt(mkc(3,3), BLACK));
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,1), mkc(3,3))); // 2 right
		assertEquals(redCannon, game.getPieceAt(mkc(3,3), RED));
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,3), mkc(3,1))); // 2 left
		assertEquals(blackCannon, game.getPieceAt(mkc(3,1), BLACK));
	}
	
	@Test // 3
	public void cannonMayNotMoveDiagonally()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,2), mkc(2,1)));
	}
	
	@Test // 4
	public void cannonMayNotJumpWithoutCapturing()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(7,2)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(7,2), mkc(7,4)));
	}
	
	@Test // 5
	public void cannonMayNotCaptureWithoutJumping()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(7,2)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(7,2), mkc(7,3)));
	}
	
	@Test // 6
	public void cannonMayCaptureWhenJumpingOnePiece()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(7,2)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(7,2), mkc(7,5)));
		assertEquals(redCannon, game.getPieceAt(mkc(7,5), RED));
	}
	
	@Test // 7
	public void cannonMayCaptureWhenJumpingMultiplePieces()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(7,2)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(7,2), mkc(7,7)));
	}
	
	@Test // 8
	public void horseCanMoveOneStepOrthogonallyAndThenOneStepDiagonally()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,2), mkc(3,1))); 
		assertEquals(redHorse, game.getPieceAt(mkc(3,1), RED));
		
	}
	
	@Test //9
	public void horseMakesIllegalMove() {
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,2), mkc(2,2)));
	}
	
	@Test //10
	public void horseMayNotJumpOverPiece() 
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,2), mkc(3,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,1), mkc(2,3)));
	}
	
	@Test //11
	public void horseCanCapturePiece()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,2), mkc(3,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,8), mkc(7,8))); 
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(2,2))); 
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,1), mkc(2,3)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1))); // dummy black move
		assertEquals(blackCannon, game.getPieceAt(mkc(4,2), RED));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,3), mkc(4,2)));
		assertEquals(redHorse, game.getPieceAt(mkc(4,2), RED));
	}
	
	@Test //12
	public void playerMayNotLeaveOwnGeneralInCheck()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(7,2)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(7,2), mkc(7,5))); // cannon in front of general
		
		// moving advisor out in front of king would let cannon take king (effectively leaving him in check)
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,4), mkc(2,5)));

	}
	
	@Test //13
	public void playerMustMoveOutOfCheck()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,2), mkc(6,2)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(6,2), mkc(6,5))); // cannon puts general in check
		
		// moving a random piece does not get rid of check
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(2,1), mkc(1,1)));
		
		// moving advisor in front of king means two pieces are between cannon and king (blocked check)		
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,4), mkc(2,5)));

	}
	
	@Test //14
	public void redRepeatsStateThreeTimesResultsInBlackWin()
	{
        game.makeMove(mkc(1,1), mkc(2,1));
        game.makeMove(mkc(1,1), mkc(2,1)); // 1
        game.makeMove(mkc(2,1), mkc(1,1));
        game.makeMove(mkc(2,1), mkc(1,1)); // 2
        game.makeMove(mkc(1,1), mkc(2,1));
        game.makeMove(mkc(1,1), mkc(2,1)); // 3
        game.makeMove(mkc(2,1), mkc(1,1));
        game.makeMove(mkc(2,1), mkc(1,1)); // 4
        assertEquals(MoveResult.BLACK_WINS, game.makeMove(mkc(1,1), mkc(2,1)));
	}
	
	@Test // 15
	public void blackRepeatsStateThreeTimesResultsInRedWin()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,9), mkc(2,9)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // 1
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); 
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1))); // 2
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1))); 
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); // 3
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1))); 
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1))); // 4
		assertEquals(MoveResult.BLACK_WINS, game.makeMove(mkc(2,1), mkc(1,1))); 
	}
	
	
	private XiangqiCoordinate mkc(int rank, int file) {
		return TestCoordinate.makeCoordinate(rank, file);
	}
	
	private void makeValidMoves(XiangqiCoordinate ... c)
	{
		int i = 1;
		while (i < c.length) {
			MoveResult mr = game.makeMove(c[i-1], c[i]);
			if (mr == ILLEGAL) {
				System.out.println(c[i-1] + "-" + c[i] + ": " +game.getMoveMessage());
			}
			assertEquals(OK, mr);
			i += 2;
		}
	}
}
