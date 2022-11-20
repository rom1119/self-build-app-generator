package com.SelfBuildApp.ddd.Project.domain.Assembler;

import com.SelfBuildApp.ddd.Project.domain.CssStyle;
import com.SelfBuildApp.ddd.Project.domain.CssValue;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CssValueAssembler {

    @Autowired
    private CssValueRepository cssValueRepository;

    public CssValue createModel(CssValue existModel, CssValue from)
    {
        CssValue model = existModel;
        if (model == null) {
            model = new CssValue();
        }

        List<Long> issetEntitiesIdsValues = new ArrayList<>();

        model.setInset(from.isInset());
        model.setSpecialValGradient(from.isSpecialValGradient());
        model.setValue(from.getValue());
        model.setValueSecond(from.getValueSecond());
        model.setValueThird(from.getValueThird());
        model.setValueFourth(from.getValueFourth());
        model.setValueFifth(from.getValueFifth());
        model.setValueSixth(from.getValueSixth());
        model.setValueSeventh(from.getValueSeventh());
        model.setValueEighth(from.getValueEighth());
        model.setValueNinth(from.getValueNinth());
        model.setUnitName(from.getUnitName());
        model.setUnitNameSecond(from.getUnitNameSecond());
        model.setUnitNameThird(from.getUnitNameThird());
        model.setUnitNameFourth(from.getUnitNameFourth());
        model.setUnitNameFifth(from.getUnitNameFifth());
        model.setUnitNameSixth(from.getUnitNameSixth());
        model.setUnitNameSeventh(from.getUnitNameSeventh());
        model.setUnitNameEighth(from.getUnitNameEighth());
        model.setUnitNameNinth(from.getUnitNameNinth());

        return model;

    }
}
