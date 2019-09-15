package com.juggle.juggle.common.filter;

import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.common.errors.StandardErrors;
import com.juggle.juggle.framework.common.session.UserInterface;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.primary.auth.service.AuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthFilter implements Filter {
    private AuthService authService;

    @Override
    public void init(FilterConfig filterChain) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String token = httpRequest.getParameter("token");
        try {
            UserSession.Authorize<UserInterface> auth = authService.readMemberProfile(token);
            if (null != auth) {
                UserSession.setAuthorize(auth);
            }
            else {
                UserSession.setAuthorize(null);
                ApiResponse resp = ApiResponse.make(StandardErrors.UNAUTHORIZED.getStatus(), "Member not found or your token has time out, please login again.", null);
                response.getWriter().write(JsonUtils.writeValue(resp));
                response.getWriter().flush();
                return;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            ApiResponse resp = ApiResponse.make(StandardErrors.INTERNAL_ERROR.getStatus(), "Failed to load member profile, please try later.", null);
            response.getWriter().write(JsonUtils.writeValue(resp));
            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(request, response);
        return;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
