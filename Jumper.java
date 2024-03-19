/*
 * Version of bug
 * 
 * @author Pranav Belligundu
 */

import info.gridworld.actor.*;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.awt.Color;

public class Jumper extends Bug{
	int turnCount;
	
	 /**
     * Constructs a blue JumperBug.
     */
	public Jumper(){
		setColor(Color.BLUE);
		turnCount = 0;
	}
	/**
	 * moves the bug around
	 */
	public void act(){
		if(turnCount > 8) removeSelfFromGrid();
		if (canJump()){
            jump();
            turnCount = 0;
        }
        else{
            turn();
            turnCount++;
		}
	}  
	/**
	 * 	Checks if the bug can jump two locations ahead
	 * 	@return boolean 		true/false 
	 */
    public boolean canJump()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next1 = loc.getAdjacentLocation(getDirection());
        Location next = next1.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        return (neighbor == null) || (neighbor instanceof Flower);
    }
    /**
     * 	Moves the bug 2 spaces ahead and places a blossom 
     * 	
     */
    public void jump()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next1 = loc.getAdjacentLocation(getDirection());
        Location next = next1.getAdjacentLocation(getDirection());
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
        Blossom flower = new Blossom();
        flower.putSelfInGrid(gr, loc);
    }
}
