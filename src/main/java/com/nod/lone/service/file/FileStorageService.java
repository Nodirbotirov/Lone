package com.nod.lone.service.file;

import com.nod.lone.model.FileStorage;
import com.nod.lone.payload.AllApiResponse;
import com.nod.lone.repository.FileStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    @Value("${file.upload.url}")
    private String filePath;


    public FileStorage save(MultipartFile multipartFile) {
        try {
            FileStorage fileStorage = new FileStorage();
            fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
            fileStorage.setFileSize(multipartFile.getSize());
            if (Objects.requireNonNull(multipartFile.getContentType()).toLowerCase().startsWith("image")){
                fileStorage.setContentType(multipartFile.getContentType());
            }else throw  new IllegalArgumentException(
                    "File type is not containing image! "
            );
            String name = makeName(multipartFile.getOriginalFilename());
            name = name.replaceAll(" ", "_");
            File file = new File(filePath);
            if (!file.exists()){
                file.mkdirs();
            }
            file = new File(String.format("%s/%s", filePath, name));
            if (!file.exists()){
                file.createNewFile();
            }
            multipartFile.transferTo(file);
            fileStorage.setName(name);
            FileStorage save = fileStorageRepository.save(fileStorage);
            return save;
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException(
                    "Error save file!"
            );
        }

    }

    public void deleteFile(Long id) {
        try {
            Optional<FileStorage> byId = fileStorageRepository.findById(id);
            if (byId.isPresent()){
                FileStorage fileStorage = byId.get();
                File file=new File(String.format("%s/%s", filePath, fileStorage.getName()));
                if (file.exists()){
                    file.delete();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Error delete file");
        }
    }

    public ResponseEntity<?> getFile(String name) {
        try {
            Optional<FileStorage> byName = fileStorageRepository.findByName(name);
            if (byName.isPresent()){
                FileStorage fileStorage = byName.get();
                Path path = Paths.get(String.format("%s/%s", filePath, fileStorage.getName()));
                Resource resourse = new UrlResource(path.toUri());
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileStorage.getName()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filname-\"" + fileStorage.getName() + "\"")
                        .body(resourse);
            }else return AllApiResponse.response(404, 0, "File not found");
        }catch (Exception e){
            e.printStackTrace();
            return AllApiResponse.response(500, 0, "Error");
        }
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()){
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }return ext;
    }

    private String makeName(String originalName) {
        String name = "";
        if (originalName != null && originalName.contains(".")){
            name = originalName.replaceAll("\\.", new Date().getTime() + ".");
        }
        return name;
    }


}
