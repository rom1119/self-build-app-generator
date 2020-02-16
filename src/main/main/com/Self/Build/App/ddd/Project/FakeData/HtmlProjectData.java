package com.Self.Build.App.ddd.Project.FakeData;

import com.Self.Build.App.ddd.Project.domain.CssStyle;
import com.Self.Build.App.ddd.Project.domain.HtmlProject;
import com.Self.Build.App.ddd.Project.domain.HtmlTag;
import com.Self.Build.App.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlTagRepository;
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

        CssStyle width = new CssStyle("width", "400px");
        CssStyle height = new CssStyle("height", "200px");
        CssStyle backgroundColor = new CssStyle("backgroundColor", "red");
        h1.setProject(htmlProject);
        h1.setTagName("h1");
        h1.addCssStyle(width);
        h1.addCssStyle(height);
        h1.addCssStyle(backgroundColor);
        htmlProject.setName("CBC Project");

//        cssStyleRepository.save(width);
//        cssStyleRepository.save(height);
//        cssStyleRepository.save(backgroundColor);
        projectRepository.save(htmlProject);
        tagRepository.save(h1);

        alreadySetup = true;
    }


}
