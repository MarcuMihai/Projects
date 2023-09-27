package ro.tuc.ds2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.validation.annotation.Validated;
import ro.tuc.ds2020.reader.Reader;

import java.util.TimeZone;


@SpringBootApplication
@Validated
public class Ds2020Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ds2020Application.class);
    }

    public static void main(String[] args){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        ConfigurableApplicationContext context=SpringApplication.run(Ds2020Application.class, args);
        Reader r=new Reader();
        try {
            r.getData(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
