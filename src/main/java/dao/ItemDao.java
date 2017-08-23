package dao;

import models.Item;
import models.Order;

import java.util.List;

/**
 * Created by Guest on 8/23/17.
 */
public interface ItemDao {//create
    void add (Item item); //J

    void addItemToOrder(Item item, Order order); //D & E
//
//    //read
    List<Item> getAll(); //A
//
    List<Order> getAllOrdersForAItem(int itemId); //D & E
//
    Item findById(int id); //B & C
//
    //update item info
    void update(int id, String name, int price, String dateSold, boolean sale); //L
//
//    //delete individual item
//    void deleteById(int id); //K
//
    //delete all items
    void clearAllItems();

}
