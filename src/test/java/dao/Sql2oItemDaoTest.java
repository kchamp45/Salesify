package dao;

import models.Item;
import models.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;


public class Sql2oItemDaoTest {
    private Sql2oItemDao itemDao;
    private Sql2oOrderDao orderDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        itemDao = new Sql2oItemDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Item item = setupNewItem();
        int itemId = item.getId();
        itemDao.add(item);
        assertNotEquals(itemId, item.getId());
    }

    public Item setupNewItem() {
        return new Item("backpack", 20, "08-23-2017", true);
    }

    public Item setupNewItem2() {
        return new Item("tent", 50, "08-21-2017", false);
    }

    @Test
    public void canFindItemById() throws Exception {
        Item item = setupNewItem();
        itemDao.add(item);
        Item foundItem = itemDao.findById(item.getId());
        assertEquals(item, foundItem);
    }

    @Test
    public void returnAllAddedItems() throws Exception {
        Item item = setupNewItem();
        itemDao.add(item);
        assertEquals(1, itemDao.getAll().size());
    }

    @Test
    public void updateItemsInfo() throws Exception {
        String initialName = "backpack";
        Item item = setupNewItem();
        itemDao.add(item);
        itemDao.update(item.getId(), "napsack", 20, "08-22-2017", true);
        Item updatedItem = itemDao.findById(item.getId());
        assertNotEquals(initialName, updatedItem.getName());

    }
    @Test
    public void clearAllItems(){
        Item item = setupNewItem();
        Item item2 = setupNewItem2();
        itemDao.add(item);
        itemDao.add(item2);
        int daoSize = itemDao.getAll().size();
        itemDao.clearAllItems();
        assertTrue(daoSize > 0 && daoSize >itemDao.getAll().size());

    }
    @Test
    public void getAllOrdersForAItemReturnsOrdersCorrectly() throws Exception {
        Order testOrder  = new Order("1");
        Order otherOrder  = new Order("2");
        orderDao.add(testOrder);
        orderDao.add(otherOrder);

        Item testItem = setupNewItem();
        itemDao.add(testItem);
        itemDao.addItemToOrder(testItem,testOrder);
        itemDao.addItemToOrder(testItem,otherOrder);

        Order[] orders = {testOrder, otherOrder}; //cf our results directly with our list of expected orders

        assertEquals(itemDao.getAllOrdersForAItem(testItem.getId()), Arrays.asList(orders));
    }
}
