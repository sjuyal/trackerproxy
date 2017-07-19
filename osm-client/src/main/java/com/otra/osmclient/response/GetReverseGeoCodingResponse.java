package com.otra.osmclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Map;

/**
 * Created by juyal.shashank on 16/05/17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetReverseGeoCodingResponse {
    @JsonProperty("place_id")
    public String placeId;
    @JsonProperty("licence")
    public String licence;
    @JsonProperty("osm_type")
    public String osmType;
    @JsonProperty("osm_id")
    public String osmId;
    @JsonProperty("lat")
    public String lat;
    @JsonProperty("lon")
    public String lon;
    @JsonProperty("display_name")
    public String displayName;
    @JsonProperty("address")
    public Address address;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        @JsonProperty("road")
        public String road;
        @JsonProperty("neighbourhood")
        public String neighbourhood;
        @JsonProperty("suburb")
        public String suburb;
        @JsonProperty("city")
        public String city;
        @JsonProperty("county")
        public String county;
        @JsonProperty("state_district")
        public String stateDistrict;
        @JsonProperty("postcode")
        public String postcode;
        @JsonProperty("country")
        public String country;
        @JsonProperty("country_code")
        public String countryCode;
    }



}
