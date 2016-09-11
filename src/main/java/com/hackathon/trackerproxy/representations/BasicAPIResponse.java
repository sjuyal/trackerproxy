package com.hackathon.trackerproxy.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by juyal.shashank on 16/09/15.
 */
@Getter
@Setter
@AllArgsConstructor
public class BasicAPIResponse {

    private String message;

}
