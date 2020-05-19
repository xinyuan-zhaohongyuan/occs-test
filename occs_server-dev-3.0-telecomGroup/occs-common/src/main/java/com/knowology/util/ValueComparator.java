package com.knowology.util;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<Map.Entry<String, Integer>>
{
    @Override
    public int compare(Map.Entry<String, Integer> mp1, Map.Entry<String, Integer> mp2) {
        return mp2.getValue() - mp1.getValue();
    }
}

