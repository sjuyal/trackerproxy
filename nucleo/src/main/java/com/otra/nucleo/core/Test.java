package com.otra.nucleo.core;

import com.otra.nucleo.utils.MapUtils;

/**
 * Created by juyal.shashank on 24/12/16.
 */
public class Test {
    public static void main(String args[]){
        // 1:  12.912018, 77.644500
        // 2: 12.911800, 77.644490
        System.out.println(MapUtils.distance(12.912018, 12.911800, 77.644500, 77.644490, 0, 0));
    }
}
