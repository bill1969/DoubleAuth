package viewstar.doubleauth.demo.jpa.web;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import viewstar.doubleauth.demo.jpa.api.ActionLog;
import viewstar.doubleauth.demo.jpa.core.UserService;
import viewstar.doubleauth.demo.entity.ResponseMessage;
import viewstar.doubleauth.demo.entity.ResultEnum;
import viewstar.doubleauth.demo.entity.ResultUtil;
import viewstar.doubleauth.demo.jpa.api.User;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RequestMapping("/users")
@RestController
public class UserController {

    /*@Autowired
    private userService userService;*/
    @Autowired
    private UserService userService;

    private String GolableSwitch = "open";   //进行认证，close--不认证，返回成功

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success!"),
            @ApiResponse(code = 488, message = "user is null"),
            @ApiResponse(code = 489, message = "userid is null"),
            @ApiResponse(code = 482, message = "user is exist!")})
    @RequestMapping(value = "createUser", method = RequestMethod.POST)
    public ResponseMessage createUser(HttpServletResponse response, @RequestBody User user) throws Exception {
        System.out.println("createUser！" + user.getUserID());
        if (user == null) {
            response.setStatus(ResultEnum.USER_IS_NULL.getCode());
            return ResultUtil.error(ResultEnum.USER_IS_NULL);
        }
        if (user.getUserID() == null) {
            response.setStatus(ResultEnum.USERID_IS_NULL.getCode());
            return ResultUtil.error(ResultEnum.USERID_IS_NULL);
        }
        if (userService.getUserByUserId(user.getUserID()) != null) {
            response.setStatus(ResultEnum.USER_IS_EXIST.getCode());
            return ResultUtil.error(ResultEnum.USER_IS_EXIST);
        } else {
            ActionLog log=new ActionLog();
            log.setActionID(0);  //0-create 1-update user info 2-update user state 3-add whitelist 4-delete whitelist 5-auth
            log.setUserID(user.getUserID());
            log.setOptime(new java.sql.Date(new Date().getTime()));
            log.setOperator(user.getSPID());
            user.setActiveTime(new java.sql.Date(new Date().getTime()));
            user.setUpdateTime(new java.sql.Date(new Date().getTime()));
            userService.logsave(log);
            userService.save(user);
            return ResultUtil.success(user);
        }
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success!"),
            @ApiResponse(code = 488, message = "user is null"),
            @ApiResponse(code = 489, message = "userid is null"),
            @ApiResponse(code = 483, message = "user is not exist!")})
    @RequestMapping(value = "modifyUser", method = RequestMethod.PUT)
    public ResponseMessage modifyUser(HttpServletResponse response, @RequestBody User user) throws Exception {
        System.out.println("modifyUser！" + user.getUserID());
        if (user == null) {
            response.setStatus(ResultEnum.USER_IS_NULL.getCode());
            return ResultUtil.error(ResultEnum.USER_IS_NULL);
        }
        if (user.getUserID() == null) {
            response.setStatus(ResultEnum.USERID_IS_NULL.getCode());
            return ResultUtil.error(ResultEnum.USERID_IS_NULL);
        }
        if (userService.getUserByUserId(user.getUserID()) == null) {
            response.setStatus(ResultEnum.USER_NOT_EXIST.getCode());
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        } else {
            ActionLog log=new ActionLog();
            log.setActionID(1);  //0-create 1-update user info 2-update user state 3-add whitelist 4-delete whitelist 5-auth
            log.setUserID(user.getUserID());
            log.setOptime(new java.sql.Date(new Date().getTime()));
            log.setOperator(user.getSPID());
            userService.logsave(log);

            User persons= (User) userService.getUserByUserId(user.getUserID());
            if (persons==null)
                userService.save(user);
            else {
                if (persons.getAccountType() >= 0) user.setAccountType(persons.getAccountType());
                if (persons.getAddress() != null) user.setAddress(persons.getAddress());
                if (persons.getCity() != null) user.setCity(persons.getCity());
                if (persons.getEpgGroup() != null) user.setEpgGroup(persons.getEpgGroup());
                if (persons.getGender() != null) user.setGender(persons.getGender());
                if (persons.getIDnumber() != null) user.setIDnumber(persons.getIDnumber());
                if (persons.getProvince() != null) user.setProvince(persons.getProvince());
                if (persons.getRegion() != null) user.setRegion(persons.getRegion());
                if (persons.getState() >= 20) user.setState(persons.getState() + 20);
                else if ((persons.getState() < 10)) user.setState(persons.getState());
                else user.setState(persons.getState() + 10);
                if (persons.getTelePhone() != null) user.setTelePhone(persons.getTelePhone());
                if (persons.getUsername() != null) user.setUsername(persons.getUsername());
                if (persons.getStbID() != null) user.setStbID(persons.getStbID());
                if (persons.getPassword() != null) user.setPassword(persons.getPassword());
                if (persons.getTeamID() > 0) user.setTeamID(persons.getTeamID());
                if (persons.getUpdateTime() != null) user.setUpdateTime(persons.getUpdateTime());
                else {
                    user.setUpdateTime(new Date());
                }

                if (persons.getCarrier() >= 0) user.setCarrier(persons.getCarrier());
                if (persons.getTradeFlag() >= 0) user.setTradeFlag(persons.getTradeFlag());
                if (persons.getMAC() != null) user.setMAC(persons.getMAC());
                if (persons.getFee() >= 0) user.setFee(persons.getFee());
                if (persons.getSPID() != null) user.setSPID(persons.getSPID());
                if (persons.getProductList() != null) user.setProductList(persons.getProductList());
                if (persons.getUserType() >= 0) user.setUserType(persons.getUserType());

                userService.save(user);

            }
            return ResultUtil.success(user);
        }
    }

    @ApiOperation(value = "修改用户状态", notes = "修改用户状态")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success!"),
            @ApiResponse(code = 489, message = "userid is null"),
            @ApiResponse(code = 483, message = "user is not exist!")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "主键id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "state", value = "状态", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "spid", value = "平台标识", required = false, paramType = "query", dataType = "String")
    })
    @PutMapping(value = "/modifyUserState")
    public ResponseMessage modifyUser(HttpServletResponse response,
                                      @RequestParam(value = "userid", required = true) String userid,
                                      @RequestParam(value = "state", required = true) int state,
                                      @RequestParam(value = "spid") String spid) throws Exception {
        User user = userService.getUserByUserId(userid);
        if (userid == null) {
            response.setStatus(ResultEnum.USERID_IS_NULL.getCode());
            return ResultUtil.error(ResultEnum.USERID_IS_NULL);
        }
        if (user == null) {
            response.setStatus(ResultEnum.USER_NOT_EXIST.getCode());
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        } else {

            if (spid != null) user.setSPID(spid);
            ActionLog log=new ActionLog();
            log.setActionID(2);  //0-create 1-update user info 2-update user state 3-add whitelist 4-delete whitelist 5-auth
            log.setUserID(user.getUserID());
            log.setOptime(new java.sql.Date(new Date().getTime()));
            log.setOperator(user.getSPID());
            userService.logsave(log);
            user.setState(state);
            user.setUpdateTime(new java.sql.Date(new Date().getTime()));
            userService.save(user);
            return ResultUtil.error(ResultEnum.SUCCESS);
        }
    }

    @ApiOperation(value = "增加白名单", notes = "增加白名单")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success!"),
            @ApiResponse(code = 483, message = "user is not exist!"),
            @ApiResponse(code = 489, message = "userid is null"),
            @ApiResponse(code = 485, message = "user is  in blacklist!!"),
            @ApiResponse(code = 484, message = "user is  in whitelist!")})
    @RequestMapping(value = "/Whitelist/{userid}", method = RequestMethod.POST)
    public ResponseMessage addWhiteList(HttpServletResponse response, @PathVariable(value = "userid") String userid) throws Exception {
        System.out.println("add User!");
        if (userid == null) {
            response.setStatus(ResultEnum.USERID_IS_NULL.getCode());
            return ResultUtil.error(ResultEnum.USERID_IS_NULL);
        }
        User user = userService.getUserByUserId(userid);
        if (user == null) {
            response.setStatus(ResultEnum.USER_NOT_EXIST.getCode());
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        }
        if (user.getState() >= 20) {
            response.setStatus(ResultEnum.USER_IN_BLACKLIST.getCode());
            return ResultUtil.error(ResultEnum.USER_IN_BLACKLIST);
        } else if (user.getState() >= 10) {
            response.setStatus(ResultEnum.USER_IN_WHITELIST.getCode());
            return ResultUtil.error(ResultEnum.USER_IN_WHITELIST);
        } else {
            ActionLog log=new ActionLog();
            log.setActionID(3);  //0-create 1-update user info 2-update user state 3-add whitelist 4-delete whitelist 5-auth
            log.setUserID(user.getUserID());
            log.setOptime(new java.sql.Date(new Date().getTime()));
            log.setOperator(user.getSPID());
            user.setState(user.getState() + 10);
            userService.logsave(log);
            userService.save(user);
            return ResultUtil.error(ResultEnum.SUCCESS);
        }
    }

    @ApiOperation(value = "删除白名单", notes = "删除白名单")
    @ApiResponses(value = {@ApiResponse(code = 483, message = "user is not exist!"),
            @ApiResponse(code = 200, message = "success!"),
            @ApiResponse(code = 489, message = "userid is null"),
            @ApiResponse(code = 486, message = "user is not in whitelist!")})
    @RequestMapping(value = "/Whitelist/{userid}", method = RequestMethod.DELETE)
    //@RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public ResponseMessage deleteWhiteList(HttpServletResponse response, @PathVariable(value = "userid") String userid) throws Exception {
        System.out.println("delete whiteList!");
        if (userid == null) {
            response.setStatus(ResultEnum.USERID_IS_NULL.getCode());
            return ResultUtil.error(ResultEnum.USERID_IS_NULL);
        }
        User user = userService.getUserByUserId(userid);
        System.out.println(user);
        if (user == null) {
            response.setStatus(ResultEnum.USER_NOT_EXIST.getCode());
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        }
        if (user.getState() >= 20) {
            response.setStatus(ResultEnum.USER_IN_BLACKLIST.getCode());
            return ResultUtil.error(ResultEnum.USER_IN_BLACKLIST);
        } else if (user.getState() >= 10) {
            ActionLog log=new ActionLog();
            log.setActionID(4);  //0-create 1-update user info 2-update user state 3-add whitelist 4-delete whitelist 5-auth
            log.setUserID(user.getUserID());
            log.setOptime(new java.sql.Date(new Date().getTime()));
            log.setOperator(user.getSPID());
            user.setState(user.getState() - 10);
            userService.logsave(log);
            userService.save(user);
            return ResultUtil.error(ResultEnum.SUCCESS);
        } else {
            response.setStatus(ResultEnum.USER_NOT_IN_WHITELIST.getCode());
            return ResultUtil.error(ResultEnum.USER_NOT_IN_WHITELIST);
        }
    }

    @ApiOperation(value = "认证", notes = "认证")
    @ApiResponses(value = {@ApiResponse(code = 483, message = "user is not exist!"),
            @ApiResponse(code = 200, message = "success!"),
            @ApiResponse(code = 481, message = "unkown error!"),
            @ApiResponse(code = 485, message = "user in blacklist!")})
    @RequestMapping(value = "/Auth/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage auth(HttpServletResponse response, @PathVariable(value = "userid") String userid) throws Exception {
        System.out.println("auth!");
        User user = userService.getUserByUserId(userid);
        JSONObject msg=new JSONObject();
        if (user == null) {
            response.setStatus(ResultEnum.USER_NOT_EXIST.getCode());
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        } else if ("close".equals(GolableSwitch)){
            ActionLog log=new ActionLog();
            log.setActionID(5);  //0-create 1-update user info 2-update user state 3-add whitelist 4-delete whitelist 5-auth
            log.setUserID(user.getUserID());
            log.setOptime(new java.sql.Date(new Date().getTime()));
            log.setOperator(user.getSPID());
            userService.logsave(log);

            msg.put("GolableSwitch",GolableSwitch);
            msg.put("userid",user.getUserID());
            return ResultUtil.success(msg);
        }
        else if ((user.getState() >= 0) &&(user.getState() < 20)) {
            msg.put("state",user.getState());
            msg.put("userid",user.getUserID());
            return ResultUtil.success(msg);
        } else if (user.getState() > 20) {
            response.setStatus(ResultEnum.USER_IN_BLACKLIST.getCode());
            return ResultUtil.error(ResultEnum.USER_IN_BLACKLIST);
        } else {
            response.setStatus(ResultEnum.UNKNOWN_ERROR.getCode());
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR);
        }
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    @ApiResponses(value = {@ApiResponse(code = 483, message = "user is not exist!"),
            @ApiResponse(code = 200, message = "success!")})
    @RequestMapping(value = "getUser/{userid}", method = RequestMethod.GET)
    public ResponseMessage getUser(HttpServletResponse response, @PathVariable(value = "userid") String userid) {
        response.setHeader("Content-Type", "application/json");
        System.out.println("getUser!");
        //content-type为application/x-json;charset=UTF-8
        if (userid == null) {
            response.setStatus(ResultEnum.USERID_IS_NULL.getCode());
            return ResultUtil.error(ResultEnum.USERID_IS_NULL);
        }
        User user = userService.getUserByUserId(userid);
        if (user == null) {
            response.setStatus(ResultEnum.USER_NOT_EXIST.getCode());
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        } else {
            response.setStatus(ResultEnum.SUCCESS.getCode());
            return ResultUtil.success(user);
        }
    }

    @ApiOperation(value = "全局认证开关", notes = "全局认证开关")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success!")})
    @RequestMapping(value = "GlobalAuth/{authSwitch}", method = RequestMethod.PUT)
    public ResponseMessage getUser(HttpServletResponse response, @PathVariable(value = "authSwitch") boolean authSwitch) throws Exception {
        System.out.println("GlobalSwitch!");
        JSONObject msg=new JSONObject();
        if (authSwitch)
            GolableSwitch = "open";
        else
            GolableSwitch = "close";
        msg.put("GolableSwitch", GolableSwitch);
        ActionLog log=new ActionLog();
        log.setActionID(6);  //0-create 1-update user info 2-update user state 3-add whitelist 4-delete whitelist 5-auth 6-globalauth
        log.setOptime(new java.sql.Date(new Date().getTime()));
        log.setOperator("system");
        userService.logsave(log);
        return ResultUtil.success(msg);
    }
}