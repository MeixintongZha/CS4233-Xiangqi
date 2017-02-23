package xiangqi.studenttapetri.versions.gammaxiangqi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static xiangqi.common.XiangqiColor.BLACK;
import static xiangqi.common.XiangqiColor.RED;
import static xiangqi.common.XiangqiPieceType.ADVISOR;
import static xiangqi.common.XiangqiPieceType.CHARIOT;
import static xiangqi.common.XiangqiPieceType.GENERAL;
import static xiangqi.common.XiangqiPieceType.SOLDIER;

import java.util.concurrent.CompletionException;

import static xiangqi.common.XiangqiPieceType.ELEPHANT;
import static xiangqi.common.MoveResult.*;
import static testutil.TestCoordinate.makeCoordinate;
import static testutil.TestPiece.*;

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

public class GammaXiangqiTestCases
{
	private XiangqiGame game;
	GammaXiangqiGame gamma;

	private static XiangqiPiece nonePiece = 
			makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE), 
			redChariot = makePiece(CHARIOT, RED),
			redAdvisor = makePiece(ADVISOR, RED),
			redGeneral = makePiece(GENERAL, RED),
			redSoldier = makePiece(SOLDIER, RED),
			redElephant = makePiece(ELEPHANT, RED),
			blackChariot = makePiece(CHARIOT, BLACK),
			blackAdvisor = makePiece(ADVISOR, BLACK),
			blackGeneral = makePiece(GENERAL, BLACK),
			blackSoldier = makePiece(SOLDIER, BLACK),
			blackElephant = makePiece(ELEPHANT, BLACK);

	@Before
	public void setup()
	{
		game = XiangqiGameFactory.makeXiangqiGame(XiangqiGameVersion.GAMMA_XQ);
		gamma = (GammaXiangqiGame) game; // used for debug
	}
	
	@Test // 1
	public void factoryProducesGammaXiangqiGame()
	{
		assertNotNull(game);
	}
	
	@Test // 2
	public void correctInitialPositions()
	{
		assertEquals(redGeneral, game.getPieceAt(mkc(1,5), RED));
		assertEquals(redAdvisor, game.getPieceAt(mkc(1,4), RED));
		assertEquals(redAdvisor, game.getPieceAt(mkc(1,6), RED));
		assertEquals(redElephant, game.getPieceAt(mkc(1,3), RED));
		assertEquals(redElephant, game.getPieceAt(mkc(1,7), RED));
		assertEquals(redChariot, game.getPieceAt(mkc(1,1), RED));
		assertEquals(redChariot, game.getPieceAt(mkc(1,9), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,1), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,3), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,5), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,7), RED));
		assertEquals(redSoldier, game.getPieceAt(mkc(4,9), RED));
		
		assertEquals(blackGeneral, game.getPieceAt(mkc(1,5), BLACK));
		assertEquals(blackAdvisor, game.getPieceAt(mkc(1,4), BLACK));
		assertEquals(blackAdvisor, game.getPieceAt(mkc(1,6), BLACK));
		assertEquals(blackElephant, game.getPieceAt(mkc(1,3), BLACK));
		assertEquals(blackElephant, game.getPieceAt(mkc(1,7), BLACK));
		assertEquals(blackChariot, game.getPieceAt(mkc(1,1), BLACK));
		assertEquals(blackChariot, game.getPieceAt(mkc(1,9), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,1), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,3), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,5), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,7), BLACK));
		assertEquals(blackSoldier, game.getPieceAt(mkc(4,9), BLACK));
	}
	
	@Test // 3
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
		assertEquals(XiangqiColor.RED, gamma.getActiveColor());
		
		// red tries to make illegal move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,1), mkc(-1,-1)));
		
		// red can then retry
		assertEquals(XiangqiColor.RED, gamma.getActiveColor());	
	}
	
	@Test // 12
	public void validMoveSwitchesActiveColor()
	{
		assertEquals(XiangqiColor.RED, gamma.getActiveColor());
		
		// red tries to make illegal move
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		
		// black is now up
		assertEquals(XiangqiColor.BLACK, gamma.getActiveColor());	
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
		game.makeMove(mkc(1,1), mkc(2,1)); // move red into place
		game.makeMove(mkc(1,1), mkc(2,1)); // move black into place
		
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
		
		game.makeMove(mkc(1,4), mkc(2,5));
		game.makeMove(mkc(1,4), mkc(2,5)); // dummy black move
		game.makeMove(mkc(2,5), mkc(3,4));
		game.makeMove(mkc(2,5), mkc(1,4)); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,4), mkc(4,2)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,4), mkc(4,5)));
	}
	
	@Test // 20
	public void generalCanVertically()
	{
		// forward
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,5), mkc(2,5)));
		assertEquals(redGeneral, game.getPieceAt(mkc(2,5), RED));
		
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		
		// backward
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(1,5)));
		assertEquals(redGeneral, game.getPieceAt(mkc(1,5), RED));
		
	}
	
	@Test // 21
	public void generaCanMoveHorizontally()
	{
		// forward
		game.makeMove(mkc(1,5), mkc(2,5));
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		
		// right
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5), mkc(2,6)));
		assertEquals(redGeneral, game.getPieceAt(mkc(2,6), RED));
		
		game.makeMove(mkc(2,1),	mkc(1,1)); // dummy black move
		
		// left 
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,6), mkc(2,5)));
		assertEquals(redGeneral, game.getPieceAt(mkc(2,5), RED));
	}
	
	@Test //22
	public void generalMayNotMoveMoreThanOneStep()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,5), mkc(3,5)));
		game.makeMove(mkc(1,5), mkc(2,5));
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		game.makeMove(mkc(2,5), mkc(2,4));
		game.makeMove(mkc(2,1),	mkc(1,1)); // dummy black move
		
		assertEquals(redGeneral, game.getPieceAt(mkc(2,4), RED));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(2,4), mkc(2,6)));
	}
	
	@Test //23
	public void generalMayNotLeavePalace()
	{
		game.makeMove(mkc(1,5), mkc(2,5));
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		game.makeMove(mkc(2,5), mkc(3,5));
		game.makeMove(mkc(2,1),	mkc(1,1)); // dummy black move
		
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
		game.makeMove(mkc(4,5), mkc(5,5));
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		game.makeMove(mkc(5,5), mkc(6,5));
		game.makeMove(mkc(2,1),	mkc(1,1)); // dummy black move
		
		// left
		assertEquals(MoveResult.OK, game.makeMove(mkc(6,5), mkc(6,4)));
		assertEquals(redSoldier, game.getPieceAt(mkc(6,4), RED));
		
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		
		// right
		assertEquals(MoveResult.OK, game.makeMove(mkc(6,4), mkc(6,5)));
		assertEquals(redSoldier, game.getPieceAt(mkc(6,5), RED));
	}
	
	@Test //29
	public void soldierCanMoveVerticallyPastRiver()
	{
		game.makeMove(mkc(4,5), mkc(5,5));
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		game.makeMove(mkc(5,5), mkc(6,5));
		game.makeMove(mkc(2,1),	mkc(1,1)); // dummy black move
		
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
		game.makeMove(mkc(1,3), mkc(3,1));
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,1), mkc(3,2)));
	}
	
	@Test //35
	public void elephantMayNotJumpOverAPiece()
	{
		game.makeMove(mkc(1,1), mkc(2,1));
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		game.makeMove(mkc(2,1), mkc(2,2)); // red chariot blocks elephant move (1,3) -> (3,1)
		game.makeMove(mkc(2,1),	mkc(1,1)); // dummy black move
		
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
		game.makeMove(mkc(4,1), mkc(5,1));
		game.makeMove(mkc(4,9), mkc(5,9));
		
		assertEquals(blackSoldier, game.getPieceAt(mkc(6,1), RED));
		assertEquals(MoveResult.OK, game.makeMove(mkc(5,1), mkc(6,1)));
		assertEquals(redSoldier, game.getPieceAt(mkc(6,1), RED));
		
	}
	
	@Test //38
	public void chariotCanCapturePiece()
	{
		game.makeMove(mkc(1,1), mkc(2,1));
		game.makeMove(mkc(1,1),	mkc(2,1)); // dummy black move
		game.makeMove(mkc(2,1), mkc(2,4)); // red chariot at (2,4)
		game.makeMove(mkc(2,1),	mkc(1,1)); // dummy black move
		
		assertEquals(blackAdvisor, game.getPieceAt(mkc(10,4), RED));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,4), mkc(10,4)));
		assertEquals(redChariot, game.getPieceAt(mkc(10,4), RED));
	}
	
	@Test //39
	public void elephantCanCapturePiece()
	{
		game.makeMove(mkc(4,3), mkc(5,3));
		game.makeMove(mkc(1,7),	mkc(3,9)); // dummy black move
		game.makeMove(mkc(5,3), mkc(6,3)); // red chariot at (2,4)
		
		assertEquals(redSoldier, game.getPieceAt(mkc(5,7), BLACK));
		assertEquals(MoveResult.OK, game.makeMove(mkc(3,9),	mkc(5,7)));
		assertEquals(blackElephant, game.getPieceAt(mkc(5,7), BLACK));
	}
	
	@Test //40
	public void advisorCanCapturePiece()
	{
		// red chariot moves to position to be capture by advisor in palace
		// black advisor captures it
		game.makeMove(mkc(1,1), mkc(2,1));
		game.makeMove(mkc(1,1),	mkc(2,1)); 
		game.makeMove(mkc(2,1), mkc(2,4)); 
		game.makeMove(mkc(1,4),	mkc(2,5)); 
		game.makeMove(mkc(2,4), mkc(8,4));
		
		assertEquals(redChariot, game.getPieceAt(mkc(3,6), BLACK));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,5),	mkc(3,6)));
		assertEquals(blackAdvisor, game.getPieceAt(mkc(3,6), BLACK));
	}
	
	@Test // 41
	public void gameEndsInADrawAfter25NonWinningRounds()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(2,1), mkc(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,1), mkc(2,1)));
		assertEquals(MoveResult.DRAW, game.makeMove(mkc(1,1), mkc(2,1)));
	}
	
	@Test //42
	public void capturingBlackGeneralResultsInRedWin()
	{
		game.makeMove(mkc(4,5), mkc(5,5)); //red
		game.makeMove(mkc(4,5), mkc(5,5));
		game.makeMove(mkc(5,5), mkc(6,5)); 
		game.makeMove(mkc(1,5), mkc(2,5)); 
		game.makeMove(mkc(6,5), mkc(7,5));
		game.makeMove(mkc(2,5),	mkc(3,5));
		assertEquals(blackGeneral, game.getPieceAt(mkc(8,5), RED));
		assertEquals(MoveResult.RED_WINS, game.makeMove(mkc(7,5), mkc(8,5)));
		assertEquals(redSoldier, game.getPieceAt(mkc(8,5), RED));
	}

	@Test //43
	public void capturingRedGeneralResultsInBlackWin()
	{
		game.makeMove(mkc(1,1), mkc(2,1));
		game.makeMove(mkc(4,5), mkc(5,5)); //black
		game.makeMove(mkc(4,5), mkc(5,5));
		game.makeMove(mkc(5,5), mkc(6,5)); 
		game.makeMove(mkc(1,5), mkc(2,5)); 
		game.makeMove(mkc(6,5), mkc(7,5));
		game.makeMove(mkc(2,5),	mkc(3,5));
		assertEquals(redGeneral, game.getPieceAt(mkc(8,5), BLACK));
		assertEquals(MoveResult.BLACK_WINS, game.makeMove(mkc(7,5), mkc(8,5)));
		assertEquals(blackSoldier, game.getPieceAt(mkc(8,5), BLACK));

	}
	
	
	@Test // 44
	public void generalCanPerformFlyingGeneralMove()
	{
		game.makeMove(makeCoordinate(4,5), makeCoordinate(5,5));
		game.makeMove(makeCoordinate(4,5), makeCoordinate(5,5));
		game.makeMove(makeCoordinate(5,5), makeCoordinate(6,5)); // black soldier captured
		game.makeMove(makeCoordinate(1,5), makeCoordinate(2,5)); // black king moves forward
		game.makeMove(makeCoordinate(6,5), makeCoordinate(7,5));
		game.makeMove(mkc(1,7),	mkc(3,9)); // dummy black move
		game.makeMove(makeCoordinate(7,5), makeCoordinate(8,5));
		game.makeMove(mkc(2,5), mkc(3,5));
				
		assertEquals(MoveResult.RED_WINS, game.makeMove(makeCoordinate(1,5), makeCoordinate(8,5)));
	}
	

	// helper methods (keep tests looking slim)
	private XiangqiCoordinate mkc(int rank, int file) {
		return TestCoordinate.makeCoordinate(rank, file);
	}

}
