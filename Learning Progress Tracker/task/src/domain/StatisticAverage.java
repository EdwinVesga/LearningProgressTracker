package domain;

public class StatisticAverage {

    private double totalSum;

    private int counter;

    public void addAmount(double amount) {
        totalSum += amount;
        counter++;
    }

    public double getAverage() {
        return totalSum / counter;
    }
}
