package persistence;

import domain.Expense;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExpenseDao {

    private ConnectionManager connectionManager;

    public ExpenseDao(ConnectionManager connectionManager) {
        this.connectionManager=connectionManager;
    }

    public void sqlInsert(Expense expense) throws SQLException {
        String sql =  "INSERT INTO expenses (userName, description, value, people, alejoSpent, ianSpent, totiSpent, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
        statement.setString(1,expense.getUserName());
        statement.setString(2,expense.getDescription());
        statement.setInt(3,expense.getValue());
        statement.setInt(4,expense.getPeople());
        statement.setBoolean(5,expense.didAlejoSpend());
        statement.setBoolean(6,expense.didIanSpend());
        statement.setBoolean(7,expense.didTotiSpend());
        statement.setString(8,expense.getDate());
        statement.executeUpdate();
    }

    public void sqlUpdate(Expense expense) throws SQLException {
        String sql =  "UPDATE expenses SET userName=?, description=?, value=?, people=?, alejoSpent=?, ianSpent=?, totiSpent=?, date=? WHERE id=" + expense.getId();
        PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
        statement.setString(1,expense.getUserName());
        statement.setString(2,expense.getDescription());
        statement.setInt(3,expense.getValue());
        statement.setInt(4,expense.getPeople());
        statement.setBoolean(5,expense.didAlejoSpend());
        statement.setBoolean(6,expense.didIanSpend());
        statement.setBoolean(7,expense.didTotiSpend());
        statement.setString(8,expense.getDate());
        statement.executeUpdate();
    }

    public int sqlDelete(int id) throws SQLException {
        String sql =  "DELETE FROM expenses WHERE id=" + id;
        Statement stmt = connectionManager.getConnection().createStatement();
        return stmt.executeUpdate(sql);
    }

    public ArrayList<Expense> sqlGetList() throws SQLException {
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        String sql =  "SELECT * FROM expenses ORDER BY id DESC";
        Statement stmt = connectionManager.getConnection().createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            Expense e = new Expense();
            e.setId(resultSet.getInt(1));
            e.setUserName(resultSet.getString(2));
            e.setDescription(resultSet.getString(3));
            e.setValue(resultSet.getInt(4));
            e.setPeople(resultSet.getInt(5));
            e.setAlejoSpent(resultSet.getBoolean(6));
            e.setIanSpent(resultSet.getBoolean(7));
            e.setTotiSpent(resultSet.getBoolean(8));
            e.setDate(resultSet.getString(9));
            expenses.add(e);
        }
        return expenses;
    }

}