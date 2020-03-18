package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectPageableRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private HtmlProjectRepository repository;

    @Autowired
    private HtmlProjectPageableRepository repositoryPageable;
//
    @Autowired
    private StandardGate gate;
//
//    @Autowired
//    private StorageService storageService;

//    @PreAuthorize("hasRole('SUPER_ADMIN')")
//    @GetMapping("/{id}")
//    @JsonView(PropertyAccess.Details.class)
//    public HtmlProject getOne(@PathVariable String id, Authentication auth) {
//        return Optional.ofNullable(repository.load(id)).orElseThrow(() -> new ResourceNotFoundException("Not found"));
//    }
//
//    @GetMapping()
//    @JsonView(PropertyAccess.List.class)
//    public PaginatedResult<HtmlProject> getAll(Pageable pageable) {
//        Page<HtmlProject> all = repositoryPageable.findAll(pageable);
//        return new PaginatedResult(all.getContent(), all.getNumber(), all.getSize(), all.getTotalElements());
//    }

//    @PostMapping("/{id}/append-tag")
//    public ResponseEntity addTag(@PathVariable String id,
//                                 @RequestBody @Validated() HtmlTag htmlTag,
//                                 BindingResult bindingResult
//                                 )
//    {
//
//        if (bindingResult.hasErrors()) {
//             return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
//        }
//        HtmlProject entity = Optional.ofNullable(repository.load(id))
//                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
//
//        AppendTagToHtmlProjectCommand command = new AppendTagToHtmlProjectCommand(new AggregateId(id), htmlTag);
//        gate.dispatch(command);
//
//        return ResponseEntity.ok(htmlTag);
//    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String arg) throws Exception {
//        Thread.sleep(1000); // simulated delay
        System.out.println("websocket");
        System.out.println(arg);
        return "ok";
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


