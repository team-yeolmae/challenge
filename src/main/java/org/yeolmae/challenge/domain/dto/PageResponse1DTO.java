package org.yeolmae.challenge.domain.dto;

import lombok.*;

import java.util.List;
@Getter
@ToString
public class PageResponse1DTO<E> {

    private int page;
    private int size;
    private int total;

    //시작 페이지 번호
    private int start;
    //끝 페이지 번호
    private int end;

    //이전 페이지의 존재 여부
    private boolean prev;
    //다음 페이지의 존재 여부
    private boolean next;

    private List<E> challengeList;

    @Builder(builderMethodName = "withAll")
    public PageResponse1DTO(PageRequest1DTO pageRequest1DTO, List<E> challengeList, int total){

        if(total <= 0){
            return;
        }

        this.page = pageRequest1DTO.getPage();
        this.size = pageRequest1DTO.getSize();

        this.total = total;
        this.challengeList = challengeList;


    }
}