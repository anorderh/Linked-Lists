package edu.sdsu.cs.datastructures;

public class Driver {
    long prevTime, startTime, endTime;
    int initialTestSize = 100000;
    int testSize;
    String textBreak = "\nX-----------------------------------------------------------------------------X\n";

    public static void main(String[] args) {
        new Driver();
    }

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

    // Timing tests
    public void timingTests() {
        List<Integer> timingSLL = new SinglyLinkedList<Integer>();

        System.out.println("\nTiming addFirst()...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {

            startTime = System.currentTimeMillis();
            fillSLL(timingSLL,true, testSize);
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }

        System.out.println("\nTiming addLast...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {

            startTime = System.currentTimeMillis();
            fillSLL(timingSLL,false, testSize);
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }

        System.out.println("\nTiming removeFirst()...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {
            fillSLL(timingSLL,false, testSize);

            startTime = System.currentTimeMillis();
            drainSLL(timingSLL,true);
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }

        System.out.println("\nTiming removeLast()...\n");
        testSize = 2000;
        for (int i = 0; i < 6; i++) {
            fillSLL(timingSLL,false, testSize);

            startTime = System.currentTimeMillis();
            drainSLL(timingSLL,false);
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }
        System.out.println(textBreak);
    }

    // Testing add(List)
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

    // Testing remove() with negative indices
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

        result = isSorted(sortSLL);

        System.out.print("sort() function");
        if (result) {
            System.out.println(" passed\n");
        } else {
            System.out.println(" failed\n");
        }

        System.out.println(textBreak);
    }

    // Testing set() with count()
    public void testSet() {
        System.out.println("Testing set()...\n");

        boolean result = true;
        testSize = 10;
        int target, newInstances;

        target = 45; // choose number not within 0 to testSize
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

        if (reverseSLL.isEmpty()) {
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

    // Testing reverse() on empty SLL
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

    public void testOutOfBoundsErrors() {
        System.out.println("Testing for \"out of bounds\" errors...\n");

        boolean result = true;
        testSize = 10;
        int oobIndexA = testSize + 1; // out of bounds positive index, greater than testSize
        int oobIndexB = (-1 * testSize) - 1; // out of bounds negative index, less than -testSize
        int newValue = testSize; // used for out of bounds set()
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

    public void drainSLL(List<Integer> sll, boolean first) {
        while (!sll.isEmpty()) {
            if (first) {
                sll.remove(0);
            } else {
                sll.remove(sll.size()-1);
            }
        }
    }

    public String printMessage(int iteration) {
        long elapsed = endTime - startTime;
        String message = "Test Size: " + testSize + " | Elapsed time: " + elapsed;

        if (iteration > 0) {
            message += " | Growth factor: " + (String.format("%.2f", (double)elapsed/prevTime));
        }
        prevTime = elapsed;

        return message;
    }

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

    public <E extends Comparable<E>> boolean isSorted(List<E> inputSLL) {
        int size = inputSLL.size();
        E prevVal = null;
        E newVal;

        for (int i = 0; i < size; i++) {
            newVal = inputSLL.get(i);

            if (prevVal != null) {
                if (prevVal.compareTo(newVal) > 0) {
                    return false;
                }
            }
            prevVal = newVal;
        }

        return true;
    }

}
