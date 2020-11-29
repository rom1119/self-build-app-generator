package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateHtmlTagCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssValueRepository;
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
    private CssValueRepository cssValueRepository;

    @Autowired
    private PathFileManager pathFileManager;

    @Override
    @Transactional
    public HtmlTag handle(UpdateHtmlTagCommand command) {
//        tagRepository.save(command.getTag());
        HtmlTag dto = command.getTag();
        HtmlTag DbENtity = tagRepository.load(command.getTagId().getId());
        DbENtity.setPathFileManager(pathFileManager);
        DbENtity.setAttrs(dto.getAttrs());

        List<Long> issetEntitiesIds = new ArrayList<>();
        List<Long> issetEntitiesIdsValues = new ArrayList<>();
        for (CssStyle css : dto.getCssStyleList()) {
            if(css.getId() != null && css.getId() > 0) {
                CssStyle dbCss = cssStyleRepository.getOne(css.getId());
                if (dbCss != null) {
                    issetEntitiesIds.add(css.getId());
                    dbCss.setResourceUrl(css.getResourceUrl());

                    dbCss.setValue(css.getValue());
                    dbCss.setValueSecond(css.getValueSecond());
                    dbCss.setValueThird(css.getValueThird());
                    dbCss.setUnitName(css.getUnitName());
                    dbCss.setUnitNameSecond(css.getUnitNameSecond());
                    dbCss.setUnitNameThird(css.getUnitNameThird());
                    for (CssValue cssVal : css.getCssValues()) {

                        if(cssVal.getId() != null && cssVal.getId() > 0) {
                            CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());

                            if (dbCssVal != null) {
                                issetEntitiesIdsValues.add(cssVal.getId());

                                dbCssVal.setInset(cssVal.isInset());
                                dbCssVal.setSpecialValGradient(cssVal.isSpecialValGradient());
                                dbCssVal.setValue(cssVal.getValue());
                                dbCssVal.setValueSecond(cssVal.getValueSecond());
                                dbCssVal.setValueThird(cssVal.getValueThird());
                                dbCssVal.setValueFourth(cssVal.getValueFourth());
                                dbCssVal.setValueFifth(cssVal.getValueFifth());
                                dbCssVal.setUnitName(cssVal.getUnitName());
                                dbCssVal.setUnitNameSecond(cssVal.getUnitNameSecond());
                                dbCssVal.setUnitNameThird(cssVal.getUnitNameThird());
                                dbCssVal.setUnitNameFourth(cssVal.getUnitNameFourth());
                                dbCssVal.setUnitNameFifth(cssVal.getUnitNameFifth());
                            }
                        }
                    }
                this.processChildren(css, dbCss);
                }
            }


        }

        int sizeCssList = DbENtity.getCssStyleList().size();

        for (int i = 0; i < sizeCssList; i++) {
            CssStyle css = DbENtity.getCssStyleList().get(i);

            int sizeCssValuesList = css.getCssValues().size();

            for (int m = 0; m < sizeCssValuesList; m++) {
                CssValue cssVAL = css.getCssValues().get(m);
                if (cssVAL.getId() != null) {

                    if (!issetEntitiesIdsValues.contains(cssVAL.getId())) {

                        css.removeCssValue(cssVAL);
                        sizeCssValuesList--;
                        m--;
                    }
                }
            }

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

                    dbCss = this.createCssStyle(css, DbENtity);

                    for (CssValue cssVal : css.getCssValues()) {
                        cssVal.setCssStyle(dbCss);
                        if (cssVal.getId() != null && cssVal.getId() > 0) {
                            CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());
                            if (dbCssVal == null) {
                                dbCss.addCssValue(cssVal);

                                cssValueRepository.save(cssVal);
                            }
                        } else {
                            dbCss.addCssValue(cssVal);

                            cssValueRepository.save(cssVal);
                        }
                    }
                } else {
                    for (CssValue cssVal : css.getCssValues()) {
                        cssVal.setCssStyle(dbCss);

                        if (cssVal.getId() != null && cssVal.getId() > 0) {
                            CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());
                            if (dbCssVal == null) {
                                dbCss.addCssValue(cssVal);

                                cssValueRepository.save(cssVal);
                            }
                        } else {
                            dbCss.addCssValue(cssVal);

                            cssValueRepository.save(cssVal);
                        }
                    }
                }
            } else {
                DbENtity.addCssStyle(css);
                css.setHtmlTag(DbENtity);

                for (CssStyle child : css.getChildren()) {
                    child.setParent(css);
                    for (CssValue cssValChild : child.getCssValues()) {
                        cssValChild.setCssStyle(child);
                    }
                }
                for (CssValue cssVal : css.getCssValues()) {
                    if (cssVal.getId() != null && cssVal.getId() > 0) {
                        CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());
                        if (dbCssVal == null) {
                            cssVal.setCssStyle(css);
                            cssValueRepository.save(cssVal);
                        }
                    } else {
                        cssVal.setCssStyle(css);
                        cssValueRepository.save(cssVal);
                    }
                }
                cssStyleRepository.save(css);

            }
        }
        return DbENtity;
    }

    @Transactional
    private CssStyle createCssStyle(CssStyle css, HtmlTag DbENtity)
    {
        css.setHtmlTag(DbENtity);
        DbENtity.addCssStyle(css);
        cssStyleRepository.save(css);

        return css;
    }

    @Transactional
    private CssStyle createCssStyleForChildren(CssStyle css, CssStyle DbENtity)
    {
        css.setParent(DbENtity);
        DbENtity.addChild(css);
        cssStyleRepository.save(css);

        return css;
    }

    private CssStyle processChildren(CssStyle dto, CssStyle DbENtity)
    {

        List<Long> issetEntitiesIds = new ArrayList<>();
        List<Long> issetEntitiesIdsValues = new ArrayList<>();
        for (CssStyle css : dto.getChildren()) {
            if(css.getId() != null && css.getId() > 0) {
                CssStyle dbCss = cssStyleRepository.getOne(css.getId());
                if (dbCss != null) {
                    issetEntitiesIds.add(css.getId());
                    dbCss.setResourceUrl(css.getResourceUrl());

                    dbCss.setValue(css.getValue());
                    dbCss.setValueSecond(css.getValueSecond());
                    dbCss.setValueThird(css.getValueThird());
                    dbCss.setUnitName(css.getUnitName());
                    dbCss.setUnitNameSecond(css.getUnitNameSecond());
                    dbCss.setUnitNameThird(css.getUnitNameThird());
                    for (CssValue cssVal : css.getCssValues()) {

                        if(cssVal.getId() != null && cssVal.getId() > 0) {
                            CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());

                            if (dbCssVal != null) {
                                issetEntitiesIdsValues.add(cssVal.getId());

                                dbCssVal.setInset(cssVal.isInset());
                                dbCssVal.setSpecialValGradient(cssVal.isSpecialValGradient());
                                dbCssVal.setValue(cssVal.getValue());
                                dbCssVal.setValueSecond(cssVal.getValueSecond());
                                dbCssVal.setValueThird(cssVal.getValueThird());
                                dbCssVal.setValueFourth(cssVal.getValueFourth());
                                dbCssVal.setValueFifth(cssVal.getValueFifth());
                                dbCssVal.setUnitName(cssVal.getUnitName());
                                dbCssVal.setUnitNameSecond(cssVal.getUnitNameSecond());
                                dbCssVal.setUnitNameThird(cssVal.getUnitNameThird());
                                dbCssVal.setUnitNameFourth(cssVal.getUnitNameFourth());
                                dbCssVal.setUnitNameFifth(cssVal.getUnitNameFifth());
                            }
                        }
                    }
                }
            }
        }

        int sizeCssList = DbENtity.getChildren().size();

        for (int i = 0; i < sizeCssList; i++) {
            CssStyle css = DbENtity.getChildren().get(i);

            int sizeCssValuesList = css.getCssValues().size();

            for (int m = 0; m < sizeCssValuesList; m++) {
                CssValue cssVAL = css.getCssValues().get(m);
                if (cssVAL.getId() != null) {
                    if (!issetEntitiesIdsValues.contains(cssVAL.getId())) {

                        css.removeCssValue(cssVAL);
                        sizeCssValuesList--;
                        m--;
                    }

                }
            }

            if (css.getId() != null) {
                if (!issetEntitiesIds.contains(css.getId())) {
                    //                cssStyleRepository.delete(css);
                    css.setPathFileManager(pathFileManager);
                    DbENtity.removeChild(css);
                    sizeCssList--;
                    i--;
                }
            }

        }

        for (CssStyle css : dto.getChildren()) {
            if(css.getId() != null && css.getId() > 0) {
                CssStyle dbCss = cssStyleRepository.getOne(css.getId());
                if (dbCss == null) {

                    dbCss = this.createCssStyleForChildren(css, dto);

                    for (CssValue cssVal : css.getCssValues()) {
                        cssVal.setCssStyle(dbCss);
                        if (cssVal.getId() != null && cssVal.getId() > 0) {
                            CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());
                            if (dbCssVal == null) {
                                dbCss.addCssValue(cssVal);

                                cssValueRepository.save(cssVal);
                            }
                        } else {
                            dbCss.addCssValue(cssVal);

                            cssValueRepository.save(cssVal);
                        }
                    }
                } else {
                    for (CssValue cssVal : css.getCssValues()) {
                        cssVal.setCssStyle(dbCss);

                        if (cssVal.getId() != null && cssVal.getId() > 0) {
                            CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());
                            if (dbCssVal == null) {
                                dbCss.addCssValue(cssVal);

                                cssValueRepository.save(cssVal);
                            }
                        } else {
                            dbCss.addCssValue(cssVal);

                            cssValueRepository.save(cssVal);
                        }
                    }
                }
            } else {
                DbENtity.addChild(css);
                css.setParent(DbENtity);
                for (CssValue cssVal : css.getCssValues()) {

                    if (cssVal.getId() != null && cssVal.getId() > 0) {
                        CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());
                        if (dbCssVal == null) {
                            cssVal.setCssStyle(css);

                            cssValueRepository.save(cssVal);
                        }
                    } else {
                        cssVal.setCssStyle(css);
                        cssValueRepository.save(cssVal);
                    }
                }
                cssStyleRepository.save(css);

            }
        }

        return dto;
    }
}
