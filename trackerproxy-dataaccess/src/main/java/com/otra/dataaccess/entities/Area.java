package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@AllArgsConstructor
@Getter
@Setter
public class Area {
    private String areaId;
    private List<LatLng> coordinates;
}
