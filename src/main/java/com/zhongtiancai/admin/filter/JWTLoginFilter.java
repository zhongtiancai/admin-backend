package com.zhongtiancai.admin.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongtiancai.admin.entity.Admin;
import com.zhongtiancai.admin.utils.JsonUtils;
import com.zhongtiancai.admin.utils.JwtTokenUtil;
import com.zhongtiancai.admin.vm.AdminUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {



    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
 
    private AuthenticationManager authenticationManager;


 
    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            Admin user = new ObjectMapper()
                    .readValue(req.getInputStream(), Admin.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

 
    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        AdminUserDetails user = (AdminUserDetails) auth.getPrincipal();
        if(user.getUsername().equals("admin")){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("admin");
            ((List) user.getAuthorities()).add(grantedAuthority);
        }
        String token = jwtTokenUtil.generateToken(user);
        res.addHeader(tokenHeader, tokenHead + " " + token);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", user.getUsername());
        userInfo.put("authorities", user.getAuthorities().stream().map(au->au.getAuthority().toString()).collect(Collectors.toList()));
        res.getWriter().println(JsonUtils.writeValueAsString(userInfo));
        res.getWriter().flush();
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}