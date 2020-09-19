package com.SelfBuildApp.infrastructure.Validation;

public enum MimeType {
    IMAGE_JPG("image/jpg"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_SVG("image/svg+xml"),
    IMAGE_PNG("image/png");

    String mimeType;

    private MimeType(String mimeTypeArg)
    {
        mimeType = mimeTypeArg;
    }
}
