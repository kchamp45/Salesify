package dao;

import models.Backpack;
import models.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by Guest on 8/24/17.
 */
public class Sql2oBackpackDaoTest {
    private Sql2oItemDao itemDao;
    private Sql2oOrderDao orderDao;
    private Sql2oBackpackDao backpackDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        itemDao = new Sql2oItemDao(sql2o);
        backpackDao = new Sql2oBackpackDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Backpack backpack = setupNewBackpack();
        int backpackId = backpack.getId();
        backpackDao.add(backpack);
        assertNotEquals(backpackId, backpack.getId());
    }

    @Test
    public void canFindBackpackById() throws Exception {
        Backpack backpack = setupNewBackpack();
        backpackDao.add(backpack);
        Backpack foundBackpack = backpackDao.findById(backpack.getId());
        assertEquals(backpack, foundBackpack);
    }

    @Test
    public void returnAllAddedBackpacks() throws Exception {
        Backpack backpack = setupNewBackpack();
        backpackDao.add(backpack);
        assertEquals(1, backpackDao.getAll().size());
    }
    @Test
    public void updateBackpackInfo() throws Exception {
        String initialCapacity = "30";
        Backpack backpack = setupNewBackpack();
        backpackDao.add(backpack);
        backpackDao.updateBackpack(backpack.getId(), "napsack", "Jansen", 20, "08-23-2017", true, 20, "green");
        Backpack updatedBackpack = backpackDao.findById(backpack.getId());
        assertNotEquals(initialCapacity, updatedBackpack.getCapacity());
    }
    @Test
    public void deleteBackpack() throws Exception {
        Backpack backpack = setupNewBackpack();
        backpackDao.add(backpack);
        backpackDao.deleteBackpack(backpack.getId());
        assertEquals(0, backpackDao.getAll().size());
    }

    @Test
    public void clearAllBackpacks(){
        Backpack backpack = setupNewBackpack();
        Backpack backpack2 = new Backpack("backpack", "Hello Kitty", 10, "07-22-2017", true, 10, "pink");
        backpackDao.add(backpack);
        backpackDao.add(backpack2);
        int daoSize = backpackDao.getAll().size();
        backpackDao.deleteAllBackpacks();
        assertTrue(daoSize > 0 && daoSize >backpackDao.getAll().size());

    }

    public Backpack setupNewBackpack() {
        return new Backpack("backpack", "Jansen", 20, "08-23-2017", true, 20, "green");
    }
    public Item setupNewItem() {
        return new Item("backpack", "Jansen", 20, "08-23-2017", true);
    }

    public Item setupNewItem2() {
        return new Item("tent", "Coleman", 50, "08-21-2017", false);
    }
}