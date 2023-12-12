/**
 * City data - the city name, state name, location designation,
 * and population est. 2017
 *
 * @author Sreevatsa Pervela
 * @since 12/4/2023
 */
public class City implements Comparable<City>
{

    // private fields
    private String name;
    private String state;
    private int population;
    private String designation;

    // constructor
    public City(String state, String name, String designation, int population)
    {
        this.name = name;
        this.state = state;
        this.designation = designation;
        this.population = population;
    }

    /**
     * Compare two cities population
     *
     * @param other the other City to compare
     * @return the following value:
     * If populations are different, then returns (this.population - other.getPopulation())
     * else if states are different, then returns (this.state.compareTo(other.getState())
     * else returns (this.name.compareTo(other.getName())
     */
    public int compareTo(City other)
    {
        if (this.population != other.getPopulation())
        {
            return this.population - other.getPopulation();
        }
        else if (!this.state.equals(other.getState()))
        {
            return this.state.compareTo(other.getState());
        }

        return this.name.compareTo(other.getName());
    }

    /**
     * Equal city name and state name
     *
     * @param other the other City to compare
     * @return true if city name and state name equal; false otherwise
     */
    public boolean equals(Object other)
    {
        City otherCity = (City) other;

        if (this.name.equals(otherCity.getName()) && this.state.equals(otherCity.getState()))
        {
            return true;
        }

        return false;
    }

    /**
     * Accessor methods
     */

    public String getName()
    {
        return name;
    }

    public String getDesignation()
    {
        return designation;
    }

    public String getState()
    {
        return state;
    }

    public int getPopulation()
    {
        return population;
    }


    /**
     * toString
     */
    @Override
    public String toString()
    {
        return String.format("%-22s %-22s %-12s %,12d", state, name, designation, population);
    }
}
