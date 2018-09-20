package com.yql.module_baseevent.BusEventBean;

/**
 * Created by kanlulu
 * DATE: 2018/9/8 12:37
 */
public class TestEvent {
    private String result;

    public TestEvent() {
    }

    public TestEvent(String result) {
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
        return "TestEvent{" +
                "result='" + result + '\'' +
                '}';
    }
}
