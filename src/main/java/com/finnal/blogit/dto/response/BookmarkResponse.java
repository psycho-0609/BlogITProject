package com.finnal.blogit.dto.response;


public class BookmarkResponse extends FavReadResponse{
    private Long totalBookMark;
    public BookmarkResponse(Long id, Integer status) {
        super(id, status);
    }

    public Long getTotalBookMark() {
        return totalBookMark;
    }

    public void setTotalBookMark(Long totalBookMark) {
        this.totalBookMark = totalBookMark;
    }

    public BookmarkResponse(Long id, Integer status, Long totalBookMark) {
        super(id, status);
        this.totalBookMark = totalBookMark;
    }
}
