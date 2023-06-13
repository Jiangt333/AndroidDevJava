package com.example.demo.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.google.gson.Gson;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@Transactional
@Component
public class UserController {

	@Autowired
    private final UserRepository UserDao;
	private Gson gson = new Gson(); //创建GSON对象
    public UserController(UserRepository UserDao) {
        this.UserDao = UserDao;
    }

    @ResponseBody
    @RequestMapping("/login")
    public void Login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String phone = request.getParameter("phone");
      	User newuser = UserDao.findByphone(phone);
      	String value = gson.toJson(newuser);
    	//String value = gson.toJson(newuser);
    	//设置首部参数
    	response.setContentType("application/json;charset=utf-8");
    	response.setStatus(200);
    	response.addHeader("Location", "#");
    	response.addDateHeader("Date", new Date().getTime());
    	//响应输出
    	ServletOutputStream out = response.getOutputStream();
    	out.write(value.getBytes());
    	out.flush();
    	out.close();
    }
    @ResponseBody
    @RequestMapping("/register/check")
    public void Register_Check(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String value = "not registered";
    	String phonenumber = request.getParameter("phonenumber");
    	List<User> userlist = UserDao.findAll();
    	for(int i=0;i<userlist.size();i++)
    	{
    		User user = userlist.get(i);
    		if(user.getPhone()==phonenumber)
    			value = "registered";
    			
    	}
    	//设置首部参数
    	response.setContentType("text/html;charset=utf-8");
    	response.setStatus(200);
    	response.addHeader("Location", "#");
    	response.addDateHeader("Date", new Date().getTime());
    	//响应输出
    	ServletOutputStream out = response.getOutputStream();
    	out.write(value.getBytes());
    	out.flush();
    	out.close();
    }
    @ResponseBody
    @RequestMapping("/register/comfirm")
    public void Register_Confirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	Gson gson = new Gson();
    	//String account = request.getParameter("account");//是否后台自动生成
    	String name = request.getParameter("name");
    	String password = request.getParameter("password");
    	String phonenumber = request.getParameter("phonenumber");
    	User newuser = new User();
    	newuser.setName(name);
    	newuser.setPhone(phonenumber);
    	newuser.setRealpassword(password);
    	UserDao.saveAndFlush(newuser);
    	//设置返回客户端的contentType
    	response.setContentType("text/html;charset=utf-8");
    	//设置状态码
    	response.setStatus(200);
    	response.addHeader("Location", "#");
    	response.addDateHeader("Date", new Date().getTime());
    	//响应输出
    	ServletOutputStream out = response.getOutputStream();
    	String value = "saved";
     	out.write(value.getBytes());
    	out.flush();
    	out.close();
    }
	
}