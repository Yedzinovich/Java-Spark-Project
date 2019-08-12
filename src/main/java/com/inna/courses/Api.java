package com.inna.courses;

import com.google.gson.Gson;
import com.inna.courses.dao.CourseDao;
import com.inna.courses.dao.sql2oCourseDao;
import com.inna.models.Course;
import org.sql2o.Sql2o;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;

public class Api {
    public static void main(String[] args) {
        Sql2o sql2o = new Sql2o("jdbc:h2:~/reviews.db;INIT=RUNSCRIPT from 'classpath:db/init.sql'");
        CourseDao courseDao = new sql2oCourseDao(sql2o);
        Gson gson = new Gson();

        post("/courses", "application/json", (req, res) -> {
            Course course = gson.fromJson(req.body(), Course.class);
            courseDao.add(course);
            res.status(201);
            return null;
        }, gson::toJson);  //method reference

        get("/courses", "application/json",
                (req, res) -> courseDao.findAll(), gson::toJson);

        get("/courses/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            //TODO: If not found?
            Course course = courseDao.findById(id);
            return course;
        }, gson::toJson);

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
