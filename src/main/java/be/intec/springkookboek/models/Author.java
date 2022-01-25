package be.intec.springkookboek.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data // Lombok Annotation
@FieldDefaults( level = AccessLevel.PRIVATE ) // Lombok Annotation
@Accessors( chain = true ) // Lombok Annotation
@AllArgsConstructor
public class Author {

	@JsonProperty // JSON Annotation
	@JacksonXmlProperty // XML Annotation
	String firstName;

	@JsonProperty // JSON Annotation
	@JacksonXmlProperty // XML Annotation
	String familyName;

}
