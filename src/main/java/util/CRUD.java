package util;

import java.util.List;

public interface CRUD<Table> {
    void save(Table entity) throws Exception;

    void update(Table entity);

    void delete(Table entity);

    List<Table> findAll();

    Table find(Integer id);
}
