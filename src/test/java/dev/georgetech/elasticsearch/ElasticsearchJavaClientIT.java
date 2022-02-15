package dev.georgetech.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchJavaClientIT {

  @Autowired
  private ElasticsearchClient elasticsearchJavaClient;
  @Autowired
  private ElasticsearchAsyncClient elasticsearchAsyncJavaClient;
  @Autowired
  private DanceRepository danceRepository;

  @Test
  void shouldInjectJavaClients() {
    Assertions.assertThat(elasticsearchJavaClient).isNotNull();
    Assertions.assertThat(elasticsearchAsyncJavaClient).isNotNull();
  }


  @Disabled("Compatibility issue between java client version and ES version")
  @Test
  void shouldGetDanceNameFromIndex() throws IOException {

    danceRepository.save(Dance.builder()
        .name("samba")
        .build());

    SearchResponse<Dance> search = elasticsearchJavaClient.search(s -> s
            .index(Indices.DANCE)
            .query(q -> q
                .term(term -> term
                    .field("name")
                    .value(v -> v.stringValue("samba"))
                )),
        Dance.class);

    for (Hit<Dance> hit : search.hits().hits()) {
      Dance dance = hit.source();
      Assertions.assertThat(dance)
          .isNotNull()
          .extracting("name")
          .isEqualTo("samba");
    }

  }

}
