package com.SelfBuildApp.ddd.Project.FakeData;

import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Project.domain.Unit.Named;
import com.SelfBuildApp.ddd.Project.domain.Unit.Size.Pixel;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
public class HtmlProjectData {


    boolean alreadySetup = false;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Autowired
    private CssStyleRepository cssStyleRepository;


//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Transactional
    @PostConstruct
    public void init() {

        if (alreadySetup)
            return;

//        if (event instanceof ContextRefreshedEvent) {
//            ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
////            applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods().forEach((e, a) -> {
////                System.out.print(e.getName());
////            });
//        }

        HtmlProject htmlProject = new HtmlProject();
        HtmlTag h1 = new HtmlTag();
        HtmlNode text = new TextNode("example text asd");
        text.setProject(htmlProject);
        htmlProject.appendChild(h1);
        h1.setProject(htmlProject);
        h1.setTagName("h1");
        HtmlTag h1Child = new HtmlTag();
        h1Child.setTagName("h1");
        h1Child.setProject(htmlProject);
        h1.appendChild(h1Child);
        h1.appendChild(text);

        CssStyle padding = new CssStyle("padding", "20");
        padding.setUnit(new Pixel());
        CssStyle border = new CssStyle("border", "40");
        border.setUnit(new Pixel());
        border.setUnitNameSecond(Named.NAME);
        border.setValueSecond("dotted");
        border.setUnitNameThird(Named.NAME);
        border.setValueThird("green");
        CssStyle width = new CssStyle("width", "400");
        width.setUnit(new Pixel());
        CssStyle height = new CssStyle("height", "200");
        height.setUnit(new Pixel());
        CssStyle backgroundColor = new CssStyle("background-color", "red");
        backgroundColor.setUnit(new Named());
        CssStyle boxSizing = new CssStyle("box-sizing", "content-box");
        boxSizing.setUnit(new Named());

        CssStyle width2 = new CssStyle("width", "200");
        width2.setUnit(new Pixel());
        CssStyle height2 = new CssStyle("height", "100");
        height2.setUnit(new Pixel());
        CssStyle backgroundColor2 = new CssStyle("background-color", "blue");
        backgroundColor2.setUnit(new Named());
        CssStyle boxSizing2 = new CssStyle("box-sizing", "content-box");
        boxSizing2.setUnit(new Named());

        h1.addCssStyle(width);
        h1.addCssStyle(height);
        h1.addCssStyle(backgroundColor);
        h1.addCssStyle(boxSizing);

        h1Child.addCssStyle(padding);
        h1Child.addCssStyle(border);
        h1Child.addCssStyle(width2);
        h1Child.addCssStyle(height2);
        h1Child.addCssStyle(backgroundColor2);
        h1Child.addCssStyle(boxSizing2);

        htmlProject.setName("CBC Project");

//        cssStyleRepository.save(width);
//        cssStyleRepository.save(height);
//        cssStyleRepository.save(backgroundColor);
        projectRepository.save(htmlProject);
        tagRepository.save(h1);

        alreadySetup = true;
    }


}
