package com.csmall.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangchao on 2015/10/8.
 */
public class CollectionHelper {
    @SuppressWarnings("unchecked")
    public static List mapToList(Map map){
        List list = new ArrayList();
        if(map == null){
            return list;
        }
        for (Map.Entry entry : (Iterable<Map.Entry>) map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }
}
