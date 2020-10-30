/**
 * (CS-310 / Program 2 - Linked Lists) SinglyLinkedList Class
 * Implementation implementing List that defines SinglyLinkedList objects
 * @author Anthony Norderhaug
 * @date 10/30/2020
 */
package edu.sdsu.cs.datastructures;

public class SinglyLinkedList<E extends Comparable<E>> implements List<E> {
    // pointers for tracking head and tail
    private Node head;
    private Node tail;
    // int field for tracking size as elements added & removed
    private int size = 0;

    /**
     * Implementation defining Node objects. Contains type parameter E for whatever data within and next pointer to
     * subsequent node.
     */
    private class Node {
        E data;
        Node next;

        private Node() { }
        private Node(E element) {
            data = element;
        }
    }

    /**
     * default constructor initializing an empty node to both head and tail
     */
    public SinglyLinkedList() {
        head = tail = new Node();
    }

    /**
     * constructor with List argument that copies inputList's contents into the new SLL
     * @param inputList                                             List input, containing new elements to be data
     */
    public SinglyLinkedList(List<E> inputList) {
        this();
        add(inputList);
    }

    /**
     * add() takes in datum and adds it to the end of the list. Addition's methods are dependent on current elements within.
     * Size is updated.
     *
     * @param datum                                                 E input, to be added
     * @return                                                      boolean indicating success
     */
    @Override
    public boolean add(E datum) {
        if (isEmpty()) {
            head.data = datum;
        } else if (head == tail) {
            tail = head.next = new Node(datum);
        } else {
            tail = tail.next = new Node(datum);
        }
        size++;

        return true;
    }

    /**
     * add(List) takes in input List and adds all of the argument's elements to residing SLL's end
     *
     * @param other                                                 List input, to be added
     * @return                                                      boolean indicating success
     */
    @Override
    public boolean add(List<E> other) {
        int inputSize = other.size();

        for (int i = 0; i < inputSize; i++) {
            add(other.get(i));
        }
        return true;
    }

    /**
     * addFirst() takes in an input E and adds it to the front of the list. Size is updated.
     *
     * @param datum                                                 E input, to be added
     * @return                                                      boolean indicating success
     */
    @Override
    public boolean addFirst(E datum) {
        if (isEmpty()) {
            head.data = datum;
        } else {
            Node oldHead = head;
            head = new Node(datum);
            head.next = oldHead;
        }
        size++;

        return true;
    }

    /**
     * addLast() takes in input E and adds to to the end of the list (effectively just calling add() with input)
     * @param datum                                                 E input, to be added
     * @return                                                      boolean indicating success
     */
    @Override
    public boolean addLast(E datum) {
        add(datum);

        return true;
    }

    /**
     * clear() removes all nodes within the SinglyLinkedList until the structure is empty
     */
    @Override
    public void clear() {
        while (!isEmpty()) {
            remove(0);
        }
    }

    /**
     * count() counts how many nodes in the SLL have the same data as the input E, known as target/
     *
     * @param target                                                E, target value used for comparison
     * @return                                                      int indicating number of occurences
     */
    @Override
    public int count(E target) {
        Node curNode = head;
        int instances = 0;

        while (curNode != null) {
            if (curNode.data.compareTo(target) == 0) {
                instances++;
            }
            curNode = curNode.next;
        }

        return instances;
    }

    /**
     * get() retrieves the node at the input index and returns the data
     * @param index                                                 int input, representing desired index
     * @return                                                      E retrieved from desired node
     */
    @Override
    public E get(int index) {
        try {
            checkValidity(index);
            return getNode(index).data;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\t!ERROR! " + e.toString() + " / Desired index out of bounds");
            return null;
        }
    }

    /**
     * isEmpty() checks if data within head pointer is null or not, indicating if list is empty.
     *
     * @return                                                      boolean identifying if list is empty
     */
    @Override
    public boolean isEmpty() {
        if (head.data == null) {
            return true;
        }
        return false;
    }

    /**
     * getNodeParent() retrieves the Node parent to a node at a specific index.
     *
     * @param index                                                 int input, representing desired index
     * @return                                                      Node parent of desired index
     */
    private Node getNodeParent(int index) {
        if (index < 0) {
            index += size;
        }

        Node curNode = head;
        while (index-1 > 0) {
            curNode = curNode.next;
            index--;
        }

        return curNode;
    }

    /**
     * getNode() retrieves node at a desired index.
     *
     * @param index                                                 int input, representing desired index
     * @return                                                      Node of desired index
     */
    private Node getNode(int index) {
        if (index == 0) {
            return getNodeParent(1);
        } else {
            return getNodeParent(index).next;
        }
    }

    /**
     * checkValidity() checks if the input index is applicable either as a positive or negative index in the structure.
     * If not, an exception is thrown.
     *
     * @param index                                                 int input, representing desired index
     * @throws IndexOutOfBoundsException                            exception indicating index is invalid
     */
    private void checkValidity(int index) throws IndexOutOfBoundsException {
        IndexOutOfBoundsException outOfBounds = new IndexOutOfBoundsException();

        if (index + size < 0 || index >= size) {
            throw outOfBounds;
        }
    }

    /**
     * remove() removes a Node from SLL, patching up holes if needed and returns the element removed. Size is updated.
     *
     * @param index                                                 int input, representing index of removal
     * @return                                                      E removed at index
     */
    @Override
    public E remove(int index) {
        try {
            checkValidity(index);
            E elementRemoved = getNode(index).data;

            if (head == tail) {
                tail.data = null;
            } else if (index == 0) {
                head = head.next;
            } else {
                disconnectNode(index);
            }
            size--;

            return elementRemoved;

        } catch (IndexOutOfBoundsException e) {
            System.out.println("\t!ERROR! " + e.toString() + " / Desired index out of bounds");
            return null;
        }
    }

    /**
     * disconnectNode() is a helper function for remove() in that given a Node's index, it transforms parent node to
     * point to node after it, effectively removing it. In case that desired Node is tail, cutTail() is called.
     *
     * @param index                                                 int input, representing index of disconnect
     */
    private void disconnectNode(int index) {
        Node parentNode = getNodeParent(index);
        if (parentNode.next == tail) {
            cutTail(parentNode);
        } else {
            parentNode.next = parentNode.next.next;
        }
    }

    /**
     * cutTail() is a helper function for disconnectNode which transforms input Node into new tail
     *
     * @param lowerTail                                             Node input, representing new tail
     */
    private void cutTail(Node lowerTail) {
        lowerTail.next = null;
        tail = lowerTail;
    }

    /**
     * reverse() reverses the order of nodes within the SLL.
     */
    @Override
    public void reverse() {
        if (isEmpty() || head == tail) {
            return;
        }
        Node pointer = head;
        Node prevNode = null;
        Node curNode;

        tail = head;
        while (pointer != null) {
            curNode = pointer.next;
            pointer.next = prevNode;
            head = prevNode = pointer;

            pointer = curNode;
        }
    }

    /**
     * set() retrieves the Node at the specified index and changes its internal data to the input E.
     *
     * @param index                                                 int input, representing index of desired Node
     * @param value                                                 E input, representing new data to be placed
     * @return                                                      E element previously held at Node
     */
    @Override
    public E set(int index, E value) {
        try {
            checkValidity(index);
            Node desiredNode = getNode(index);

            E prevValue = desiredNode.data;
            desiredNode.data = value;

            return prevValue;

        } catch (IndexOutOfBoundsException e) {
            System.out.println("\t!ERROR! " + e.toString() + " / Desired index out of bounds" +
                            "\n\tAdding intended data as new node to list's end...");
            add(value);
            return null;
        }
    }

    /**
     * size() returns the int field size
     * @return                                                      size, int field
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * sort() operates off the insertion sort algorithm and sort the SLL's nodes in increasing order
     */
    @Override
    public void sort() {
        E keyData;
        int prevIndex;

        // insertion sort
        for (int i = 1; i < size; i++) {
            keyData = get(i);
            prevIndex = i - 1;


            while (prevIndex >= 0 && get(prevIndex).compareTo(keyData) > 0) {
                set(prevIndex + 1, get(prevIndex));
                prevIndex--;
            }
            set(prevIndex + 1, keyData);
        }
    }
}
