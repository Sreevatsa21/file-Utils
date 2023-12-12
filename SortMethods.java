/**
 *	SortMethods - Sorts an array of Integers in ascending order.
 *
 *	@author Sreevatsa Pervela
 *	@since	11/30/2023
 */
public class SortMethods {
	
	/**
	 *	Bubble Sort algorithm - in ascending order
	 *	@param arr		array of Integer objects to sort
	 */
	public void bubbleSort(Integer [] arr) {
		for (int outer = arr.length-1; outer > 0; outer--) 
			for (int inner = 0; inner < outer;inner ++) 
				if(arr[inner].compareTo(arr[inner +1]) >0) 
					swap(arr, inner, inner+1);
				
	}
	
	/**
	 *	Swaps two Integer objects in array arr
	 *	@param arr		array of Integer objects
	 *	@param x		index of first object to swap
	 *	@param y		index of second object to swap
	 */
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
	
	/*****************************************************************/
	/************************* For Testing ***************************/
	/*****************************************************************/
	
	/**
	 *	Print an array of Integers to the screen
	 *	@param arr		the array of Integers
	 */
	public void printArray(Integer[] arr) {
		if (arr.length == 0) System.out.print("(");
		else System.out.printf("( %4d", arr[0]);
		for (int a = 1; a < arr.length; a++) {
			if (a % 10 == 0) System.out.printf(",\n  %4d", arr[a]);
			else System.out.printf(", %4d", arr[a]);
		}
		System.out.println(" )");
	}

	public static void main(String[] args) {
		SortMethods se = new SortMethods();
		se.run();
	}
	
	public void run() {
		Integer[] arr = new Integer[10];
		// Fill arr with random numbers
		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nBubble Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		bubbleSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();
	
		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nSelection Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		selectionSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();

		
		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nInsertion Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		insertionSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();

/*		
		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nMerge Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		mergeSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();
*/
	}
}
