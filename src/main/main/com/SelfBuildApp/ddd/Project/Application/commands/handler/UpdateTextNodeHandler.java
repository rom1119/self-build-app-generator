package com.SelfBuildApp.ddd.Project.Application.commands.handler;

import com.SelfBuildApp.cqrs.annotation.CommandHandlerAnnotation;
import com.SelfBuildApp.cqrs.command.handler.CommandHandler;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateTextNodeCommand;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.TextNode;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.TextNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@CommandHandlerAnnotation
public class UpdateTextNodeHandler implements CommandHandler<UpdateTextNodeCommand, TextNode> {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private TextNodeRepository tagRepository;


    @Override
    @Transactional
    public TextNode handle(UpdateTextNodeCommand command) {
//        tagRepository.save(command.getTag());
        TextNode dto = command.getTextNode();
        TextNode DbENtity = tagRepository.load(command.getTextNodeId().getId());
        DbENtity.setText(dto.getText());


//        cssStyleRepository.flush();
        return DbENtity;
    }
}
