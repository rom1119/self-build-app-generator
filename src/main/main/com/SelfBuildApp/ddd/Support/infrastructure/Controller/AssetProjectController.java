package com.SelfBuildApp.ddd.Support.infrastructure.Controller;

import com.SelfBuildApp.Storage.PathFileManager;
import com.SelfBuildApp.cqrs.command.impl.StandardGate;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateAssetCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.CreateFontFaceCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateAssetCommand;
import com.SelfBuildApp.ddd.Project.Application.commands.UpdateFontFaceCommand;
import com.SelfBuildApp.ddd.Project.domain.*;
import com.SelfBuildApp.ddd.Support.infrastructure.PropertyAccess;
import com.SelfBuildApp.ddd.Support.infrastructure.repository.AssetProjectRepository;
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
@RequestMapping("/api/assets")
public class AssetProjectController {

    @Autowired
    private AssetProjectRepository repository;

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

    @GetMapping("/{id}")
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public List<FontFace> getAllForProject(@PathVariable String id, Authentication auth) {
        List<FontFace> list = repository.findAllForProjectId(id);

        return list;
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity create(
                             @RequestBody @Validated() AssetProject assetProject,
                             BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
//        MultipartFile file = assetProject.getFile();
//        AssetProject entity = Optional.ofNullable(this.entityManager.find(AssetProject.class, id))
//                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        CreateAssetCommand command = new CreateAssetCommand( assetProject);
        AssetProject res = (AssetProject) gate.dispatch(command);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable String id,
                                     @RequestBody @Validated() AssetProject assetProject,
                                     BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid data", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        MultipartFile file = assetProject.getFile();
        AssetProject entity = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        UpdateAssetCommand command = new UpdateAssetCommand( assetProject);
        AssetProject res = (AssetProject) gate.dispatch(command);

        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @JsonView(PropertyAccess.HtmlTagDetails.class)
    public ResponseEntity delete(@PathVariable String id)
    {
        Optional<AssetProject> entity = Optional.ofNullable(repository.findById(Long.valueOf(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.get().setPathFileManager(pathFileManager);

        this.entityManager.remove(entity);

        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}/resource")
    @Transactional
    public ResponseEntity deleteResource(@PathVariable String id)
    {
        AssetProject entity = Optional.ofNullable(this.entityManager.find(AssetProject.class, id))
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        entity.setPathFileManager(pathFileManager);
        entity.deleteResource();

        return ResponseEntity.ok("ok");
    }


}


