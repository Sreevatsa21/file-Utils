/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	 Sreevatsa Pervela
 *	@since	12/5/2023
 */
public class City implements Comparable<City> {
	
	// fields
	private String name;
	private String state;
	private String designation;
	private int population;
	// constructor
	public City (String nam, String stateIn, String citTyp, int pop) {
		name = nam;
		state = stateIn;
		designation = citTyp;
		population = pop;
	}
	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	public void compareTo(City other){
		if (population != other.population)
			return (this.population- other.population);
		else if (state != other.state)
			return (this.state - other.state);
		else
			return(this.name - other.name);
	}
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	public boolean equals (City other) {
		if ( other != null && this.name == other.name && this.state 
			== other.state)
	}
	/**	Accessor methods */
	public String getCityName()
	{
		return this.name;
	}
	public String getStateName()
	{
		return this.state;
	}
	public String getCityType()
	{
		return this.designation;
	}
	public int getPop() {
		return this.population;
	}
	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
						population);
	}
}
