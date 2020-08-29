package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.ddd.CanonicalModel.AggregateId;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendChildToTagCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.AppendTextToTagCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateHtmlTagCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateTextNodeCommand;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.CssStyleRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.TextNodeRepository;
import com.SelfBuildApp.infrastructure.User.exception.ApiError;
import com.SelfBuildApp.infrastructure.User.exception.ResourceNotFoundException;
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
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/css-style")
public class CssStyleController {

    @Autowired
    private CssStyleRepository repository;

    @Autowired
    private TextNodeRepository textNodeRepository;

    @Autowired
    private PathFileManager pathFileManager;

    @PersistenceContext
    protected EntityManager entityManager;
//
    @Autowired
    private StandardGate gate;
//
//    @Autowired
//    private StorageService storageService;

//    @PreAuthorize("hasRole('SUPER_ADMIN')")



    @PostMapping("/{id}/resource")
    @Transactional
    public ResponseEntity updateResource(@PathVariable String id,
                                 @ModelAttribute @Validated() CssImage image,
                             BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        MultipartFile file = image.getFile();
        CssStyle entity = Optional.ofNullable(this.entityManager.find(CssStyle.class, Long.valueOf(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.setPathFileManager(pathFileManager);
        entity.saveResource(file);

        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}/resource")
    @Transactional
    public ResponseEntity deleteResource(@PathVariable String id)
    {
        CssStyle entity = Optional.ofNullable(this.entityManager.find(CssStyle.class, Long.valueOf(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.setPathFileManager(pathFileManager);
        entity.deleteResource();

        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/value/{id}")
    @Transactional
    public ResponseEntity deleteValue(@PathVariable String id)
    {
        System.out.println("DELETE VALUE=" + id);
        CssValue entity = Optional.ofNullable(this.entityManager.find(CssValue.class, Long.valueOf(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entityManager.remove(entity);

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


