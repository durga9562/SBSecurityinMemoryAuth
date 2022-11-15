package sbinmemory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().withUser("sam").password("{noop}sam").authorities("ADMIN");
		auth.inMemoryAuthentication().withUser("syed").password("{noop}syed").authorities("EMPLOYEE");
		auth.inMemoryAuthentication().withUser("jai").password("{noop}jai").authorities("STUDENT");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//URL-AccessTypes
				.antMatchers("/home").permitAll()
				.antMatchers("/welcome").authenticated()
				.antMatchers("/admin").hasAuthority("ADMIN")
				.antMatchers("/emp").hasAuthority("EMPLOYEE")
				.antMatchers("/std").hasAuthority("STUDENT")
				//.anyRequest().hasAuthority("ADMIN")
				//.antMatchers("/common").hasAnyAuthority("ADMIN","EMPLOYEE")
				
				//Login Form Details 
				.and()
				.formLogin()
				.defaultSuccessUrl("/welcome",true)
				
				//Logout Details
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				
				//Exception Details
				.and()
				.exceptionHandling()
				.accessDeniedPage("/denied")
				;
	}

}
