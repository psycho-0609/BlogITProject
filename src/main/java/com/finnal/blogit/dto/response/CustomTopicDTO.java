package com.finnal.blogit.dto.response;


public class CustomTopicDTO extends TopicResponse{

    private String shortDescription;
    public CustomTopicDTO(Integer id, String name, String shortDescription) {
        super(id, name);
        this.shortDescription = shortDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
