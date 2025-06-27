package org.csu.healthsystem.exception;


import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.common.ResponseCode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;



import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 通类异常处理
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResponse.createForError(500,"出错了，请联系后端管理员");
    }
    /**
     *重复元素异常处理
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CommonResponse<Object> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        String message = e.getMessage();
        int i=message.indexOf("Duplicate entry");
        String errMsg=message.substring(i);
        String[] arr=errMsg.split(" ");
        return CommonResponse.createForError(arr[2]+"已存在");
    }
    /**
     * 处理请求参数缺失异常（400）
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少参数: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "缺少参数: " + e.getParameterName());
    }
    /**
     * 处理请求参数格式错误异常（400）
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数类型错误: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "参数类型错误: " + e.getName());
    }

    /**
     * 处理未授权异常（401）
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public CommonResponse<Object> handleAuthenticationException(AuthenticationException e) {
        log.error("身份验证失败: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.UNAUTHORIZED.getCode(), "身份验证失败，请重新登录");
    }

    /**
     * 处理权限不足异常（403）
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public CommonResponse<Object> handleAccessDeniedException(AccessDeniedException e) {
        log.error("权限不足: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.FORBIDDEN.getCode(), "权限不足，无法访问");
    }

    /**
     * 处理找不到资源（404）
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CommonResponse<Object> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("请求的资源不存在: {}", e.getRequestURL());
        return CommonResponse.createForError(ResponseCode.NOT_FOUND.getCode(), "嗯？奶龙没有这种东西的哦");
    }

    /**
     * 405 错误的方式访问资源
     * */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public CommonResponse<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return CommonResponse.createForError(405, "请求方式错误，请使用正确的 HTTP 方法");
    }

    /**
     * 处理客户端请求超时异常（408）
     */
    @ExceptionHandler(SocketTimeoutException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ResponseBody
    public CommonResponse<Object> handleSocketTimeoutException(SocketTimeoutException e) {
        log.error("请求超时: {}", e.getMessage());
        return CommonResponse.createForError(408, "世界上最遥远的距离，莫过于你与奶龙之间少了互联网的桥梁");
    }

    /**
     * 处理网络问题导致的服务器无法连接（503）
     */
    @ExceptionHandler(UnknownHostException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public CommonResponse<Object> handleUnknownHostException(UnknownHostException e) {
        log.error("无法连接服务器: {}", e.getMessage());
        return CommonResponse.createForError(503, "世界上最遥远的距离，莫过于你与奶龙之间少了互联网的桥梁");
    }

    /**
     * 处理数据库约束异常（500）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CommonResponse<Object> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("数据库约束异常: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "操作失败，请检查数据约束");
    }

    /**
     * 处理 HTTP 客户端错误（400-499）
     */
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse<Object> handleHttpClientErrorException(HttpClientErrorException e) {
        log.error("HTTP 客户端错误: {}", e.getMessage());
        return CommonResponse.createForError(e.getStatusCode().value(), "客户端请求错误");
    }

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResponse<Object> handleLoginException(LoginException e) {
        log.error("登录异常: {} - {}", e.getErrorMessage(), e.getErrorMessage());
        return CommonResponse.createForError(400, e.getErrorMessage());
    }


}
