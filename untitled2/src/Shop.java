import java.util.ArrayList;
import java.util.Date;

public class Shop implements PetShop {
    private ArrayList<SetMeal> list = new ArrayList<>();
    private double money;

    public Shop(double money) {
        this.money = money;
    }

    public ArrayList<SetMeal> getList() {
        return list;
    }

    public void setList(ArrayList<SetMeal> list) {
        this.list = list;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public void sale(String name) throws NoSuchAnimal{
        for(SetMeal setMeal : list){
            if(setMeal.getAnimal().getName().equals(name)){
                list.remove(setMeal);
                money+=setMeal.getCost();
                System.out.println(new Date()+"出售：");
                System.out.println(setMeal);
                System.out.println("资金："+money);
                return;
            }
        }
        throw  new NoSuchAnimal(name);
    }

    @Override
    public void purchase(int number,Animal animal) {
        if(animal.getCost()*number>money) throw  new NoEnoughMoney(animal.getCost()*number - money);
        for(int i= 0;i<number;i++){
            SetMeal setMeal = new SetMeal(animal,new Vaccine(10));
            list.add(setMeal);
            money -= setMeal.getCost();
            System.out.println(new Date()+"进货：");
            System.out.println(setMeal);
        }
        System.out.println("资金："+money);
    }
    public void injectVaccine(Animal animal){
        System.out.println(animal.getName()+"在"+new Date()+"注射疫苗");
    }
    public void raise(Animal animal){
        System.out.println(animal.getName()+"在"+new Date()+"寄养");
    }
}
