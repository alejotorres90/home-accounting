package domain;

public class User {
    private String name;
    private int debt;

    public User (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }
}