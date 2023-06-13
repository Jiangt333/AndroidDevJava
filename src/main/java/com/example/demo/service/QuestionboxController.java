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

import com.example.demo.entity.Questionbox;
import com.example.demo.repository.QuestionboxRepository;
import com.google.gson.Gson;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Controller
@Transactional
@Component
public class QuestionboxController {
	@Autowired
    private final  QuestionboxRepository BoxDao;
	private Gson gson = new Gson(); //创建GSON对象
    public  QuestionboxController(QuestionboxRepository BoxDao) {
        this.BoxDao = BoxDao;
    }
    @ResponseBody
    @RequestMapping("/getsource")
    public void Login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	//获取提问内容
    	String target = request.getParameter("target");
    	List<Questionbox> questionlist = BoxDao.findBytarget(target);
    	String userJson = gson.toJson(questionlist);
    	//设置首部参数
    	response.setContentType("application/json;charset=utf-8");
    	response.setStatus(200);
    	response.addHeader("Location", "#");
    	response.addDateHeader("Date", new Date().getTime());	
    	//将问题列表返回
    	ServletOutputStream out = response.getOutputStream();
    	out.write(userJson.getBytes());
    	out.flush();
    	out.close();
    }
    @ResponseBody
    @RequestMapping("/gettarget")
    public void Register_Check(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	//获取我的提问内容
    	String source = request.getParameter("source");
    	List<Questionbox> questionlist = BoxDao.findBysource(source);
    	String userJson = gson.toJson(questionlist);
    	//设置首部参数
    	response.setContentType("application/json;charset=utf-8");
    	response.setStatus(200);
    	response.addHeader("Location", "#");
    	response.addDateHeader("Date", new Date().getTime());	
    	//将问题列表返回
    	ServletOutputStream out = response.getOutputStream();
    	out.write(userJson.getBytes());
    	out.flush();
    	out.close();
    }


}

