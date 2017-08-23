package dao;

import models.Item;
import models.Order;

import java.util.List;

/**
 * Created by Guest on 8/23/17.
 */
public interface OrderDao {
    void add(Order order);

    void addOrderToItem(Order order, Item item);

    //read
    //find individual order
    Order findById(int id);
//
    List<Order> getAll();
//
    List<Item> getAllItemsForAOrder(int id);

    //update item info
    void update(int id, String number);

    //delete all items
    void clearAllOrders();

//    //delete
//    void deleteById(int id);
}

