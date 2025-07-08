package Customer;

public class Customer {
    private String name;
    private double balance;
    private String address;
    private String email;

    public Customer(String name, double balance, String address, String email) {
        this.name = name;
        this.balance = balance;
        this.address=address;
        this.email=email;
    }

    public void deductBalance(double amount) throws IllegalStateException {
        if (balance < amount) {
            throw new IllegalStateException("Insufficient balance");
        }
        balance -= amount;
    }

    public double getBalance() { return balance; }
    public String getName() { return name; }
    public String getAddress(){ return address;}
    public String getEmail(){ return email;}
}