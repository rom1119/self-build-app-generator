package com.SelfBuildApp.ddd.Project.domain.Assembler;

import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CssAssembler {

    @Autowired
    private CssValueRepository cssValueRepository;

    @Autowired
    private CssValueAssembler cssValueAssembler;

    public CssStyle createModel(CssStyle existModel, CssStyle from)
    {
        CssStyle model = existModel;
        if (model == null) {
            model = new CssStyle();
        }

        List<Long> issetEntitiesIdsValues = new ArrayList<>();


        model.setResourceUrl(from.getResourceUrl());

        model.setValue(from.getValue());
        model.setValueSecond(from.getValueSecond());
        model.setValueThird(from.getValueThird());
        model.setValueFourth(from.getValueFourth());
        model.setValueFifth(from.getValueFifth());
        model.setUnitName(from.getUnitName());
        model.setUnitNameSecond(from.getUnitNameSecond());
        model.setUnitNameThird(from.getUnitNameThird());
        model.setUnitNameFourth(from.getUnitNameFourth());
        model.setUnitNameFifth(from.getUnitNameFifth());
        for (CssValue cssVal : from.getCssValues()) {

            if(cssVal.getId() != null && cssVal.getId() > 0) {
                CssValue dbCssVal = cssValueRepository.getOne(cssVal.getId());

                if (dbCssVal != null) {
                    issetEntitiesIdsValues.add(cssVal.getId());

                    dbCssVal = cssValueAssembler.createModel(dbCssVal, cssVal);
                }
            }
        }

        model.setIssetEntitiesIdsValues(issetEntitiesIdsValues);

        return model;

    }
}
