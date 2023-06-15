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

import com.example.demo.Attention;
import com.example.demo.repository.AttentionRepository;
import com.google.gson.Gson;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@Transactional
@Component
public class AttentionController {
    @Autowired
    private AttentionRepository attentionRepository;

    @ResponseBody
    @RequestMapping("/square/myattention")
    public void squareMyAttention(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String source = request.getParameter("myattention");

        // 根据source值从数据库中获取对应的target列表
        List<Attention> attentions = attentionRepository.findBysource(source);

        // 提取target值列表
        List<String> targetList = new ArrayList<>();
        for (Attention attention : attentions) {
            targetList.add(attention.getTarget());
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
}
