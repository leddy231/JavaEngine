public class Tuple<T> {
    public T obj1;
    public T obj2;
    public boolean isEmpty;
    public Tuple(T obj1, T obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    private Tuple() {
        isEmpty = true;
    }

    public static <T> Tuple<T> empty() {
        return new Tuple<T>();
    }
}