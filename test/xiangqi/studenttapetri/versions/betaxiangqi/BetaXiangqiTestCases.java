package xiangqi.studenttapetri.versions.betaxiangqi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import static testutil.TestCoordinate.makeCoordinate;
import xiangqi.XiangqiGameFactory;
import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiGameVersion;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;

public class BetaXiangqiTestCases
{
	
	private XiangqiGame game;
	BetaXiangqiGame beta;
	
	@Before
	public void setup()
	{
		game = XiangqiGameFactory.makeXiangqiGame(XiangqiGameVersion.BETA_XQ);
		beta = (BetaXiangqiGame) game; // used for debug
	}
	
	@Test
	public void factoryProducesBetaXiangqiGame()
	{
		assertNotNull(game);
	}
	
	@Test
	public void invalidCoordinatesMoveReturnIllegalMove()
	{
		// all invalid source + valid destination
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(-1, -1), makeCoordinate(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(-1, 3), makeCoordinate(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(-1, 6), makeCoordinate(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, 6), makeCoordinate(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(6, 6), makeCoordinate(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(6, 3), makeCoordinate(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(6, -1), makeCoordinate(3, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, -1), makeCoordinate(3, 3)));
		
		// all valid source + invalid destination
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, 3), makeCoordinate(-1, -1)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, 3), makeCoordinate(-1, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, 3), makeCoordinate(-1, 6)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, 3), makeCoordinate(3, 6)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, 3), makeCoordinate(6, 6)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, 3), makeCoordinate(6, 3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, 3), makeCoordinate(6, -1)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3, 3), makeCoordinate(3, -1)));
	}
	
	@Test
	public void invalidCoordinatesResultInAppropriateMessage()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(6, 6), makeCoordinate(3, 3)));
		assertTrue(game.getMoveMessage().contains("invalid"));
	}
	
	@Test
	public void redGeneralPlacedCorrectly()
	{
		XiangqiPiece returned = game.getPieceAt(makeCoordinate(1,3), XiangqiColor.RED);
		
		assertEquals(XiangqiPieceType.GENERAL, returned.getPieceType());
		assertEquals(XiangqiColor.RED, returned.getColor());
	}
	
	@Test 
	public void redSoldierPlacedCorrectly()
	{
		XiangqiPiece returned = game.getPieceAt(makeCoordinate(2,3), XiangqiColor.RED);
		assertEquals(XiangqiPieceType.SOLDIER, returned.getPieceType());
		assertEquals(XiangqiColor.RED, returned.getColor());
	}
	
	@Test
	public void redAdvisorsPlacedCorrectly()
	{
		XiangqiPiece returned1 = game.getPieceAt(makeCoordinate(1,2), XiangqiColor.RED);
		XiangqiPiece returned2 = game.getPieceAt(makeCoordinate(1,4), XiangqiColor.RED);
		
		assertEquals(XiangqiPieceType.ADVISOR, returned1.getPieceType());
		assertEquals(XiangqiColor.RED, returned1.getColor());

		assertEquals(XiangqiPieceType.ADVISOR, returned2.getPieceType());
		assertEquals(XiangqiColor.RED, returned2.getColor());
		
	}
	
	@Test
	public void redChariotsPlacedCorrectly()
	{
		XiangqiPiece returned1 = game.getPieceAt(makeCoordinate(1,1), XiangqiColor.RED);
		XiangqiPiece returned2 = game.getPieceAt(makeCoordinate(1,5), XiangqiColor.RED);
		
		assertEquals(XiangqiPieceType.CHARIOT, returned1.getPieceType());
		assertEquals(XiangqiColor.RED, returned1.getColor());

		assertEquals(XiangqiPieceType.CHARIOT, returned2.getPieceType());
		assertEquals(XiangqiColor.RED, returned2.getColor());	
	}
	
	@Test
	public void blackGeneralPlacedCorrectly()
	{
		XiangqiPiece returned = game.getPieceAt(makeCoordinate(1,3), XiangqiColor.BLACK);
		
		assertEquals(XiangqiPieceType.GENERAL, returned.getPieceType());
		assertEquals(XiangqiColor.BLACK, returned.getColor());
	}
	
	@Test 
	public void blackSoldierPlacedCorrectly()
	{
		XiangqiPiece returned = game.getPieceAt(makeCoordinate(2,3), XiangqiColor.BLACK);
		assertEquals(XiangqiPieceType.SOLDIER, returned.getPieceType());
		assertEquals(XiangqiColor.BLACK, returned.getColor());
	}
	
	@Test
	public void blackAdvisorsPlacedCorrectly()
	{
		XiangqiPiece returned1 = game.getPieceAt(makeCoordinate(1,2), XiangqiColor.BLACK);
		XiangqiPiece returned2 = game.getPieceAt(makeCoordinate(1,4), XiangqiColor.BLACK);
		
		assertEquals(XiangqiPieceType.ADVISOR, returned1.getPieceType());
		assertEquals(XiangqiColor.BLACK, returned1.getColor());

		assertEquals(XiangqiPieceType.ADVISOR, returned2.getPieceType());
		assertEquals(XiangqiColor.BLACK, returned2.getColor());
		
	}
	
	@Test
	public void blackChariotsPlacedCorrectly()
	{
		XiangqiPiece returned1 = game.getPieceAt(makeCoordinate(1,1), XiangqiColor.BLACK);
		XiangqiPiece returned2 = game.getPieceAt(makeCoordinate(1,5), XiangqiColor.BLACK);
		
		assertEquals(XiangqiPieceType.CHARIOT, returned1.getPieceType());
		assertEquals(XiangqiColor.BLACK, returned1.getColor());

		assertEquals(XiangqiPieceType.CHARIOT, returned2.getPieceType());
		assertEquals(XiangqiColor.BLACK, returned2.getColor());	
	}
	
	@Test 
	public void moveFromEmptyCoordinateIsIllegal()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3,3), makeCoordinate(3,4)));
	}
	
	@Test
	public void redIsAllowedToMoveRedPiece() 
	{	
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3)));
	}
	
	@Test 
	public void redIsNotAllowedToMoveBlackPiece()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(5,1), makeCoordinate(4,1)));
	}
	
	@Test
	public void blackIsAllowedToMoveBlackPiece() 
	{	
		// first move is red
		game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3)));
	}
	
	@Test 
	public void blackIsNotAllowedToMoveRedPiece()
	{
		// first move is red
		game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(5,1), makeCoordinate(4,1)));
	}
	
	@Test
	public void illegalMoveLetsPlayerRetry()
	{
		
		// red tries to make illegal move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(2,3), makeCoordinate(4,3)));
		
		// red can then retry
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3)));
		assertEquals(XiangqiColor.RED, game.getPieceAt(makeCoordinate(3,3),	XiangqiColor.RED).getColor());
	}
	
	
	@Test 
	public void soldierCanOnlyMoveOneStepForward()
	{
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(2,3), makeCoordinate(4,3)));
	}
	
	@Test 
	public void soldierCannotMoveSideways()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(2,3), makeCoordinate(2,4)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(2,3), makeCoordinate(2,2)));
	}
	
	@Test 
	public void chariotCanMoveVertically()
	{
		// one step forward
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		// three steps forward
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(4,1)));
		// one step backward
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		// three steps backward
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(4,1), makeCoordinate(1,1)));
	}
	
	@Test
	public void chariotCanMoveHorizontally()
	{
		game.makeMove(makeCoordinate(1,1), makeCoordinate(3,1)); // red chariot into position (3,1)
		game.makeMove(makeCoordinate(1,1), makeCoordinate(3,1)); // black chariot into position (3,1)
		
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,1), makeCoordinate(3,2)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,1), makeCoordinate(3,3)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,2), makeCoordinate(3,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,3), makeCoordinate(3,1)));
	}
	
	@Test
	public void chariotCannotJumpOverPiece() {
		
		game.makeMove(makeCoordinate(1,1), makeCoordinate(3,1)); // red into position
		game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3)); // black into position
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(3,1), makeCoordinate(3,5)));
	}
	
	@Test
	public void advisorCanOnlyMoveOneStepDiagonally() 
	{	
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,2), makeCoordinate(2,1)));
		assertEquals(XiangqiPieceType.ADVISOR, game.getPieceAt(makeCoordinate(2,1), XiangqiColor.RED).getPieceType());
		assertEquals(XiangqiPieceType.NONE, game.getPieceAt(makeCoordinate(1,2), XiangqiColor.RED).getPieceType());
		
		
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,4), makeCoordinate(2,5)));
		assertEquals(XiangqiPieceType.ADVISOR, game.getPieceAt(makeCoordinate(2,5), XiangqiColor.BLACK).getPieceType());
		
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(2,1), makeCoordinate(4,3))); // two steps diagonally is not okay
	}
	
	
	@Test 
	public void advisorCannotMoveVertically() 
	{	
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(1,2), makeCoordinate(2,2)));
	}
	
	@Test
	public void advisorCannotMoveHorizontally()
	{
		game.makeMove(makeCoordinate(1,2), makeCoordinate(2,1)); // set up red advisor to be moved
		game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3)); // black round irrelevant
		
		// try to move advisor horizontally
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(2,1), makeCoordinate(2,2)));
	}
	
	@Test
	public void generalCanOnlyMoveOneStepHorizontallyInPalace()
	{
		game.makeMove(makeCoordinate(1,1), makeCoordinate(3,1)); // move red chariot left of general out of the way
		game.makeMove(makeCoordinate(1,5), makeCoordinate(3,5)); // move black chariot right of general out of the way
		game.makeMove(makeCoordinate(1,2), makeCoordinate(2,1)); // move red advisor left of general out of the way
		game.makeMove(makeCoordinate(1,4), makeCoordinate(2,5)); // move black advisor right of general out of the way

		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,3), makeCoordinate(1,2)));
		assertEquals(XiangqiPieceType.GENERAL, game.getPieceAt(makeCoordinate(1,2), XiangqiColor.RED).getPieceType());

		// move black general 1 step right
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,3), makeCoordinate(1,4)));
		//System.out.println(game.getMoveMessage());
		assertEquals(XiangqiPieceType.GENERAL, game.getPieceAt(makeCoordinate(1,4), XiangqiColor.BLACK).getPieceType());	
		
		// fail to move red general 2 steps right, forward, and left
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(1,2), makeCoordinate(1,4)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(1,2), makeCoordinate(2,2)));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(1,2), makeCoordinate(1,1)));	
	}
	
	@Test
	public void chariotCanCapturePiece()
	{
		assertEquals(XiangqiColor.BLACK, game.getPieceAt(makeCoordinate(5,1), XiangqiColor.RED).getColor());
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(5,1)));
		assertEquals(XiangqiPieceType.CHARIOT, game.getPieceAt(makeCoordinate(5,1), XiangqiColor.RED).getPieceType());
		assertEquals(XiangqiColor.RED, game.getPieceAt(makeCoordinate(5,1), XiangqiColor.RED).getColor());
	}
	
	@Test
	public void advisorCanCapturePiece()
	{
		game.makeMove(makeCoordinate(1,5), makeCoordinate(4,5));
		assertEquals(XiangqiColor.RED, game.getPieceAt(makeCoordinate(2,1), XiangqiColor.BLACK).getColor());
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,2), makeCoordinate(2,1)));
		assertEquals(XiangqiColor.BLACK, game.getPieceAt(makeCoordinate(2,1), XiangqiColor.BLACK).getColor());
		assertEquals(XiangqiPieceType.ADVISOR, game.getPieceAt(makeCoordinate(2,1), XiangqiColor.BLACK).getPieceType());
	}
	
	@Test
	public void soldierCanCapturePiece()
	{
		game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3));
		assertEquals(XiangqiColor.RED, game.getPieceAt(makeCoordinate(3,3), XiangqiColor.BLACK).getColor());
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3)));
		assertEquals(XiangqiColor.BLACK, game.getPieceAt(makeCoordinate(3,3), XiangqiColor.BLACK).getColor());
	}
	
	@Test
	public void capturingOwnColorPieceIsIllegal() 
	{
		// chariot
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(1,1), makeCoordinate(1,2)));
		// general
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(1,3), makeCoordinate(1,2)));
		//advisor
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(1,2), makeCoordinate(2,3)));
		
		// soldier
		game.makeMove(makeCoordinate(1,1), makeCoordinate(3,1));
		game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)); // black
		game.makeMove(makeCoordinate(3,1), makeCoordinate(3,3)); // chariot now in front of soldier
		game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)); // black
		assertEquals(MoveResult.ILLEGAL, game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3)));
	}
	
	@Test
	public void gameEndsInADrawAfter10NonWinningRounds()
	{
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.DRAW, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
	}
	
	@Test 
	public void capturingBlackGeneralResultsInRedWin()
	{
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(3,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,3), makeCoordinate(4,3)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,1), makeCoordinate(1,1)));
		assertEquals(MoveResult.RED_WINS, game.makeMove(makeCoordinate(4,3), makeCoordinate(5,3)));
	}
	
	@Test 
	public void capturingRedGeneralResultsInBlackWin()
	{
		game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1));
		game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3));
		game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1));
		game.makeMove(makeCoordinate(3,3), makeCoordinate(4,3));
		game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1));
		assertEquals(MoveResult.BLACK_WINS, game.makeMove(makeCoordinate(4,3), makeCoordinate(5,3)));
	}
	
	@Test
	public void checkMateResultsInAWin()
	{
		game.makeMove(makeCoordinate(1,1), makeCoordinate(5,1));
		game.makeMove(makeCoordinate(2,3), makeCoordinate(3,3));
		game.makeMove(makeCoordinate(5,1), makeCoordinate(4,1));
		game.makeMove(makeCoordinate(1,4), makeCoordinate(2,3));
		game.makeMove(makeCoordinate(4,1), makeCoordinate(3,1));
		game.makeMove(makeCoordinate(2,3), makeCoordinate(3,4));
		assertEquals(MoveResult.RED_WINS, game.makeMove(makeCoordinate(3,1), makeCoordinate(5,1)));
	}
	
	@Test
	public void generalCanCaptureHisWayOutOfCheck()
	{
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(5,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(5,1), makeCoordinate(5,2)));
	}
	
	@Test
	public void generalCanPerformFlyingGeneralMove()
	{
		game.makeMove(makeCoordinate(1,2), makeCoordinate(2,1));
		game.makeMove(makeCoordinate(1,4), makeCoordinate(2,5));
		game.makeMove(makeCoordinate(1,3), makeCoordinate(1,2));
		game.makeMove(makeCoordinate(1,3), makeCoordinate(1,4));
		assertEquals(MoveResult.RED_WINS, game.makeMove(makeCoordinate(1,2), makeCoordinate(5,2)));
	}

}
