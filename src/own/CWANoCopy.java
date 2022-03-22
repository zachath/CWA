package own;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CWANoCopy<Integer> implements CWASubset<Integer> {
    //Private lock.
    private transient final Object lock = new Object();

    //The array of objects, only accessed via setArray() and getArray().
    private transient volatile Object[] array;

    public CWANoCopy() {
        array = new Object[1];
    }

    @Override
    public Integer get(int index) {
        return (Integer) array[index];
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean add(Integer element) {
        synchronized (lock) {
            array[size() - 1] = element;
            return true;
        }
    }

    @Override
    public Integer remove(int index) {
        synchronized (lock) {
            Integer oldValue = get(index);
            array[index] = -1;
            return oldValue;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new CWAIterator<>(array, 0);
    }

    private final static class CWAIterator<Integer> implements Iterator<Integer> {
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
            return cursor < snapshot.length && (int) snapshot[cursor] != -1;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (Integer) snapshot[cursor++];
        }
    }
}
