package persistence;

import domain.Expense;
import domain.Payment;
import domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {

    private ConnectionManager connectionManager;

    public UserDao(ConnectionManager connectionManager){
        this.connectionManager=connectionManager;
    }

    public int sqlGetDebt(String userName) throws SQLException{
        String sql = "SELECT * FROM users WHERE name = '" + userName + "'";
        Statement stmt = connectionManager.getConnection().createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        resultSet.next();
        return resultSet.getInt(2);
    }

    public User[] sqlGetCreditors(String userName) throws SQLException{
        User[] creditors = new User[2];
        String sql = "SELECT * FROM users WHERE name != '" + userName + "'";
        Statement stmt = connectionManager.getConnection().createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        resultSet.next();
        creditors[0] = new User(resultSet.getString(1));
        creditors[0].setDebt(resultSet.getInt(2));
        resultSet.next();
        creditors[1] = new User(resultSet.getString(1));
        creditors[1].setDebt(resultSet.getInt(2));
        return creditors;
    }

    public void sqlUpdateDebtsByPayment(Payment payment) throws SQLException{
        String sql="";
        Statement stmt;
        sql = "UPDATE users SET debt=debt-" + payment.getValue() + " WHERE name = '" + payment.getUserName() + "'";
        stmt = connectionManager.getConnection().createStatement();
        stmt.executeUpdate(sql);
        sql = "UPDATE users SET debt=debt+" + payment.getValue() + " WHERE name = '" + payment.getCreditor() + "'";
        stmt = connectionManager.getConnection().createStatement();
        stmt.executeUpdate(sql);

    }

    public void sqlUpdateDebtsByExpense(Expense expense) throws SQLException{
        String sql;
        Statement stmt;
        int count = 0;

        if(expense.didAlejoSpend()) {
            sql = "UPDATE users SET debt=debt+" + expense.getValue() / expense.getPeople() + " WHERE name = 'Alejo'";
            stmt = connectionManager.getConnection().createStatement();
            stmt.executeUpdate(sql);
            count++;
        }

        if(expense.didIanSpend()) {
            sql = "UPDATE users SET debt=debt+" + expense.getValue() / expense.getPeople() + " WHERE name = 'Ian'";
            stmt = connectionManager.getConnection().createStatement();
            stmt.executeUpdate(sql);
            count++;
        }

        if(expense.didTotiSpend()) {
            sql = "UPDATE users SET debt=debt+" + expense.getValue() / expense.getPeople() + " WHERE name = 'Toti'";
            stmt = connectionManager.getConnection().createStatement();
            stmt.executeUpdate(sql);
            count++;
        }

        sql = "UPDATE users SET debt=debt-" + expense.getValue() / expense.getPeople() * count + " WHERE name = '" + expense.getUserName() + "'";
        stmt = connectionManager.getConnection().createStatement();
        stmt.executeUpdate(sql);

    }

}