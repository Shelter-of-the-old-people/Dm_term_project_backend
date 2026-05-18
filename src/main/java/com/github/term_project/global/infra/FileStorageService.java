package com.github.term_project.global.infra;

import com.github.term_project.global.config.AppProperties;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final AppProperties appProperties;

    @PostConstruct
    void init() throws IOException {
        Files.createDirectories(profileDir());
    }

    public String storeProfileImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "업로드할 파일이 비어있습니다.");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_FILE_TYPE);
        }

        String original = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
        String ext = "";
        int dot = original.lastIndexOf('.');
        if (dot >= 0) {
            ext = original.substring(dot);
        }
        String filename = UUID.randomUUID() + ext;

        try {
            Path target = profileDir().resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            String publicPath = appProperties.upload().publicUrlPrefix()
                    + "/" + appProperties.upload().profileSubdir()
                    + "/" + filename;
            log.info("Stored profile image: {} -> {}", original, target);
            return publicPath;
        } catch (IOException e) {
            log.error("Failed to store profile image", e);
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    private Path profileDir() {
        return Paths.get(appProperties.upload().rootDir(), appProperties.upload().profileSubdir())
                .toAbsolutePath().normalize();
    }
}
