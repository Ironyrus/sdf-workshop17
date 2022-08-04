package workshop17.apiworkshop.app;

import javax.sound.sampled.SourceDataLine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
HOSTED ON
https://git.heroku.com/salty-sea-23958.git
heroku login
heroku create (Then copy the heroku.git website url)
git init
git remote add heroku  <replace wif your heroku git url>
git add .
git commit -m "new"
git push heroku master 

TO START PROGRAM ON VS CODE
mvn compile
mvn package
mvn spring-boot:run
 */

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {

		SpringApplication.run(AppApplication.class, args);
	}

}
