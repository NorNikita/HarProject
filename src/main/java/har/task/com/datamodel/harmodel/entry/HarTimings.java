package har.task.com.datamodel.harmodel.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class HarTimings {

    private Long blocked;
    private Long dns;
    private Long connect;
    private Long send;
    private Long wait;
    private Long receive;
    private Long ssl;
    private String comment;
}
