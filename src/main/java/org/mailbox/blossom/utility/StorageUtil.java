package org.mailbox.blossom.utility;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mailbox.blossom.config.NcpConfig;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageUtil {
    private final NcpConfig ncpConfig;

    public String uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = UUID.randomUUID().toString();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            ncpConfig.amazonS3().putObject(ncpConfig.getBucket(), fileName, multipartFile.getInputStream(), objectMetadata);

            return ncpConfig + "/" + fileName;
        } catch (SdkClientException | IOException e) {
            log.error("uploadFile error", e);
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            ncpConfig.amazonS3().deleteObject(ncpConfig.getBucket(), fileName);
        } catch (SdkClientException e) {
            log.error("uploadFile error", e);
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
    }

}
