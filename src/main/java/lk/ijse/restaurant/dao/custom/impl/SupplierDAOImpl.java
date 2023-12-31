package lk.ijse.restaurant.dao.custom.impl;

import lk.ijse.restaurant.dao.custom.SupplierDAO;
import lk.ijse.restaurant.dao.custom.impl.util.SQLUtil;
import lk.ijse.restaurant.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public List<Supplier> loadAll() throws SQLException {
        List<Supplier> supplierList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM suppliers");
        while (rst.next()) {
            Supplier supplier = new Supplier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
            supplierList.add(supplier);
        }
        return supplierList;
    }

    @Override
    public int save(Supplier s) throws SQLException {
        return SQLUtil.execute("INSERT INTO suppliers VALUES(?,?,?,?)", s.getId(), s.getName(), s.getContact(), s.getAddress());
    }

    @Override
    public Supplier search(String id) throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM suppliers WHERE id=?",id);
        if (rst.next()){
            return new Supplier(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));
        }
        return null;
    }

    @Override
    public int update(Supplier s) throws SQLException {
        return SQLUtil.execute("UPDATE suppliers SET name=? , contact=? , address=? WHERE id=?",s.getName(),s.getContact(),s.getAddress(),s.getId());
    }

    @Override
    public int delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM suppliers WHERE id=?",id);
    }
}
