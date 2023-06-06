package com.example.demo;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@Transactional
@Component
public class UserController {

	@Autowired
    private final UserRepository UserDao;
    public UserController(UserRepository UserDao) {
        this.UserDao = UserDao;
    }

    public void deleteById(Long id) {
    	UserDao.deleteById(id);
    }
    @ResponseBody
    @RequestMapping("/login")
    public void Login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	//设置返回客户端的contentType
    	response.setContentType("text/html;charset=utf-8");
    	//设置状态码
    	response.setStatus(200);
    	response.addHeader("Location", "#");
    	//添加类型为long的header
    	response.addDateHeader("Date", new Date().getTime());
    	ServletOutputStream out = response.getOutputStream();
    	String name = request.getParameter("user");
      	User newuser = UserDao.findBylogin(name);
    	String value = newuser.getRealpassword();
    	out.write(value.getBytes());
    	out.flush();
    	out.close();
    }
}