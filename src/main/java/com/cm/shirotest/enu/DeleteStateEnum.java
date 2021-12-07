package com.cm.shirotest.enu;

/**
 * @author 陈萌
 * @Date 2021/12/7 0007 21:39
 * @ProjectName shiro-test
 */
public enum DeleteStateEnum {

    NO(0, "未删除"),
    YES(0, "未删除"),
    ;
    String describe;
    Integer value;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    DeleteStateEnum(Integer value, String describe) {
        this.describe = describe;
        this.value = value;
    }
}
