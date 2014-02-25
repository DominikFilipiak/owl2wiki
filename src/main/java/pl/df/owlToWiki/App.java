package pl.df.owlToWiki;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.df.owlToWiki.wiki.WikiFacade;
import pl.df.owlToWiki.wiki.WikiFacadeImpl;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
//        OwlLoader owlLoader = context.getBean(OwlLoaderImpl.class);
//        owlLoader.loadFiles();
        WikiFacade wikiFacade = context.getBean(WikiFacadeImpl.class);
        wikiFacade.write();
    }
}
