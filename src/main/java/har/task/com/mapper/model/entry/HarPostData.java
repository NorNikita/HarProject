package har.task.com.mapper.model.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class HarPostData {

    private String mimeType;
    private List<HarParams> params = new ArrayList<>();
    private String text;
    private String comment;
}
