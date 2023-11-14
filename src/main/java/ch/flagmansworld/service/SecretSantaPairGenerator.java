package ch.flagmansworld.service;

import ch.flagmansworld.model.Participant;
import ch.flagmansworld.model.SecretSantaPair;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecretSantaPairGenerator {

  @Value("${xmas.shuffleRounds:3}")
  private int shuffleRounds;

  public List<SecretSantaPair> generateSecretSantaPairs(List<Participant> participants) {
    final var secretSantaPairs = new ArrayList<SecretSantaPair>();
    final var giftedParticipants = participants.stream()
      .map(p -> new Participant(p.name(), p.email()))
      .collect(Collectors.toCollection(ArrayList::new));

    for (var i = 0; i < shuffleRounds; i++) {
      Collections.shuffle(participants);
      Collections.shuffle(giftedParticipants);
    }

    do {
      secretSantaPairs.clear();
      for (final var secretSanta : participants) {
        for (final var gifted : giftedParticipants) {
          if (!Objects.equals(secretSanta.email(), gifted.email())) {
            secretSantaPairs.add(new SecretSantaPair(secretSanta, gifted));
            giftedParticipants.remove(gifted);
            break;
          }
        }
      }
    } while (secretSantaPairs.size() != participants.size());

    return secretSantaPairs;
  }
}
