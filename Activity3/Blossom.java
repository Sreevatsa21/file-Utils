/*
 * A version of a flower
 * 
 * @author Pranav Belligundu
 */
 
import info.gridworld.actor.Flower;
import java.awt.Color;

public class Blossom extends Flower{
	int lifetime;
	
	/**
     * Constructs a flower of a green color, and lifetime
     * @param lifetime 		lifetime of the blossom, dies after reaches
     * 						0
     */
	public Blossom(int lifetime){
		this.lifetime = lifetime;
		setColor(Color.GREEN);
	}
	
	/**
     * Constructs a green flower and lifetime at 10.
     */
	public Blossom(){
		lifetime = 10;
		setColor(Color.GREEN);
	}
	
	/**
     * Causes the color of this flower to darken, and after lifetime
     * reaches 0, it removes itself from the grid.
     */
	public void act(){
		if(lifetime <= 0) this.removeSelfFromGrid();
		lifetime --;
		super.act();
	}
	
}
