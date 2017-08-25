import com.google.gson.Gson;

import exceptions.ApiException;
import org.sql2o.Sql2o;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;
import com.google.gson.Gson;
import dao.Sql2oOrderDao;
import dao.Sql2oItemDao;
import dao.Sql2oBackpackDao;
import models.Order;
import models.Item;
import models.Backpack;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;


    public class App {

        public static void main(String[] args) {
            Sql2oOrderDao orderDao;
            Sql2oItemDao itemDao;
            Sql2oBackpackDao backpackDao;
            org.sql2o.Connection conn;
            Gson gson = new Gson();

            String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'"; //check me!
            Sql2o sql2o = new Sql2o(connectionString, "", "");
            itemDao = new Sql2oItemDao(sql2o);
            orderDao = new Sql2oOrderDao(sql2o);
            backpackDao = new Sql2oBackpackDao(sql2o);
            conn = sql2o.open();

            //CREATE
            post("/items/new", "application/json", (req, res) -> {
                Item item = gson.fromJson(req.body(), Item.class);
                itemDao.add(item);
                res.status(201);;
                return gson.toJson(item);
            });
            get("/items", "application/json", (req, res) -> {
                Item item = gson.fromJson(req.body(), Item.class);
                res.status(201);;
                return gson.toJson(itemDao.getAll());
            });

            post("/backpacks/new", "application/json", (req, res) -> {
                Backpack backpack = gson.fromJson(req.body(), Backpack.class);
                backpackDao.add(backpack);
                res.status(201);
                return gson.toJson(backpack);
            });

//            //READ
//            get("/items", "application/json", (req, res) -> {
//                return gson.toJson(itemDao.getAll());
//            });
//
//            get("/items/:id", "application/json", (req, res) -> {
//                int itemId = Integer.parseInt(req.params("id"));
//                return gson.toJson(itemDao.findById(itemId));
//            });
//
            //FILTERS
            exception(ApiException.class, (exception, req, res) -> {
                ApiException err = (ApiException) exception;
                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("status", err.getStatusCode());
                jsonMap.put("errorMessage", err.getMessage());
                res.type("application/json");
                res.status(err.getStatusCode());
                res.body(gson.toJson(jsonMap));
            });

            after((req, res) ->{
                res.type("application/json");
            });
//            post("/orders/new", "application/json", (req, res) -> {
//                Order order = gson.fromJson(req.body(), Order.class);
//                orderDao.add(order);
//                res.status(201);;
//                return gson.toJson(order);
//            });
//
//            //READ
//            get("/orders", "application/json", (req, res) -> {
//                return gson.toJson(orderDao.getAll());
//            });
//
//            get("/orders/:id", "application/json", (req, res) -> {
//                int orderId = Integer.parseInt(req.params("id"));
//                return gson.toJson(orderDao.findById(orderId));
//            });
//
//            post("/items/:itemId/backpack/new", "application/json", (req, res) -> {
//                int itemId = Integer.parseInt(req.params("itemId"));
//                Backpack backpack = gson.fromJson(req.body(), Backpack.class);
//                backpack.setBackpackId(itemId); //why do I need to set separately?
//                backpackDao.add(backpack);
//                res.status(201);
//                return gson.toJson(backpack);
//            });
        }
    }


