package xiangqi.studenttapetri.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.XiangqiCoordinateImpl;
import xiangqi.studenttapetri.common.XiangqiPieceImpl;

/**
 * Board that deals with logic involving coordinates and pieces in Xiangqi.
 * 
 * This board accepts moves and logic accompanied by an aspect. Internally, it
 * normalizes all coordinates in order to keep track of the absolute positions of 
 * pieces, instead of their aspect positions. 
 * 
 * @author Tim Petri
 * @version Feb 19, 2017
 */
public class XiangqiBoard
{
	private XiangqiColor DEFAULT_NORMAL = XiangqiColor.RED;
	
	private int ranks;
	private int files;
	private XiangqiColor normal;
	
	private Map<XiangqiCoordinateImpl, XiangqiPieceImpl> board;
	
	public XiangqiBoard(int ranks, int files) {
		this.ranks = ranks;
		this.files = files;
		this.normal = DEFAULT_NORMAL;
		board = new HashMap<>();
	}

	/**
	 * Puts the given piece at the given coordinate, assuming the player's perspective that is given.
	 * @param where the coordinates 
	 * @param piece the piece 
	 * @param aspect the view from which the piece is placed
	 */
	public void putPiece(XiangqiCoordinate where, XiangqiPieceImpl piece, XiangqiColor aspect)
	{
		XiangqiCoordinateImpl loc = normalizeCoordinate(where, aspect);
		board.put(loc, piece);	
	}
	
	/**
	 * Returns the piece that is current residing at the given coordinate, assuming the 
	 * player's perspective that is given.
	 * 
	 * @param where the coordinates 
	 * @param aspect the view from which the piece is placed
	 * @return the piece at the specified coordinates
	 */
	public XiangqiPieceImpl getPieceAt(XiangqiCoordinate where, XiangqiColor aspect)
	{
		// normalize coordinate
		XiangqiCoordinateImpl loc = normalizeCoordinate(where, aspect);
		
		if (board.get(loc) == null) {
			return XiangqiPieceImpl.makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE);
		}
		return board.get(loc);
	}
	
	/**
	 * Return true if the given coordinate's rank and file fall within bounds of board.
	 * @param location the coordinates
	 * @return the validity of coordinate
	 */
	public boolean isValidCoordinate(XiangqiCoordinate location)
	{
		int rank = location.getRank();
		int file = location.getFile();
		
		// System.out.println("(" + rank + ", " +  file + ")");
	
		return (rank >= 1 && rank <= this.ranks && 
				file >= 1 && file <= this.files);
	}

	/**
	 * Moves the piece residing at the given source coordinate to the given destination,
	 * coordinate assuming the given player's perspective.
	 * 
	 * @param source from where the piece is moved
	 * @param destination to where the piece is moved
	 * @param aspect the view from which the piece is moved
	 */
	public void movePiece(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiColor aspect)
	{
		// normalize coordinates
		XiangqiCoordinateImpl from = normalizeCoordinate(source, aspect);
		XiangqiCoordinateImpl to = normalizeCoordinate(destination, aspect);
		
		// make move
		board.put(to, board.get(from));
		board.remove(from);
		
		return;
		
	}
	
	private XiangqiCoordinateImpl normalizeCoordinate(XiangqiCoordinate coord, XiangqiColor aspect)
	{
		if (aspect == normal) return new XiangqiCoordinateImpl(coord);

		int normalizedRank = this.ranks - coord.getRank() + 1;
		int normalizedFile = this.files - coord.getFile() + 1;

		return new XiangqiCoordinateImpl(normalizedRank, normalizedFile);
	}
	
	/**
	 * Returns true if the general with the given color has been captured.
	 * 
	 * @param generalColor 
	 * @return whether the general is still on the board
	 */
	public boolean isGeneralCaptured(XiangqiColor generalColor)
	{
		return (getGeneralCoordinates(generalColor) == null);
	}

	/**
	 * Returns true if the general with the given color is in check mate.
	 * @param generalColor
	 * @return whether the general is in check mate
	 */
	public boolean isGeneralInCheckMate(XiangqiColor generalColor)
	{
		if (isGeneralThreatened(generalColor)) {
			if (canGeneralMoveOutOfCheck(generalColor)) {
				return false;
			}
			if (canCheckBeStopped(generalColor)) {
				return false;
			}
			
			// no saving him
			return true;
		}

		return false;
	}
	
	// Returns true if the move of any piece of given color stops general from being in check.
	private boolean canCheckBeStopped(XiangqiColor generalColor)
	{
		boolean result = false;
		
		Set<XiangqiCoordinateImpl> occupiedCoordinates = new HashSet<>(board.keySet());
		
		for (XiangqiCoordinateImpl loc: occupiedCoordinates) 
		{	
			XiangqiPieceImpl piece = board.get(loc);
			
			if (piece.getColor() != generalColor || piece.getPieceType() == XiangqiPieceType.GENERAL) 
				continue; // only interested in same color pieces that aren't general
			
			Set<XiangqiCoordinateImpl> validDestinations = getValidMovesForPiece(piece, loc);
			
			for (XiangqiCoordinateImpl to: validDestinations) {
				
				// temporarily move piece (might involve capturing a piece)
				XiangqiPieceImpl temp = board.get(to);
				board.put(to, piece);
				board.remove(loc);
				
				// see if he is still in check
				if (!isGeneralThreatened(generalColor))  {
					result = true;
				}
				
				// revert changes
				board.put(loc, piece);
				board.remove(to);
				if (temp != null) board.put(to, temp);
			
				// early exit
				if (result) break;
			}
			
			if (result) break;
		}
		
		return result;
	}

	// Returns true if any of the valid moves a general can make takes him out of check.
	private boolean canGeneralMoveOutOfCheck(XiangqiColor generalColor)
	{
		XiangqiCoordinateImpl generalLocation = getGeneralCoordinates(generalColor);
		XiangqiPieceImpl general = board.get(generalLocation);
		
		Set<XiangqiCoordinateImpl> validDestinations = getValidMovesForPiece(general, generalLocation);
		boolean result = false;
		
		for (XiangqiCoordinateImpl to: validDestinations) {
			
			// temporarily move general (might involve capturing a piece)
			XiangqiPieceImpl temp = board.get(to);
			board.put(to, general);
			board.remove(generalLocation);
			
			// see if he is still in check
			if (!isGeneralThreatened(generalColor))  {
				result = true;
			}
			
			// revert changes
			board.put(generalLocation, general);
			board.remove(to);
			if (temp != null) board.put(to, temp);
		
			// early exit
			if (result) break;
		}
		
		return result;
	}

	// Returns true if the given colored general can be captured by an enemy piece
	private boolean isGeneralThreatened(XiangqiColor generalColor)
	{
		XiangqiCoordinateImpl generalLocation = getGeneralCoordinates(generalColor);
		
		for (XiangqiCoordinateImpl loc: board.keySet()) 
		{	
			XiangqiPieceImpl piece = board.get(loc);	
			
			if (piece.getColor() != generalColor && piece.isValidMove(loc, generalLocation)) {

				return true;
				
			}
		}
		return false;
	}
	
	// Get coordinates of the general in given color (null if general does not exist)
	private XiangqiCoordinateImpl getGeneralCoordinates(XiangqiColor generalColor) {

		for (XiangqiCoordinateImpl loc: board.keySet()) 
		{	
			if (board.get(loc).getPieceType() == XiangqiPieceType.GENERAL &&
					board.get(loc).getColor() == generalColor)
				return loc;
		}
		
		return null;
	}
	
	// Generate a set of normalized coordinates to which the given piece can move from given location
	private Set<XiangqiCoordinateImpl> getValidMovesForPiece(XiangqiPieceImpl piece, XiangqiCoordinateImpl loc) 
	{
		Set<XiangqiCoordinateImpl> all = getAllValidCoordinates();
		Set<XiangqiCoordinateImpl> valid = new HashSet<>();
		
		// XiangqiCoordinateImpl norm = normalizeCoordinate(loc, piece.getColor());
		
		for (XiangqiCoordinateImpl c: all)
			
			if (!loc.equals(c)) {
				
				// if the piece is black, we need to unnormalize the coordinates for testing
				XiangqiCoordinateImpl normFrom = normalizeCoordinate(loc, piece.getColor());
				XiangqiCoordinateImpl normTo = normalizeCoordinate(c, piece.getColor());
				if (piece.isValidMove(normFrom, normTo)) {
					valid.add(c);
				}
			}
		
		return valid;
	}
	
	// Generate a set of all valid coordinates (on board)
	private Set<XiangqiCoordinateImpl> getAllValidCoordinates() 
	{
		Set<XiangqiCoordinateImpl> all = new HashSet<>();
		
		for (int r = this.ranks; r > 0; r--) {
			for (int f = 1; f <= this.files; f++) {
				all.add(new XiangqiCoordinateImpl(r, f));	
			}
		}
		return all;
	}

}
