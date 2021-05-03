package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendKeyFrameToHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendSelectorToKeyFrameCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateKeyFrameCommand;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.AdvanceCssStyleCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.impl.DefaultHtmlCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Project.domain.KeyFrame;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectPageableRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.KeyFrameRepository;
import com.SelfBuildApp.infrastructure.User.exception.ApiError;
import com.SelfBuildApp.infrastructure.User.exception.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/key-frame")
public class KeyFrameController {

    @Autowired
    private KeyFrameRepository repository;

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private DefaultHtmlCodeGenerator htmlCodeGenerator;

    @Autowired
    private AdvanceCssStyleCodeGenerator cssStyleCodeGenerator;

    @Autowired
    private HtmlProjectPageableRepository repositoryPageable;
//
    @Autowired
    private StandardGate gate;

    @PersistenceContext
    protected EntityManager entityManager;
//
//
//    @Autowired
//    private StorageService storageService;

    @Autowired
    private PathFileManager pathFileManager;

//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    @JsonView(PropertyAccess.Details.class)
    public KeyFrame getOne(@PathVariable String id, Authentication auth) {
        Optional<KeyFrame> load = Optional.ofNullable(repository.load(id));
        load.orElseThrow(() -> new ResourceNotFoundException("Not found"));
        KeyFrame htmlProject = load.get();


        return htmlProject;
    }


    @GetMapping("/project/{id}")
    @JsonView(PropertyAccess.List.class)
    public List<KeyFrame> getAll(@PathVariable String id) {
        List<KeyFrame> all = repository.findAllForProjectId(id);
        return all;
    }

    @PostMapping("/project/{id}/append")
    public ResponseEntity addKeyFrame(@PathVariable String id,
                                 @RequestBody @Validated() KeyFrame mediaQuery,
                                 BindingResult bindingResult
                                 )
    {

        if (bindingResult.hasErrors()) {
             return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        HtmlProject entity = Optional.ofNullable(projectRepository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        AppendKeyFrameToHtmlProjectCommand command = new AppendKeyFrameToHtmlProjectCommand(new AggregateId(id), mediaQuery);
        gate.dispatch(command);

        return ResponseEntity.ok(mediaQuery);
    }

    @PutMapping("/{id}")
    public ResponseEntity putKeyFrame(@PathVariable String id,
                                 @RequestBody @Validated() KeyFrame mediaQuery,
                                 BindingResult bindingResult
                                 )
    {

        if (bindingResult.hasErrors()) {
             return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        KeyFrame entity = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        UpdateKeyFrameCommand command = new UpdateKeyFrameCommand(new AggregateId(id), mediaQuery);
        gate.dispatch(command);

        return ResponseEntity.ok(mediaQuery);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity delete(@PathVariable String id)
    {
        KeyFrame entity = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.setPathFileManager(pathFileManager);

        this.entityManager.remove(entity);

        return ResponseEntity.ok(entity);
    }

    @PostMapping("/{id}/append-selector")
    public ResponseEntity addSelector(@PathVariable String id,
                                      @RequestBody @Validated() PseudoSelector pseudoSelector,
                                      BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        KeyFrame entity = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        AppendSelectorToKeyFrameCommand command = new AppendSelectorToKeyFrameCommand(new AggregateId(id), pseudoSelector);
        gate.dispatch(command);

        return ResponseEntity.ok(pseudoSelector);
    }



}


