package ro.tuc.ds2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.validation.annotation.Validated;
import ro.tuc.ds2020.controller.ApplicationController;
import ro.tuc.ds2020.view.LoginPage;
import ro.tuc.ds2020.service.ApplianceService;

import java.util.*;

@SpringBootApplication
@Validated
public class Ds2020Application extends SpringBootServletInitializer {

    @Bean
    public HessianProxyFactoryBean hessianInvoker() {
        HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
        invoker.setServiceUrl("https://spring2-sd2021-kaj.herokuapp.com/hellohessian");
        //invoker.setServiceUrl("http://localhost:8080/hellohessian");
        invoker.setServiceInterface(ApplianceService.class);
        return invoker;
    }

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        ConfigurableApplicationContext context = SpringApplication.run(Ds2020Application.class, args);
        ApplianceService applianceService = context.getBean(ApplianceService.class);
        context.close();
        System.setProperty("java.awt.headless", "false");

        LoginPage loginPage = new LoginPage();
        ApplicationController applicationController = new ApplicationController(loginPage);
        applicationController.setApplianceService(applianceService);
    }
}
