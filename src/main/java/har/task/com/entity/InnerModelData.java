package har.task.com.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "inner_model_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InnerModelData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count_request")
    private Long countRequest;

    @Column(name = "data", columnDefinition = "json")
    private String data;

    public InnerModelData(Long countRequest, String data) {
        this.countRequest = countRequest;
        this.data = data;
    }
}
