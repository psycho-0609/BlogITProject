package com.finnal.blogit.config;

import com.finnal.blogit.constant.Constant;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FireBaseConfig {

    @EventListener
    public void init(ApplicationReadyEvent event) {

        try {

            ClassPathResource serviceAccount = new ClassPathResource("firebaseServiceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(Constant.BUCKET_NAME)
                    .build();
            FirebaseApp.initializeApp(options);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }
}
