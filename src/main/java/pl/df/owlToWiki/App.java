package pl.df.owlToWiki;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.df.owlToWiki.facade.OwlToWikiFacade;
import pl.df.owlToWiki.facade.OwlToWikiFacadeImpl;
import pl.df.owlToWiki.facade.wiki.WikiWriter;
import pl.df.owlToWiki.facade.wiki.WikiWriterImpl;

import java.io.IOException;

public class App {
    public static void main(String[] args) {

        System.out.println(">>> Usage: java -Dconfig=file:/config/Path -jar owl2wiki.jar");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        OwlToWikiFacade owlToWikiFacade = context.getBean(OwlToWikiFacadeImpl.class);

        owlToWikiFacade.performOwlToWikiAction();
        System.out.println(">>>> Done! By pressing the enter key a rollback will be performed. <<<<");
        try {
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
            WikiWriter wikiWriter = context.getBean(WikiWriterImpl.class);
            wikiWriter.rollbackCreatedArticles();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
