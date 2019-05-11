package com.polaris;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class StopRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM STOPS WHERE ID = :id";
    private static final String SQL_FIND_ALL = "SELECT * FROM STOPS";
    private static final String SQL_INSERT = "INSERT INTO STOPS (REVIEWS) values(:reviews)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM STOPS WHERE ID = :id";

    private static final BeanPropertyRowMapper<Stop> ROW_MAPPER = new BeanPropertyRowMapper<>(Stop.class);

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public Stop findById(long id) {
        try {
            SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, paramSource, ROW_MAPPER);
        }
        catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Iterable<Stop> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, ROW_MAPPER);
    }

    public int save(Stop stop) {
        /*
        TODO: Not sure how to do this (or the rest of the queries in general)

        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("reviews", stop.getReviews());
         */
        SqlParameterSource paramSource = null;

        return jdbcTemplate.update(SQL_INSERT, paramSource);
    }

    public void deleteById(long id) {
        SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(SQL_DELETE_BY_ID, paramSource);
    }
}
