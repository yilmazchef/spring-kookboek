package be.intec.springkookboek.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data // Lombok Annotation
@FieldDefaults( level = AccessLevel.PRIVATE ) // Lombok Annotation
@Accessors(chain = true) // Lombok Annotation
@JacksonXmlRootElement( localName = "article" ) // XML Annotation
public class Article {

	@JsonProperty // JSON Annotation
	@JacksonXmlProperty // XML Annotation
	String title;

	@JsonProperty // JSON Annotation
	@JacksonXmlProperty // XML Annotation
	Date publicationDate;

	@JsonProperty // JSON Annotation
	@JacksonXmlElementWrapper( localName = "authors" ) // XML Annotation for defining collections
	@JacksonXmlProperty( localName = "author" ) // XML Annotation
	List< Author > authors;

	@JsonProperty
	@JacksonXmlProperty // XML Annotation
	String content;

}