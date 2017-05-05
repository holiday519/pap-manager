package com.pxene.pap.domain.models;

public class LandpageCodeModel {
    private String id;

    private String landpageId;

    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLandpageId() {
        return landpageId;
    }

    public void setLandpageId(String landpageId) {
        this.landpageId = landpageId == null ? null : landpageId.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", landpageId=").append(landpageId);
        sb.append(", code=").append(code);
        sb.append("]");
        return sb.toString();
    }
}