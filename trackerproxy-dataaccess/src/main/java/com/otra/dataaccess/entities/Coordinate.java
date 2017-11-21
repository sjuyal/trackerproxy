package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@AllArgsConstructor
@Data
public class Coordinate{
    private Double latitude;
    private Double longitude;
}
