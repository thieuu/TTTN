package com.example.API.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.API.service.NguoiDungService;

@Configuration @EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
    NguoiDungService userService;
	
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userService) // Cung cáp userservice cho spring security
            .passwordEncoder(passwordEncoder()); // cung cấp password encoder
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/","/sanpham/**","/sanpham","/admin","/admin/**","/image/**","/thongke/**","/check/**","/verify","/xacthuc","/verify/email","/giaban").permitAll() // Cho phép tất cả mọi người truy cập vào địa chỉ này
                    .antMatchers(HttpMethod.POST,"/khachhang","/login","/dangki").permitAll()
                    .antMatchers(HttpMethod.GET,"/donhang/sdt/{sdt}","/khachhang/{sdt}","/ctdonhang/{idDH}","/khachhang/**","/khachhang/{email}","/login","/quenmatkhau").permitAll()
                    .antMatchers(HttpMethod.PUT,"/doimatkhau","/suathongtin").permitAll()
                    .antMatchers(HttpMethod.POST,"/donhang","/ctdonhang","/phieunhap","/ctphieunhap").permitAll()
                    .antMatchers(HttpMethod.PUT,"/khachhang/{sdt}","/doimatkhau","/hienthi").permitAll()
                    .antMatchers("/sanpham/**","/sanphamdaxoa","/khachhang","/khachhang/**","/donhang","/donhang/**",
                    			 "/phieunhap","/phieunhap/**","/ctphieunhap","/ctphieunhap/**","/loai","/loai/**","/hang","/hang/**",
                    			 "/upfile","/xoasanpham/{id}").permitAll()
                   .anyRequest().authenticated();
//                    .antMatchers(HttpMethod.GET,"/donhang/sdt/{sdt}","/khachhang/{sdt}","/ctdonhang/{idDH}").hasAnyAuthority("USER","ADMIN")
//                    .antMatchers(HttpMethod.PUT,"/doimatkhau").hasAnyAuthority("USER","ADMIN")
//                    .antMatchers(HttpMethod.POST,"/donhang","/ctdonhang").hasAuthority("USER")
//                    .antMatchers(HttpMethod.PUT,"/khachhang/{sdt}").hasAuthority("USER")
//                    .antMatchers("/sanpham/**","/sanphamdaxoa","/khachhang","/khachhang/**","/donhang","/donhang/**",
//                    			 "/phieunhap","/phieunhap/**","/ctphieunhap","/ctphieunhap/**","/loai","/loai/**","/kieu","/kieu**",
//                    			 "/upfile","/xoasanpham/{id}").hasAuthority("ADMIN")
//                   .anyRequest().authenticated();

        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
