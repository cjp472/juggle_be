package com.juggle.juggle.api;

import com.juggle.juggle.framework.api.utils.ApiExceptionResolver;
import com.juggle.juggle.CoreFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProjectApiExceptionResolver extends ApiExceptionResolver {
    private static Logger LOG = LoggerFactory.getLogger(ProjectApiExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView ret = super.resolveException(request, response, handler, ex);
        LOG.debug("Handled Error: ", ex);
        CoreFramework.getInstance().rollback();
        return ret;
    }
}
