package de.hsba.a16.bi.mitfahrzentrale;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers()
                    .frameOptions()
                    .sameOrigin()
                    .and() // allow the h2-console to be used in a frame
                .authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll() // enable access to the h2-console
                    .antMatchers("/all-trips/**").permitAll()
                    .antMatchers("/users/*/comments").permitAll()
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/", "/index").permitAll()
                    .antMatchers("/js/**", "/css/**").permitAll() // permit JS resources
                    .anyRequest().authenticated()
//                .antMatchers("/").authenticated()
//                .antMatchers("/h2-console/**").permitAll() // enable access to the h2-console
//                .antMatchers("/signup").permitAll()
//                .antMatchers("/new-trip").permitAll()
//                .antMatchers("/book-trip/**").permitAll()
//                .antMatchers("/js/**").permitAll() // permit JS resources
//                .antMatchers("/users/**").permitAll()
//                .antMatchers("/further/**").permitAll()
//                .antMatchers("/index").permitAll()
//				  .antMatchers("/trips/**").permitAll()
//			      .antMatchers("/all-trips/**").permitAll()
//                .antMatchers("/trip-booked").permitAll()
//                .antMatchers("/new-trip").permitAll()
			        .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/index")
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/index")
                    .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public IDialect securityDialect() {
        return new SpringSecurityDialect();
    }


}
