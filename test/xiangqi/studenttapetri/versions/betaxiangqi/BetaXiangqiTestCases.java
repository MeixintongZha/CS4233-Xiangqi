package xiangqi.studenttapetri.versions.betaxianqi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import xiangqi.XiangqiGameFactory;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiGameVersion;

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
	
	
	
	

}
