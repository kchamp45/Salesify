package dao;

import models.Backpack;
import models.Item;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

class Sql2oBackpackDao extends Sql2oItemDao implements ItemDao {

    public Sql2oBackpackDao(Sql2o sql2o) {
        super(sql2o);
    }

    public void add(Backpack backpack) {
        String sql = "UPDATE items SET(capacity, color) = (:capacity, :color) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            super.add(backpack);
            int id = backpack.getId();
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("capacity", backpack.getCapacity())
                    .addParameter("color", backpack.getColor())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public Backpack findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM items WHERE id = :id")
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Backpack.class);
        }
    }

    public List getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM items WHERE type= backpack")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Backpack.class);
        }
    }

    public void updateBackpack(int id, String type, String name, int price, String dateSold, boolean sale, int capacity, String color) {
        String sql = "UPDATE items SET (capacity, color) = (:capacity, :color) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("capacity", capacity)
                    .addParameter("color", color)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public void deleteBackpack(int id) {
        String sql = "DELETE from items WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public void deleteAllBackpacks() {
        String sql = "DELETE from items WHERE type=:backpack";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }
}
