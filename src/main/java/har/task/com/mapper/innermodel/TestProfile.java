package har.task.com.mapper.innermodel;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TestProfile {

    private List<Request> requests = new ArrayList<>();
}
