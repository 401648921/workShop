public class SetMeal {
    private Animal animal;
    private Vaccine vaccine;
    private double cost;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public SetMeal(Animal animal, Vaccine vaccine) {
        this.animal = animal;
        this.vaccine = vaccine;
        this.cost = animal.getCost()+vaccine.getCost();
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    @Override
    public String toString() {
        return "SetMeal{" +
                "animal=" + animal +
                ", vaccine=" + vaccine +
                '}';
    }
}
