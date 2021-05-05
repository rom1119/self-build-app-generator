package com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.selector;

import com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.KeyFrameSelector;

public class PercentSelector extends KeyFrameSelector {
    public static String NAME = "percent-animation-selector";

    protected int val;

    public PercentSelector(int val) {
        this.val = val;
    }

    @Override
    public String getValue() {
        return this.val + "%";
    }
}
