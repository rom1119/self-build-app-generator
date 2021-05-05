package com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.selector;

import com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.KeyFrameSelector;

public class ToSelector extends KeyFrameSelector {
    public static String NAME = "to-animation-selector";

    @Override
    public String getValue() {
        return "to";
    }
}
