package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.cqrs.query.PaginatedResult;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.Application.commands.*;
import com.SelfBuildApp.ddd.Project.domain.AssetProject;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.AdvanceCssStyleCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.SimpleCssStyleCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Struct.HtmlProjectCode;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.impl.DefaultHtmlCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.WebsiteCrawler.PageCrawler;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectPageableRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/html-project")
public class HtmlProjectController {

    @Autowired
    private HtmlProjectRepository repository;

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
//    @Autowired
//    private StorageService storageService;

    @Autowired
    private PathFileManager pathFileManager;

//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    @JsonView( PropertyAccess.HtmlTagDetails.class)
    public HtmlProject getOne(@PathVariable String id, Authentication auth) {
        Optional<HtmlProject> load = Optional.ofNullable(repository.fetchFullProjectData(id));
        load.orElseThrow(() -> new ResourceNotFoundException("Not found"));
        HtmlProject htmlProject = load.get();

        htmlProject.setPathFileManager(pathFileManager);

        return htmlProject;
    }

    @GetMapping("/short-data/{id}")
    @JsonView( PropertyAccess.HtmlTagDetails.class)
    public HtmlProject getOneShortData(@PathVariable String id, Authentication auth) {
        Optional<HtmlProject> load = Optional.ofNullable(repository.fetchSimpleProjectData(id));
        load.orElseThrow(() -> new ResourceNotFoundException("Not found"));
        HtmlProject htmlProject = load.get();

        htmlProject.setPathFileManager(pathFileManager);

        return htmlProject;
    }

    @GetMapping("/import-website")
    public String loadWebsitePage(@RequestParam String url) {
        PageCrawler pageCrawler = new PageCrawler();

//        try {
//            pageCrawler.run(url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println("url");
        System.out.println(url);

        return "ok";
    }

    @GetMapping()
    @JsonView(PropertyAccess.List.class)
    public PaginatedResult<HtmlProject> getAll(Pageable pageable) {
        Page<HtmlProject> all = repositoryPageable.findAll(pageable);
        return new PaginatedResult(all.getContent(), all.getNumber(), all.getSize(), all.getTotalElements());
    }

    @PostMapping("/{id}/append-tag")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity addTag(@PathVariable String id,
                                 @RequestBody @Validated() HtmlTag htmlTag,
                                 BindingResult bindingResult
                                 )
    {

        if (bindingResult.hasErrors()) {
             return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        HtmlProject entity = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        AppendTagToHtmlProjectCommand command = new AppendTagToHtmlProjectCommand(new AggregateId(id), htmlTag);
        gate.dispatch(command);

        return ResponseEntity.ok(htmlTag);
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity create(
            @RequestBody @Validated() HtmlProject htmlProject,
            BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
//        MultipartFile file = assetProject.getFile();
//        AssetProject entity = Optional.ofNullable(this.entityManager.find(AssetProject.class, id))
//                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        CreateHtmlProjectCommand command = new CreateHtmlProjectCommand( htmlProject);
        HtmlProject res = (HtmlProject) gate.dispatch(command);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable String id,
                                 @RequestBody @Validated() HtmlProject htmlProject,
                                 BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
//        MultipartFile file = htmlProject.getFile();
        HtmlProject entity = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        UpdateHtmlProjectCommand command = new UpdateHtmlProjectCommand( htmlProject);
        HtmlProject res = (HtmlProject) gate.dispatch(command);

        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity delete(@PathVariable String id)
    {
        HtmlProject opt = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        HtmlProject entity = opt;
//        entity.setPathFileManager(pathFileManager);

        this.entityManager.remove(entity);

        return ResponseEntity.ok("ok");
    }
}


