package fsj_3_22.final_certification.online_store.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests()
      .requestMatchers("/authentication/sign_out", "/cart/**", "/order/**").authenticated()
      .requestMatchers("/authentication/**").anonymous()
      .requestMatchers("*/admin/**").hasRole("ADMIN")
      .requestMatchers("/**").permitAll()
    ;
    return httpSecurity.build();
  }
}
