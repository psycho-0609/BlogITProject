package com.finnal.blogit.dto.response;

import com.finnal.blogit.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUserDetailDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String thumbnail;

    public String getThumbnail() {
//        return thumbnail == null || Strings.isEmpty(thumbnail) ? "/img/defaultUserImg.jpg": Constant.FIREBASE_URL + Constant.BUCKET_NAME + "/avatarUser/" + id +"/" + thumbnail;
        return thumbnail == null || Strings.isEmpty(thumbnail) ? "/img/defaultUserImg.jpg":"/imgUser/"+id+"/"+thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
