package vn.noron.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    FirebaseMessaging firebaseMessaging() throws IOException {
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
//        FirebaseOptions firebaseOptions = FirebaseOptions
//                .builder()
//                .setCredentials(googleCredentials)
//                .build();
//        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
//        return FirebaseMessaging.getInstance(app);
//    }
}
