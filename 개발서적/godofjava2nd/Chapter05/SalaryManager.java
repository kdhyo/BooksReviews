package godofjava2nd.Chapter05;

public class SalaryManager {

    private static double MONTH_COUNT = 12d;

    public static void main(String[] args) {
        SalaryManager salaryManager = new SalaryManager();
        int yearlySalary = 20000000;
        System.out.println(salaryManager.getMonthlySalary(yearlySalary));
    }

    public double getMonthlySalary(int yearlySalary) {
        double monthlySalary = yearlySalary / MONTH_COUNT;
        double tax = calculateTax(monthlySalary);
        double nationalPension = calculateNationalPension(monthlySalary);
        double healthInsurance = calculateHealthInsurance(monthlySalary);
        double deductibleAmount = tax + nationalPension + healthInsurance;

        monthlySalary -= deductibleAmount;
        return monthlySalary;
    }

    public double calculateTax(double monthSalary) {
        double result = monthSalary * 0.125;
        System.out.println("tax :: " + result);
        return result;
    }

    public double calculateNationalPension(double monthSalary) {
        double result = monthSalary * 0.081;
        System.out.println("nationalPension :: " + result);
        return result;
    }

    public double calculateHealthInsurance(double monthSalary) {
        double result = monthSalary * 0.135;
        System.out.println("healthInsurance :: " + result);
        return result;
    }

}