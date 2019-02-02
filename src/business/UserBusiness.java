package business;

import domain.User;
import persistence.UserDao;

import java.sql.SQLException;

public class UserBusiness {

    private UserDao userDao;

    public UserBusiness (UserDao userDao){
        this.userDao = userDao;
    }

    public String getDebt(String username) throws SQLException {
        int userDebt = userDao.sqlGetDebt(username);
        User[] creditors = userDao.sqlGetCreditors(username);

        if(userDebt!=0) {

            //Case user alone owes only one creditor or only one creditor owes the user
            for (int i = 0; i < 2; i++) {
                if (userDebt + creditors[i].getDebt() == 0) {
                    if (userDebt > creditors[i].getDebt()) {
                        String money;
                        if (userDebt % 100 > 10) {
                            money = userDebt / 100 + "." + userDebt % 100;
                        } else if (userDebt % 100 > 0){
                            money = userDebt / 100 + ".0" + userDebt % 100;
                        } else {
                            money = "" + userDebt / 100;
                        }
                        return "You owe " + creditors[i].getName() + " $" + money + ".";
                    } else {
                        String money;
                        if (creditors[i].getDebt() % 100 > 10) {
                            money = creditors[i].getDebt() / 100 + "." + creditors[i].getDebt() % 100;
                        } else if (creditors[i].getDebt() % 100 > 0) {
                            money = creditors[i].getDebt() / 100 + ".0" + creditors[i].getDebt() % 100;
                        } else {
                            money = "" + creditors[i].getDebt() / 100;
                        }
                        return creditors[i].getName() + " owes you $" + money + ".";
                    }
                }
            }

            //Case user and a creditor owe the other creditor
            if (userDebt>0 && creditors[0].getDebt()>0){
                String money;
                if (userDebt % 100 > 10) {
                    money = userDebt / 100 + "." + userDebt % 100;
                } else if (userDebt % 100 > 0) {
                    money = userDebt / 100 + ".0" + userDebt % 100;
                } else {
                    money = "" + userDebt / 100;
                }
                return "You owe " + creditors[1].getName() + " $" + money + ".";
            }

            if (userDebt>0 && creditors[1].getDebt()>0){
                String money;
                if (userDebt % 100 > 10) {
                    money = userDebt / 100 + "." + userDebt % 100;
                } else if (userDebt % 100 > 0) {
                    money = userDebt / 100 + ".0" + userDebt % 100;
                } else {
                    money = "" + userDebt / 100;
                }
                return "You owe " + creditors[0].getName() + " $" + money + ".";
            }

            //Case creditor owes user and another creditor
            if (userDebt<0 && creditors[0].getDebt()<0){
                String money;
                if ((-userDebt) % 100 > 10) {
                    money = -userDebt / 100 + "." + -userDebt % 100;
                } else if ((-userDebt) % 100 > 0) {
                    money = -userDebt / 100 + ".0" + -userDebt % 100;
                } else {
                    money = "" + -userDebt / 100;
                }
                return creditors[1].getName() + " owes you $" + money + ".";
            }

            if (userDebt<0 && creditors[1].getDebt()<0){
                String money;
                if ((-userDebt) % 100 > 10) {
                    money = -userDebt / 100 + "." + -userDebt % 100;
                } else if ((-userDebt) % 100 > 0) {
                    money = -userDebt / 100 + ".0" + -userDebt % 100;
                } else {
                    money = "" + -userDebt / 100;
                }
                return creditors[0].getName() + " owes you $" + money + ".";
            }

            //Case user owes both creditors
            if (userDebt > 0) {
                String money, money2;
                if ((userDebt + creditors[1].getDebt()) % 100 > 10) {
                    money = (userDebt + creditors[1].getDebt()) / 100 + "." + (userDebt + creditors[1].getDebt()) % 100;
                } else if ((userDebt + creditors[1].getDebt()) % 100 > 0) {
                    money = (userDebt + creditors[1].getDebt()) / 100 + ".0" + (userDebt + creditors[1].getDebt()) % 100;
                } else {
                    money = "" + (userDebt + creditors[1].getDebt()) / 100;
                }
                if ((userDebt + creditors[0].getDebt()) % 100 > 10) {
                    money2 = (userDebt + creditors[0].getDebt()) / 100 + "." + (userDebt + creditors[0].getDebt()) % 100;
                } else if ((userDebt + creditors[0].getDebt()) % 100 > 0) {
                    money2 = (userDebt + creditors[0].getDebt()) / 100 + ".0" + (userDebt + creditors[0].getDebt()) % 100;
                } else {
                    money2 = "" + (userDebt + creditors[0].getDebt()) / 100;
                }
                return "You owe " + creditors[0].getName() + " and " + creditors[1].getName() + " $" + money + " and $" + money2 + " respectively.";
            }

            //Case both creditors owe user
            if (userDebt < 0) {
                String money, money2;
                if (creditors[0].getDebt() % 100 > 10) {
                    money = creditors[0].getDebt() / 100 + "." + creditors[0].getDebt() % 100;
                } else if (creditors[0].getDebt() % 100 > 0) {
                    money = creditors[0].getDebt() / 100 + ".0" + creditors[0].getDebt() % 100;
                } else {
                    money = "" + creditors[0].getDebt() / 100;
                }
                if (creditors[1].getDebt() % 100 > 10) {
                    money2 = creditors[1].getDebt() / 100 + "." + creditors[1].getDebt() % 100;
                } else if (creditors[1].getDebt() % 100 > 0) {
                    money2 = creditors[1].getDebt() / 100 + ".0" + creditors[1].getDebt() % 100;
                } else {
                    money2 = "" + creditors[1].getDebt() / 100;
                }
                return creditors[0].getName() + " and " +  creditors[1].getName() + " owe you $" + money + " and $" +  money2 + " respectively.";
            }

        }
        return "You don't have any pending debts.";
    }

}