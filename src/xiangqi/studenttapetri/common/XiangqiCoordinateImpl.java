/**
 * 
 */
package xiangqi.studenttapetri.common;

import xiangqi.common.XiangqiCoordinate;

/**
 * Implementation of XiangqiCoordinate interface. 
 * 
 * <p>
 * This class provides a simple way of keeping track of (rank, file) coordinates. It 
 * implements methods to be used as a key in a hashmap.
 * </p>
 * 
 * @author Tim Petri
 * @version Feb 6, 2017
 */
public class XiangqiCoordinateImpl implements XiangqiCoordinate
{
	
	private final int rank;
	private final int file;

	public static XiangqiCoordinate makeCoordinate(int file, int rank)
	{
		return new XiangqiCoordinateImpl(file, rank);
	}
	
	public XiangqiCoordinateImpl(XiangqiCoordinate xc) 
	{
		this(xc.getRank(), xc.getFile());
	}
	
	public XiangqiCoordinateImpl(int rank, int file)
	{
		this.rank = rank;
		this.file = file;
	}
	
	/* 
	 * @see xiangqi.common.XiangqiCoordinate#getRank()
	 */
	@Override
	public int getRank()
	{
		return rank;
	}

	/* 
	 * @see xiangqi.common.XiangqiCoordinate#getFile()
	 */
	@Override
	public int getFile()
	{
		return file;
	}
	
	@Override
	public String toString()
	{
		return "(" + getRank() + ", " + getFile() + ")";
	}
	
	@Override
	public  int hashCode()
	{
		// computes hash using 31*x + y rule..
		
		final int prime = 31;
		
		int result = 17;
		result = prime * result + file;
		result = prime * result + rank;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		
		if (obj == null) return false;
		
		if (obj.getClass() != this.getClass())
			return false;
		
		final XiangqiCoordinate other = (XiangqiCoordinate) obj;
		if (this.getRank() != other.getRank()) 
			return false;
		if (this.getFile() != other.getFile())
			return false;
		
		return true;
	}

}
