package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location implements Serializable {

    private Integer id;
    private String roadId;
    private String roadName;
    private String neighbourhood;
    private String suburb;
    private String city;
    private String county;
    private String stateDistrict;
    private String postcode;
    private String country;
    private String countryCode;
    private DateTime createdAt;
    private DateTime updatedAt;

    public Location(String roadId, String roadName, String neighbourhood, String suburb,
                    String city, String county, String stateDistrict, String postcode,
                    String country, String countryCode) {
        this.roadId = roadId;
        this.roadName = roadName;
        this.neighbourhood = neighbourhood;
        this.suburb = suburb;
        this.city = city;
        this.county = county;
        this.stateDistrict = stateDistrict;
        this.postcode = postcode;
        this.country = country;
        this.countryCode = countryCode;
    }

    public static class LocationMapper implements ResultSetMapper<Location> {

        public Location map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Location(resultSet.getInt("id"),
                    resultSet.getString("road_id"),
                    resultSet.getString("road_name"),
                    resultSet.getString("neighbourhood"),
                    resultSet.getString("suburb"),
                    resultSet.getString("city"),
                    resultSet.getString("county"),
                    resultSet.getString("state_district"),
                    resultSet.getString("postcode"),
                    resultSet.getString("country"),
                    resultSet.getString("country_code"),
                    new DateTime(resultSet.getTimestamp("created_at")),
                    new DateTime(resultSet.getTimestamp("updated_at")));
        }
    }
}
