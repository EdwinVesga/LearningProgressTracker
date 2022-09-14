package domain;


public class StatisticResult<T> {

    private T most;

    private T least;

    public StatisticResult(T most, T least) {
        this.most = most;
        this.least = least;
    }

    public T getMost() {
        return most;
    }

    public T getLeast() {
        return least;
    }
}
