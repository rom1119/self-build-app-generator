package com.Self.Build.App.infrastructure.controller;

import com.Self.Build.App.infrastructure.User.Model.User;
import com.Self.Build.App.infrastructure.User.Repository.UserRepository;
import com.Self.Build.App.infrastructure.User.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private UserRepository userRepository;
//
//    @Autowired
//    private IUserService userService;
//
//    @Autowired
//    private StorageService storageService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id, Authentication auth) {
        System.out.println(auth.getPrincipal());
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAll()
    {
        List<User> all = userRepository.findAllOrderByScoreDesc();

        return ResponseEntity.ok(all);
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


