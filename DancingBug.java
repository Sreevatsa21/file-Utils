/** 
 * @author Sreevatsa Pervela
 * @since 3/12/2024
 */

import info.gridworld.actor.Bug;

/**
 * A <code>BoxBug</code> traces out a square "box" of a given size. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class DancingBug extends Bug
{
    private int counter;
    private int sideLength;
    private int[] arr;
    

    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public DancingBug(int[] arr)
    {
        counter = 0;
        this.arr = arr;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
		int turns = arr[counter];
		for(int i = 0; i < turns; i++){
			turn();
		}
		counter ++;
		if(counter == arr.length) counter = 0;
		super.act();
    }
}
