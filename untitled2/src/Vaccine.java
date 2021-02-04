public class Vaccine {
    private double cost;

    public Vaccine(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Vaccine{" +
                "cost=" + cost +
                '}';
    }
}
