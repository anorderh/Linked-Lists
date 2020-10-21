

public class Driver {
    long prevTime, startTime, endTime;
    int initialTestSize = 5;
    int testSize;
    String textBreak = "\nX-------------------------------------------------------X\n";

    public static void main(String[] args) {
        new Driver();
    }

    Driver() {
        timingTests();
        testListAdd();
        testRemove();
        testSort();
        testSet();
        testReverseEmpty();
        // clear() method proven in timingTests() and testReverseEmpty()
    }

    // Timing tests
    public void timingTests() {
        SinglyLinkedList<Integer> timingSLL = new SinglyLinkedList<>();

        System.out.println("\nTiming addFirst()...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {

            startTime = System.currentTimeMillis();
            fillSSL(timingSLL,true, testSize);
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }

        System.out.println("\nTiming addLast...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {

            startTime = System.currentTimeMillis();
            fillSSL(timingSLL,false, testSize);
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }

        System.out.println("\nTiming removeFirst()...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {
            fillSSL(timingSLL,false, testSize);

            startTime = System.currentTimeMillis();
            drainSSL(timingSLL,true);
            endTime = System.currentTimeMillis();

            System.out.println(printMessage(i));
            testSize *= 2;
            timingSLL.clear();
        }

        System.out.println("\nTiming removeLast()...\n");
        testSize = initialTestSize;
        for (int i = 0; i < 6; i++) {
            fillSSL(timingSLL,false, testSize);

            startTime = System.currentTimeMillis();
            drainSSL(timingSLL,false);
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
        SinglyLinkedList<Integer> fullSSL = new SinglyLinkedList<>();
        SinglyLinkedList<Integer> partSSL = new SinglyLinkedList<>();

        fillSSL(fullSSL, false, origSize);
        fillSSL(partSSL, false, partSize);

        System.out.println("Adding fullSSL and partSSL together...");
        fullSSL.add(partSSL);
        int newSize = fullSSL.size();
        if (newSize != (origSize + partSize)) {
            result = false;
        }
        System.out.println("Original size: " + origSize + " | Part size: " + partSize + " | New size: " + newSize);

        for (int j = 0; j < partSize; j++) {
            if (!fullSSL.get(j+origSize).equals(partSSL.get(j))) {
                result = false;
            }
        }

        System.out.println("Full SSL Contents: " + fullSSL);
        System.out.println("Part SSL Contents: " + partSSL);

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

        SinglyLinkedList<Integer> removeSSL = new SinglyLinkedList<>();
        SinglyLinkedList<Integer> compareSSL = new SinglyLinkedList<>();
        fillSSL(removeSSL, false, testSize);
        fillSSL(compareSSL, false, testSize);

        System.out.println("Before removal...");
        System.out.println("    Remove SSL Contents: " + removeSSL);
        System.out.println("    Compare SSL Contents: " + compareSSL);

        System.out.println("Removing positive index " + positiveIndex + " from Remove SSL " +
                "and negative index " + negativeIndex + " from Compare SSL");
        int positiveRemoval, negativeRemoval;
        positiveRemoval = removeSSL.remove(positiveIndex);
        negativeRemoval = compareSSL.remove(negativeIndex);
        if (positiveRemoval != negativeRemoval) {
            result = false;
        }

        System.out.println("Remove SSL Contents: " + removeSSL);
        System.out.println("Compare SSL Contents: " + compareSSL);

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
        SinglyLinkedList<Integer> sortSSL = new SinglyLinkedList<>();

        fillSSL(sortSSL, true, testSize);
        System.out.println("Sort SSL Contents before sort: " + sortSSL);
        sortSSL.sort();
        System.out.println("Sort SSL Contents after sort: " + sortSSL);


        result = sortSSL.isSorted();

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
        SinglyLinkedList<Integer> setSSL = new SinglyLinkedList<>();
        fillSSL(setSSL, false, testSize);

        System.out.println("Adding " + target + " to " + newInstances + " internal nodes...");
        for (int i = 0; i < newInstances; i++) {
            setSSL.set(i, target);
        }
        System.out.println("Set SSL Contents: " + setSSL);

        if (setSSL.count(target) != newInstances) {
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

    // Testing reverse() on empty SSL
    public void testReverseEmpty() {
        System.out.println("Testing reverse() on empty list...\n");

        boolean result = true;
        testSize = 10;
        SinglyLinkedList<Integer> reverseEmptySSL = new SinglyLinkedList<>();

        fillSSL(reverseEmptySSL, false, testSize);
        reverseEmptySSL.clear();
        reverseEmptySSL.reverse();

        if (!reverseEmptySSL.isEmpty()) {
            result = false;
        }
        System.out.println("reverseEmpty SSL Contents: " + reverseEmptySSL);

        System.out.print("reverseEmpty() function");
        if (result) {
            System.out.println(" passed\n");
        } else {
            System.out.println(" failed\n");
        }

        System.out.println(textBreak);
    }

    public void fillSSL(SinglyLinkedList<Integer> sll, boolean first, int size) {
        int initialElement = 0;

        for (int j = 0; j < size; j++) {
            if (first) {
                sll.addFirst(initialElement++);
            } else {
                sll.addLast(initialElement++);
            }
        }
    }

    public void drainSSL(SinglyLinkedList<Integer> sll, boolean first) {
        while (!sll.isEmpty()) {
            if (first) {
                sll.removeFirst();
            } else {
                sll.removeLast();
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
}
