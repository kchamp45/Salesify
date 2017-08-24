package models;

/**
 * Created by Guest on 8/24/17.
 */
public class Bike extends Item {
    private int gear;
    private String purpose;

    public Bike(String type, String name, int price, String dateSold, boolean sale, int gear, String purpose) {
        super(type, name, price, dateSold, sale);
        this.gear = gear;
        this.purpose = purpose;
    }

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Bike bike = (Bike) o;

        if (gear != bike.gear) return false;
        return purpose.equals(bike.purpose);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + gear;
        result = 31 * result + purpose.hashCode();
        return result;
    }
}
