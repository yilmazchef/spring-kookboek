package be.intec.springkookboek.views;


import be.intec.springkookboek.models.Article;
import be.intec.springkookboek.models.Author;
import be.intec.springkookboek.models.Format;
import be.intec.springkookboek.utils.ArticleParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class MainView implements CommandLineRunner {

	private static final Logger LOG = Logger.getLogger(MainView.class.getName());

	public static void main( String[] args ) {

		SpringApplication.run( MainView.class, args );
	}


	@Override
	public void run( final String... args ) throws Exception {

		Article article = new Article()
				.setTitle( "The art of parsing." )
				.setPublicationDate( new Date() )
				.setAuthors( List.of( new Author( "Justin", "Bieber" ), new Author( "Nikola", "Tesla" ) ) )
				.setContent( "Interesting story." );

		String xmlArticle = ArticleParser.serialize( article, Format.XML );
		String jsonArticle = ArticleParser.serialize( article, Format.JSON );

		LOG.info( xmlArticle );
		LOG.info( jsonArticle );
	}

}
