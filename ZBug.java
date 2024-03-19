/** 
 * 
 * @author Sreevatsa Pervela
 */

import info.gridworld.actor.Bug;

/**
 * A <code>BoxBug</code> traces out a square "box" of a given size. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class ZBug extends Bug
{
    private int steps;
    private int sideLength;
    private int phaseCounter = 0;

    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public ZBug(int length)
    {
		setDirection(90);
        steps = 0;
        sideLength = length;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
		if(!canMove() ||  phaseCounter > 2){
			setDirection(90);
			return;
		}
        if (steps < sideLength && canMove())
        {
            move();
            steps++;
        }
        else if(phaseCounter == 0){
			turn();
            turn();
            turn();
            steps = 0;
            phaseCounter++;
		}
        else
        {
			turn();
            turn();
            turn();
            turn();
            turn();
            steps = 0;
            phaseCounter++;
        }
    }
}
