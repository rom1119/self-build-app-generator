package com.SelfBuildApp.ddd.Project.infrastructure.repo;

import com.SelfBuildApp.ddd.Project.domain.HtmlTagAttr;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class HtmlAttrConverter implements AttributeConverter<Map<String, HtmlTagAttr>, String> {

    protected ObjectMapper objectMapper;

    public HtmlAttrConverter() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(Map<String, HtmlTagAttr> customerInfo) {

        String customerInfoJson = null;
        try {
            if (customerInfo != null) {
                customerInfoJson = objectMapper.writeValueAsString(customerInfo);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        try {
//        } catch (final JsonProcessingException e) {
//            logger.error("JSON writing error", e);
//        }

        return customerInfoJson;
    }

    @Override
    public Map<String, HtmlTagAttr> convertToEntityAttribute(String customerInfoJSON) {

        Map<String, HtmlTagAttr> customerInfo = null;
        TypeReference<Map<String, HtmlTagAttr>> typeRef
                = new TypeReference<Map<String, HtmlTagAttr>>() {};
        try {
            if (customerInfoJSON != null) {
                customerInfo = objectMapper.readValue(customerInfoJSON, typeRef);

            }
        } catch (final IOException e) {
            e.printStackTrace();

//            logger.error("JSON reading error", e);
        }

        return customerInfo;
    }
}
