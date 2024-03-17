package com.chat.messenger.Controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chat.messenger.DocumentModels.Files;
import com.chat.messenger.DocumentModels.Message;
import com.chat.messenger.DocumentModels.User;
import com.chat.messenger.Repositories.FileRepo;
import com.chat.messenger.Repositories.MessageRepo;
import com.chat.messenger.Repositories.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;

    @PostMapping("/addUser")
    @CrossOrigin(origins = "*")
    public User addUser(@RequestBody User user) {
        System.out.println(user.getUserName());
        return userRepo.insert(user);
    }

    @GetMapping("/login")
    @CrossOrigin(origins = "*")
    public String checkUser(@RequestParam String userName, @RequestParam String password, HttpServletRequest request) {
        User user = userRepo.findBy(userName, password);
        HttpSession session = request.getSession();
        // Store data in session
        session.setAttribute("username", user.getUserName());
        String name = (String) session.getAttribute("username");
        return name;
    }

    @GetMapping("/logout")
    @CrossOrigin(origins = "*")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged Out";
    }

    @GetMapping("/getUsers")
    @CrossOrigin(origins = "*")
    public java.util.List<User> getUsers(@RequestParam String user) {
        return userRepo.findAllBy(user);
    }

    @PostMapping("/uploadForms")
    @CrossOrigin(origins = "*")
    public String uploadForm(@RequestParam("files") MultipartFile[] files, @RequestParam("message") String message,
            @RequestParam("from") String from, @RequestParam("to") String to) {

        String filePath;
        System.out.println(message);

        filePath = System.getProperty("user.dir") + "/Uploads" + File.separator + files[0].getOriginalFilename();
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(files[0].getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(files[0].getOriginalFilename());

        System.out.println(from);
        System.out.println(to);

        return "Success";
    }

    @PostMapping("/uploadMessage")
    @CrossOrigin(origins = "*")
    public Message uploadMessage(@RequestParam("message") String message, @RequestParam("from") String from,
            @RequestParam("to") String to) {
        Message m = new Message();
        m.setDate();
        m.setTime();
        m.setMessage(message);
        m.setFrom(from);
        m.setTo(to);
        return messageRepo.insert(m);
    }

    @GetMapping("/getMessages")
    @CrossOrigin(origins = "*")
    public List<Message> getMessages(@RequestParam("from") String from, @RequestParam("to") String to) {
        return messageRepo.findBy(from, to);
    }

    @PostMapping("/uploadFiles")
    @CrossOrigin(origins = "*")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("from") String from,
            @RequestParam("to") String to) {

        for (MultipartFile file : files) {
            Files f = new Files();
            f.setDate();
            f.setTime();
            f.setFrom(from);
            f.setTo(to);
            f.setFile("http://localhost:8000/download/" + file.getOriginalFilename());
            String filePath = System.getProperty("user.dir") + "/Uploads" + File.separator + file.getOriginalFilename();
            try {
                FileOutputStream fout = new FileOutputStream(filePath);
                fout.write(file.getBytes());
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "Success";
    }
}
