package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.cqrs.query.PaginatedResult;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendTagToHtmlProjectCommand;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.AdvanceCssStyleCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.SimpleCssStyleCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Struct.HtmlProjectCode;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.impl.DefaultHtmlCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
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
//
//    @Autowired
//    private StorageService storageService;

//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    @JsonView(PropertyAccess.Details.class)
    public HtmlProject getOne(@PathVariable String id, Authentication auth) {
        return Optional.ofNullable(repository.load(id)).orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    @GetMapping("/test/{id}")
    public HtmlProjectCode getTest(@PathVariable String id) {
        HtmlProject entity = Optional.ofNullable(repository.load(id)).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        HtmlProjectCodeItem generateHtml = (HtmlProjectCodeItem)htmlCodeGenerator.generate(entity);
        cssStyleCodeGenerator.setTagsCodeItem(generateHtml.getChildren());
        CssProjectCodeItem generateCss = (CssProjectCodeItem)cssStyleCodeGenerator.generate(entity);


        return new HtmlProjectCode(generateHtml.getContent(),
                generateCss.getContent());
    }

    @GetMapping()
    @JsonView(PropertyAccess.List.class)
    public PaginatedResult<HtmlProject> getAll(Pageable pageable) {
        Page<HtmlProject> all = repositoryPageable.findAll(pageable);
        return new PaginatedResult(all.getContent(), all.getNumber(), all.getSize(), all.getTotalElements());
    }

    @PostMapping("/{id}/append-tag")
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


//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<User>> getAll()
//    {
//        List<User> all = userRepository.findAllOrderByScoreDesc();
//
//        return ResponseEntity.ok(all);
//    }
//
//    @PutMapping( path = "/{id}")
//    public ResponseEntity update(
//                            @PathVariable final Long id,
//                             @RequestBody @Validated(Edited.class) UserDetails userDetails,
////                                 @RequestPart( "imageFile") MultipartFil imageFile,
//                            BindingResult bindingResult
//                 ) throws Exception {
//
//        if (bindingResult.hasErrors()) {
//             return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
//
//        }
//
//        User userDb = Optional.ofNullable(userService.findByIdToEdit(id))
//                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono strony"));
//////
//        UserDetails user = userService.updateUserDetails(userDb.getUserDetails(), userDetails);
////
//        return ResponseEntity.ok(userDb);
//
//    }

}


