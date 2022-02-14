package dev.georgetech.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DanceRepository extends ElasticsearchRepository<Dance, String> {

}
