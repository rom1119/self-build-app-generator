package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateMediaQueryCommand;
import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@CommandHandlerAnnotation
public class UpdateMediaQueryHandler implements CommandHandler<UpdateMediaQueryCommand, MediaQuery> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private MediaQueryRepository mediaQueryRepository;

    @Autowired
    private CssStyleRepository cssStyleRepository;

    @Autowired
    private CssValueRepository cssValueRepository;

    @Autowired
    private PathFileManager pathFileManager;

    @Override
    @Transactional
    public MediaQuery handle(UpdateMediaQueryCommand command) {
//        tagRepository.save(command.getTag());
        MediaQuery dto = command.getMediaQuery();
        MediaQuery DbENtity = mediaQueryRepository.findById(Long.valueOf(command.getAggregateId().getId())).get();

//        List<Long> issetEntitiesIds = new ArrayList<>();
        List<Long> issetEntitiesIdsValues = new ArrayList<>();
        for (CssValue cssVal : dto.getCssValues()) {
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

        int sizeCssValuesList = DbENtity.getCssValues().size();

        for (int m = 0; m < sizeCssValuesList; m++) {
            CssValue cssVAL = DbENtity.getCssValues().get(m);
            if (cssVAL.getId() != null) {

                if (!issetEntitiesIdsValues.contains(cssVAL.getId())) {

                    DbENtity.removeCssValue(cssVAL);
                    sizeCssValuesList--;
                    m--;
                }
            }
        }

        for (CssValue cssVal : dto.getCssValues()) {
            cssVal.setMediaQuery(DbENtity);

            if (cssVal.getId() != null && cssVal.getId() > 0) {
                CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());
                if (dbCssVal == null) {
                    DbENtity.addCssValue(cssVal);

                    cssValueRepository.save(cssVal);
                }
            } else {
                DbENtity.addCssValue(cssVal);

                cssValueRepository.save(cssVal);
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
