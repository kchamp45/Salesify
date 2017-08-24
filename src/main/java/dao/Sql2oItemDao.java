package dao;

import models.Item;
import models.Order;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 8/23/17.
 */
public class Sql2oItemDao implements ItemDao{
    public final Sql2o sql2o;

    public Sql2oItemDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Item item){
        String sql = "INSERT INTO items (type, name, price, dateSold, sale) VALUES (:type, :name, :price, :dateSold, :sale)";

        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("type", item.getType())
                    .addParameter("name", item.getName())
                    .addParameter("price", item.getPrice())
                    .addParameter("dateSold", item.getDateSold())
                    .addParameter("sale", item.isSale())
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("PRICE", "price")
                    .addColumnMapping("DATESOLD", "dateSold")
                    .addColumnMapping("SALE", "sale")
                    .executeUpdate()
                    .getKey();
            item.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public Item findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM items WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Item.class);
        }
    }

    @Override
    public List<Item> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM items")
                    .executeAndFetch(Item.class);
        }
    }

    @Override
    public void update(int id, String newType, String newName, int newPrice, String newDate, boolean newSale) {
        String sql = "UPDATE items SET (type, name, price, dateSold, sale) = (:type, :name, :price, :dateSold, :sale) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("type", newType)
                    .addParameter("name", newName)
                    .addParameter("price", newPrice)
                    .addParameter("dateSold", newDate)
                    .addParameter("sale", newSale)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void clearAllItems() {
        String sql = "DELETE from items";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addItemToOrder(Item item, Order order) {
        String sql = "INSERT INTO items_orders (itemid, orderid) VALUES (:itemId, :orderId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("itemId", item.getId())
                    .addParameter("orderId", order.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public List<Order> getAllOrdersForAItem(int itemId) {
        List<Order> orders = new ArrayList(); // initialize an empty list
        String joinQuery = "SELECT orderid FROM items_orders WHERE itemid = :itemId";

        try (Connection con = sql2o.open()) {
            List<Integer> allOrdersIds = con.createQuery(joinQuery)
                    .addParameter("itemId", itemId)
                    .executeAndFetch(Integer.class);
            for (Integer foodId : allOrdersIds) {
                String orderQuery = "SELECT * FROM orders WHERE id = :orderId";
                orders.add(
                        con.createQuery(orderQuery)
                                .addParameter("orderId", foodId)
                                .executeAndFetchFirst(Order.class));
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return orders;
    }
}
