package com.tensquare.user.controller;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 变更关注数
	 * @param userId
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/incFollow/{userId}/{count}",method= RequestMethod.POST)
	public Result incFollow(@PathVariable(value = "userId") String userId, @PathVariable(value = "count")int count){
		userService.incFollowcount(userId,count);
		return new Result(true,StatusCode.OK,"关注数变更成功");
	}


	/**
	 * 变更粉丝
	 * @param userId
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/incFans/{userId}/{count}",method= RequestMethod.POST)
	public Result incFans(@PathVariable(value = "userId") String userId, @PathVariable(value = "count")int count){
		userService.incFans(userId,count);
		return new Result(true,StatusCode.OK,"粉丝数变更成功");
	}

	// POST /user/login 登陆
	@RequestMapping(value = "/login",method= RequestMethod.POST)
	public Result login(@RequestBody  Map<String,String> map){
		User user =  userService.login(map.get("mobile"),map.get("password"));
		if(user != null){
			//登录成功 签发token, 响应
			String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");

			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put("token",token);
			resultMap.put("name",user.getNickname());
			resultMap.put("avatar",user.getAvatar());

			return new Result(true,StatusCode.OK,"登录成功",resultMap);
		}else{
			return new Result(false,StatusCode.LOGINERROR,"登录失败");
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id, HttpServletRequest request){
		Claims claims = (Claims) request.getAttribute("role_admin");
		if(claims == null){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
		}

		//是管理员
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}





	@RequestMapping(value = "/register/{code}",method= RequestMethod.POST)
	public Result register(@PathVariable(value = "code") String code, @RequestBody User user){
		userService.register(code,user);
		return new Result(true,StatusCode.OK,"注册成功");
	}


	/**
	 * 发送手机验证码
	 * @param mobile  手机号码
	 * @return
	 */
	@RequestMapping(value = "/sendsms/{mobile}",method= RequestMethod.POST)
	public Result sendsms(@PathVariable(value ="mobile") String mobile){
		userService.sendsms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){

		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){

		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){

		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	

	
}
