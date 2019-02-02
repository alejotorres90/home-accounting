package domain;

public class Payment {

    private int id;
    private String userName;
    private int value;
    private String creditor;
    private String date;

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

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        if (creditor == null) {
            throw new NullPointerException();
        } else {
            this.creditor = creditor;
        }
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

    @Override
    public String toString() {
        if (value%100>10){
            return userName + " paid $" + value/100 + "." + value%100 + " to " + creditor + " on " + getDateFormatted() + ".";
        } else if (value%100>0){
            return userName + " paid $" + value/100 + ".0" + value%100 + " to " + creditor + " on " + getDateFormatted() + ".";
        } else {
            return userName + " paid $" + value/100 + " to " + creditor + " on " + getDateFormatted() + ".";
        }
    }

}