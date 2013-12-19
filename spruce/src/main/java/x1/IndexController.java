package x1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        System.out.println(request.getQueryString());
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        return "index";
    }

    @RequestMapping("/reg")
    public String reg() {
        return "reg";
    }

    @RequestMapping("/{ownerId}")
    public String toTwitter(@PathVariable("ownerId") String ownerId) {
        return "reg";
    }

    @RequestMapping("/home")
    public String welcome() {
        return "home";
    }

    @RequestMapping("/home1")
    public String home1() {
        return "home1";
    }
}
