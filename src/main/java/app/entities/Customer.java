package app.entities;

public class Customer {
    int customerId;
    String customerEmail;
    String customerName;
    String customerPassword;
    int customerBalance;


    public Customer(int customerId, String customerEmail, String customerPassword) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
    }


    public Customer(int customerId, String customerName, String customerPassword, String customerEmail, int customerBalance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customerEmail = customerEmail;
        this.customerBalance = customerBalance;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerPassword='" + customerPassword + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerBalance=" + customerBalance +
                '}';
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public int getCustomerBalance() {
        return customerBalance;
    }
}
