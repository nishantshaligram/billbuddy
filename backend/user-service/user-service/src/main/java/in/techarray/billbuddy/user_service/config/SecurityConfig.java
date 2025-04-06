package in.techarray.billbuddy.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> {
            try {
                cors.init(http);
            } catch (Exception e) {
                throw new RuntimeException("CORS configuration failed");
            }
        });
        http.csrf( csrf -> csrf.disable() )
            .authorizeHttpRequests( auth -> auth
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
