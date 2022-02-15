package dev.georgetech.elasticsearch;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchRepositoryIT {

  @Autowired
  private DanceRepository danceRepository;

  @Test
  void shouldCreateDance() {
    Dance samba = danceRepository.save(Dance.builder()
        .name("samba")
        .build());

    Assertions.assertThat(samba)
        .isNotNull()
        .extracting("name")
        .isEqualTo("samba");
  }

  @Test
  void shouldGetDance() {
    Dance samba = danceRepository.save(Dance.builder()
        .name("samba")
        .build());

    Optional<Dance> dance = danceRepository.findById(samba.getId());

    Assertions.assertThat(dance)
        .isNotEmpty()
        .contains(samba);
  }

}
