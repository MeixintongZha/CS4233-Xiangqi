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
	
	private XiangqiCoordinate redGeneral;
	private XiangqiCoordinate blackGeneral;
	
	private Map<XiangqiCoordinateImpl, XiangqiPieceImpl> board;
	private Map<XiangqiPieceImpl, XiangqiCoordinateImpl> activeCoordinates;
	
	BetaXiangqiBoard() {
		
		board = new HashMap<>();
	}

	public void putPiece(XiangqiCoordinate where, XiangqiPieceImpl piece, XiangqiColor aspect)
	{
		XiangqiCoordinateImpl loc = normalizeCoordinate(where, aspect);
		board.put(loc, piece);	
		
		if (piece.getPieceType() == XiangqiPieceType.GENERAL) {
			
			if (piece.getColor() == XiangqiColor.RED) 
				redGeneral = loc;
			else 
				blackGeneral = loc;
		}
	}
	
	public XiangqiPieceImpl getPieceAt(XiangqiCoordinate where, XiangqiColor aspect)
	{
		// normalize coordinate
		XiangqiCoordinateImpl loc = normalizeCoordinate(where, aspect);
		
		if (board.get(loc) == null) {
			return XiangqiPieceImpl.makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE);
		}
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
	
	public void printBoard() 
	{	
		String s = "";
		
		for (int r = RANKS; r > 0; r--) {
			for (int f = 1; f <= FILES; f++) {
				XiangqiCoordinateImpl c = new XiangqiCoordinateImpl(r, f);
				if (board.get(c) == null)
					s += "[  ]";
				else {
					s += "[";
					s += (board.get(c).getColor() == XiangqiColor.RED) ? "r" : "b";
					s += board.get(c).getPieceType().getSymbol();
					s += "]";
				}
			}
			s+= "\n";
		}
		System.out.println(s);
	}
}
