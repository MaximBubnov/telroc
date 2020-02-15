package com.packagename.telroc.back.config;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class CustomRequestCache extends HttpSessionRequestCache{

    //Сохраняет неаутентифицированные запросы,
    //чтобы мы могли перенаправить пользователя на страницу, к которой он пытался получить доступ после входа в систему.
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) { //
        if (!SecurityUtils.isFrameworkInternalRequest(request)) {
            super.saveRequest(request, response);
        }
    }
}
