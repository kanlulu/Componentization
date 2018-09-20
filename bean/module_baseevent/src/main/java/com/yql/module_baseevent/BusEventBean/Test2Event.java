package com.yql.module_baseevent.BusEventBean;

/**
 * Created by kanlulu
 * DATE: 2018/9/8 12:47
 */
public class Test2Event {
    private String result;

    public Test2Event() {
    }

    public Test2Event(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Test2Event{" +
                "result='" + result + '\'' +
                '}';
    }
}
