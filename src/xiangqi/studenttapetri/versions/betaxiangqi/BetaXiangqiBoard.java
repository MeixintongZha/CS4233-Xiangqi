/**
 * 
 */
package xiangqi.studenttapetri.versions.betaxiangqi;

import java.util.HashMap;
import java.util.Map;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.XiangqiCoordinateImpl;
import xiangqi.studenttapetri.common.XiangqiPieceImpl;

/**
 * Board that deals with logic involving coordinates and pieces.
 * 
 * This board accepts moves and logic accompanied by an aspect. Internally, it then
 * normalizes all coordinates in order to keep track of the absolute positions of 
 * pieces, instead of their aspect positions. 
 * 
 * @author Tim Petri
 * @version Feb 7, 2016
 */
public class BetaXiangqiBoard
{
	private final int FILES = 5;
	private final int RANKS = 5;
	
	private XiangqiColor normal = XiangqiColor.RED;
	
	private Map<XiangqiCoordinateImpl, XiangqiPieceImpl> board;
	
	BetaXiangqiBoard() {
		
		board = new HashMap<>();
		// System.out.println("Beta board constructed");
	}
	
	// TODO: type of piece?
	public void putPiece(XiangqiCoordinate where, XiangqiPieceImpl piece, XiangqiColor aspect)
	{
		
		XiangqiCoordinateImpl loc = normalizeCoordinate(where, aspect);
		// System.out.println("Normalized " + loc.getRank() + ", " + loc.getFile());
		board.put(loc, piece);
		
	}
	
	public XiangqiPieceImpl getPieceAt(XiangqiCoordinate where, XiangqiColor aspect)
	{
		
		// normalize coordinate
		XiangqiCoordinateImpl loc = normalizeCoordinate(where, aspect);
		
		if (board.get(loc) == null) {
			return XiangqiPieceImpl.makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE);
		}
		
		// TODO: should probably not return actual reference
		return board.get(loc);
	}
	
	public boolean isValidCoordinate(XiangqiCoordinate source)
	{
		
		int rank = source.getRank();
		int file = source.getFile();
	
		return (rank >= 1 && rank <= RANKS && 
				file >= 1 && file <= FILES);
	}
	
	private XiangqiCoordinateImpl normalizeCoordinate(XiangqiCoordinate coord, XiangqiColor aspect)
	{
		if (aspect == normal) return new XiangqiCoordinateImpl(coord);

		int normalizedRank = RANKS - coord.getRank() + 1;
		int normalizedFile = FILES - coord.getFile() + 1;

		return new XiangqiCoordinateImpl(normalizedRank, normalizedFile);

	}

	public void movePiece(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiColor aspect)
	{
		// normalize coordinates
		XiangqiCoordinateImpl from = normalizeCoordinate(source, aspect);
		XiangqiCoordinateImpl to = normalizeCoordinate(destination, aspect);
		
		// make move
		board.put(to, board.get(from));
		board.put(from, null);
		
		return;
		
	}
}
