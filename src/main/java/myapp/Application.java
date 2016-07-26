package myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

import com.phraseapp.incontexteditor.PhraseAppEnabledCondition;
import com.phraseapp.incontexteditor.PhraseAppConfiguration;
import com.phraseapp.incontexteditor.PhraseAppMessageSource;
import com.phraseapp.incontexteditor.PhraseAppJavascriptHeader;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    @Conditional(PhraseAppEnabledCondition.class)
    public MessageSource messageSource() {
        return new PhraseAppMessageSource();
    }

    @Bean
    public PhraseAppJavascriptHeader phraseAppJavascriptHeader() {
        return new PhraseAppJavascriptHeader();
    }
}
