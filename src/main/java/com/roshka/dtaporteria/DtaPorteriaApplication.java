package com.roshka.dtaporteria;
import com.roshka.dtaporteria.config.FirebaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class DtaPorteriaApplication {

	public static void main(String[] args) {
		FirebaseConfig firebaseConfig = new FirebaseConfig();

		SpringApplication.run(DtaPorteriaApplication.class, args);

	}
}
