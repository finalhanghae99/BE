package com.product.application.chatting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseChatListDto {
    private List<ResponseChattingDto> responseChattingDtoList;
}
