package xiangqi.studenttapetri.versions.deltaxiangqi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static testutil.TestPiece.makePiece;
import static xiangqi.common.MoveResult.ILLEGAL;
import static xiangqi.common.MoveResult.OK;
import static xiangqi.common.XiangqiColor.BLACK;
import static xiangqi.common.XiangqiColor.RED;
import static xiangqi.common.XiangqiPieceType.ADVISOR;
import static xiangqi.common.XiangqiPieceType.CHARIOT;
import static xiangqi.common.XiangqiPieceType.ELEPHANT;
import static xiangqi.common.XiangqiPieceType.GENERAL;
import static xiangqi.common.XiangqiPieceType.SOLDIER;
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
	
	@Test // 1
	public void factoryProducesDeltaXiangqiGame()
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
	
	@Test // 3
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
	
	@Test // 4
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
	
	@Test // 5
	public void cannonMayNotMoveDiagonally()
	{
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,2), mkc(2,1)));
	}
	
	@Test // 6
	public void cannonMayNotJumpWithoutCapturing()
	{
		game.makeMove(mkc(3,2), mkc(7,2));
		game.makeMove(mkc(1,1), mkc(2,1)); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(7,2), mkc(7,4)));
	}
	
	@Test // 7
	public void cannonMayNotCaptureWithoutJumping()
	{
		game.makeMove(mkc(3,2), mkc(7,2));
		game.makeMove(mkc(1,1), mkc(2,1)); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(7,2), mkc(7,3)));
	}
	
	@Test // 8
	public void cannonMayCaptureWhenJumpingOnePiece()
	{
		game.makeMove(mkc(3,2), mkc(7,2));
		game.makeMove(mkc(1,1), mkc(2,1)); // dummy black move
		assertEquals(MoveResult.OK, game.makeMove(mkc(7,2), mkc(7,5)));
		assertEquals(redCannon, game.getPieceAt(mkc(7,5), RED));
	}
	
	@Test // 9
	public void cannonMayCaptureWhenJumpingMultiplePieces()
	{
		game.makeMove(mkc(3,2), mkc(7,2));
		game.makeMove(mkc(1,1), mkc(2,1)); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(7,2), mkc(7,7)));
	}
	
	@Test // 10
	public void horseCanMoveOneStepOrthogonallyAndThenOneStepDiagonally()
	{
		assertEquals(MoveResult.OK, game.makeMove(mkc(1,2), mkc(3,1))); 
		assertEquals(redHorse, game.getPieceAt(mkc(3,1), RED));
		
	}
	
	@Test //11
	public void horseMakesIllegalMove() {
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(1,2), mkc(2,2)));
	}
	
	@Test //12
	public void horseMayNotJumpOverPiece() 
	{
		game.makeMove(mkc(1,2), mkc(3,1));
		game.makeMove(mkc(1,1), mkc(2,1)); // dummy black move
		assertEquals(MoveResult.ILLEGAL, game.makeMove(mkc(3,1), mkc(2,3)));
	}
	
	
	private XiangqiCoordinate mkc(int rank, int file) {
		return TestCoordinate.makeCoordinate(rank, file);
	}
}
