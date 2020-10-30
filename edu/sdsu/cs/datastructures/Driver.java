/**
 * (CS-310 / Program 2 - Linked Lists) Driver Class
 * Class that times and tests the accuracy of SinglyLinkedList's internal functions, as specified by Canvas assignment page.
 * @author Anthony Norderhaug
 * @date 10/29/2020
 */

package edu.sdsu.cs.datastructures;

public class Driver {
    // helper fields for tracking time, growth, and standardizing test sizes
    long prevTime, startTime, endTime;
    int initialTestSize = 100000;
    int testSize;

    // used for readability
    String textBreak = "\nX-----------------------------------------------------------------------------X\n";

    public static void main(String[] args) {
        new Driver();
    }

    /**
     * Instance of Driver() calling all test functions, each named respectively for what function is being examined
     */
    Driver() {
        timingTests();
        testListAdd();
        testRemove();
        testClear();
        testSort();
        testSet();
        testReverse();
        testReverseEmpty();
        testOutOfBoundsErrors();
    }

    /**
     * timingTests() initializes a List with a SinglyLinkedList and times its add and remove functions from both the front
     * and back ends. For each method & location combo, multiple iterations of doubling size are performed.
     */
    public void timingTests() {
        List<Integer> timingSLL = new SinglyLinkedList<Integer>();

        System.out.println("\nTiming addFirst()...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {

            startTime = System.currentTimeMillis();
            fillSLL(timingSLL,true, testSize);  // helper method which fills up SLL to testSize argument
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }

        System.out.println("\nTiming addLast...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {

            startTime = System.currentTimeMillis();
            fillSLL(timingSLL,false, testSize); // helper method which fills up SLL to testSize argument
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }

        System.out.println("\nTiming removeFirst()...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {
            fillSLL(timingSLL,false, testSize); // filing up SLL before removal

            startTime = System.currentTimeMillis();
            drainSLL(timingSLL,true);           // helper method which drains SLL until empty
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }

        System.out.println("\nTiming removeLast()...\n");
        testSize = 2000;
        for (int i = 0; i < 6; i++) {
            fillSLL(timingSLL,false, testSize); // filing up SLL before removal

            startTime = System.currentTimeMillis();
            drainSLL(timingSLL,false);          // helper method which drains SLL until empty
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }
        System.out.println(textBreak);
    }

    /**
     * testListAdd() declares, initializes, and fills up two List objects (initialized with SinglyLinkedList) and adds
     * them together with the internal add(List) function. Comparing size and its relative element, statement is printed
     * indicating result.
     */
    public void testListAdd() {
        System.out.println("Testing add(List)...\n");

        boolean result = true;
        int origSize, partSize;
        origSize = 10;
        partSize = 5;
        List<Integer> fullSLL = new SinglyLinkedList<>();
        List<Integer> partSLL = new SinglyLinkedList<>();

        fillSLL(fullSLL, false, origSize);
        fillSLL(partSLL, false, partSize);

        System.out.println("Before add(List)...");
        System.out.println("    fullSLL Contents: " + SLLtoString(fullSLL));
        System.out.println("    partSLL Contents: " + SLLtoString(partSLL));

        System.out.println("Adding fullSLL and partSLL together...");
        fullSLL.add(partSLL);
        int newSize = fullSLL.size();
        if (newSize != (origSize + partSize)) {
            result = false;
        }
        System.out.println("Original size: " + origSize + " | Part size: " + partSize + " | New size: " + newSize);

        for (int j = 0; j < partSize; j++) {
            if (!fullSLL.get(j+origSize).equals(partSLL.get(j))) {
                result = false;
            }
        }

        System.out.println("After add(List)...");
        System.out.println("    fullSLL Contents: " + SLLtoString(fullSLL));
        System.out.println("    partSLL Contents: " + SLLtoString(partSLL));

        System.out.print("add(List) function");
        if (result) {
            System.out.println(" passed\n");
        } else {
            System.out.println(" failed\n");
        }

        System.out.println(textBreak);
    }

    /**
     * testRemove() declares, initializes, and fills up two Lists (initialized with SinglyLinkedList) and tests whether
     * the internal remove() function is accurate for both positive and negative indices. Prints statement indicating
     * result.
     */
    public void testRemove() {
        System.out.println("Testing remove()...\n");

        boolean result = true;
        testSize = 10;
        int positiveIndex = 7;
        int negativeIndex = positiveIndex - testSize;

        List<Integer> removeSLL = new SinglyLinkedList<>();
        List<Integer> compareSLL = new SinglyLinkedList<>();
        fillSLL(removeSLL, false, testSize);
        fillSLL(compareSLL, false, testSize);

        System.out.println("Before removal...");
        System.out.println("    removeSLL Contents: " + SLLtoString(removeSLL));
        System.out.println("    compareSLL Contents: " + SLLtoString(compareSLL));

        System.out.println("Removing positive index " + positiveIndex + " from removeSLL " +
                "and negative index " + negativeIndex + " from compareSLL");
        int positiveRemoval, negativeRemoval;
        positiveRemoval = removeSLL.remove(positiveIndex);
        negativeRemoval = compareSLL.remove(negativeIndex);

        // remove() retrieves the element removed so if they're not equivalent, remove() is faulty
        // Integers stored in ints, as opposed to SinglyLinkedList's count() using .compareTo since generic
        if (positiveRemoval != negativeRemoval) {
            result = false;
        }

        System.out.println("After removal...");
        System.out.println("    removeSLL Contents: " + SLLtoString(removeSLL));
        System.out.println("    compareSLL Contents: " + SLLtoString(compareSLL));

        System.out.print("remove() function");
        if (result) {
            System.out.println(" passed\n");
        } else {
            System.out.println(" failed\n");
        }

        System.out.println(textBreak);
    }

    /**
     * testSort() declares, initializes, and fills a List object (with SinglyLinkedList) but in reverse order. Its internal
     * sort() function is then called and a Driver helper method is used to evaluate its "supposed "sorted nature. Prints
     * statement indicating result.
     */
    // Testing sort()
    public void testSort() {
        System.out.println("Testing sort()...\n");

        boolean result = true;
        testSize = 10;
        List<Integer> sortSLL = new SinglyLinkedList<>();

        fillSLL(sortSLL, true, testSize);
        System.out.println("Before sort...\n\tsortSLL Contents: " + SLLtoString(sortSLL));
        sortSLL.sort();
        System.out.println("After sort...\n\tsortSLL Contents after sort: " + SLLtoString(sortSLL));

        result = isSorted(sortSLL, false);

        System.out.print("sort() function");
        if (result) {
            System.out.println(" passed\n");
        } else {
            System.out.println(" failed\n");
        }

        System.out.println(textBreak);
    }

    /**
     * testSet() declares, initializes, and fills a List object (with SinglyLinkedList) and then sets a specific amount
     * of elements to a target value. Count() is then called to identify if the List contains the target that specific
     * amount of times. Prints statement indicating result.
     */
    public void testSet() {
        System.out.println("Testing set()...\n");

        boolean result = true;
        testSize = 10;
        int target, newInstances;

        target = 45; // choose number not within 0 to testSize
        // to ensure no pre-existing values the same as "target" skew count() if it were working properly
        newInstances = 5; // newInstances <= testSize
        List<Integer> setSLL = new SinglyLinkedList<>();
        fillSLL(setSLL, false, testSize);

        System.out.println("Before set()...");
        System.out.println("    setSLL Contents: " + SLLtoString(setSLL));

        System.out.println("Adding " + target + " to first " + newInstances + " indices...");
        for (int i = 0; i < newInstances; i++) {
            setSLL.set(i, target);
        }
        System.out.println("After set()...");
        System.out.println("    setSLL Contents: " + SLLtoString(setSLL));

        if (setSLL.count(target) != newInstances) {
            result = false;
        } else {
            System.out.println(target + " identified " + newInstances + " times!");
        }

        System.out.print("set() function");
        if (result) {
            System.out.println(" passed\n");
        } else {
            System.out.println(" failed\n");
        }

        System.out.println(textBreak);
    }

    /**
     * testClear() declares, initializes, and fills a List (with SinglyLinkedList) and then calls its clear method. Checking
     * size and isEmpty() function after, a statement is printed indicating result.
     */
    public void testClear() {
        System.out.println("Testing clear()...\n");

        boolean result = true;
        testSize = 10;
        List<Integer> clearSLL = new SinglyLinkedList<>();

        fillSLL(clearSLL, false, testSize);
        System.out.println("Before clear()...");
        System.out.println("    clearSLL Contents: " + SLLtoString(clearSLL));
        System.out.println("    clearSLL size: " + clearSLL.size());
        clearSLL.clear();
        System.out.println("After clear()...");
        System.out.println("    clearSLL Contents: " + SLLtoString(clearSLL));
        System.out.println("    clearSLL size: " + clearSLL.size());

        if (clearSLL.size() != 0) {
            result = false;
        } else {
            System.out.println("clearSLL size is equal to 0!");
        }

        if (!clearSLL.isEmpty()) {
            result = false;
        } else {
            System.out.println("clearSLL.isEmpty() returned true!");
        }

        System.out.print("clear() function");
        if (result) {
            System.out.println(" passed\n");
        } else {
            System.out.println(" failed\n");
        }

        System.out.println(textBreak);
    }

    /**
     * testReverse() declares, initializes, and fills a List (with SinglyLinkedList) and calls its reverse() function.
     * Its reverse order is then confirmed with Driver helper isSorted(). Prints statement indicating result.
     */
    public void testReverse() {
        System.out.println("Testing reverse()...\n");

        boolean result = true;
        testSize = 10;
        List<Integer> reverseSLL = new SinglyLinkedList<>();

        fillSLL(reverseSLL, false, testSize);
        System.out.println("Before reverse()...");
        System.out.println("    reverseSLL Contents: " + SLLtoString(reverseSLL));

        reverseSLL.reverse();
        System.out.println("After reverse()...");
        System.out.println("    reverseSLL Contents: " + SLLtoString(reverseSLL));

        if (isSorted(reverseSLL, true)) {
            System.out.println("reverseSLL is in reverse order!");
        } else {
            System.out.println("reverseSLL is not in reverse order.");
            result = false;
        }

        System.out.print("reverse() function");
        if (result) {
            System.out.println(" passed\n");
        } else {
            System.out.println(" failed\n");
        }

        System.out.println(textBreak);
    }

    /**
     * testReverseEmpty() declares, initializes, and fills a List object (with SinglyLinkedList) and then clears it.
     * Reverse() is called on empty structure and if no errors produced, isEmpty() is called to confirm empty nature.
     * Prints statement indicating result.
     */
    public void testReverseEmpty() {
        System.out.println("Testing reverse() on empty list...\n");

        boolean result = true;
        testSize = 10;
        List<Integer> reverseEmptySLL = new SinglyLinkedList<>();

        fillSLL(reverseEmptySLL, false, testSize);
        reverseEmptySLL.clear();
        System.out.println("Before reverse() on empty list...");
        System.out.println("    reverseEmptySLL Contents: " + SLLtoString(reverseEmptySLL));

        reverseEmptySLL.reverse();
        System.out.println("After reverse() on empty list...");
        System.out.println("    reverseEmptySLL Contents: " + SLLtoString(reverseEmptySLL));

        if (!reverseEmptySLL.isEmpty()) {
            result = false;
        }

        System.out.print("reverse() function on empty list");
        if (result) {
            System.out.println(" passed\n");
        } else {
            System.out.println(" failed\n");
        }

        System.out.println(textBreak);
    }

    /**
     * testOutOfBoundsErrors() declares, initializes, and fills a List object (with SinglyLinkedList) and calls its
     * remove(), get(), and set() with invalid indices. Confirming that all retireved values are the desired null, a
     * statement is printed indicating the result.
     */
    public void testOutOfBoundsErrors() {
        System.out.println("Testing for \"out of bounds\" errors...\n");

        boolean result = true;
        testSize = 10;
        int oobIndexA = testSize + 1; // out of bounds positive index, greater than testSize
        int oobIndexB = (-1 * testSize) - 1; // out of bounds negative index, less than -testSize
        int newValue = testSize; // new element, used for out of bounds set()
        Integer outputA, outputB, outputC;

        List<Integer> outOfBoundsSLL = new SinglyLinkedList<>();
        fillSLL(outOfBoundsSLL, false, testSize);

        System.out.println("Before out of bounds remove()...");
        System.out.println("    outOfBoundsSLL Contents: " + SLLtoString(outOfBoundsSLL));
        System.out.println("~~~ remove() Error message should be below... ~~~");
        outputA = outOfBoundsSLL.remove(oobIndexA);
        System.out.println("After out of bounds remove()...");
        System.out.println("    outOfBoundsSLL Contents: " + SLLtoString(outOfBoundsSLL) + "\n");

        System.out.println("Before out of bounds get()...");
        System.out.println("    outOfBoundsSLL Contents: " + SLLtoString(outOfBoundsSLL));
        System.out.println("~~~ get() Error message should be below... ~~~");
        outputB = outOfBoundsSLL.get(oobIndexB);
        System.out.println("After out of bounds get()...");
        System.out.println("    outOfBoundsSLL Contents: " + SLLtoString(outOfBoundsSLL) + "\n");

        System.out.println("Before out of bounds set()...");
        System.out.println("    outOfBoundsSLL Contents: " + SLLtoString(outOfBoundsSLL));
        System.out.println("~~~ set() Error message should be below...~~~ ");
        outputC = outOfBoundsSLL.set(oobIndexB, newValue);
        System.out.println("After out of bounds set()...");
        System.out.println("    outOfBoundsSLL Contents: " + SLLtoString(outOfBoundsSLL) + "\n");

        if (outOfBoundsSLL.size() == ++testSize) {
            System.out.println("set() correctly added newValue to SLL");
        } else {
            System.out.println("set() failed to add newValue to SLL");
        }

        if (outputA == null && outputB == null && outputC == null) {
            System.out.println("All out of bounds indices correctly retrieved null!");
        } else {
            result = false;
            System.out.println("1+ out of bounds indices failed to retrieve null.");
        }

        System.out.print("Out of bounds indices");
        if (result) {
            System.out.println(" all correctly caused errors and were handled\n");
        } else {
            System.out.println(" partially/fully failed to cause errors and be handled\n");
        }

        System.out.println(textBreak);
    }

    /**
     * fillSLL() fills a List (presumed to be SLL implementation) with a base element of 0 incrementing each iteration.
     * Fills up to argument size and uses boolean to indicate whether adding to front or end.
     *
     * @param sll                                                   input SLL
     * @param first                                                 boolean for front or end
     * @param size                                                  int for desired size
     */
    public void fillSLL(List<Integer> sll, boolean first, int size) {
        int initialElement = 0;

        for (int j = 0; j < size; j++) {
            if (first) {
                sll.addFirst(initialElement++);
            } else {
                sll.addLast(initialElement++);
            }
        }
    }

    /**
     * drainSLL() drains a List (presumed to be SLL implementation) until it is empty. Uses boolean to indicate whether
     * removing from front or end.
     *
     * @param sll                                                   input SLL
     * @param first                                                 boolean for front or end
     */
    public void drainSLL(List<Integer> sll, boolean first) {
        while (!sll.isEmpty()) {
            if (first) {
                sll.remove(0);
            } else {
                sll.remove(sll.size()-1);
            }
        }
    }

    /**
     * printMessage() is helper function for printing derived time for each iteration of timingTests(). Iterations past
     * initial utilize class field prevTime to calculate growth.
     *
     * @param iteration                                             int representing current iteration
     * @return                                                      String containing message
     */
    public String printMessage(int iteration) {
        long elapsed = endTime - startTime;
        String message = "Test Size: " + testSize + " | Elapsed time: " + elapsed;

        if (iteration > 0) {
            message += " | Growth factor: " + (String.format("%.2f", (double)elapsed/prevTime));
        }
        prevTime = elapsed;

        return message;
    }

    /**
     * SLLtoString() returns a String containing all of an SLL's elements for readability
     *
     * @param inputSLL                                              input SLL
     * @param <E>                                                   type parameter for internal elements
     * @return                                                      String containing SLL string representation
     */
    public <E extends Comparable<E>> String SLLtoString(List<E> inputSLL) {
        int size = inputSLL.size();
        String contents = "";

        for (int i = 0; i < size; i++) {
            if (i+1 == size) {
                contents += inputSLL.get(i);
            } else {
                contents += inputSLL.get(i) + ", ";
            }
        }

        return "[ " + contents + " ]";
    }

    /**
     * isSorted() compares subsequent values of an SLL and confirms the entirety is in increasing or decreasing order.
     * Boolean species "in order" or "reverse" order.
     *
     * @param inputSLL                                              input SLL
     * @param reverse                                               boolean indicating if reverse order desired
     * @param <E>                                                   type parameter for internal elements
     * @return                                                      boolean indicating properly sorted or not
     */
    public <E extends Comparable<E>> boolean isSorted(List<E> inputSLL, boolean reverse) {
        int size = inputSLL.size();
        E prevVal = null;
        E newVal;

        for (int i = 0; i < size; i++) {
            newVal = inputSLL.get(i);

            if (prevVal != null) {
                if (!reverse && prevVal.compareTo(newVal) > 0) {
                    return false;
                } else if (reverse && prevVal.compareTo(newVal) < 0) {
                    return false;
                }
            }
            prevVal = newVal;
        }

        return true;
    }

}
