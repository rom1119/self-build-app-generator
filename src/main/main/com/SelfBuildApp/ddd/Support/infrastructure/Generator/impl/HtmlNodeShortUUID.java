package com.SelfBuildApp.ddd.Support.infrastructure.Generator.impl;

import com.SelfBuildApp.ddd.Project.domain.HtmlNode;
import com.SelfBuildApp.ddd.Support.infrastructure.Generator.ShortUUIDGenerator;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.validation.Validation;
import java.util.List;

@Service
public class HtmlNodeShortUUID {

    @Autowired
    private HtmlNodeRepository htmlNodeRepository;

    @Autowired
    private ShortUUIDGenerator shortUUIDGenerator;


    public HtmlNodeShortUUID(HtmlNodeRepository htmlNodeRepository, ShortUUIDGenerator shortUUIDGenerator) {
        this.htmlNodeRepository = htmlNodeRepository;
        this.shortUUIDGenerator = shortUUIDGenerator;
    }

    public String generateUnique(String projectId) {
        String shortUUID = null;
        List<HtmlNode> nodes = null;
        do {
            shortUUID = shortUUIDGenerator.generate();

            nodes = htmlNodeRepository.findByShortUUID(shortUUID, projectId);

            if (nodes == null) {
                break;
            }
        } while (nodes.size() > 0);

        return shortUUID;

    }
}
