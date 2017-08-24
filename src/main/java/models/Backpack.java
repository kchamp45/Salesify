package models;

/**
 * Created by Guest on 8/24/17.
 */
public class Backpack extends Item {
    private String color;
    private int capacity;

    public Backpack(String type, String name, int price, String dateSold, boolean sale,  int capacity, String color) {
        super(type, name, price, dateSold, sale);
        this.color = color;
        this.capacity = capacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Backpack backpack = (Backpack) o;

        if (capacity != backpack.capacity) return false;
        return color.equals(backpack.color);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + capacity;
        return result;
    }
}
