package vn.noron.apiconfig.config.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import vn.noron.apiconfig.config.authentication.JwtAuthenticationFilter;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/authenticate").permitAll()
                .antMatchers("/api/v1/data-synchronization").permitAll()
                .antMatchers("/api/v1/forgot-password").permitAll()
                .antMatchers("/api/v1/notification/**").permitAll()
                .antMatchers("/api/v1/news/search-similar").permitAll()
                .antMatchers("/api/v1/news/update-similar-group-id-article").permitAll()
                .anyRequest().authenticated();

        http.addFilterAfter(
                authenticationJwtTokenFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Swagger convert camelcase to snake_case
     *
     * @param objectMapper
     * @return
     */
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper.setPropertyNamingStrategy(SNAKE_CASE));
    }

}
