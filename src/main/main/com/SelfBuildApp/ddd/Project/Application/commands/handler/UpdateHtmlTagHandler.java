package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateHtmlTagCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
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

    @Autowired
    private PathFileManager pathFileManager;

    @Override
    @Transactional
    public HtmlTag handle(UpdateHtmlTagCommand command) {
//        tagRepository.save(command.getTag());
        HtmlTag dto = command.getTag();
        HtmlTag DbENtity = tagRepository.load(command.getTagId().getId());
        DbENtity.setPathFileManager(pathFileManager);

        List<Long> issetEntitiesIds = new ArrayList<>();
        for (CssStyle css : dto.getCssStyleList()) {
            if(css.getId() != null && css.getId() > 0) {
                CssStyle dbCss = cssStyleRepository.getOne(css.getId());
                if (dbCss != null) {
                    issetEntitiesIds.add(css.getId());
                    dbCss.setValue(css.getValue());
                    dbCss.setValueSecond(css.getValueSecond());
                    dbCss.setValueThird(css.getValueThird());
                    dbCss.setUnitName(css.getUnitName());
                    dbCss.setUnitNameSecond(css.getUnitNameSecond());
                    dbCss.setUnitNameThird(css.getUnitNameThird());
                }
            }
        }

        int sizeCssList = DbENtity.getCssStyleList().size();

        for (int i = 0; i < sizeCssList; i++) {
            CssStyle css = DbENtity.getCssStyleList().get(i);
            if (!issetEntitiesIds.contains(css.getId())) {
//                cssStyleRepository.delete(css);
                css.setPathFileManager(pathFileManager);
                DbENtity.removeCssStyle(css);
                sizeCssList--;
                i--;
            }
        }

        for (CssStyle css : dto.getCssStyleList()) {
            if(css.getId() != null && css.getId() > 0) {
                CssStyle dbCss = cssStyleRepository.getOne(css.getId());
                if (dbCss == null) {
                    css.setHtmlTag(DbENtity);
                    DbENtity.addCssStyle(css);
                    cssStyleRepository.save(css);
                }
            } else {
                DbENtity.addCssStyle(css);
                css.setHtmlTag(DbENtity);
                cssStyleRepository.save(css);
            }
        }
//        cssStyleRepository.flush();
        return DbENtity;
    }
}
