package godofjava2nd.Chapter01;

public class Calculator {

    private Calculator() {
    }

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        int num1 = 10;
        int num2 = 5;
        System.out.println(calculator.add(num1, num2));
        System.out.println(calculator.subtract(num1, num2));
        System.out.println(calculator.multiply(num1, num2));
        System.out.println(calculator.divide(num1, num2));
    }

    public int add(int num1, int num2) {
        return num1 + num2;
    }

    public int subtract(int num1, int num2) {
        return num1 - num2;
    }

    public int multiply(int num1, int num2) {
        return num1 * num2;
    }

    public int divide(int num1, int num2) {
        return num1 / num2;
    }

}