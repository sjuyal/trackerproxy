package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Created by juyal.shashank on 08/12/16.
 */
@AllArgsConstructor
@Getter
@Data
public class MetricPair {
    String metric;
    Double distance;
    Double time;
}
