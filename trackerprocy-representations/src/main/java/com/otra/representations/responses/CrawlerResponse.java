package com.otra.representations.responses;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juyal.shashank on 16/09/15.
 */
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrawlerResponse {

    @JsonProperty("latitude")
    public Double latitude;
    @JsonProperty("fallback_of")
    public Object fallbackOf;
    @JsonProperty("uuid")
    public String uuid;
    @JsonProperty("nearby")
    public List<Nearby> nearby = null;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("city_uuid")
    public String cityUuid;
    @JsonProperty("longitude")
    public Double longitude;
    @JsonProperty("uuid_array")
    public List<String> uuidArray = null;
    @JsonProperty("center")
    public List<Double> center = null;
    @JsonProperty("super_type")
    public String superType;
    @JsonProperty("fallback_uuid")
    public Object fallbackUuid;
    @JsonProperty("feature_type")
    public String featureType;
    @JsonProperty("url_name")
    public String urlName;
    @JsonProperty("polygon")
    public Polygon polygon;
    @JsonProperty("name")
    public String name;
    @JsonProperty("original_uuid")
    public String originalUuid;
    @JsonProperty("bounding_box_uuids")
    public List<String> boundingBoxUuids = null;
    @JsonProperty("locality_polygon_uuid")
    public String localityPolygonUuid;
    @JsonProperty("city_id")
    public Integer cityId;
    @JsonProperty("_score")
    public Double score;

    @Data
    @AllArgsConstructor
    public static class Nearby {

        @JsonProperty("url_name")
        public String urlName;
        @JsonProperty("name")
        public String name;
        @JsonProperty("uuid")
        public String uuid;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Polygon {
        @JsonProperty("coordinates")
        public List<List<List<Double>>> coordinates = null;
        @JsonProperty("type")
        public String type;

    }

}
