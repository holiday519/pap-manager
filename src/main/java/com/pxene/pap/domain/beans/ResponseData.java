package com.pxene.pap.domain.beans;

/**
 * Created by wangshuai on 2017/7/27.
 */
public class ResponseData {
    private String id;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
