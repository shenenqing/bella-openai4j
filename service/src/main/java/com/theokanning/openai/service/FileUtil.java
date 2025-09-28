package com.theokanning.openai.service;

import okhttp3.MediaType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiangTao
 * @date 2024年05月08 17:29
 **/
public class FileUtil {
    private static final Map<String, String> MIME_TO_EXTENSION = new HashMap<>();
    private static final Map<String, String> EXTENSION_TO_MIME = new HashMap<>();

    static {
        // 初始化MIME类型到文件扩展名的映射
        MIME_TO_EXTENSION.put("text/html", "html");
        MIME_TO_EXTENSION.put("text/css", "css");
        MIME_TO_EXTENSION.put("text/javascript", "js");
        MIME_TO_EXTENSION.put("image/jpg", "jpg");
        MIME_TO_EXTENSION.put("image/jpeg", "jpg");
        MIME_TO_EXTENSION.put("image/png", "png");
        MIME_TO_EXTENSION.put("image/gif", "gif");
        MIME_TO_EXTENSION.put("image/webp", "webp");
        MIME_TO_EXTENSION.put("image/svg+xml", "svg");
        MIME_TO_EXTENSION.put("application/pdf", "pdf");
        MIME_TO_EXTENSION.put("application/zip", "zip");
        MIME_TO_EXTENSION.put("audio/mpeg", "mp3");
        MIME_TO_EXTENSION.put("audio/mp3", "mp3");
        MIME_TO_EXTENSION.put("audio/mp4", "m4a");
        MIME_TO_EXTENSION.put("audio/wav", "wav");
        MIME_TO_EXTENSION.put("audio/wave", "wav");
        MIME_TO_EXTENSION.put("audio/webm", "webm");
        MIME_TO_EXTENSION.put("audio/amr", "amr");
        MIME_TO_EXTENSION.put("video/mp4", "mp4");
        MIME_TO_EXTENSION.put("video/quicktime", "mov");
        MIME_TO_EXTENSION.put("video/mpeg", "mpeg");

        // Microsoft Word文档
        MIME_TO_EXTENSION.put("application/msword", "doc");
        MIME_TO_EXTENSION.put("application/json", "json");
        MIME_TO_EXTENSION.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");

        // Microsoft Excel电子表格
        MIME_TO_EXTENSION.put("application/vnd.ms-excel", "xls");
        MIME_TO_EXTENSION.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");

        MIME_TO_EXTENSION.put("application/vnd.ms-powerpoint", "ppt");
        MIME_TO_EXTENSION.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");

        MIME_TO_EXTENSION.put("text/plain", "txt");
        MIME_TO_EXTENSION.put("text/markdown", "md");
        MIME_TO_EXTENSION.put("text/csv", "csv");

        MIME_TO_EXTENSION.put("application/xml", "xml");
        MIME_TO_EXTENSION.put("application/epub+zip", "epub");
        MIME_TO_EXTENSION.put("message/rfc822", "eml");
        MIME_TO_EXTENSION.put("application/vnd.ms-outlook", "msg");

        // 编程语言相关的MIME类型
        MIME_TO_EXTENSION.put("text/x-c", "c");
        MIME_TO_EXTENSION.put("text/x-csharp", "cs");
        MIME_TO_EXTENSION.put("text/x-c++", "cpp");
        MIME_TO_EXTENSION.put("text/x-java", "java");
        MIME_TO_EXTENSION.put("text/x-php", "php");
        MIME_TO_EXTENSION.put("text/x-python", "py");
        MIME_TO_EXTENSION.put("text/x-ruby", "rb");
        MIME_TO_EXTENSION.put("text/x-tex", "tex");
        MIME_TO_EXTENSION.put("application/x-sh", "sh");
        MIME_TO_EXTENSION.put("application/typescript", "ts");
        // 可以根据需要添加更多的MIME类型

        EXTENSION_TO_MIME.put("html", "text/html");
        EXTENSION_TO_MIME.put("css", "text/css");
        EXTENSION_TO_MIME.put("js", "text/javascript");
        EXTENSION_TO_MIME.put("jpg", "image/jpg");
        EXTENSION_TO_MIME.put("jpeg", "image/jpeg");
        EXTENSION_TO_MIME.put("png", "image/png");
        EXTENSION_TO_MIME.put("gif", "image/gif");
        EXTENSION_TO_MIME.put("webp", "image/webp");
        EXTENSION_TO_MIME.put("svg", "image/svg+xml");
        EXTENSION_TO_MIME.put("pdf", "application/pdf");
        EXTENSION_TO_MIME.put("zip", "application/zip");
        EXTENSION_TO_MIME.put("mp3", "audio/mpeg");
        EXTENSION_TO_MIME.put("m4a", "audio/mp4");
        EXTENSION_TO_MIME.put("wav", "audio/wav");
        EXTENSION_TO_MIME.put("webm", "audio/webm");
        EXTENSION_TO_MIME.put("amr", "audio/amr");
        EXTENSION_TO_MIME.put("mp4", "video/mp4");
        EXTENSION_TO_MIME.put("mov", "video/quicktime");
        EXTENSION_TO_MIME.put("mpeg", "video/mpeg");
        EXTENSION_TO_MIME.put("c", "text/x-c");
        EXTENSION_TO_MIME.put("cs", "text/x-csharp");
        EXTENSION_TO_MIME.put("cpp", "text/x-c++");
        EXTENSION_TO_MIME.put("java", "text/x-java");
        EXTENSION_TO_MIME.put("php", "text/x-php");
        EXTENSION_TO_MIME.put("py", "text/x-python");
        EXTENSION_TO_MIME.put("rb", "text/x-ruby");
        EXTENSION_TO_MIME.put("tex", "text/x-tex");
        EXTENSION_TO_MIME.put("sh", "application/x-sh");
        EXTENSION_TO_MIME.put("ts", "application/typescript");
        EXTENSION_TO_MIME.put("doc", "application/msword");
        EXTENSION_TO_MIME.put("json", "application/json");
        EXTENSION_TO_MIME.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        EXTENSION_TO_MIME.put("xls", "application/vnd.ms-excel");
        EXTENSION_TO_MIME.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        EXTENSION_TO_MIME.put("ppt", "application/vnd.ms-powerpoint");
        EXTENSION_TO_MIME.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        EXTENSION_TO_MIME.put("txt", "text/plain");
        EXTENSION_TO_MIME.put("md", "text/markdown");
        EXTENSION_TO_MIME.put("csv", "text/csv");
        EXTENSION_TO_MIME.put("xml", "application/xml");
        EXTENSION_TO_MIME.put("epub", "application/epub+zip");
        EXTENSION_TO_MIME.put("eml", "message/rfc822");
        EXTENSION_TO_MIME.put("msg", "application/vnd.ms-outlook");


    }

    public static MediaType getFileUploadMediaType(String fileName) {
        return MediaType.parse(EXTENSION_TO_MIME.getOrDefault(getFileExtension(fileName), "text/plain"));
    }

    public static String getSubType(MediaType mediaType) {
        if(mediaType == null) {
            return null;
        }
        return mediaType.subtype();
    }

    public static String extraPureMediaType(MediaType mediaType) {
        return mediaType.type() + "/" + mediaType.subtype();
    }

    public static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);  // Includes the dot
        }
        return "";  // No extension found
    }

    /**
     * Helper method to read all bytes from an InputStream
     *
     * @param inputStream the InputStream to read from
     * @return a byte array containing all the bytes read from the InputStream
     */
    public static byte[] readAllBytes(InputStream inputStream) {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[8192];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error reading from InputStream", e);
        }
    }


}

