package com.SelfBuildApp.ddd.Project.domain;

import com.SelfBuildApp.infrastructure.Validation.FileConstrain;
import com.SelfBuildApp.infrastructure.Validation.FileExtension;
import com.SelfBuildApp.infrastructure.Validation.Image;
import com.SelfBuildApp.infrastructure.Validation.MimeType;
import org.springframework.web.multipart.MultipartFile;

public class DtoFontFile {

    @FileConstrain( size = 10000, extensions = {FileExtension.FONT_TTF, FileExtension.FONT_OTF, FileExtension.FONT_WOFF, FileExtension.FONT_WOFF2})
    protected MultipartFile file;

    public DtoFontFile(MultipartFile file) {
        this.file = file;
    }

    public DtoFontFile() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
