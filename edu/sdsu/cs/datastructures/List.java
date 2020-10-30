/**
 * (CS-310 / Program 2 - Linked Lists) List Interface
 * Interface declaring several methods for implementing classes to define
 * @author Anthony Norderhaug
 * @date 10/30/2020
 */
package edu.sdsu.cs.datastructures;

public interface List<E> {
    boolean add(E datum);
    boolean add(List<E> other);
    boolean addFirst(E datum);
    boolean addLast(E datum);
    void clear();
    int count(E target);
    E get(int index);
    boolean isEmpty();
    E remove(int index);
    void reverse();
    E set(int index, E value);
    int size();
    void sort();
}
