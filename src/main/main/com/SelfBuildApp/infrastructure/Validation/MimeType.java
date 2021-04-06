package com.SelfBuildApp.infrastructure.Validation;

public enum MimeType {
    IMAGE_JPG("image/jpg"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_SVG("image/svg+xml"),
    IMAGE_PNG("image/png"),
    FONT_TTF("font/ttf"),
    FONT_OTF("font/otf"),
    FONT_WOFF("font/woff"),
    FONT_WOFF2("font/woff2");

    String mimeType;

    private MimeType(String mimeTypeArg)
    {
        mimeType = mimeTypeArg;
    }
}
