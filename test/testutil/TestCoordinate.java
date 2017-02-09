package testutil;

import xiangqi.common.XiangqiCoordinate;

public class TestCoordinate implements XiangqiCoordinate
{
	private final int rank;
	private final int file;
	
	private TestCoordinate(int rank, int file)
	{
		this.rank = rank;
		this.file = file;
	}
	
	public static XiangqiCoordinate makeCoordinate(int rank, int file)
	{
		return new TestCoordinate(rank, file);
	}
	
	@Override
	public int getRank()
	{
		return rank;
	}
	
	@Override
	public int getFile()
	{
		return file;
	}
	
	@Override
	public String toString() {
		String result = "(" + getRank() + ", " + getFile() + ")";
		return result;
	}
}