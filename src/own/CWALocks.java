package own;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CWALocks<T> implements CWASubset<T> {
    //The array of objects, only accessed via setArray() and getArray().
    private transient volatile Object[] array;

    public CWALocks() {
        setArray(new Object[0]);
    }

    private Object[] getArray() {
        return array;
    }

    private void setArray(Object[] arr) {
        array = arr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) getArray()[index];
    }

    @Override
    public int size() {
        return getArray().length;
    }

    @Override
    public boolean add(Object element) {
        Object[] es = getArray();
        int len = es.length;
        es = Arrays.copyOf(es, len + 1);
        es[len] = element;
        setArray(es);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
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

    @Override
    public Iterator<T> iterator() {
        //TODO: Is not in accordance with the CopyOnWriteArrayList where getArray() is simple handed to the the iterator instead of a copy.
        Object[] newArray = new Object[size()];
        System.arraycopy(getArray(), 0, newArray, 0, size());
        return new CWALocksIterator<>(newArray, 0);
    }

    private final static class CWALocksIterator<T> implements Iterator<T> {
        /** Snapshot of the array */
        private final Object[] snapshot;

        /** Index of element to be returned by subsequent call to next.  */
        private int cursor;

        public CWALocksIterator(Object[] snapshot, int cursor) {
            this.snapshot = snapshot;
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return cursor < snapshot.length;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) snapshot[cursor++];
        }
    }
}
