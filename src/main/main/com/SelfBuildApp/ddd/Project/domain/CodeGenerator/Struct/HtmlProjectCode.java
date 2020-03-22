package com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Struct;

import java.io.Serializable;

public class HtmlProjectCode implements Serializable {

    protected String html;
    protected String css;

    public HtmlProjectCode(String html, String css) {
        this.html = html;
        this.css = css;
    }

    public HtmlProjectCode() {
    }

    public String getHtml() {
        return html;
    }

    public String getCss() {
        return css;
    }
}
