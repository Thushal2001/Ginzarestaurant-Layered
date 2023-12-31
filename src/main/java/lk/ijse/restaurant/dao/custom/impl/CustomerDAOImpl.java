package lk.ijse.restaurant.dao.custom.impl;

import lk.ijse.restaurant.dao.custom.CustomerDAO;
import lk.ijse.restaurant.dao.custom.impl.util.SQLUtil;
import lk.ijse.restaurant.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<Customer> loadAll() throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM customers");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6));
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public int save(Customer c) throws SQLException {
        return SQLUtil.execute("INSERT INTO customers VALUES (?,?,?,?,?,?)", c.getId(), c.getName(), c.getNic(), c.getEmail(), c.getContact(), c.getAddress());
    }

    @Override
    public Customer search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customers WHERE id=?", id);
        if (rst.next()) {
            return new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6));
        }
        return null;
    }

    @Override
    public int update(Customer c) throws SQLException {
        return SQLUtil.execute("UPDATE customers SET name=? , nic=? , email=? , contact=? , address=? WHERE id=?", c.getName(), c.getNic(), c.getEmail(), c.getContact(), c.getAddress(), c.getId());
    }

    @Override
    public int delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM customers WHERE id=?", id);
    }

    @Override
    public List<String> loadCustomerIds() throws SQLException {
        List<String> arrayList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT id FROM customers ORDER BY id ASC");
        while (rst.next()) {
            arrayList.add(rst.getString(1));
        }
        return arrayList;
    }
}
