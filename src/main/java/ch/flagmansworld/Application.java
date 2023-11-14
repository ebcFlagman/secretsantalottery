package ch.flagmansworld;

import ch.flagmansworld.config.ParticipantConfig;
import ch.flagmansworld.service.EmailService;
import ch.flagmansworld.service.SecretSantaPairGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailException;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Application implements CommandLineRunner {

  private final EmailService emailService;
  private final SecretSantaPairGenerator generator;
  private final ParticipantConfig participantConfig;

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(final String... args) {
    final var participants = participantConfig.getParticipants();

    if (participants.size() % 2 == 0) {
      final var secretSantaPairs = generator.generateSecretSantaPairs(participants);

      secretSantaPairs.forEach(secretSantaPair -> {
        try {
          emailService.sendEmail(secretSantaPair.secretSanta(), secretSantaPair.gifted());
        } catch (final MailException e) {
          log.error(String.format("Cannot send mail for SecretSanta pair %s and %s", secretSantaPair.secretSanta().name(), secretSantaPair.gifted().name()), e);
        }
      });
    } else {
      log.warn("Program works only with even number of participants");
    }
  }
}
