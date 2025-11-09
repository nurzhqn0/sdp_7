package models;

import java.util.*;

public class Document {
    private String fileName;
    private String format;
    private String content;
    private Map<String, String> metadata;
    private String watermark;

    public Document(String fileName, String format) {
        this.fileName = fileName;
        this.format = format;
        this.content = "";
        this.metadata = new HashMap<>();
        this.watermark = null;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setMetadata(Map<String, String> metadata){
        this.metadata = metadata;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFormat() {
        return format;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }

    public Map<String, String> getMetadata() {
        return new HashMap<>(metadata);
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    public String getWatermark() {
        return watermark;
    }

    @Override
    public String toString() {
        return String.format("models.Document[name=%s, format=%s, metadata=%d]",
                fileName, format, metadata.size());
    }
}
