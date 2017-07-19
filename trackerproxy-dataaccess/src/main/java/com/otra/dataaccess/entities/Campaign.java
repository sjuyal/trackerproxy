package com.otra.dataaccess.entities;

import com.otra.dataaccess.enums.CampaignState;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;
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
public class Campaign implements Serializable {

    private Integer id;
    private String name;
    private Integer advertiserId;
    private CampaignState status;
    private DateTime startDate;
    private DateTime endDate;
    private Integer budget;
    private DateTime createdAt;
    private DateTime updatedAt;

    public static class CampaignMapper implements ResultSetMapper<Campaign> {

        public Campaign map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

            CampaignState campaignState = CampaignState.getCampaignState(resultSet.getString("status"));

            return new Campaign(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("advertiser_id"),
                    campaignState,
                    new DateTime(resultSet.getTimestamp("start_date")),
                    new DateTime(resultSet.getTimestamp("end_date")),
                    resultSet.getInt("budget"),
                    new DateTime(resultSet.getTimestamp("created_at")),
                    new DateTime(resultSet.getTimestamp("updated_at")));
        }
    }

    public Boolean isRunning() {
        if ((startDate.isEqualNow() || startDate.isBeforeNow()) && (endDate.isEqualNow() || endDate.isAfterNow())) {
            if (CampaignState.LIVE.name().equalsIgnoreCase(status.name())) {
                return true;
            }
        }
        return false;
    }
}
