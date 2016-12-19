package com.pxene.pap.domain.model.basic.view;

public class ImageSizeTypeModel {
    private String id;

    private String name;

    private String path;

    private String typeId;

    private String sizeId;

    private Float volume;

    private String typeName;

    private String code;

    private String sizeName;

    private Integer width;

    private Integer height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId == null ? null : sizeId.trim();
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName == null ? null : sizeName.trim();
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", path=").append(path);
        sb.append(", typeId=").append(typeId);
        sb.append(", sizeId=").append(sizeId);
        sb.append(", volume=").append(volume);
        sb.append(", typeName=").append(typeName);
        sb.append(", code=").append(code);
        sb.append(", sizeName=").append(sizeName);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append("]");
        return sb.toString();
    }
}