package har.task.com.datamodel.harmodel.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HarLog {

    @JsonProperty(defaultValue = "1.1")
    private String version;

    private HarCreator creator;
    private HarBrowser browser;
    private List<HarPages> pages = new ArrayList<>();
    private List<HarEntries> entries = new ArrayList<>();
    private String comment;
}
