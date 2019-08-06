package com.inna.courses.dao;

import com.inna.courses.exc.DaoException;
import com.inna.models.Course;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class sql2oCourseDao implements CourseDao {

    private final Sql2o sql2o;

    public sql2oCourseDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Course course) throws DaoException {
        String sql = "INSERT INTO courses(name, url) VALUES (:name, :url)";
        try(Connection con = sql2o.open()){
            int id = (int)con.createQuery(sql).bind(course).executeUpdate().getKey();
            course.setId(id);
        }catch (Sql2oException e){
            throw new DaoException(e, "Problem adding the course " + course);
        }
    }

    @Override
    public List<Course> findAll() {
        return null;
    }
}
