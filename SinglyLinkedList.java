public class SinglyLinkedList<E extends Comparable<E>> implements List<E> {
    private Node head;
    private Node tail;

    private class Node {
        E data;
        Node next;

        private Node() {
            data = null;
        }
        private Node(E element) {
            init(element);
        }
        public void init(E element) {
            data = element;
        }
    }

    public SinglyLinkedList() {
        head = tail = new Node();
    }

    public SinglyLinkedList(List<E> inputList) {
        this();
        for (int i = 0; i < inputList.size(); i++) {
            add(inputList);
        }
    }

    @Override
    public boolean add(E datum) {
        if (isEmpty()) {
            head.data = datum;
            return true;
        }

        tail.next = new Node(datum);
        tail = tail.next;
        return true;
    }

    @Override
    public boolean add(List<E> other) {
        for (int i = 0; i < other.size(); i++) {
            add(other.get(i));
        }
        return true;
    }

    @Override
    public boolean addFirst(E datum) {
        if (isEmpty()) {
            head.data = datum;
            return true;
        }
        Node curNode = head;
        E storedData;


        tail.next = new Node();
        tail = tail.next;
        while (curNode != null) {
            storedData = curNode.data;
            curNode.data = datum;
            datum = storedData;

            curNode = curNode.next;
        }

        return true;
    }

    private boolean swapData(Node A, Node B) {
        E storedData = A.data;
        A.data = B.data;
        B.data = storedData;

        return true;
    }

    @Override
    public boolean addLast(E datum) {
        add(datum);

        return true;
    }

    @Override
    public void clear() {
        new SinglyLinkedList();
    }

    @Override
    public int count(E target) {
        Node curNode = head;
        int instances = 0;

        while (!curNode.equals(null)) {
            if (curNode.data.equals(target)) {
                instances++;
            }
            curNode = curNode.next;
        }

        return instances;
    }

    @Override
    public E get(int index) {
        Node curNode = head;
        while (index > 0) {
            curNode = curNode.next;
            index--;
        }

        return curNode.data;
    }

    @Override
    public boolean isEmpty() {
        if (head.data == null) {
            return true;
        }
        return false;
    }

    private Node getNode(int index) {
        Node curNode = head;
        while (index > 0) {
            curNode = curNode.next;
            index--;
        }

        return curNode;
    }

    @Override
    public E remove(int index) {
        E elementRemoved = get(index);
        Node curNode = getNode(index);

        swapData(curNode, curNode.next);
        while (curNode.next != tail) {
            curNode = curNode.next;
            swapData(curNode, curNode.next);
        }
        tail = curNode;

        return elementRemoved;
    }

    @Override
    public void reverse() {
        Node pointer = head;
        Node prevNode = null;
        Node curNode;

        tail = head;
        while (pointer != null) {
            curNode = pointer;
            pointer = pointer.next;

            curNode.next = prevNode;
            prevNode = curNode;
            head = curNode;
        }
    }

    @Override
    public E set(int index, E value) {
        Node desiredNode = getNode(index);
        E prevValue = desiredNode.data;

        desiredNode.data = value;
        return prevValue;
    }

    @Override
    public int size() {
        Node curNode = head;
        int size = 0;

        while (curNode != null) {
            curNode = curNode.next;
            size++;
        }

        return size;
    }

    @Override
    public void sort() {
        E keyData;
        int prevIndex;

        for (int i = 1; i < size(); i++) {
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
