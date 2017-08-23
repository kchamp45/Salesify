package models;

/**
 * Created by Guest on 8/23/17.
 */
public class Order {
    private String number;
    private int id;

    public Order(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

        Order order = (Order) o;

        if (id != order.id) return false;
        return number.equals(order.number);
    }

    @Override
    public int hashCode() {
        int result = number.hashCode();
        result = 31 * result + id;
        return result;
    }
}
