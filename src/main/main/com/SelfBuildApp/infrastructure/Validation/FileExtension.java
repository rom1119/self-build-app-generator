package com.SelfBuildApp.infrastructure.Validation;

public enum FileExtension {
    IMG("img"),
    FONT_TTF("ttf"),
    FONT_OTF("otf"),
    FONT_WOFF("woff"),
    FONT_WOFF2("woff2");

    String extension;

    private FileExtension(String mimeTypeArg)
    {
        extension = mimeTypeArg;
    }
}
