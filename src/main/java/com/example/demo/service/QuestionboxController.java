package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
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
	@RequestMapping("/gettarget")
	public void GetTarget(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取被提问的人的内容
		String targetphone = request.getParameter("phone");
		String state = request.getParameter("state");
		List<Questionbox> boxlist = BoxDao.findByTargetphoneAndState(targetphone, state);
		String userJson = gson.toJson(boxlist);

		// 设置首部参数
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(200);
		response.addHeader("Location", "#");
		response.addDateHeader("Date", new Date().getTime());

		// 将问题箱数据库内容返回
		ServletOutputStream out = response.getOutputStream();
		out.write(userJson.getBytes());
		out.flush();
		out.close();
	}

	@ResponseBody
	@RequestMapping("/getsource")
	public void GetSource(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取提问的人问的内容
		String sourcephone = request.getParameter("phone");
		String state = request.getParameter("state");
		List<Questionbox> boxlist = BoxDao.findBySourcephoneAndState(sourcephone, state);
		String userJson = gson.toJson(boxlist);
		// 设置首部参数
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(200);
		response.addHeader("Location", "#");
		response.addDateHeader("Date", new Date().getTime());
		// 将问题箱数据库内容返回
		ServletOutputStream out = response.getOutputStream();
		out.write(userJson.getBytes());
		out.flush();
		out.close();
	}

	@ResponseBody
	@RequestMapping("/DeleteItem")
	public void DeleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 删除提问
		String idstr = request.getParameter("id");
		int id = Integer.parseInt(idstr);
		BoxDao.deleteById(id);
		String userJson = gson.toJson("delete!");

		// 设置首部参数
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(200);
		response.addHeader("Location", "#");
		response.addDateHeader("Date", new Date().getTime());
		// 将问题箱数据库内容返回
		ServletOutputStream out = response.getOutputStream();
		out.write(userJson.getBytes());
		out.flush();
		out.close();
	}
}

