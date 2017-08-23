package dao;

import models.Item;
import models.Order;
import models.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by Guest on 8/23/17.
 */
public class Sql2oOrderDaoTest {
    private Sql2oOrderDao orderDao;
    private Connection conn;
    private Sql2oItemDao itemDao;


    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        orderDao = new Sql2oOrderDao(sql2o);
        itemDao = new Sql2oItemDao(sql2o);
        conn = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Order setupNewOrder(){
        return new Order("1");
    }
    public Order setupNewOrder2(){
        return new Order("2");
    }
    public Item setupNewItem() {
        return new Item("backpack", 20, "08-23-2017", true);
    }

    public Item setupNewItem2() {
        return new Item("tent", 50, "08-21-2017", false);
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Order order = setupNewOrder();
        int origOrderId = order.getId();
        orderDao.add(order);
        assertNotEquals(origOrderId, order.getId());
    }
    @Test
    public void existingOrderCanBeFoundById() throws Exception {
        Order order = setupNewOrder();
        orderDao.add(order);
        Order foundOrder = orderDao.findById(order.getId());
        assertEquals(order, foundOrder);
    }
    @Test
    public void addedOrdersAreReturnedFromGetAll() throws Exception {
        Order order = setupNewOrder();
        orderDao.add(order);
        assertEquals(1, orderDao.getAll().size());
    }
    @Test
    public void updateOrdersInfo() throws Exception {
        String initialOrderNumber = "1";
        Order order = setupNewOrder();
        orderDao.add(order);
        orderDao.update(order.getId(), "2");
        Order updatedOrder = orderDao.findById(order.getId());
        assertNotEquals(initialOrderNumber, updatedOrder.getNumber());
    }

    @Test
    public void clearAllOrders(){
        Order order = setupNewOrder();
        Order order2 = setupNewOrder2();
        orderDao.add(order);
        orderDao.add(order2);
        int daoSize = orderDao.getAll().size();
        orderDao.clearAllOrders();
        assertTrue(daoSize > 0 && daoSize >orderDao.getAll().size());
    }
    @Test
    public void addOrderToItemAddsTypeCorrectly() throws Exception {

        Item testItem = setupNewItem();
        Item altItem = setupNewItem2();

        itemDao.add(testItem);
        itemDao.add(altItem);

        Order testOrder = setupNewOrder();

        orderDao.add(testOrder);

        orderDao.addOrderToItem(testOrder, testItem);
        orderDao.addOrderToItem(testOrder, altItem);

        assertEquals(2, orderDao.getAllItemsForAOrder(testOrder.getId()).size());
    }
}