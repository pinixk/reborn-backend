package com.reborn.reborn.service;

import com.reborn.reborn.dto.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LocalFileService implements FileService {

    @Value("${file.upload.directory}")
    private String directory;

    @Override
    public List<FileDto> uploadFile(List<MultipartFile> multipartFile) {
        List<FileDto> files = new ArrayList<>();
        multipartFile.forEach(file -> {
            String originFileName = file.getOriginalFilename();
            String uploadFileName = createUploadFileName(originFileName);
            String fullPath = getFullPath(uploadFileName);
            try {
                file.transferTo(new File(fullPath));
            } catch (IOException e) {
                //TODO 예외처리 해야함
                throw new RuntimeException(e);
            }
            files.add(new FileDto(originFileName, uploadFileName));
        });
        return files;
    }

    @Override
    public boolean deleteFile(String uploadFilename) {
        //TODO 해당 파일을 지우지 못했을 경우, 1. 해당 파일 이름 없음. 2. 그냥 시스템 상으로 실패,  
        File file = new File(getFullPath(uploadFilename));
        return file.delete();
    }

    @Override
    public String getFullPath(String uploadFileName) {
        return directory + uploadFileName;
    }

    @Override
    public Resource downloadFile(String filename) {
        try {
            return new UrlResource("file:" + getFullPath(filename));
        } catch (Exception e) {
            //TODO 예외처리
            throw new RuntimeException("aa");
        }
    }
}
