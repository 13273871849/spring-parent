### Spring boot控制器异常统一处理之@RestControllerAdvice

#### 1.@RestControllerAdvice注解定义全局处理异常类，异常处理类于@ControllerAdvice相比不需要添加@ResponseBody就可以返回JSON格式异常，
```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ControllerAdvice
@ResponseBody
public @interface RestControllerAdvice {
	@AliasFor("basePackages")
	String[] value() default {};
	@AliasFor("value")
	String[] basePackages() default {};
	Class<?>[] basePackageClasses() default {};
	Class<?>[] assignableTypes() default {};

	Class<? extends Annotation>[] annotations() default {};

}
```
* 该注解被@ControllerAdvice和@ResponseBody声明，作用相当于@ExceptionHandler标注的异常处理方法同时也被@ResponseBody注解声明
* 可以指定异常处理要扫描的包

#### 2.@ExceptionHandler注解声明处理具体异常的方法；
```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionHandler {

	/**
	 * 异常处理方法将会处理参数指定的异常类，如果为空，将会默认处理方法参数指定的任何异常类
	 */
	Class<? extends Throwable>[] value() default {};

}
```
#### 3.控制器异常处理实现类
```
package com.yaomy.control.exception.advice;

import com.yaomy.control.exception.enums.HttpStatusMsg;
import com.yaomy.control.exception.po.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

/**
 * @Description: 控制并统一处理异常类
 * @ExceptionHandler标注的方法优先级问题，它会找到异常的最近继承关系，也就是继承关系最浅的注解方法
 * @Version: 1.0
 */
@RestControllerAdvice
public final class ExceptionAdviceHandler {

    /**
     * 未知异常
     */
    @ExceptionHandler(value = Exception.class)
    public BaseResponse unKnowExceptionHandler(Exception e) {
        e.printStackTrace();
        StackTraceElement[] elements = e.getStackTrace();
        String message = StringUtils.EMPTY;
        if(elements.length > 0){
            StackTraceElement element = elements[0];
            message = StringUtils.join("控制器", element.getClassName(), ".", element.getMethodName(), "类的第", element.getLineNumber(), "行发生", e.toString(), "异常");
        }
        if(StringUtils.isBlank(message)){
            message = e.toString();
        }
        return BaseResponse.createResponse(HttpStatusMsg.UNKNOW_EXCEPTION.getStatus(), message);
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(value = RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        e.printStackTrace();
        StackTraceElement[] elements = e.getStackTrace();
        String message = StringUtils.EMPTY;
        if(elements.length > 0){
            StackTraceElement element = elements[0];
            message = StringUtils.join("控制器", element.getClassName(), ".", element.getMethodName(), "类的第", element.getLineNumber(), "行发生", e.toString(), "异常");
        }
        if(StringUtils.isBlank(message)){
            message = e.toString();
        }
        return BaseResponse.createResponse(HttpStatusMsg.RUNTIME_EXCEPTION.getStatus(), message);
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public BaseResponse nullPointerExceptionHandler(NullPointerException e) {
        e.printStackTrace();
        StackTraceElement[] elements = e.getStackTrace();
        String message = StringUtils.EMPTY;
        if(elements.length > 0){
            StackTraceElement element = elements[0];
            message = StringUtils.join("控制器", element.getClassName(), ".", element.getMethodName(), "类的第", element.getLineNumber(), "行发生", e.toString(), "异常");
        }
        if(StringUtils.isBlank(message)){
            message = e.toString();
        }
        return BaseResponse.createResponse(HttpStatusMsg.NULL_POINTER_EXCEPTION.getStatus(), message);
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public BaseResponse classCastExceptionHandler(ClassCastException e) {
        e.printStackTrace();
        StackTraceElement[] elements = e.getStackTrace();
        String message = StringUtils.EMPTY;
        if(elements.length > 0){
            StackTraceElement element = elements[0];
            message = StringUtils.join("控制器", element.getClassName(), ".", element.getMethodName(), "类的第", element.getLineNumber(), "行发生", e.toString(), "异常");
        }
        if(StringUtils.isBlank(message)){
            message = e.toString();
        }
        return BaseResponse.createResponse(HttpStatusMsg.CLASS_CAST_EXCEPTION.getStatus(), message);
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    public BaseResponse iOExceptionHandler(IOException e) {
        e.printStackTrace();
        StackTraceElement[] elements = e.getStackTrace();
        String message = StringUtils.EMPTY;
        if(elements.length > 0){
            StackTraceElement element = elements[0];
            message = StringUtils.join("控制器", element.getClassName(), ".", element.getMethodName(), "类的第", element.getLineNumber(), "行发生", e.toString(), "异常");
        }
        if(StringUtils.isBlank(message)){
            message = e.toString();
        }
        return BaseResponse.createResponse(HttpStatusMsg.IO_EXCEPTION.getStatus(), message);
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e) {
        e.printStackTrace();
        StackTraceElement[] elements = e.getStackTrace();
        String message = StringUtils.EMPTY;
        if(elements.length > 0){
            StackTraceElement element = elements[0];
            message = StringUtils.join("控制器", element.getClassName(), ".", element.getMethodName(), "类的第", element.getLineNumber(), "行发生", e.toString(), "异常");
        }
        if(StringUtils.isBlank(message)){
            message = e.toString();
        }
        return BaseResponse.createResponse(HttpStatusMsg.INDEX_OUTOF_BOUNDS_EXCEPTION.getStatus(), message);
    }

    /**
     * 参数类型不匹配
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public BaseResponse requestTypeMismatch(MethodArgumentTypeMismatchException e){
        return BaseResponse.createResponse(HttpStatusMsg.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTIION.getStatus(), "参数类型不匹配，参数"+e.getName()+"类型必须为"+e.getRequiredType());
    }
    /**
     * 缺少参数
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public BaseResponse requestMissingServletRequest(MissingServletRequestParameterException e) {
        return BaseResponse.createResponse(HttpStatusMsg.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION.getStatus(), "缺少必要参数，参数名称为"+e.getParameterName());
    }
    /**
     * 请求method不匹配
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public BaseResponse requestMissingServletRequest(HttpRequestMethodNotSupportedException e) {
        return BaseResponse.createResponse(HttpStatusMsg.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION.getStatus(), "不支持"+e.getMethod()+"方法，支持"+ StringUtils.join(e.getSupportedMethods(), ",")+"类型");
    }

}

```

GitHub源码：