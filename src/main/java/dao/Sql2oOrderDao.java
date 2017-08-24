package dao;

import models.Item;
import models.Order;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 8/23/17.
 */
public class Sql2oOrderDao implements OrderDao {
    private final Sql2o sql2o;

    public Sql2oOrderDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Order order) {
        String sql = "INSERT INTO orders (number) VALUES (:number)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("number", order.getNumber())
                    .addColumnMapping("NUMBER", "number")
                    .executeUpdate()
                    .getKey();
            order.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Order findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Order.class);
        }
    }
    @Override
    public List<Order> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders")
                    .executeAndFetch(Order.class);
        }
    }
    @Override
    public void update(int id, String newNumber) {
        String sql = "UPDATE orders SET (number) = (:number) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("number", newNumber)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void clearAllOrders() {
        String sql = "DELETE from orders";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void addOrderToItem(Order order, Item item){
        String sql = "INSERT INTO items_orders (itemid, orderid) VALUES (:itemId, :orderId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("itemId", item.getId())
                    .addParameter("orderId", order.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Item> getAllItemsForAOrder(int orderId) {
        List<Item> items = new ArrayList();
        String joinQuery = "SELECT itemid FROM items_orders WHERE orderid = :orderId";//pull out itemids for join table when they match a orderId in the same table

        try (Connection con = sql2o.open()) {
            List<Integer> allItemIds = con.createQuery(joinQuery)
                    .addParameter("orderId", orderId)
                    .executeAndFetch(Integer.class);
            for (Integer itemId : allItemIds){
                String itemQuery = "SELECT * FROM items WHERE id = :itemId";
                items.add(
                        con.createQuery(itemQuery)
                                .addParameter("itemId", itemId) //add itemId to sql query for our search
                                .executeAndFetchFirst(Item.class));//items (objects) pulled out will be from the item class
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return items; //return the values found from above evaluation
    }

}
