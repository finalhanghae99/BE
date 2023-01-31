package com.product.application.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.application.common.ResponseMessage;
import com.product.application.security.UserDetailsImpl;
import com.product.application.security.UserDetailsServiceImpl;
import com.product.application.security.jwt.JwtAuthFilter;
import com.product.application.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.product.application.common.exception.ErrorCode.DO_NOT_HAVE_PERMISSION_ERROR_MSG;
import static com.product.application.common.exception.ErrorCode.FORBIDDEN_ERROR;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf 중지
        http.csrf().disable();
        // 세션정책 중지
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // api 허용정책 설정
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, new String[]{"/users/signup","/users/login","/users/checkemail","/users/checknickname"}).permitAll()
                .antMatchers(HttpMethod.GET, new String[]{"/api/test", "/review/bestsix", "/reservation/findall", "/reservation/{reservationId}", "/reservation/listsix"}).permitAll()
                .antMatchers("/reviewlookup/**").permitAll()
                .antMatchers("/camping/permit/**").permitAll()
                .antMatchers("/chat/**").permitAll()
                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위해 JwtAuthFilter 적용
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        // 이 설정을 해주지 않으면 밑의 corsConfigurationSource가 적용되지 않습니다!
        http.exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint)
                .and().headers().frameOptions().disable()
                .and().cors();

        return http.build();
    }

    //403 Forbidden 예외처리
    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                response.setStatus(FORBIDDEN_ERROR.getStatusCode());
                String json = new ObjectMapper().writeValueAsString(new ResponseMessage<>(FORBIDDEN_ERROR, FORBIDDEN_ERROR));
                response.setContentType("application/json; charset=utf8");
                response.getWriter().write(json);
            };

    /**
     * 이 설정을 해주면, 우리가 설정한대로 CorsFilter가 Security의 filter에 추가되어
     * 예비 요청에 대한 처리를 해주게 됩니다.
     * cors 개념 참고 - https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();

        // 서버에서 응답하는 리소스에 접근 가능한 출처를 명시
        // Access-Control-Allow-Origin
        //config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:3000"); //요거 변경하시면 됩니다.
        config.addAllowedOrigin("http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com");

        // 특정 헤더를 클라이언트 측에서 꺼내어 사용할 수 있게 지정
        // 만약 지정하지 않는다면, Authorization 헤더 내의 토큰 값을 사용할 수 없음
        // Access-Control-Expose-Headers
        config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);

        // 본 요청에 허용할 HTTP method(예비 요청에 대한 응답 헤더에 추가됨)
        // Access-Control-Allow-Methods
        config.addAllowedMethod("*");

        // 본 요청에 허용할 HTTP header(예비 요청에 대한 응답 헤더에 추가됨)
        // Access-Control-Allow-Headers
        config.addAllowedHeader("*");

        // 기본적으로 브라우저에서 인증 관련 정보들을 요청 헤더에 담지 않음
        // 이 설정을 통해서 브라우저에서 인증 관련 정보들을 요청에 담을 수 있도록 해줍니다.
        // Access-Control-Allow-Credentials
        config.setAllowCredentials(true);

        // allowCredentials 를 true로 하였을 때,
        // allowedOrigin의 값이 * (즉, 모두 허용)이 설정될 수 없도록 검증합니다.
        config.validateAllowCredentials();

        // 어떤 경로에 이 설정을 적용할 지 명시합니다. (여기서는 전체 경로)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }


}