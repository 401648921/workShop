public class Bird extends Animal {
    private String color;

    public Bird(String name, double cost, String color) {
        super(name, cost);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String toString() {
        return "Bird{" +
                "color='" + color + '\'' +
                ", name='" + getName() + '\'' +
                ", cost=" + getCost() +
                '}';
    }
}
