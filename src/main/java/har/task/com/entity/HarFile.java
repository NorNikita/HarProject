package har.task.com.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "har_fale")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HarFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "browser")
    private String browser;

    @Column(name = "version")
    private String version;

    @Column(name = "content")
    private String content;
}
