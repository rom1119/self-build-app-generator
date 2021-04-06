package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.Project.domain.FontFace;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class UpdateFontFaceCommand implements Serializable {

    private FontFace fontFace;

    public UpdateFontFaceCommand(FontFace fontFace) {
        this.fontFace = fontFace;
    }

    public FontFace getFontFace() {
        return fontFace;
    }
}
