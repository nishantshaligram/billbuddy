package in.techarray.billbuddy.user_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

    @Order(1)
    @Bean
    SecurityFilterChain filteringCriteria(HttpSecurity http ) throws Exception{
        http.cors( cors -> cors.disable() )
        .csrf( csrf -> csrf.disable() )
        .authorizeHttpRequests( authorize -> authorize.requestMatchers( "/auth/*").permitAll() )
        // .authorizeHttpRequests( authorize -> authorize.requestMatchers( "/expense/*" ).authenticated() );
        .authorizeHttpRequests( authorize -> authorize.anyRequest().permitAll() );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
