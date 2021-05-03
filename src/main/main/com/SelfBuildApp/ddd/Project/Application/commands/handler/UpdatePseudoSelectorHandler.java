package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdatePseudoSelectorCommand;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@CommandHandlerAnnotation
public class UpdatePseudoSelectorHandler implements CommandHandler<UpdatePseudoSelectorCommand, PseudoSelector> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private PseudoSelectorRepository selectorRepository;

    @Autowired
    private CssStyleRepository cssStyleRepository;

    @Autowired
    private CssValueRepository cssValueRepository;

    @Autowired
    private PathFileManager pathFileManager;

    @Autowired
    private MediaQueryRepository mediaQueryRepository;

    @Override
    @Transactional
    public PseudoSelector handle(UpdatePseudoSelectorCommand command) {
//        tagRepository.save(command.getTag());
        PseudoSelector dto = command.getPseudoSelector();
        PseudoSelector DbENtity = selectorRepository.findById(command.getId()).get();
        DbENtity.setPathFileManager(pathFileManager);
        DbENtity.setValue(dto.getValue());
        DbENtity.setUnitName(dto.getUnitName());

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
                                dbCssVal.setValueSixth(cssVal.getValueSixth());
                                dbCssVal.setValueSeventh(cssVal.getValueSeventh());
                                dbCssVal.setValueEighth(cssVal.getValueEighth());
                                dbCssVal.setUnitName(cssVal.getUnitName());
                                dbCssVal.setUnitNameSecond(cssVal.getUnitNameSecond());
                                dbCssVal.setUnitNameThird(cssVal.getUnitNameThird());
                                dbCssVal.setUnitNameFourth(cssVal.getUnitNameFourth());
                                dbCssVal.setUnitNameFifth(cssVal.getUnitNameFifth());
                                dbCssVal.setUnitNameSixth(cssVal.getUnitNameSixth());
                                dbCssVal.setUnitNameSeventh(cssVal.getUnitNameSeventh());
                                dbCssVal.setUnitNameEighth(cssVal.getUnitNameEighth());
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
                css.setPseudoSelector(DbENtity);

                if (css.getMediaQuery() != null) {
                    MediaQuery med = mediaQueryRepository.getOne(css.getMediaQuery().getId());
                    css.setMediaQuery(med);
                }

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
    private CssStyle createCssStyle(CssStyle css, PseudoSelector DbENtity)
    {
        if (css.getMediaQuery() != null) {
            MediaQuery med = mediaQueryRepository.getOne(css.getMediaQuery().getId());
            css.setMediaQuery(med);
        }
        css.setPseudoSelector(DbENtity);
        DbENtity.addCssStyle(css);
        cssStyleRepository.save(css);

        return css;
    }

    @Transactional
    private CssStyle createCssStyleForChildren(CssStyle css, CssStyle DbENtity)
    {
        if (css.getMediaQuery() != null) {
            MediaQuery med = mediaQueryRepository.getOne(css.getMediaQuery().getId());
            css.setMediaQuery(med);
        }
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
                                dbCssVal.setValueSixth(cssVal.getValueSixth());
                                dbCssVal.setValueSeventh(cssVal.getValueSeventh());
                                dbCssVal.setValueEighth(cssVal.getValueEighth());
                                dbCssVal.setUnitName(cssVal.getUnitName());
                                dbCssVal.setUnitNameSecond(cssVal.getUnitNameSecond());
                                dbCssVal.setUnitNameThird(cssVal.getUnitNameThird());
                                dbCssVal.setUnitNameFourth(cssVal.getUnitNameFourth());
                                dbCssVal.setUnitNameFifth(cssVal.getUnitNameFifth());
                                dbCssVal.setUnitNameSixth(cssVal.getUnitNameSixth());
                                dbCssVal.setUnitNameSeventh(cssVal.getUnitNameSeventh());
                                dbCssVal.setUnitNameEighth(cssVal.getUnitNameEighth());
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

                if (css.getMediaQuery() != null) {
                    MediaQuery med = mediaQueryRepository.getOne(css.getMediaQuery().getId());
                    css.setMediaQuery(med);
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

        return dto;
    }
}
