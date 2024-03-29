package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.Application.commands.*;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.TextNodeRepository;
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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api/html-tag")
public class HtmlTagController {

    @Autowired
    private HtmlTagRepository repository;

    @Autowired
    private TextNodeRepository textNodeRepository;

    @PersistenceContext
    protected EntityManager entityManager;
//
    @Autowired
    private StandardGate gate;

    @Autowired
    private PathFileManager pathFileManager;
//
//    @Autowired
//    private StorageService storageService;

//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public HtmlTag getOne(@PathVariable String id, Authentication auth) {
        Optional<HtmlTag> load = Optional.ofNullable(repository.load(id));
        load.orElseThrow(() -> new ResourceNotFoundException("Not found"));

        HtmlTag htmlTag = load.get();
        htmlTag.setPathFileManager(pathFileManager);

        return htmlTag;
    }

    @PutMapping("/text/{id}")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity updateTextNode(@PathVariable String id,
                                         @RequestBody @Validated() TextNode textNode,
                                         BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        TextNode entity = Optional.ofNullable(textNodeRepository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        UpdateTextNodeCommand command = new UpdateTextNodeCommand(new AggregateId(id), textNode);
        TextNode res = (TextNode) gate.dispatch(command);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity update(@PathVariable String id,
                                 @RequestBody @Validated() HtmlTag htmlTag,
                                 BindingResult bindingResult
                                 )
    {

        if (bindingResult.hasErrors()) {
             return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        HtmlTag entity = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        UpdateHtmlTagCommand command = new UpdateHtmlTagCommand(new AggregateId(id), htmlTag);
        HtmlTag res = (HtmlTag) gate.dispatch(command);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/append-text")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity addText(@PathVariable String id,
                                   @RequestBody @Validated() TextNode textNode,
                                   BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        HtmlTag entity = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        AppendTextToTagCommand command = new AppendTextToTagCommand(new AggregateId(id), textNode);
        TextNode dispatch = (TextNode) gate.dispatch(command);

        return ResponseEntity.ok(textNode);
    }


    @PostMapping("/{id}/append-tag")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity addChild(@PathVariable String id,
                                 @RequestBody @Validated() HtmlTag htmlTag,
                                 BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        HtmlTag entity = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        AppendChildToTagCommand command = new AppendChildToTagCommand(new AggregateId(id), htmlTag);
        gate.dispatch(command);

        return ResponseEntity.ok(htmlTag);
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
        HtmlTag entity = Optional.ofNullable(repository.load(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        AppendSelectorToTagCommand command = new AppendSelectorToTagCommand(new AggregateId(id), pseudoSelector);
        gate.dispatch(command);

        return ResponseEntity.ok(pseudoSelector);
    }



    @DeleteMapping("/{id}")
    @Transactional
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity delete(@PathVariable String id)
    {
        HtmlNode entity = Optional.ofNullable(this.entityManager.find(HtmlNode.class, id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.setPathFileManager(pathFileManager);

        this.entityManager.remove(entity);

        return ResponseEntity.ok(entity);
    }

    @PostMapping("/{id}/resource")
    @Transactional
    public ResponseEntity updateResource(@PathVariable String id,
                                         @ModelAttribute @Validated() TagImage image,
                                         BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        MultipartFile file = image.getFile();
        HtmlTag entity = Optional.ofNullable(this.entityManager.find(HtmlTag.class, id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.setPathFileManager(pathFileManager);
        entity.saveResource(file);

        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}/resource")
    @Transactional
    public ResponseEntity deleteResource(@PathVariable String id)
    {
        HtmlTag entity = Optional.ofNullable(this.entityManager.find(HtmlTag.class, id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.setPathFileManager(pathFileManager);
        entity.deleteResource();

        return ResponseEntity.ok("ok");
    }
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


