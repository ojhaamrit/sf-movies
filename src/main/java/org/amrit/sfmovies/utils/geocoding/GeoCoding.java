package org.amrit.sfmovies.utils.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCoding {

    @JsonProperty(value="lat")
    private Double lat;

    @JsonProperty(value="lon")
    private Double lon;

    @JsonProperty(value="display_name")
    private String displayName;
}
