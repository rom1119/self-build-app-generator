package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.infrastructure.Validation.Image;
import org.springframework.web.multipart.MultipartFile;

public class DtoImage {

    @Image(maxWidth = 10000, maxHeight = 10000, size = 30000)
    protected MultipartFile file;

    public DtoImage(MultipartFile file) {
        this.file = file;
    }

    public DtoImage() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
