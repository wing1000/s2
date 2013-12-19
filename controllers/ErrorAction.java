package controllers;

import cn.bran.play.JapidResult;
import japidviews.Application.error.E404;
import japidviews.Application.error.E500;
import play.modules.router.Any;

public class ErrorAction extends Admin {

    @RequestMapping("/404")
    public static void error404() {
        throw new JapidResult(new E404().render());
    }

    @RequestMapping("/500")
    public static void error500() {
        throw new JapidResult(new E500().render());
    }
}
