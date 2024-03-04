package com.nod.lone.controller;

import com.nod.lone.service.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileStorageController {

    private final FileStorageService fileStorageService;

//        @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public HttpEntity<?> save(@RequestPart MultipartFile file) {
//        return ResponseEntity.ok(fileStorageService.save(file));
//    }

    @GetMapping("/get-file/{name}")
    public HttpEntity<?> getFile(@PathVariable String name){
        return fileStorageService.getFile(name);
    }

}
