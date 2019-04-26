package cn.web.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author : Kavin Gu
 * Project Name : ueditor-test
 * Description :
 * @version : ${VERSION} 2019/1/10 11:54
 * Modified by : kavingu
 */
@Configuration
public class ErrorConfig {
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
                ErrorPage error50xPage = new ErrorPage(HttpStatus.BAD_REQUEST,"/500.html");

                container.addErrorPages(error401Page, error404Page, error500Page,error50xPage);
            }
        };
    }
}
