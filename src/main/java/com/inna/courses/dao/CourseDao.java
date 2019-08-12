package com.inna.courses.dao;

import com.inna.courses.exc.DaoException;
import com.inna.models.Course;

import java.util.List;

public interface CourseDao {
    void add(Course course)throws DaoException;

    List<Course> findAll();

    Course findById(int id);
}
