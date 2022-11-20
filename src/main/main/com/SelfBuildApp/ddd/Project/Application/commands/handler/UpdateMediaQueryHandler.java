package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateMediaQueryCommand;
import com.SelfBuildApp.ddd.Project.domain.Assembler.CssValueAssembler;
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

    @Autowired
    private CssValueAssembler cssValueAssembler;

    @Override
    @Transactional
    public MediaQuery handle(UpdateMediaQueryCommand command) {
//        tagRepository.save(command.getTag());
        MediaQuery dto = command.getMediaQuery();
        MediaQuery DbENtity = mediaQueryRepository.findById(Long.valueOf(command.getAggregateId().getId())).get();
        DbENtity.setName(dto.getName());
        DbENtity.setColor(dto.getColor());
        DbENtity.setColorUnitName(dto.getColorUnitName());
//        List<Long> issetEntitiesIds = new ArrayList<>();
        List<Long> issetEntitiesIdsValues = new ArrayList<>();
        for (CssValue cssVal : dto.getCssValues()) {
            if(cssVal.getId() != null && cssVal.getId() > 0) {
                CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());

                if (dbCssVal != null) {
                    issetEntitiesIdsValues.add(cssVal.getId());

                    dbCssVal = cssValueAssembler.createModel(dbCssVal, cssVal);

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


}
