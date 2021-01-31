package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.AdvanceCssStyleCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.ForInlineCssStyleCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.SimpleCssStyleCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Css.item.CssProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Html.item.HtmlProjectCodeItem;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.Struct.HtmlProjectCode;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.impl.DefaultHtmlCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.CodeGenerator.impl.InlineStyleHtmlCodeGenerator;
import com.SelfBuildApp.ddd.Project.domain.HtmlProject;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
import com.SelfBuildApp.infrastructure.User.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
public class WebSocketController {

    @Autowired
    private HtmlProjectRepository projectRepository;

    @Autowired
    private DefaultHtmlCodeGenerator htmlCodeGenerator;

    @Autowired
    private InlineStyleHtmlCodeGenerator inlineHtmlCodeGenerator;

    @Autowired
    private AdvanceCssStyleCodeGenerator cssStyleCodeGenerator;

    @Autowired
    private ForInlineCssStyleCodeGenerator forInlineCssStyleCodeGenerator;

    @Autowired
    protected ObjectMapper objectMapper;

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

    @MessageMapping("/update/html-code")
//    @SendTo("/topic/greetings")
    @SendToUser("/queue/position-updates")
    public HtmlProjectCode greeting(String json, String type) throws Exception {
//        Thread.sleep(1000); // simulated delay

        Map<String, String> map
                = objectMapper.readValue(json, new TypeReference<Map<String,String>>(){});
        String projectUuid = map.get("projectId");
        String typeCode = map.get("type");
        System.out.println("websocket");
        System.out.println(projectUuid);
        System.out.println(typeCode);

        HtmlProject entity = Optional.ofNullable(projectRepository.load(projectUuid))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        HtmlProjectCodeItem generateHtmlProject;
        String generateHtml = "";
        String generateCss = "";

        if (typeCode.equals("normal")) {
//            System.out.println(typeCode);

            generateHtmlProject = (HtmlProjectCodeItem)htmlCodeGenerator.generate(entity);

            cssStyleCodeGenerator.setTagsCodeItem(generateHtmlProject.getChildren());

            generateCss = cssStyleCodeGenerator.generate(entity).getContent();
            generateHtml = generateHtmlProject.getContent();


        } else if (typeCode.equals("inline-css")) {
            generateHtmlProject = (HtmlProjectCodeItem)inlineHtmlCodeGenerator.generate(entity);

            forInlineCssStyleCodeGenerator.setTagsCodeItem(generateHtmlProject.getChildren());

            generateCss = forInlineCssStyleCodeGenerator.generate(entity).getContent();
            generateHtmlProject.setHeadStyles(generateCss);
            generateHtml = generateHtmlProject.getContent();

            generateCss = "";

        }


        return new HtmlProjectCode(generateHtml,
                generateCss);
//
//        return new HtmlProjectCode(map.get("projectID"),
//                type);


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


