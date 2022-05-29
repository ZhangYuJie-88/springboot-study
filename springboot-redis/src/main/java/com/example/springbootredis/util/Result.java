package com.example.springbootredis.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author haoxr
 * @date 2020-06-23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private String code;

    private T data;

    private String msg;

    private Integer total;

    public Result(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public Result(T data, String msg, String code) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    @JsonIgnore
    public static <T> Result<T> success() {
        return success(null);
    }

    @JsonIgnore
    public static <T> Result<T> success(T data) {
        ResultCode rce = ResultCode.SUCCESS;
        return result(rce, data);
    }

    @JsonIgnore
    public static <T> Result<T> success(T data, Long total) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(data);
        result.setTotal(total.intValue());
        return result;
    }


    public static <T> Result<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failed();
        }
    }

    public static <T> Result<T> failed() {
        return result(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), ResultCode.SYSTEM_EXECUTION_ERROR.getMsg(), null);
    }

    public static <T> Result<T> failed(IResultCode resultCode) {
        return result(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static <T> Result<T> failed(IResultCode resultCode, String msg) {
        return result(resultCode.getCode(), StringUtils.isEmpty(msg) ? resultCode.getMsg() : msg, null);
    }

    private static <T> Result<T> result(IResultCode resultCode, T data) {
        return result(resultCode.getCode(), resultCode.getMsg(), data);
    }

    private static <T> Result<T> result(String code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    @JsonIgnore
    public boolean isUserLevel() {
        return Objects.nonNull(this.code) && this.code.startsWith("A");
    }

    @JsonIgnore
    public boolean isSysLevel() {
        return Objects.nonNull(this.code) && (this.code.startsWith("B") || this.code.startsWith("C"));
    }

    @JsonIgnore
    public boolean isSuccess(Result<T> result) {
        return result != null && ResultCode.SUCCESS.getCode().equals(result.getCode());
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ResultCode.SUCCESS.getCode().equals(this.getCode());
    }

    @JsonIgnore
    public boolean isFailed() {
        return !isSuccess();
    }
}
