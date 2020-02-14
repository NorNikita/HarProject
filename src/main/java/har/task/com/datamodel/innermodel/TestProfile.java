package har.task.com.datamodel.innermodel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TestProfile implements Serializable {

    private List<Request> requests = new ArrayList<>();
}
