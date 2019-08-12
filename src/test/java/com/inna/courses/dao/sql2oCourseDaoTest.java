package com.inna.courses.dao;

import com.inna.models.Course;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class sql2oCourseDaoTest {

    private sql2oCourseDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        dao = new sql2oCourseDao(sql2o);
        //Keep connection open through the test
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception{
        Course course = newTestCourse();
        int originalCourseId = course.getId();

        dao.add(course);

        assertNotEquals(originalCourseId, course.getId());
    }

    @Test
    public void noCoursesReturnsEmptyList() throws Exception {
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void addedCoursesAreReturnedFromFindAll() throws Exception {
        Course course = newTestCourse();
        dao.add(course);
        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void existingCoursesCanBeFoundById() throws Exception {
        Course course = newTestCourse();
        dao.add(course);

        Course found = dao.findById(course.getId());
        assertEquals(course, found);
    }

    private Course newTestCourse() {
        return new Course("Test", "http://test.com");
    }
}