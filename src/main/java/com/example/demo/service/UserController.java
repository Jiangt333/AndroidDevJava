package com.example.demo.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.User;
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
		request.setCharacterEncoding("UTF-8");
    	String phone = request.getParameter("phone");
      	User newuser = UserDao.findByphone(phone);
      	String value = gson.toJson(newuser);
    	//String value = gson.toJson(newuser);
    	//设置首部参数
    	response.setContentType("application/json;charset=utf-8");
    	
		response.setStatus(200);
    	//响应输出
    	ServletOutputStream out = response.getOutputStream();
    	out.write(value.getBytes("UTF-8"));
    	out.flush();
    	out.close();
    }
    @ResponseBody
    @RequestMapping("/register/check")
    public void Register_Check(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String value = "notRegistered";
    	String phonenumber = request.getParameter("phonenumber");
    	List<User> userlist = UserDao.findAll();
    	for(int i=0;i<userlist.size();i++)
    	{
    		User user = userlist.get(i);
    		if(user.getPhone().equals(phonenumber))
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
    @RequestMapping("/register/confirm")
    public void Register_Confirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	//String account = request.getParameter("account");//是否后台自动生成
    	String name = request.getParameter("name");
    	String password = request.getParameter("password");
    	String phonenumber = request.getParameter("phonenumber");
    	User newuser = new User();
    	newuser.setName(name);
    	newuser.setPhone(phonenumber);
    	newuser.setRealpassword(password);
    	newuser.setIschanged(0);
    	UserDao.saveAndFlush(newuser);
    	//设置返回客户端的contentType
    	response.setContentType("text/html;charset=utf-8");
    	//设置状态码
    	response.setStatus(200);
    	//响应输出
    	ServletOutputStream out = response.getOutputStream();
    	//ServletInputStream inputStream =request.getInputStream();
    	String value = "saved";
     	out.write(value.getBytes());
    	out.flush();
    	out.close();
    }
    @RequestMapping("/changeName")
    public void Name_Changing(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	//String account = request.getParameter("account");//是否后台自动生成
    	String name = request.getParameter("name");
    	String phonenumber = request.getParameter("phonenumber");
    	User user = UserDao.findByphone(phonenumber);
    	user.setName(name);
    	UserDao.saveAndFlush(user);
    }
    @RequestMapping("/changePassword")
    public void Password_Changing(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	//String account = request.getParameter("account");//是否后台自动生成
    	String password = request.getParameter("password");
    	String phonenumber = request.getParameter("phonenumber");
    	User user = UserDao.findByphone(phonenumber);
    	user.setRealpassword(password);
    	UserDao.saveAndFlush(user);
    }
    @PostMapping("/upload")
    public void uploadAudio(HttpServletRequest request,HttpServletResponse response, @RequestParam("file") MultipartFile file) throws IOException{
        String uploadPath = "D:\\AndroidMPV\\images\\";
        String phone = request.getHeader("phone");
        String filePath = uploadPath + phone+ ".png";
        File dest = new File(filePath);
        file.transferTo(dest);
        User user = UserDao.findByphone(phone);
        user.setIschanged(1);
        UserDao.saveAndFlush(user);
    }
    @ResponseBody
    @PostMapping("/getheader")
    public void uploadAudio(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String uploadPath = "D:\\AndroidMPV\\images\\";
        String phone = request.getParameter("phone");
        String filePath = uploadPath + phone+ ".png";
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(filePath)));   	
    	ByteArrayOutputStream output = new ByteArrayOutputStream();
    	ImageIO.write(bufferedImage, "png", output);
    	//设置返回客户端的contentType
    	response.setContentType("text/html;charset=utf-8");
    	//设置状态码
    	response.setStatus(200);
    	//响应输出
    	ServletOutputStream out = response.getOutputStream();
    	out.write(output.toByteArray());
    	out.flush();
    	out.close();
    }

}