package BMG.BookManamgnet.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    @GetMapping("/home")
    public String handleWelcome(){
        return "Home";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return "Home_admin";
    }

    @GetMapping("/user/home")
    public String handleUserHome(){
        return "Home_user";
    }

    @GetMapping("/login")
    public String handleLogin(){
        return "Login";
    }

    @GetMapping("/register")
    public String handleRegister(){
        return "RegisterUser";
    }
}
