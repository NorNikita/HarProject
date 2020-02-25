package com.pflb.hartask.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "har_file")
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

    @Column(name = "content", columnDefinition = "json")
    private String content;

    public HarFile(String browser, String version, String content) {
        this.browser = browser;
        this.version = version;
        this.content = content;
    }
}
