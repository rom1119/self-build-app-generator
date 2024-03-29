package com.SelfBuildApp.infrastructure.User.Model;

import com.SelfBuildApp.infrastructure.Validation.Group.Edited;
import com.SelfBuildApp.infrastructure.Validation.Group.FileValidationGroup;
import com.SelfBuildApp.infrastructure.Validation.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table( name = "user_details" )
public class UserDetails implements Serializable {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull(groups = Edited.class)
    @NotEmpty(groups = Edited.class)
    private String firstName;

    @NotNull(groups = Edited.class)
    @NotEmpty(groups = Edited.class)
    private String lastName;

    @Column(name = "file_name")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fileName;

    @Transient
    @Image(maxHeight = 1000, maxWidth = 1000, groups = {FileValidationGroup.class})
    @NotNull(groups = {FileValidationGroup.class})
    private MultipartFile file;

    @OneToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int score;

    public UserDetails() {
    }

    public UserDetails(String firstName,
                       String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @JsonIgnore()
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonIgnore()
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
