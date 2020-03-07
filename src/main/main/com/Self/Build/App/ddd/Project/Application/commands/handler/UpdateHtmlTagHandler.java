package com.Self.Build.App.ddd.Project.Application.commands.handler;

import com.Self.Build.App.cqrs.annotation.CommandHandlerAnnotation;
import com.Self.Build.App.cqrs.command.handler.CommandHandler;
import com.Self.Build.App.ddd.Project.Application.commands.UpdateHtmlTagCommand;
import com.Self.Build.App.ddd.Project.domain.CssStyle;
import com.Self.Build.App.ddd.Project.domain.HtmlTag;
import com.Self.Build.App.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlTagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@CommandHandlerAnnotation
public class UpdateHtmlTagHandler implements CommandHandler<UpdateHtmlTagCommand, HtmlTag> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private HtmlTagRepository tagRepository;

    @Autowired
    private CssStyleRepository cssStyleRepository;

    @Override
    @Transactional
    public HtmlTag handle(UpdateHtmlTagCommand command) {
//        tagRepository.save(command.getTag());
        HtmlTag dto = command.getTag();
        HtmlTag DbENtity = tagRepository.load(command.getTagId().getId());

        List<Long> issetEntitiesIds = new ArrayList<>();
        for (CssStyle css : dto.getCssStyleList()) {
            if(css.getId() != null && css.getId() > 0) {
                CssStyle dbCss = cssStyleRepository.getOne(css.getId());
                if (dbCss != null) {
                    issetEntitiesIds.add(css.getId());
                    dbCss.setValue(css.getValue());
                    dbCss.setUnitName(css.getUnitName());
                }
            }
        }

        int sizeCssList = DbENtity.getCssStyleList().size();

        for (int i = 0; i < sizeCssList; i++) {
            CssStyle css = DbENtity.getCssStyleList().get(i);
            if (!issetEntitiesIds.contains(css.getId())) {
//                cssStyleRepository.delete(css);
                DbENtity.removeCssStyle(css);
            }
        }

        for (CssStyle css : dto.getCssStyleList()) {
            if(css.getId() != null && css.getId() > 0) {
                CssStyle dbCss = cssStyleRepository.getOne(css.getId());
                if (dbCss == null) {
                    css.setHtmlTag(DbENtity);
                    cssStyleRepository.save(css);
                }
            } else {
                css.setHtmlTag(DbENtity);
                cssStyleRepository.save(css);
            }
        }
//        cssStyleRepository.flush();
        return dto;
    }
}
