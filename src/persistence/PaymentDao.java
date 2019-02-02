package persistence;

import domain.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PaymentDao {

    private ConnectionManager connectionManager;

    public PaymentDao(ConnectionManager connectionManager) {
        this.connectionManager=connectionManager;
    }

    public void sqlInsert(Payment payment) throws SQLException {
        String sql =  "INSERT INTO payments (userName, value, creditor, date) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
        statement.setString(1,payment.getUserName());
        statement.setInt(2,payment.getValue());
        statement.setString(3,payment.getCreditor());
        statement.setString(4,payment.getDate());
        statement.executeUpdate();
    }

    public void sqlUpdate(Payment payment) throws SQLException {
        String sql =  "UPDATE payments SET userName=?, value=?, creditor=?, date=? WHERE id=" + payment.getId();
        PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
        statement.setString(1,payment.getUserName());
        statement.setInt(2,payment.getValue());
        statement.setString(3,payment.getCreditor());
        statement.setString(4,payment.getDate());
        statement.executeUpdate();
    }

    public int sqlDelete(int id) throws SQLException {
        String sql =  "DELETE FROM payments WHERE id=" + id;
        Statement stmt = connectionManager.getConnection().createStatement();
        return stmt.executeUpdate(sql);
    }

    public ArrayList<Payment> sqlGetList() throws SQLException {
        ArrayList<Payment> payments = new ArrayList<Payment>();
        String sql =  "SELECT * FROM payments ORDER BY id DESC";
        Statement stmt = connectionManager.getConnection().createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            Payment e = new Payment();
            e.setId(resultSet.getInt(1));
            e.setUserName(resultSet.getString(2));
            e.setValue(resultSet.getInt(3));
            e.setCreditor(resultSet.getString(4));
            e.setDate(resultSet.getString(5));
            payments.add(e);
        }
        return payments;
    }

}