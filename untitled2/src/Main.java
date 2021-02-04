public class Main {
    public static void main(String[] args) {
        PetShop petShop = new Shop(10);
        try{
            petShop.purchase(2,new Dog("雪纳瑞",10,"白色"));
        }catch(NoEnoughMoney e){
            System.out.println("缺少"+e.getMessage()+"元");
        }
        petShop.purchase(1,new Dog("雪纳瑞",10,"白色"));
        try{
            petShop.sale("鸟");
        }catch (NoSuchAnimal e){
            System.out.println("缺少"+e.getMessage()+"动物");
        }
        petShop.sale("雪纳瑞");
        Bird bird = new Bird("鹦鹉",15,"红色");
        Shop shop = (Shop)petShop;
        shop.raise(bird);
        shop.injectVaccine(bird);
    }
}
