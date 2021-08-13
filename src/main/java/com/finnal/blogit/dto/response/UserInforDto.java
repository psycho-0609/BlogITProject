package com.finnal.blogit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInforDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String thumbnail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userDetailId;

    public String getThumbnail() {
        return thumbnail == null ? "/img/defaultUserImg.jpg":"/imgUser/"+userDetailId+"/"+thumbnail;
    }
}
