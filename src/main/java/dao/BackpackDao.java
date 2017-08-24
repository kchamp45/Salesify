package dao;

import models.Backpack;
import models.Item;

import java.util.List;

/**
 * Created by Guest on 8/24/17.
 */
public interface BackpackDao {
    List<Backpack> getAll(String type);

    void updateBackpack(int id, int capacity, String color);

    void deleteBackpack(int id);

    void clearAllBackpacks(String type);
}
