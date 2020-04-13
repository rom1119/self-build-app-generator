package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

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
    public ResponseEntity updateResource(@PathVariable String id,
                             @RequestParam("file") MultipartFile file,

                             @RequestBody @Validated() CssImage htmlTag,
                             BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        CssStyle entity = Optional.ofNullable(this.entityManager.find(CssStyle.class, Long.valueOf(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        System.out.println(file);
//        try {
////            entity.saveResource(file.getInputStream(), file.getOriginalFilename(), file.);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return ResponseEntity.ok("ass");
    }

    @DeleteMapping("/{id}/resource")
    @Transactional
    public ResponseEntity deleteResource(@PathVariable String id)
    {
        CssStyle entity = Optional.ofNullable(this.entityManager.find(CssStyle.class, id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

//        this.entityManager.remove(entity);

        return ResponseEntity.ok(entity);
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


