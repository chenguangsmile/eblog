package com.zhangbao.eblog.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Sunny
 * @create 2020-06-2020/6/16-21:24
 */
@Data
public class Result implements Serializable {
    private int status;
    private String msg;
    private Object data;
    private String action;

    public static Result succ() {
        Result m = new Result();
        m.setStatus(0);
        m.setData("");
        m.setMsg("操作成功");
        return m;
    }
    public static Result succ(Object data) {
        Result m = new Result();
        m.setStatus(0);
        m.setData(data);
        m.setMsg("操作成功");
        return m;
    }

    public static Result succ(String mess, Object data) {
        Result m = new Result();
        m.setStatus(0);
        m.setData(data);
        m.setMsg(mess);

        return m;
    }

    public static Result fail(String mess) {
        Result m = new Result();
        m.setStatus(-1);
        m.setData(null);
        m.setMsg(mess);

        return m;
    }

    public static Result fail(String mess, Object data) {
        Result m = new Result();
        m.setStatus(-1);
        m.setData(data);
        m.setMsg(mess);

        return m;
    }
    public Result action(String action){
        this.action = action;
        return this;
    }
}
