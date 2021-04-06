package com.SelfBuildApp.ddd.Project.Application.commands;

import com.SelfBuildApp.cqrs.annotation.Command;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.domain.FontFace;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;

import java.io.Serializable;

@SuppressWarnings("serial")
@Command
public class CreateFontFaceCommand implements Serializable {

    private FontFace fontFace;

    public CreateFontFaceCommand(FontFace fontFace) {
        this.fontFace = fontFace;
    }

    public FontFace getFontFace() {
        return fontFace;
    }
}
