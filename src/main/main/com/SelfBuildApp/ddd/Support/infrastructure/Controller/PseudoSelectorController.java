package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.Application.commands.*;
import com.SelfBuildApp.ddd.Project.domain.HtmlNode;
import com.SelfBuildApp.ddd.Project.domain.HtmlTag;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;
import com.SelfBuildApp.ddd.Project.domain.TextNode;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.PseudoSelectorRepository;
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
@RequestMapping("/api/pseudo-selector")
public class PseudoSelectorController {

    @Autowired
    private PseudoSelectorRepository repository;


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
    public PseudoSelector getOne(@PathVariable String id, Authentication auth) {
        Optional<PseudoSelector> load = repository.findById(Long.valueOf(id));
        load.orElseThrow(() -> new ResourceNotFoundException("Not found"));

        PseudoSelector htmlTag = load.get();
        htmlTag.setPathFileManager(pathFileManager);

        return htmlTag;
    }

    @PutMapping("/{id}")
    @JsonView( PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity update(@PathVariable String id,
                                 @RequestBody @Validated() PseudoSelector pseudoSelector,
                                 BindingResult bindingResult
                                 )
    {

        if (bindingResult.hasErrors()) {
             return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        PseudoSelector entity = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        UpdatePseudoSelectorCommand command = new UpdatePseudoSelectorCommand(Long.valueOf(id), pseudoSelector);
        PseudoSelector res = (PseudoSelector) gate.dispatch(command);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/resource")
    @JsonView( PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity updateResource(@PathVariable String id,
                             @RequestParam("file") MultipartFile file,
                             @RequestBody @Validated() PseudoSelector pseudoSelector,
                             BindingResult bindingResult
    )
    {

//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
//        }
//        PseudoSelector entity = repository.findById(Long.valueOf(id))
//                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
//
//        AppendChildToTagCommand command = new AppendChildToTagCommand(new AggregateId(id), pseudoSelector);
//        gate.dispatch(command);

        return ResponseEntity.ok(pseudoSelector);
    }

    @DeleteMapping("/{id}")
    @JsonView( PropertyAccess.HtmlTagDetails.class)
    @Transactional
    public ResponseEntity delete(@PathVariable String id)
    {
        PseudoSelector entity = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.setPathFileManager(pathFileManager);

        this.entityManager.remove(entity);

        return ResponseEntity.ok(entity);
    }


}


