package models;

import java.time.LocalDateTime;

/**
 * Created by Guest on 8/23/17.
 */
public class Item {
    private String name;
    private int price;
    private String dateSold;
    private boolean sale;
    private int id;

    public Item(String name, int price, String dateSold, boolean sale) {
        this.name = name;
        this.price = price;
        this.dateSold = dateSold;
        this.sale = sale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSold(boolean sale) {
        this.sale = sale;
    }

    public String getDateSold() {
        return dateSold;
    }

    public void setDateSold(String dateSold) {
        this.dateSold = dateSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (price != item.price) return false;
        if (sale != item.sale) return false;
        if (id != item.id) return false;
        if (!name.equals(item.name)) return false;
        return dateSold != null ? dateSold.equals(item.dateSold) : item.dateSold == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price;
        result = 31 * result + (sale ? 1 : 0);
        result = 31 * result + (dateSold != null ? dateSold.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
