package dao;

import models.Bike;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;


class Sql2oBikeDao extends Sql2oItemDao implements ItemDao {

    public Sql2oBikeDao(Sql2o sql2o) {
        super(sql2o);
    }

//    public void add(Bike bike){
//        String sql = "UPDATE items SET(gear, purpose) = (:gear, :purpose) WHERE id = :id";
//        try (Connection con = sql2o.open()) {
//            super.add(bike);
//            int id = bike.getId();
//            con.createQuery(sql)
//                    .addParameter("id", id)
//                    .addParameter("gear", bike.getGear())
//                    .addParameter("purpose", bike.getPurpose())
//                    .executeUpdate();
//        } catch (Sql2oException ex) {
//            System.out.println(ex);
//        }
//    }
}