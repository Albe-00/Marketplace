package DAO;

import java.util.List;

public abstract class DAO {
    protected DatabaseConnection dbConnection;

    public abstract Object select(int id);
    public abstract List<Object> selectAll();
    public abstract boolean delete(int id);
    public abstract int insert(Object obj);
    public abstract boolean update(Object obj);
}
