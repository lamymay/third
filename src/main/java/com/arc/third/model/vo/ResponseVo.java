package com.arc.third.model.vo;


import java.io.Serializable;

/**
 * 该类对controller返回值做了统一封装
 * 返回数据的数据具有一致的格式
 *
 * @param <T>
 * @author May
 */
@Deprecated
public class ResponseVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态信息 code 用String或者数字类型（long/int？）。私以为：类型是数字类型 可能高效一些，
     */
    private int code;

    /**
     * 状态信息
     */
    private String msg;


    /**
     * 有效数据
     */
    private T data;

    //构造器
    public ResponseVo() {
    }

    public ResponseVo(T data) {
        this.data = data;
    }

    public ResponseVo(ProjectCodeEnum projectCode) {
        this.code = projectCode.getCode();
        this.msg = projectCode.getMsg();
        this.data = null;
    }

    public ResponseVo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //success方法
    public static <T> ResponseVo<T> success() {
        return new ResponseVo<T>(ProjectCodeEnum.SUCCESS.getCode(), ProjectCodeEnum.SUCCESS.getMsg(), null);
    }

    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo<T>(ProjectCodeEnum.SUCCESS.getCode(), ProjectCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ResponseVo<T> success(ProjectCodeEnum enumCode) {
        return new ResponseVo<T>(enumCode.getCode(), enumCode.getMsg(), null);
    }

    public static <T> ResponseVo<T> success(ProjectCodeEnum enumCode, T data) {
        return new ResponseVo<T>(enumCode.getCode(), enumCode.getMsg(), data);
    }

    //失败
    public static <T> ResponseVo<T> failure(ProjectCodeEnum enumCode) {
        return new ResponseVo<T>(enumCode.getCode(), enumCode.getMsg(), null);
    }

    public static <T> ResponseVo<T> failure(T data) {
        return new ResponseVo<T>(ProjectCodeEnum.FAILURE.getCode(), ProjectCodeEnum.FAILURE.getMsg(), data);
    }

    public static <T> ResponseVo<T> failure(ProjectCodeEnum enumCode, T data) {
        return new ResponseVo<T>(enumCode.getCode(), enumCode.getMsg(), data);
    }

    public static ResponseVo failure() {
        return new ResponseVo(ProjectCodeEnum.FAILURE);
    }

    public static ResponseVo failure(int code, String msg) {
        return new ResponseVo(code, msg, ProjectCodeEnum.FAILURE);
    }

    @Override
    public String toString() {
        return "ResponseVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

//
//    /**
//     * 处理微服务的返回结果
//     *
//     * @param response
//     * @param <T>
//     * @return
//     */
//    public static <T> ResponseVo<T> buildResponse(MicroServiceResponse<T> response) {
//        if (response == null) {
//            throw new BusinessRuntimeException(ProjectCodeEnum.NULL_RESPONSE);
//        }
//        return StringUtils.equals(response.getCode(), ProjectCodeEnum.SUCCESS.getCode()) ? ResponseVo.success(response.getData()) : ResponseVo.failure(ProjectCodeEnum.buildFailure(response.getCode(), response.getMsg()));
//    }
//
//    /**
//     * 处理微服务的返回结果（类型转换）
//     *
//     * @param response
//     * @param <T>
//     * @return
//     */
//    public static <T> ResponseVo<T> convertResponse(MicroServiceResponse<?> response, Class<T> target) {
//        if (response == null || target == null) {
//            throw new BusinessRuntimeException(ProjectCodeEnum.NULL_RESPONSE);
//        }
//        T instance = BeanCopierUtil.copyBean(response.getData(), target);
//        return StringUtils.equals(response.getCode(), ProjectCodeEnum.SUCCESS.getCode()) ? ResponseVo.success(instance) : ResponseVo.failure(ProjectCodeEnum.buildFailure(response.getCode(), response.getMsg()));
//    }
//
//    /**
//     * 处理微服务的返回结果（分页类型转换）
//     *
//     * @param response
//     * @param <T>
//     * @return
//     */
//    public static <T> ResponseVo<PageResponse<T>> convertPageResponse(MicroServiceResponse<? extends PageResponse> response, Class<T> target) {
//        if (response == null || target == null) {
//            throw new BusinessRuntimeException(ProjectCodeEnum.NULL_RESPONSE);
//        }
//        if (response.getData() == null) {
//            throw new BusinessRuntimeException(ProjectCodeEnum.PAGE_NULL_RESPONSE);
//        }
//        List<T> list = BeanCopierUtil.copyList(response.getData().getData(), target);
//        PageResponse<T> instance = new PageResponse<>();
//        instance.setData(list);
//        instance.setTotalPages(response.getData().getTotalPages());
//        instance.setTotalElements(response.getData().getTotalElements());
//        return StringUtils.equals(response.getCode(), ProjectCodeEnum.SUCCESS.getCode()) ? ResponseVo.success(instance) : ResponseVo.failure(ProjectCodeEnum.buildFailure(response.getCode(), response.getMsg()));
//    }
//
//    /**
//     * 处理微服务的返回结果（列表类型转换）
//     *
//     * @param response
//     * @param <T>
//     * @return
//     */
//    public static <T> ResponseVo<List<T>> convertListResponse(MicroServiceResponse<? extends List> response, Class<T> target) {
//        if (response == null || target == null) {
//            throw new BusinessRuntimeException(ProjectCodeEnum.NULL_RESPONSE);
//        }
//        List<T> instance = BeanCopierUtil.copyList(response.getData(), target);
//        return StringUtils.equals(response.getCode(), ProjectCodeEnum.SUCCESS.getCode()) ? ResponseVo.success(instance) : ResponseVo.failure(ProjectCodeEnum.buildFailure(response.getCode(), response.getMsg()));
//    }
//
//    /**
//     * 处理微服务的返回结果（含参数名映射列表类型转换）
//     *
//     * @param response
//     * @param <T>
//     * @return
//     */
//    public static <T> ResponseVo<List<T>> convertListResponse(MicroServiceResponse<? extends List> response, Class<T> target, Map<String, String> relation) {
//        if (response == null || target == null) {
//            throw new BusinessRuntimeException(ProjectCodeEnum.NULL_RESPONSE);
//        }
//        List<T> instance = BeanCopierUtil.copyList(response.getData(), target, relation);
//        return StringUtils.equals(response.getCode(), ProjectCodeEnum.SUCCESS.getCode()) ? ResponseVo.success(instance) : ResponseVo.failure(ProjectCodeEnum.buildFailure(response.getCode(), response.getMsg()));
//    }

}
