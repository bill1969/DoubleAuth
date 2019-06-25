package viewstar.doubleauth.demo.entity;

import lombok.Data;

@Data
public class ResponseMessage<T> {
    //错误码
    private int ErrorCode;
    //信息描述
    private String ErrorMsg;
    //具体的信息内容
    private T Data;
}
