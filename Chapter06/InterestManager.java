public class InterestManager {

    public static void main(String[] args) {
        InterestManager interestManager = new InterestManager();
        long amount = 1000000l;
        for (int day = 10; day <= 365; day+=10) {
            double result = interestManager.calculateAmount(day, amount);
            System.out.println("day - " + day + " : " + result);
        }
    }

    public double calculateAmount(int day, long amount) {
        double rate = this.getInterestRate(day);
        double interest = (amount * rate) / 100d;
        return amount + interest;
    }

    public double getInterestRate(int day) {
        if (day <= 90) {
            return 0.5d;
        }
        if (day <= 180) {
            return 1d;
        }
        if (day <= 364) {
            return 2d;
        }
        return 5.6d;
    }

}