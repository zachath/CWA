//Michael Foussianis and Zacharias Thorell
//2022

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Self implemented version of the CopyOnWriteArrayList.
 * Implements only the Iterable interface since the only operations of interest are modification (add/remove) and
 * iteration.
 * Based upon: {@link java.util.concurrent.CopyOnWriteArrayList}
 * OpenJDK GitHub: https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/concurrent/CopyOnWriteArrayList.java
 * @param <T> Type the CWA contains.
 */
public class CWA<T> implements Iterable<T> {

    //Private intrinsic lock.
    private final Object lock = new Object();

    //The array of objects, volatile to ensure that updates propagate predictably to other threads.
    private volatile Object[] array;

    public CWA() {
        array = new Object[0];
    }

    /**
     * Adds the specified element to the list by locking it from other threads and adding to a "fresh copy of the array"
     * as not to interrupt operations of other threads.
     *
     * @param element to be added to the list.
     * @return true.
     */
    public boolean add(T element) {
        synchronized (lock) {
            Object[] newArray = array;
            int length = newArray.length;
            newArray = Arrays.copyOf(newArray, length+1); //Är detta en optimering?
            newArray[length] = element;
            array = newArray;
            return true;
        }
    }

    /**
     * Removes the element of the specified index in the list by locking it from other threads and removing from a
     * "fresh copy" of the array as not to interrupt operations of other threads.
     *
     * @param index of the element to remove.
     * @return the element that was removed from the list.
     * @throws IndexOutOfBoundsException if index lies outside the bounds of the list.
     */
    public T remove(int index) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public Iterator<T> iterator() {
        return new CWAIterator();
    }

    private class CWAIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            throw new IllegalStateException("Not implemented");
        }

        @Override
        public T next() {
            throw new IllegalStateException("Not implemented");
        }
    }
}
