package com.inna.courses.dao;

import com.inna.courses.exc.DaoException;
import com.inna.models.Review;

import java.util.List;

public interface ReviewDao {
    void add(Review review)throws DaoException;

    List<Review> findAll();
    List<Review> findByCourseId(int courseId);


}
