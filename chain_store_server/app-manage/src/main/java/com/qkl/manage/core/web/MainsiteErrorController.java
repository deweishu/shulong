package com.qkl.manage.core.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dengjihai on 2017/8/23.
 */
@Controller
public class MainsiteErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String handleError() {
        return "page/500";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
