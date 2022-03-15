package own;//Michael Foussianis and Zacharias Thorell
//2022

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Self implemented version of the CopyOnWriteArrayList.
 * OpenJDK GitHub: https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/concurrent/CopyOnWriteArrayList.java
 * @param <T> Type the own.CWA contains.
 */
public class CWA<T> implements CWASubset<T> {
    //Private lock.
    private transient final Object lock = new Object();

    //The array of objects, only accessed via setArray() and getArray().
    private transient volatile Object[] array;

    private Object[] getArray() {
        return array;
    }

    private void setArray(Object[] arr) {
        array = arr;
    }

    public CWA() {
        setArray(new Object[0]);
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) getArray()[index];
    }

    public int size() {
        return getArray().length;
    }

    /**
     * Adds the specified element to the list by locking it from other threads and adding to a fresh copy of the array
     * as not to interrupt operations of other threads.
     *
     * @param element to be added to the list.
     * @return true.
     */
    @Override
    public boolean add(T element) {
        synchronized (lock) {
            Object[] es = getArray();
            int len = es.length;
            es = Arrays.copyOf(es, len + 1);
            es[len] = element;
            setArray(es);
            return true;
        }
    }

    /**
     * Removes the element of the specified index in the list by locking it from other threads and removing from a
     * fresh copy of the array as not to interrupt operations of other threads.
     *
     * @param index of the element to remove.
     * @return the element that was removed from the list.
     * @throws IndexOutOfBoundsException if index lies outside the bounds of the list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        synchronized (lock) {
            Object[] newArray = getArray();
            int length = newArray.length;
            T oldValue = (T) newArray[index];

            int amountMoved = length - index - 1;
            Object[] newElements;
            if (amountMoved == 0) {
                newElements = Arrays.copyOf(newArray, length - 1);
            }
            else {
                newElements = new Object[length - 1];
                System.arraycopy(newArray, 0, newElements, 0, index);
                System.arraycopy(newArray, index + 1, newElements, index, amountMoved);
            }

            setArray(newElements);
            return oldValue;
        }
    }

    @Override
    public Iterator<T> iterator() {
        //TODO: Is not in accordance with the CopyOnWriteArrayList where getArray() is simple handed to the the iterator instead of a copy.
        /*Object[] newArray = new Object[size()];
        System.arraycopy(getArray(), 0, newArray, 0, size() - 1);*/
        return new CWAIterator<>(getArray(), 0);
    }

    private final static class CWAIterator<T> implements Iterator<T> {
        /** Snapshot of the array */
        private final Object[] snapshot;

        /** Index of element to be returned by subsequent call to next.  */
        private int cursor;

        CWAIterator(Object[] snapshot, int cursor) {
            this.snapshot = snapshot;
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return cursor < snapshot.length;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) snapshot[cursor++];
        }
    }
}