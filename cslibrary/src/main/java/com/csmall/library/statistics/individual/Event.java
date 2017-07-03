package com.csmall.library.statistics.individual;

import java.sql.Timestamp;

/**
 * Created by wangchao on 2016/6/7.
 */
public class Event implements Logable{
    public String type;
    public String name;
    public String detail;
    public Timestamp time = new Timestamp(System.currentTimeMillis());

    @Override
    public String toString() {
        String sb = type +
                "," +
                name +
                "," +
                detail +
                "," +
                time.getTime();
        return sb;
    }

}
