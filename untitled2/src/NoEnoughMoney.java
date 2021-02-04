public class NoEnoughMoney extends RuntimeException {
    private double money;

    public NoEnoughMoney(double money) {
        super(String.valueOf(money));

    }
}
