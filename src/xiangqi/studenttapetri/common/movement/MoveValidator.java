package xiangqi.studenttapetri.common.movement;

import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;


/**
 * The MoveValidator interface is used for validating moves of Xiangqi piences.
 * A class that realizes this interface will hold the logic for validating any move that
 * piece can make on the Xiangqi beta board.
 * 
 * @author Tim Petri	
 * @version Feb 5, 2017
 */
public interface MoveValidator
{

	/**
	 * This method returns true if moving a piece from the source to destination is considered valid.
	 * Each class that realizes this interface contains its own logic for validating moves.
	 * 
	 * @param source from where the the piece is moved
	 * @param destination to where the piece is moved
	 * @param piece the piece (context) that holds this move validator, and will call this method
	 * 
	 * @return whether the move is valid
	 */
	boolean isValid(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece);

}
