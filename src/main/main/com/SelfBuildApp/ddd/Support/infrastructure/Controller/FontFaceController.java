package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.ddd.Project.Application.commands.*;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.FontFaceRepository;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.HtmlProjectRepository;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/font-face")
public class FontFaceController {

    @Autowired
    private FontFaceRepository repository;

    @Autowired
    private HtmlProjectRepository projectRepository;

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
    public List<FontFace> getAllForProject(@PathVariable String id, Authentication auth) {
        List<FontFace> list = repository.findAllForProjectId(id);

        return list;
    }

    @PostMapping("/project/{projectId}/append")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity create(@PathVariable String projectId,
                                 @RequestBody @Validated() FontFace fontFace,
                                 BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        Optional<HtmlProject> entity = Optional.ofNullable(Optional.ofNullable(projectRepository.load(projectId))
                .orElseThrow(() -> new ResourceNotFoundException("Not found")));
        fontFace.setProject(entity.get());
        CreateFontFaceCommand command = new CreateFontFaceCommand( fontFace);
        FontFace res = (FontFace) gate.dispatch(command);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity update(@PathVariable String id,
                                         @RequestBody @Validated() FontFace fontFace,
                                         BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        Optional<FontFace> entity = Optional.ofNullable(repository.findById(Long.valueOf(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        UpdateFontFaceCommand command = new UpdateFontFaceCommand( fontFace);
        FontFace res = (FontFace) gate.dispatch(command);

        return ResponseEntity.ok(res);
    }





    @DeleteMapping("/{id}")
    @Transactional
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity delete(@PathVariable String id)
    {
        Optional<FontFace> entity = Optional.ofNullable(repository.findById(Long.valueOf(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.get().setPathFileManager(pathFileManager);

        this.entityManager.remove(entity.get());

        return ResponseEntity.ok(entity.get());
    }

    @PostMapping("/{id}/resource/{assetId}")
    @Transactional
    public ResponseEntity updateResource(@PathVariable String id, @PathVariable String assetId,
                                         @ModelAttribute @Validated() DtoFontFile dtoFontFile,
                                         BindingResult bindingResult
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        MultipartFile file = dtoFontFile.getFile();
        FontFace entity = Optional.ofNullable(this.entityManager.find(FontFace.class, Long.valueOf(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        AssetProject entityAsset = Optional.ofNullable(this.entityManager.find(AssetProject.class, Long.valueOf(assetId)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        if (entityAsset.getFontFace() != entity) {
            throw new Exception("Can not update asset for without font face with id=" + id);
        }


        entityAsset.setPathFileManager(pathFileManager);
        entityAsset.saveResource(file);

        return ResponseEntity.ok(entityAsset);
    }

    @DeleteMapping("/{id}/resource/{assetId}")
    @Transactional
    public ResponseEntity deleteResource(@PathVariable String id, @PathVariable String assetId) throws Exception {
        FontFace entity = Optional.ofNullable(this.entityManager.find(FontFace.class, Long.valueOf(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        AssetProject entityAsset = Optional.ofNullable(this.entityManager.find(AssetProject.class, Long.valueOf(assetId)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        if (entityAsset.getFontFace() != entity) {
            throw new Exception("Can not update asset for without font face with id=" + id);
        }

        entityAsset.setPathFileManager(pathFileManager);
        entityAsset.deleteResource();

        return ResponseEntity.ok("ok");
    }


}


