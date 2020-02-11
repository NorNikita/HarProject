package har.task.com.mapper.model.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HarRequest {

    private HttpMethod method;
    private String url;
    private String httpVersion;
    private List<HarCookies> cookies = new ArrayList<>();
    private List<HarHeaders> headers = new ArrayList<>();
    private List<HarQueryString> queryString = new ArrayList<>();
    private HarPostData postData;
    private Long headersSize;
    private Long bodySize;
    private String comment;

}
