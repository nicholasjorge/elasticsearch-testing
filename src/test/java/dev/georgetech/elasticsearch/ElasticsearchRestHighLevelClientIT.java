package dev.georgetech.elasticsearch;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchRestHighLevelClientIT {

  @Autowired
  private RestHighLevelClient restHighLevelClient;
  @Autowired
  private DanceRepository danceRepository;

  @Test
  void injectedComponentsAreNotNull() {
    assertThat(restHighLevelClient).isNotNull();
    assertThat(danceRepository).isNotNull();
  }

  @Test
  void shouldIndexJsonObject() throws IOException {
    String jsonObject = """
        {
          "name":"samba"
        }
        """;
    IndexRequest request = new IndexRequest(Indices.DANCE);
    request.source(jsonObject, XContentType.JSON);

    IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);

    assertThat(response.getResult()).isEqualTo(Result.CREATED);
    assertThat(response.getVersion()).isEqualTo(1);
    assertThat(response.getIndex()).isEqualTo(Indices.DANCE);
  }

  @Test
  void shouldQueryIndexedDocument() throws IOException {
    danceRepository.save(Dance.builder()
        .name("samba")
        .build());

    SearchRequest searchRequest = new SearchRequest();
    SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    SearchHit[] searchHits = response.getHits().getHits();
    List<String> names =
        Arrays.stream(searchHits)
            .map(hit -> {
              Map<String, Object> sourceAsMap = hit.getSourceAsMap();
              return (String) sourceAsMap.get("name");
            }).toList();

    assertThat(names)
        .isNotEmpty()
        .contains("samba");
  }

}
