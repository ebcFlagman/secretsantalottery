package ch.flagmansworld.service;

import ch.flagmansworld.model.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class EmailService {

  @Value("${xmas.text}")
  private String xmasText;

  @Value("${spring.mail.username}")
  private String from;

  private final JavaMailSender emailSender;

  public void sendEmail(final Participant secretSanta, final Participant gifted) throws MailSendException {
    final var message = new SimpleMailMessage();
    message.setFrom(String.format("Secret Santa <%s>", from));
    message.setTo(secretSanta.email());
    message.setSubject("Secret Santa Lottery");
    message.setText(MessageFormat.format(xmasText, secretSanta.name(), gifted.name()));
    emailSender.send(message);
  }
}
