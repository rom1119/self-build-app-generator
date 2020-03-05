package com.Self.Build.App.ddd.Support.infrastructure.Controller;

import com.Self.Build.App.cqrs.command.impl.StandardGate;
import com.Self.Build.App.ddd.CanonicalModel.AggregateId;
import com.Self.Build.App.ddd.Project.Application.commands.AppendChildToTagCommand;
import com.Self.Build.App.ddd.Project.Application.commands.AppendTagToHtmlProjectCommand;
import com.Self.Build.App.ddd.Project.Application.commands.UpdateHtmlTagCommand;
import com.Self.Build.App.ddd.Project.Application.commands.handler.AppendChildToTagHandler;
import com.Self.Build.App.ddd.Project.domain.HtmlProject;
import com.Self.Build.App.ddd.Project.domain.HtmlTag;
import com.Self.Build.App.ddd.Support.infrastructure.repository.HtmlTagRepository;
import com.Self.Build.App.infrastructure.User.exception.ApiError;
import com.Self.Build.App.infrastructure.User.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/html-tag")
public class HtmlTagController {

    @Autowired
    private HtmlTagRepository repository;
//
    @Autowired
    private StandardGate gate;
//
//    @Autowired
//    private StorageService storageService;

//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public HtmlTag getOne(@PathVariable String id, Authentication auth) {
        return Optional.ofNullable(repository.load(id)).orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    @PutMapping("/{id}")
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

        if (!entity.equals(htmlTag)) {
            throw new AccessDeniedException("Not Access  to html tag");
        }

        UpdateHtmlTagCommand command = new UpdateHtmlTagCommand(htmlTag);
        HtmlTag res = (HtmlTag) gate.dispatch(command);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/append-tag")
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

        return ResponseEntity.ok(entity);
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


