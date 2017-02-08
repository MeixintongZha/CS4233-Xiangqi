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
	
	@Before
	public void setup()
	{
		game = XiangqiGameFactory.makeXiangqiGame(XiangqiGameVersion.BETA_XQ);
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
	public void chariotCanMoveVerticallyForward()
	{
		// one step forward
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1)));
		// three steps forward
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(1,1), makeCoordinate(4,1)));
	}
	
	@Test 
	public void chariotCanMoveVerticallyBackward()
	{
		game.makeMove(makeCoordinate(1,1), makeCoordinate(2,1));
		game.makeMove(makeCoordinate(1,1), makeCoordinate(4,1));
		
		// one step backward
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(2,1), makeCoordinate(1,1)));
		// three steps backward
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(4,1), makeCoordinate(1,1)));
	}
	
	@Test
	public void chariotCanMoveHorizontally()
	{
		game.makeMove(makeCoordinate(1,1), makeCoordinate(3,1)); // red into position
		game.makeMove(makeCoordinate(1,1), makeCoordinate(3,1)); // black into position
		
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,1), makeCoordinate(3,2)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,1), makeCoordinate(3,3)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,2), makeCoordinate(3,1)));
		assertEquals(MoveResult.OK, game.makeMove(makeCoordinate(3,3), makeCoordinate(3,1)));
	}
	
	public void chariotCannotOverPiece() {
		return;
	}

	
}
