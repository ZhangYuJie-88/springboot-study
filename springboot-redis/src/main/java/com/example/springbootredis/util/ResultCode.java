package com.example.springbootredis.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h3>springboot-study</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-29 17:47
 **/
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {
    SUCCESS("00000", "success"),
    // ------------------------系统相关------------------------------------
    SYSTEM_EXECUTION_ERROR("B0001", "系统执行出错");
    private String code;
    private String msg;

    public static ResultCode getValue(String code) {
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    }
