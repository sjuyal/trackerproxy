package com.otra.osmclient.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by juyal.shashank on 16/05/17.
 */
@AllArgsConstructor
@Data
public class GetReverseGeoCodingRequest {
    private String productId;
    private String sellerId;
}
