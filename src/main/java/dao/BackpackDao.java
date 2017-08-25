package dao;

import models.Backpack;
import models.Item;

import java.util.List;

public interface BackpackDao {
    List<Backpack> getAll(String type);

    void updateBackpack(int id, int capacity, String color);

    void deleteBackpack(int id);

    void clearAllBackpacks(String type);
}
