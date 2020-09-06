package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.cqrs.query.PaginatedResult;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendMediaQueryToHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendTagToHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateMediaQueryCommand;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.AdvanceCssStyleCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Struct.HtmlProjectCode;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.impl.DefaultHtmlCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.MediaQuery;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectPageableRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.MediaQueryRepository;
import com.SelfBuildApp.infrastructure.User.exception.ApiError;
import com.SelfBuildApp.infrastructure.User.exception.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/media-query")
public class MediaQueryController {

    @Autowired
    private MediaQueryRepository repository;

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
//
//    @Autowired
//    private StorageService storageService;

    @Autowired
    private PathFileManager pathFileManager;

//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    @JsonView(PropertyAccess.Details.class)
    public MediaQuery getOne(@PathVariable String id, Authentication auth) {
        Optional<MediaQuery> load = repository.findById(Long.valueOf(id));
        load.orElseThrow(() -> new ResourceNotFoundException("Not found"));
        MediaQuery htmlProject = load.get();


        return htmlProject;
    }


    @GetMapping("/project/{id}")
    @JsonView(PropertyAccess.List.class)
    public List<MediaQuery> getAll(@PathVariable String id) {
        List<MediaQuery> all = repository.findAllForProjectId(id);
        return all;
    }

    @PostMapping("/project/{id}")
    public ResponseEntity addMediaQuery(@PathVariable String id,
                                 @RequestBody @Validated() MediaQuery mediaQuery,
                                 BindingResult bindingResult
                                 )
    {

        if (bindingResult.hasErrors()) {
             return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        HtmlProject entity = Optional.ofNullable(projectRepository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        AppendMediaQueryToHtmlProjectCommand command = new AppendMediaQueryToHtmlProjectCommand(new AggregateId(id), mediaQuery);
        gate.dispatch(command);

        return ResponseEntity.ok(mediaQuery);
    }

    @PutMapping("/{id}")
    public ResponseEntity putMediaQuery(@PathVariable String id,
                                 @RequestBody @Validated() MediaQuery mediaQuery,
                                 BindingResult bindingResult
                                 )
    {

        if (bindingResult.hasErrors()) {
             return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        MediaQuery entity = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        UpdateMediaQueryCommand command = new UpdateMediaQueryCommand(new AggregateId(id), mediaQuery);
        gate.dispatch(command);

        return ResponseEntity.ok(mediaQuery);
    }



}


