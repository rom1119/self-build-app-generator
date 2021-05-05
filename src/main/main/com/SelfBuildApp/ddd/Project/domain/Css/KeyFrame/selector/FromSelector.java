package com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.selector;

import com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.KeyFrameSelector;

public class FromSelector  extends KeyFrameSelector {
    public static String NAME = "from-animation-selector";

    @Override
    public String getValue() {

        return "from";
    }
}
