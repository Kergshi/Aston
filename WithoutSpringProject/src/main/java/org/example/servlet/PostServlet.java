package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.context.AppContext;
import org.example.dto.PostIncomingDto;
import org.example.dto.PostOutGoingDto;
import org.example.service.PostService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/post/*"})
public class PostServlet extends HttpServlet {
    private final transient PostService postService;
    private final ObjectMapper objectMapper;

    public PostServlet() {
        this.postService = AppContext.get(PostService.class);
        this.objectMapper = new ObjectMapper();
    }

    public PostServlet(PostService postService) {
        this.postService = postService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String responseAnswer;
        try {
            String[] pathPart = req.getPathInfo().split("/");
            if ("all".equals(pathPart[1])) {
                List<PostOutGoingDto> postDtoList = postService.findAll();
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(postDtoList);
            } else {
                long authorId = Long.parseLong(pathPart[1]);
                PostOutGoingDto postDto = postService.findById(authorId);
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(postDto);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String responseAnswer;
        try {
            String[] pathPart = req.getPathInfo().split("/");
            long postId = Long.parseLong(pathPart[1]);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            postService.deleteById(postId);
            responseAnswer = "Post with id " + postId + " deleted";
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String json = getJson(req);
        String responseAnswer;
        Optional<PostIncomingDto> postResponse;
        try {
            postResponse = Optional.ofNullable(objectMapper.readValue(json, PostIncomingDto.class));
            PostIncomingDto post = postResponse.orElseThrow(IllegalArgumentException::new);
            responseAnswer = objectMapper.writeValueAsString(postService.save(post));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect post Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String json = getJson(req);
        String responseAnswer;
        PostIncomingDto postResponse;
        try {
            postResponse = objectMapper.readValue(json, PostIncomingDto.class);
            postService.update(postResponse);
            responseAnswer = "Post update";
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect post Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    private static void setJsonHeader(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private static String getJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader postData = req.getReader();
        String line;
        while ((line = postData.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
