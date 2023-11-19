package blogservices.blogdevbackend.global.common;

import blogservices.blogdevbackend.global.exception.GlobalException;
import blogservices.blogdevbackend.global.exception.GlobalExceptionResponseCode;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
// S3 업로드 설정
public class S3Utils {
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String TIME_SEPARATOR = "_";
    // private static final String CATEGORY_PREFIX = "/";

    public static String buildFileName(MultipartFile originalFileName) {

        String fileName = originalFileName.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(originalFileName.getSize());
        metadata.setContentType(originalFileName.getContentType());

        String fileType = originalFileName.getContentType();
        String originalFileExtension;

        if (ObjectUtils.isEmpty(fileType)) {
            return null;
        } else if (fileType.contains("image/jpeg")) {
            originalFileExtension = ".jpg";
        } else if (fileType.contains("image/gif")) {
            originalFileExtension = ".png";
        } else if (fileType.contains("image/png"))
            originalFileExtension = ".gif";
        else {
            throw new GlobalException(GlobalExceptionResponseCode.FILE_UPLOAD_TYPE_ERROR);
        }

        String now = String.valueOf(System.currentTimeMillis());

        return now + FILE_EXTENSION_SEPARATOR + fileName + originalFileExtension;
    }
}
