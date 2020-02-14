package har.task.com.datamodel.harmodel.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class HarCookies {

    private String name;
    private String value;
    private String path;
    private String domain;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date expires;

    private Boolean httpOnly;
    private Boolean secure;

    private String comment;
}
