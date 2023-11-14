package ch.flagmansworld.config;

import ch.flagmansworld.model.Participant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "xmas")
@Data
public class ParticipantConfig {

  private List<Participant> participants;
}
