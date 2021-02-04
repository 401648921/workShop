public class Dog extends Animal {
    private String color;

    public Dog(String name, double cost, String color) {
        super(name, cost);
        this.color = color;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "color='" + color + '\'' +
                ", name='" + getName() + '\'' +
                ", cost=" + getCost() +
                '}';
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
