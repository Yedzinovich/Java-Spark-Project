package com.inna.courses;

import com.google.gson.Gson;
import com.inna.courses.dao.CourseDao;
import com.inna.courses.dao.sql2oCourseDao;
import com.inna.courses.exc.ApiError;
import com.inna.models.Course;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Api {
    public static void main(String[] args) {
        String datasource = "jdbc:h2:~/reviews.db";
        if(args.length > 0){
            if(args.length != 2){
                System.out.println("java Api <port> ,datasource.");
                System.exit(0);
            }
            port(Integer.parseInt(args[0]));
            datasource = args[1];
        }

        Sql2o sql2o = new Sql2o(String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", datasource),
                "", "");
        CourseDao courseDao = new sql2oCourseDao(sql2o);
        Gson gson = new Gson();

        post("/courses", "application/json", (req, res) -> {
            Course course = gson.fromJson(req.body(), Course.class);
            courseDao.add(course);
            res.status(201);
            return course;
        }, gson::toJson);  //method reference

        get("/courses", "application/json",
                (req, res) -> courseDao.findAll(), gson::toJson);

        get("/courses/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Course course = courseDao.findById(id);
            if(course == null){
                throw new ApiError(404, "Could not find course with id " + id);
            }
            return course;
        }, gson::toJson);

        exception(ApiError.class, ((exception, request, response) -> {
            ApiError error = (ApiError) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", error.getStatus());
            jsonMap.put("error msg", error.getMessage());
            response.type("application/json");
            response.status(error.getStatus());
            response.body(gson.toJson(jsonMap));
        }));

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
