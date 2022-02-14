package dev.georgetech.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(
    indexName = Indices.DANCE,
    createIndex = false
)
@Setting(settingPath = "es-settings.json")
public class Dance {

  @Id
  @Field(type = FieldType.Keyword)
  private String id;

  @Field(type = FieldType.Text)
  private String name;

}
