package viewstar.doubleauth.demo.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    private final static Logger logger= LoggerFactory.getLogger(ExceptionHandler.class);
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseMessage handel(Exception e){
        if(e instanceof MyException){
            MyException myException =(MyException)e;
            return ResultUtil.error( myException.getCode(),myException.getMessage());
        }else {
            logger.error("[系统异常] {}",e);
            return ResultUtil.error(ResultEnum.SYSTEM_ERROR);
        }
    }
}