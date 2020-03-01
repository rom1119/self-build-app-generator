package com.Self.Build.App.ddd.Project.domain.Unit.Size;

import com.Self.Build.App.ddd.Project.domain.Unit.ColorUnit;
import com.Self.Build.App.ddd.Project.domain.Unit.SizeUnit;

public class Pixel extends SizeUnit
{
    public static final String NAME = "pixel-size-unit";
    protected String value;

    public Pixel(String value) {
        this.value = value;
    }

    public Pixel() {
    }

    @Override
    public String getValue() {
        return value + "px";
    }

    @Override
    public String getName() {
        return Pixel.NAME;
    }
}
