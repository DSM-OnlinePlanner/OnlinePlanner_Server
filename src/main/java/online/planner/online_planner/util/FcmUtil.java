package online.planner.online_planner.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.List;

@Component
public class FcmUtil {

    @Value("${fcm.key.dir}")
    private String keyDir;

    public void sendPushMessage(List<String> tokens, String title, String content) {
        try{
            ClassPathResource resource = new ClassPathResource(keyDir);

            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();

            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(firebaseOptions);
            }

            MulticastMessage message = MulticastMessage.builder()
                    .putData("title", title)
                    .putData("content", content)
                    .addAllTokens(tokens)
                    .build();

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            System.out.println("successful send Message : " + response.getResponses());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
