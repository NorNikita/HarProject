package har.task.com.mapper.model.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class HarParams {

    private String name;
    private String value;
    private String fileName;
    private String contentType;
    private String comment;

}
