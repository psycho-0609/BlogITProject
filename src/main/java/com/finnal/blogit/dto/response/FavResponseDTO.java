package com.finnal.blogit.dto.response;

public class FavResponseDTO extends FavReadResponse{
    private Long countFav;
    public FavResponseDTO(Long id, Integer status,Long countFav) {
        super(id, status);
        this.countFav = countFav;
    }

    public Long getCountFav() {
        return countFav;
    }

    public void setCountFav(Long countFav) {
        this.countFav = countFav;
    }
}
