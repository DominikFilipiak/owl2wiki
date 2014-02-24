package pl.df.owlToWiki;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        OwlLoader owlLoader = context.getBean(OwlLoaderImpl.class);
        owlLoader.loadFiles();
    }
}
