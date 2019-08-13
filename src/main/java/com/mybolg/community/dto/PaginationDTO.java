package com.mybolg.community.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(long total, Integer page, Integer size) {
        Integer totalPage;
        int totalCount=(int)total;
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }
        this.totalPage=totalPage;
        if(page<1){
            page=1;
        }else if(page>totalPage){
            page=totalPage;
        }
        this.page=page;

        pages.add(page);
        //添加当前页两边的页数
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page + i);
            }
        }
        //是否展示上一页
        if(page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        //是否展示下一页
        if(page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }

        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }

        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
