package edu.sdsu.cs.datastructures;

public class SinglyLinkedList<E extends Comparable<E>> implements List<E> {
    private Node head;
    private Node tail;
    private int size = 0;

    private class Node {
        E data;
        Node next;

        private Node() { }
        private Node(E element) {
            data = element;
        }
    }

    public SinglyLinkedList() {
        head = tail = new Node();
    }

    public SinglyLinkedList(List<E> inputList) {
        this();
        add(inputList);
    }

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

    @Override
    public boolean add(List<E> other) {
        int inputSize = other.size();

        for (int i = 0; i < inputSize; i++) {
            add(other.get(i));
        }
        return true;
    }

    @Override
    public boolean addFirst(E datum) {
        E prevData;

        if (isEmpty()) {
            head.data = datum;
        } else {
            Node oldHead = head;
            head = new Node(datum);
            head.next = oldHead;

            if (head.next.next == null) {
                tail = oldHead;
            }
        }
        size++;

        return true;
    }

    @Override
    public boolean addLast(E datum) {
        add(datum);

        return true;
    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            remove(0);
        }
    }

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

    @Override
    public E get(int index) {
        try {
            return getNode(index).data;
        } catch (NullPointerException e) {
            System.out.println("\t!ERROR! " + e.toString() + " / Desired index out of bounds");
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        if (head.data == null) {
            return true;
        }
        return false;
    }

    private Node getNodeParent(int index) throws NullPointerException {
        if (index < 0) {
            index += size;
            if (index < 0) {
                return null;
            }
        }

        Node curNode = head;
        while (index-1 > 0) {
            curNode = curNode.next;
            index--;
        }

        return curNode;
    }

    private Node getNode(int index) throws NullPointerException {
        if (index == 0) {
            return getNodeParent(1);
        } else {
            return getNodeParent(index).next;
        }
    }

    @Override
    public E remove(int index) {
        try {
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

        } catch (NullPointerException e) {
            System.out.println("\t!ERROR! " + e.toString() + " / Desired index out of bounds");
            return null;
        }
    }

    private void disconnectNode(int index) throws NullPointerException {
        Node parentNode = getNodeParent(index);
        if (parentNode.next == tail) {
            cutTail(parentNode);
        } else {
            parentNode.next = parentNode.next.next;
        }
    }

    private void cutTail(Node lowerTail) {
        lowerTail.next = null;
        tail = lowerTail;
    }

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

    @Override
    public E set(int index, E value) {
        try {
            Node desiredNode = getNode(index);

            E prevValue = desiredNode.data;
            desiredNode.data = value;

            return prevValue;

        } catch (NullPointerException e) {
            System.out.println("\t!ERROR! " + e.toString() + " / Desired index out of bounds" +
                            "\n\tAdding intended data as new node to list's end...");
            add(value);
            return null;
        }
    }

    @Override
    public int size() {
        return size;
    }

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
