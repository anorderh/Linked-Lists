public class SinglyLinkedList<E extends Comparable<E>> implements List<E> {
    private Node head;
    private Node tail;
    // include a size field

    private class Node {
        E data;
        Node next;

        private Node() {
            data = null;
        }
        private Node(E element) {
            init(element);
        }
        void init(E element) {
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
        Node curNode = head;

        if (isEmpty()) {
            head.data = datum;
        } else if (head == tail) {
            prevData = head.data;
            head.data = datum;
            tail = head.next = new Node(prevData);
        } else {
            Node oldHead = head;
            head = new Node(datum);
            head.next = oldHead;
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
        while (!isEmpty()) {
            removeFirst();
        }
    }

    @Override
    public int count(E target) {
        Node curNode = head;
        int instances = 0;

        while (curNode != null) {
            if (curNode.data == target) {
                instances++;
            }
            curNode = curNode.next;
        }

        return instances;
    }

    @Override
    public E get(int index) {
        return getNode(index).data;
    }

    @Override
    public boolean isEmpty() {
        if (head.data == null) {
            return true;
        }
        return false;
    }

    private Node getNodeParent(int index) {
        Node curNode = head;
        while (index-1 > 0) {
            curNode = curNode.next;
            index--;
        }

        return curNode;
    }

    private Node getNode(int index) {
        if (index == 0) {
            return getNodeParent(1);
        } else {
            return getNodeParent(index).next;
        }
    }

    @Override
    public E remove(int index) {
        if (index < 0) {
            index += size();
        }
        E elementRemoved = getNode(index).data;

        if (head == tail) {
            tail.data = null;
        } else if (index == 0) {
            head = head.next;
        } else {
            disconnectNode(index);
        }

        return elementRemoved;
    }

    public void disconnectNode(int index) {
        Node parentNode = getNodeParent(index);
        if (parentNode.next == tail) {
            cutTail(parentNode);
        } else {
            parentNode.next = parentNode.next.next;
        }
    }

    public void cutTail(Node newTail) {
        newTail.next = null;
        tail = newTail;
    }

    public void removeFirst() {
        remove(0);
    }

    public void removeLast() {
        remove(size()-1);
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
            curNode = pointer;
            curNode.next = prevNode;
            head = prevNode = curNode;

            pointer = pointer.next;
        }
    }

    @Override
    public E set(int index, E value) {
        Node desiredNode = getNode(index);
        E prevValue = null;
        if (desiredNode == null) {
            tail = tail.next = new Node(value);
        } else {
            prevValue = desiredNode.data;
            desiredNode.data = value;
        }

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

        // insertion sort
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

    public boolean isSorted() {
        Node curNode = head;

        while (curNode.next != null) {
            if (curNode.data.compareTo(curNode.next.data) > 0) {
                return false;
            }
            curNode = curNode.next;
        }
        return true;
    }

    public String toString() {
        String contents = "";
        Node curNode = head;

        while (curNode != null && curNode.data != null) {
            if (curNode == tail) {
                contents += curNode.data;
            } else {
                contents += curNode.data + ", ";
            }
            curNode = curNode.next;
        }

        return "[ " + contents + " ]";
    }
}
