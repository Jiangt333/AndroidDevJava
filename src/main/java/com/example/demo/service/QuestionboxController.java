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

import com.example.demo.Questionbox;
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

		// 返回删除成功信息
		ServletOutputStream out = response.getOutputStream();
		out.write(userJson.getBytes());
		out.flush();
		out.close();
	}

	@ResponseBody
	@RequestMapping("/Answer")
	public void Answer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 保存回答
		String idstr = request.getParameter("id");
		String answer = request.getParameter("answer");
		String answertime = request.getParameter("answertime");
		int id = Integer.parseInt(idstr);
		String state = "1";
		Questionbox qbox = BoxDao.findById(id);
		qbox.setAnswer(answer);
		qbox.setState(state);
		qbox.setAnswerTime(answertime);
		BoxDao.saveAndFlush(qbox);
		String qboxJson = gson.toJson(qbox);

		// 设置首部参数
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(200);
		response.addHeader("Location", "#");
		response.addDateHeader("Date", new Date().getTime());

		// 返回更新后的对象
		ServletOutputStream out = response.getOutputStream();
		out.write(qboxJson.getBytes());
		out.flush();
		out.close();
	}

	@ResponseBody
	@RequestMapping("/Question")
	public void Question(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 保存提问
		String idstr = request.getParameter("id");
		String question = request.getParameter("question");
		String questiontime = request.getParameter("questiontime");
		int id = Integer.parseInt(idstr);
		Questionbox qbox = BoxDao.findById(id);
		qbox.setQuestion(question);
		String state = "0";
		qbox.setState(state);
		qbox.setQuestionTime(questiontime);
		BoxDao.saveAndFlush(qbox);
		String qboxJson = gson.toJson(qbox);
		// 设置首部参数
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(200);
		response.addHeader("Location", "#");
		response.addDateHeader("Date", new Date().getTime());

		// 返回更新后的对象
		ServletOutputStream out = response.getOutputStream();
		out.write(qboxJson.getBytes());
		out.flush();
		out.close();
	}
	
	@ResponseBody
	@RequestMapping("/AskQuestion")
	public void AskQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String source = request.getParameter("source");
		String target = request.getParameter("target");
		String question = request.getParameter("question");
		String questiontime = request.getParameter("questiontime");
		String targetName = request.getParameter("targetName");
		String state = "0";
		Questionbox newquestion = new Questionbox();
    	newquestion.setSourcePhone(source);
		newquestion.setTargetPhone(target);
		newquestion.setQuestion(question);
    	newquestion.setQuestionTime(questiontime);
		newquestion.setState(state);
		newquestion.setTargetName(targetName);
		BoxDao.saveAndFlush(newquestion);

		// 设置首部参数
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(200);
		response.addHeader("Location", "#");
		response.addDateHeader("Date", new Date().getTime());

		// 返回更新后的对象
		ServletOutputStream out = response.getOutputStream();
		out.flush();
		out.close();
	}

}

