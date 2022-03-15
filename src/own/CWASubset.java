package own;//Interface defining the functionality of the subset this thesis is working with.

public interface CWASubset<T> extends Iterable<T> {
    T get(int index);
    int size();
    boolean add(T element);
    T remove(int index);
}
