package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Project.domain.WebsiteCrawler.PageCrawler;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@CommandHandlerAnnotation
public class UpdateHtmlProjectHandler implements CommandHandler<UpdateHtmlProjectCommand, HtmlProject> {

    @Autowired
    private HtmlProjectRepository repository;

    @Autowired
    private HtmlTagRepository htmlTagRepository;

    @Autowired
    private MediaQueryRepository mediaQueryRepository;

    @Autowired
    private FontFaceRepository fontFaceRepository;

    @Autowired
    private KeyFrameRepository keyFrameRepository;

    @Autowired
    private PageCrawler pageCrawler;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private PathFileManager pathFileManager;


    @Override
    public HtmlProject handle(UpdateHtmlProjectCommand command) {

        HtmlProject dto = command.getProject();
        HtmlProject dbEntity = repository.load(dto.getId());
        dbEntity.setName(dto.getName());
        if (dto.getPageUrl() != null) {
            String oldUrl = dbEntity.getPageUrl();
            dbEntity.setPageUrl(dto.getPageUrl());

            this.deleteProjectDataIfUrlPageIsChange(oldUrl, dbEntity);
            try {
                pageCrawler.run(dto.getPageUrl(), dbEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dbEntity;
    }

    @Transactional
    private void deleteProjectDataIfUrlPageIsChange(String oldPageUrl, HtmlProject dbEntity)
    {
        if (oldPageUrl.equals( dbEntity.getPageUrl())) {
            return;
        }

        List<HtmlNode> tags = htmlTagRepository.findAllHtmlTagsForProject(dbEntity.getId());
        List<MediaQuery> mediaQueryList = mediaQueryRepository.findAllForProjectId(dbEntity.getId());
        List<FontFace> fontFaceList = fontFaceRepository.findAllForProjectId(dbEntity.getId());
        List<KeyFrame> keyFrameList = keyFrameRepository.findAllForProjectId(dbEntity.getId());

        System.out.println("all tags= " + tags.size());
        System.out.println("all mediaQueryList= " + mediaQueryList.size());
        System.out.println("all fontFaceList= " + fontFaceList.size());
        System.out.println("all keyFrameList= " + keyFrameList.size());

        for (HtmlNode el: tags) {
            el.setPathFileManager(pathFileManager);

            entityManager.remove(el);
        }

        for (MediaQuery el: mediaQueryList) {

            entityManager.remove(el);
        }

        for (FontFace el: fontFaceList) {
            el.setPathFileManager(pathFileManager);

            entityManager.remove(el);
        }

        for (KeyFrame el: keyFrameList) {
            el.setPathFileManager(pathFileManager);

            entityManager.remove(el);
        }

    }
}
