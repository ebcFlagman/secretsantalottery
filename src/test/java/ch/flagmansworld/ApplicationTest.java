package ch.flagmansworld;

import ch.flagmansworld.config.ParticipantConfig;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTest {

  @Autowired
  private ParticipantConfig participantConfig;

  @RegisterExtension
  static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
    .withConfiguration(GreenMailConfiguration.aConfig().withUser("santa", "secret"))
    .withPerMethodLifecycle(false);

  @Test
  void generateSecretSantaPairs_shouldSendEmailToEachGifted() {
    await().atMost(10, SECONDS).untilAsserted(() -> {
      MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
      assertEquals(4, receivedMessages.length);

      for (final var receivedMessage : receivedMessages) {
        assertTrue(participantConfig.getParticipants().stream()
          .anyMatch(p -> {
            try {
              return p.email().equals(receivedMessage.getRecipients(Message.RecipientType.TO)[0].toString());
            } catch (final MessagingException e) {
              throw new RuntimeException(e);
            }
          }));
      }
    });
  }
}
