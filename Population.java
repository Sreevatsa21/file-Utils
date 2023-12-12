import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Population - <This is a program which tells >
 * <p>
 * Requires FileUtils and Prompt classes.
 *
 * @author Sreevatsa Pervela
 * @since 12/4/2023
 */
public class Population
{

    // List of cities
    private List<City> cities;

    // Sort Methods
    private SortMethods sortMethods = new SortMethods();

    // US cities population data file
    private static final String DATA_FILE = "./usPopData2017.txt";

    public static void main(String[] args)
    {
        Population p = new Population();
        p.printIntroduction();
        int choice = 0;
        p.getCitiesInfo();
        do
        {
            choice = p.printMenu();
            p.run(choice);
        } while (choice != 9);
    }


    /**
     * Prints the introduction to Population
     */
    public void printIntroduction()
    {
        System.out.println("   ___                  _       _   _");
        System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
        System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
        System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
        System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
        System.out.println("           |_|");
        System.out.println();
    }

    /**
     * Print out the choices for population sorting
     */
    public int printMenu()
    {
        System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
        System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
        System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
        System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
        System.out.println("5. Fifty most populous cities in named state");
        System.out.println("6. All cities matching a name sorted by population");
        System.out.println("9. Quit");

        int integer = 0;
        Prompt pr = new Prompt();
        do
        {
            integer = pr.getInt("Enter selection ");
        } while (integer > 9);

        return integer;
    }

    public void getCitiesInfo()
    {
        cities = new ArrayList<City>();

        FileUtils file = new FileUtils();
        Scanner input = file.openToRead(DATA_FILE).useDelimiter("[\t\n]");
        while (input.hasNext())
        {
            City city = new City(input.next(), input.next(), input.next(), input.nextInt());
            cities.add(city);
        }
        System.out.println(cities.size() + " cities in database.\n");
    }

    public void run(int choice)
    {
        long startMillisec = System.currentTimeMillis();
        switch (choice)
        {
            case 1:
                sortMethods.selectionSort(cities);
                printIt();
                break;
            case 2:
                sortMethods.mergeSort(cities, 0, cities.size() - 1, 1);
                printIt();
                break;
            case 3:
                sortMethods.insertionSort(cities);
                printIt();
                break;
            case 4:
                sortMethods.mergeSort(cities, 0, cities.size() - 1, 2);
                printIt();
                break;
            case 5:
                boolean isStateFound = false;
                String stateName;
                do
                {
                    stateName = Prompt.getString("Enter state name (ie. Alabama) ->");
                    isStateFound = findState(stateName);
                    if (!isStateFound)
                    {
                        System.out.printf("ERROR: %s is not valid\n",stateName);
                    }
                } while(!isStateFound);
                startMillisec = System.currentTimeMillis();
                sortMethods.mergeSort(cities, 0, cities.size() - 1, 1);
                printItWithStateFilter(stateName);
                break;
            case 6:
                String filter = Prompt.getString("Enter city name ->");
                sortMethods.mergeSort(cities, 0, cities.size() - 1, 1);
                printItWithFilter(filter);
                break;
            case 9:
                System.out.println("Thank you for using Population!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input.");
        }
        long endMillisec = System.currentTimeMillis();
        long timeElapsed = endMillisec - startMillisec;

        System.out.printf("\nElapsed Time: %d milliseconds\n\n", timeElapsed);

    }

    private boolean findState(String stateName)
    {
        for (int i = 0; i < cities.size(); i++)
        {
            if (cities.get(i).getState().equalsIgnoreCase(stateName))
            {
                return true;
            }
        }

        return false;
    }

    private void printIt()
    {
        System.out.printf("\n\t%-30s\t%-30s\t%-30s\t%10s ", "State", "City", "Type", "Population");
        for (int i = 0; i < 50; i++)
        {
            City city = cities.get(i);
            System.out.printf("\n%2d:\t%-30s\t%-30s\t%-30s\t%,10d", i + 1, city.getState(), city.getName(), city.getDesignation(), city.getPopulation());
        }
    }

    private void printItWithFilter(String filter)
    {
        int count = 1;
        System.out.printf("\n\t%-30s\t%-30s\t%-30s\t%10s ", "State", "City", "Type", "Population");
        for (int i = 0; i < cities.size(); i++)
        {
            City city = cities.get(i);
            if (city.getName().contains(filter))
            {
                System.out.printf("\n%2d:\t%-30s\t%-30s\t%-30s\t%,10d", count, city.getState(), city.getName(), city.getDesignation(), city.getPopulation());
                count++;
                if (count == 51)
                {
                    return;
                }
            }
        }
    }

    private boolean printItWithStateFilter(String stateName)
    {
        int count = 1;
        boolean result = false;
        System.out.printf("\n\t%-30s\t%-30s\t%-30s\t%10s ", "State", "City", "Type", "Population");
        for (int i = 0; i < cities.size(); i++)
        {
            City city = cities.get(i);
            if (city.getState().equalsIgnoreCase(stateName))
            {
                result = true;
                System.out.printf("\n%2d:\t%-30s\t%-30s\t%-30s\t%,10d", count, city.getState(), city.getName(), city.getDesignation(), city.getPopulation());
                count++;
                if (count == 51)
                {
                    return result;
                }
            }
        }

        return result;
    }
}
