/** 
 * @author Sreevatsa Pervela
 * @since 3/12/2024
 */

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains box bugs. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class DancingBugRunner
{
	private static int[] turns = { 1, 0, 0, 0, 1, 0, 0, 3, 4,
							4, 0, 0, 1, 0, 3, 2, 0, 7,
							0, 0, 0, 3, 2, 1 };
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        DancingBug alice = new DancingBug(turns);
        alice.setColor(Color.ORANGE);
        world.add(new Location(7, 8), alice);
        world.show();
    }
}
