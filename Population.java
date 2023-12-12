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
                selectionSort(cities);
                printIt();
                break;
            case 2:
                mergeSort(cities, 0, cities.size() - 1, 1);
                printIt();
                break;
            case 3:
                insertionSort(cities);
                printIt();
                break;
            case 4:
                mergeSort(cities, 0, cities.size() - 1, 2);
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
                mergeSort(cities, 0, cities.size() - 1, 1);
                printItWithStateFilter(stateName);
                break;
            case 6:
                String filter = Prompt.getString("Enter city name ->");
                mergeSort(cities, 0, cities.size() - 1, 1);
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
    /**
     * Swaps two City objects in City List
     *
     * @param list a of Integer objects
     * @param x    index of first object to swap
     * @param y    index of second object to swap
     */
    private void swap(List<City> list, int x, int y)
    {
        City temp = list.get(x);
        list.set(x, list.get(y));
        list.set(y, temp);
    }

    /**
     * Selection Sort algorithm - in ascending order
     *
     * @param list of City objects to sort
     */
    public void selectionSort(List<City> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++)
            {
                if (list.get(j).getPopulation() < list.get(minIndex).getPopulation())
                {
                    minIndex = j;
                }
            }
            swap(list, minIndex, i);
        }
    }

    /**
     * Insertion Sort algorithm to sort city names in ascending order
     *
     * @param list array of Integer objects to sort
     */
    public void insertionSort(List<City> list)
    {
        for (int i = 1; i < list.size() - 1; i++)
        {
            City city = list.get(i);

            int j = i - 1;
            while (j >= 0 && city.getName().compareTo(list.get(j).getName()) < 0)
            {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, city);
        }
    }
	/**
	 *	Merge Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 */
	public void mergeSort(List<City> list, int left, int right, int sortType) {
		 if (left < right)
        {

            int mid = left + (right - left) / 2;

            mergeSort(list, left, mid, sortType);
            mergeSort(list, mid + 1, right, sortType);

            merge(list, left, mid, right, sortType);
        }
	}
	public static void merge(List<City> list, int leftIndex, int mid, int rightIndex, int sortType)
    {

        // build and fill temp lists for left and right and merge them after sorting

        int leftListSize = mid - leftIndex + 1;
        int rightListSize = rightIndex - mid;

        List<City> leftList = new ArrayList<>();
        List<City> rightList = new ArrayList<>();

        for (int i = 0; i < leftListSize; ++i)
            leftList.add(list.get(leftIndex + i));

        for (int j = 0; j < rightListSize; ++j)
            rightList.add(list.get(mid + 1 + j));

        int i = 0, j = 0;

        int k = leftIndex;

        while (i < leftListSize && j < rightListSize)
        {
            //population descending order
            if (sortType == 1)
            {
                if (leftList.get(i).getPopulation() >= rightList.get(j).getPopulation())
                {
                    list.set(k, leftList.get(i));
                    i++;
                }
                else
                {
                    list.set(k, rightList.get(j));
                    j++;
                }
                k++;
            }
            // city name descending order
            else if (sortType == 2)
            {
                if (leftList.get(i).getName().compareTo(rightList.get(j).getName()) >= 0)
                {
                    list.set(k, leftList.get(i));
                    i++;
                }
                else
                {
                    list.set(k, rightList.get(j));
                    j++;
                }
                k++;
            }
        }

        // add remaining values
        while (i < leftListSize)
        {
            list.set(k, leftList.get(i));
            i++;
            k++;
        }

        // add remaining values
        while (j < rightListSize)
        {
            list.set(k, rightList.get(j));
            j++;
            k++;
        }
    }
	
}
