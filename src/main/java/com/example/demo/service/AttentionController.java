package com.example.demo.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Attention;
import com.example.demo.User;
import com.example.demo.repository.AttentionRepository;
import com.example.demo.repository.UserRepository;
import com.google.gson.Gson;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@Transactional
@Component
public class AttentionController {
    @Autowired
    private AttentionRepository attentionDao;
    @Autowired
    private UserRepository userDao;
    public AttentionController(AttentionRepository attentionDao,UserRepository userDao) {
		this.attentionDao = attentionDao;
        this.userDao = userDao;
	}
    public class ListofTarget{
        String TargetName;
        String Target;
        byte[] imageBytes;
        public void additem(String TargetName,String Target,byte[] imageBytes){
            this.TargetName = TargetName;
            this.Target = Target;
            this.imageBytes = imageBytes;
        } 
    }
    @ResponseBody
    @RequestMapping("/square/myattention")
    public void squareMyAttention(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String source = request.getParameter("myattention");

        // 根据source值从数据库中获取对应的target列表
        List<Attention> attentions = attentionDao.findBysource(source);

        // 提取target值列表
        List<ListofTarget> targetList = new ArrayList<>();
        for (Attention attention : attentions) {
            ListofTarget t = new ListofTarget();
            String uploadPath = "D:\\myproject\\AndroidDevJava\\images\\";
            String defaultImagePath = "D:\\myproject\\AndroidDevJava\\images\\1.png";
            String filePath = uploadPath + attention.getTarget() + ".png";

            File imageFile = new File(filePath);
            if (!imageFile.exists()) {
                filePath = defaultImagePath;
            }

            System.out.println(filePath);
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(filePath)));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", output);

            t.additem(attention.getTargetName(),attention.getTarget(),output.toByteArray());
            targetList.add(t);
        }

        // 转换为JSON字符串
        Gson gson = new Gson();
        String attenJson = gson.toJson(targetList);

        // 设置首部参数
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        response.addHeader("Location", "#");
        response.addDateHeader("Date", new Date().getTime());

        // 响应输出
        ServletOutputStream out = response.getOutputStream();
        out.write(attenJson.getBytes());
        out.flush();
        out.close();
    }

    @ResponseBody
    @RequestMapping("/square/myfans")
    public void squareMyFans(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String target = request.getParameter("myfans");

        // 根据source值从数据库中获取对应的target列表
        List<Attention> Fans = attentionDao.findBytarget(target);

        // 提取target值列表
        List<ListofTarget> sourceList = new ArrayList<>();
        for (Attention fan : Fans) {
            ListofTarget t = new ListofTarget();
            String uploadPath = "D:\\myproject\\AndroidDevJava\\images\\";
            String defaultImagePath = "D:\\myproject\\AndroidDevJava\\images\\1.png";
            String filePath = uploadPath + fan.getSource() + ".png";
            
            File imageFile = new File(filePath);
            if (!imageFile.exists()) {
                filePath = defaultImagePath;
            }

            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(filePath)));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", output);
            t.additem(fan.getSourceName(),fan.getSource(),output.toByteArray());
            sourceList.add(t);
        }

        // 转换为JSON字符串
        Gson gson = new Gson();
        String fanJson = gson.toJson(sourceList);

        // 设置首部参数
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        response.addHeader("Location", "#");
        response.addDateHeader("Date", new Date().getTime());

        // 响应输出
        ServletOutputStream out = response.getOutputStream();
        out.write(fanJson.getBytes());
        out.flush();
        out.close();
    }

    @ResponseBody
    @RequestMapping("/square/add")
    public void squareAddAttention(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ref = "successful";
        String source = request.getParameter("source");
        String sourceName = request.getParameter("sourceName");
        String target = request.getParameter("target");
        // 根据source值从数据库中获取对应的target列表
        List<Attention> attentions = attentionDao.findBysource(source);

        // 提取target值列表
        for (Attention attention : attentions) {
            if(attention.getTarget().equals(target)){
                ref = "repeated";
                break;
            }
        }
        
        if(ref.equals("successful")){
            Attention newAtten = new Attention();
            newAtten.setSource(source);
            newAtten.setTarget(target);
            newAtten.setSourceName(sourceName);
            //根据target找到TargetName
            List<User> userlist = userDao.findAll();
    	    for(int i=0;i<userlist.size();i++)
    	    {
    		    User user = userlist.get(i);
                if(user.getPhone().equals(target)){
                    newAtten.setTargetName(user.getName());
                    break;
                }
    	    }
            attentionDao.saveAndFlush(newAtten);
        }

        // 设置首部参数
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        response.addHeader("Location", "#");
        response.addDateHeader("Date", new Date().getTime());

        //如果对方未注册，则会导致响应失败，在前端提示用户不存在即可
        // 响应输出
        ServletOutputStream out = response.getOutputStream();
        out.write(ref.getBytes());
        out.flush();
        out.close();
    }

    @ResponseBody
    @RequestMapping("/square/delete")
    public void squareDeleteAttention(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String target = request.getParameter("target");
        String source = request.getParameter("source");

		List<Attention> deleteOp = attentionDao.findBytarget(target);

        for (Attention d : deleteOp) {
            if(source.equals(d.getSource()))
            attentionDao.delete(d);
        }

        // 设置首部参数
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        response.addHeader("Location", "#");
        response.addDateHeader("Date", new Date().getTime());

        // 响应输出
        ServletOutputStream out = response.getOutputStream();
        out.flush();
        out.close();
    }
}
