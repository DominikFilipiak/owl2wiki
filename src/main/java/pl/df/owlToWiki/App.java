package pl.df.owlToWiki;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.df.owlToWiki.facade.OwlToWikiFacade;
import pl.df.owlToWiki.facade.OwlToWikiFacadeImpl;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        OwlToWikiFacade owlToWikiFacade = context.getBean(OwlToWikiFacadeImpl.class);
        owlToWikiFacade.performOwlToWikiAction();
    }
}
