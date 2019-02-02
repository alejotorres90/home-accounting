package domain;

public class Expense {
    private int id;
    private String userName;
    private String description;
    private int value;
    private int people;
    private String date;
    private boolean alejoSpent;
    private boolean ianSpent;
    private boolean totiSpent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new NullPointerException();
        } else {
            this.description = description;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value == 0) {
            throw new NullPointerException();
        } else {
            this.value = value;
        }
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateFormatted() {
        return date.substring(5, 7) + "/" + date.substring(8, 10) + "/" + date.substring(0, 4);
    }

    public boolean didAlejoSpend() {
        return alejoSpent;
    }

    public void setAlejoSpent(boolean alejoSpent) {
        this.alejoSpent = alejoSpent;
    }

    public boolean didIanSpend() {
        return ianSpent;
    }

    public void setIanSpent(boolean ianSpent) {
        this.ianSpent = ianSpent;
    }

    public boolean didTotiSpend() {
        return totiSpent;
    }

    public void setTotiSpent(boolean totiSpent) {
        this.totiSpent = totiSpent;
    }

    @Override
    public String toString() {

        String expense = userName + " spent $";


        if (value%100>10) {
            expense = expense + value / 100 + "." + value % 100 + " on "+ getDateFormatted() + ". " + people + " people participated, ";
        } else if (value%100>0) {
            expense = expense + value / 100 + ".0" + value % 100 + " on " + getDateFormatted() + ". " + people + " people participated, ";
        } else {
            expense = expense + value / 100 + " on " + getDateFormatted() + ". " + people + " people participated, ";
        }

        if (userName.equals("Alejo")){
            if (ianSpent && totiSpent){
                expense = expense + "Ian and Toti ";
            } else if (ianSpent){
                expense = expense + "Ian ";
            } else {
                expense = expense + "Toti ";
            }
        }

        if (userName.equals("Ian")){
            if (alejoSpent && totiSpent){
                expense = expense + "Alejo and Toti ";
            } else if (alejoSpent){
                expense = expense + "Alejo ";
            } else {
                expense = expense + "Toti ";
            }
        }

        if (userName.equals("Toti")){
            if (alejoSpent && ianSpent){
                expense = expense + "Alejo and Ian ";
            } else if (alejoSpent){
                expense = expense + "Alejo ";
            } else {
                expense = expense + "Ian ";
            }
        }

        expense = expense + "among them.";

        return expense;

    }
}