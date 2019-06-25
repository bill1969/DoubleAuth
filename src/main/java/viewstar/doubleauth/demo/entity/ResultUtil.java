package viewstar.doubleauth.demo.entity;

import lombok.Data;

public class ResultUtil {

    public static ResponseMessage success(Object object) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setErrorCode(ResultEnum.SUCCESS.getCode());
        responseMessage.setErrorMsg(ResultEnum.SUCCESS.getMsg());
        responseMessage.setData(object);
        return responseMessage;
    }

    /**
     * 操作成功不返回消息
     * @return
     */
    public static ResponseMessage success() {
        return success(null);
    }

    /**
     * 操作失败返回的消息
     * @param code
     * @param msg
     * @return
     */
    public static ResponseMessage error(int code,String msg) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setErrorCode(code);
        responseMessage.setErrorMsg(msg);
        return responseMessage;
    }

    /**
     * 操作失败返回消息，对error的重载
     * @param resultEnum
     * @return
     */
    public static ResponseMessage error(ResultEnum resultEnum){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setErrorCode(resultEnum.getCode());
        responseMessage.setErrorMsg(resultEnum.getMsg());
        return responseMessage;
    }
}