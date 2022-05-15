package com.example.springbootsecurity.config;

import com.example.springbootsecurity.filter.JwtAuthenticationFilter;
import com.example.springbootsecurity.filter.JwtAuthorizationFilter;
import com.example.springbootsecurity.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <h3>springboot-study</h3>
 * <p>SpringSecurity配置类</p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-15 16:23
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 放行的路径
     */
    private final String[] PATH_RELEASE = {
            "/login",
            "/all"
    };
    /***根据用户名找到用户*/
    private final UserDetailsServiceImpl userDetailService;

    private final MacLoginUrlAuthenticationEntryPoint macLoginUrlAuthenticationEntryPoint;

    private final MyAccessDeniedHandler myAccessDeniedHandler;

    private final MyLogoutSuccessHandler myLogoutSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                /**antMatchers (这里的路径) permitAll 这里是允许所有人 访问*/
                .antMatchers(PATH_RELEASE).permitAll()
                /** 映射任何请求 */
                .anyRequest()

                /** 指定任何经过身份验证的用户都允许使用URL。*/
                .authenticated()

                /** 指定支持基于表单的身份验证 */
                .and().formLogin().permitAll()

                /** 允许配置异常处理。可以自己传值进去 使用WebSecurityConfigurerAdapter时，将自动应用此WebSecurityConfigurerAdapter 。*/
                .and().exceptionHandling()

                /** 设置要使用的AuthenticationEntryPoint。 macLoginUrlAuthenticationEntryPoint 验证是否登录*/
                .authenticationEntryPoint(macLoginUrlAuthenticationEntryPoint)

                /** 指定要使用的AccessDeniedHandler 处理拒绝访问失败。*/
                .accessDeniedHandler(myAccessDeniedHandler)

                /** 提供注销支持。 使用WebSecurityConfigurerAdapter时，将自动应用此WebSecurityConfigurerAdapter 。
                 * 默认设置是访问URL “ / logout”将使HTTP会话无效，清理配置的所有rememberMe()身份验证，清除SecurityContextHolder ，
                 * 然后重定向到“ / login？success”，从而注销用户*/
                .and().logout().logoutSuccessHandler(myLogoutSuccessHandler)

                /** 处理身份验证表单提交。 授予权限 */
                .and().addFilter(new JwtAuthenticationFilter(authenticationManager()))
                /** 处理HTTP请求的BASIC授权标头，然后将结果放入SecurityContextHolder 。 */
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                /**不需要session */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 因为使用了BCryptPasswordEncoder来进行密码的加密，所以身份验证的时候也的用他来判断
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    /**
     * 密码加密
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
