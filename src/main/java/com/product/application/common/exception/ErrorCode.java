package com.product.application.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER("파라미터 값을 확인해주세요.", 400),
    INVALID_USERNAME_PATTERN("id는 소문자와 숫자 조합 4자리에서 10자리입니다.",400),
    INVALID_PASSWORD_PATTERN("비밀번호는 소문자, 대문자, 숫자, 특수문자(!@#$%^&+=) 조합 8자리에서 15자리입니다.",400),
    DUPLICATE_USEREMAIL("중복된 이메일이 존재합니다.", 400),
    DUPLICATE_EMAIL("해당 이메일로 이미 가입된 회원입니다.", 400),
    DUPLICATE_NICKNAME("중복된 닉네임이 존재합니다.", 400),
    REQUIRED_ALL("모든 항목이 필수값입니다.",400),
    REQUIRED_AT_LEAST_ONE("적어도 하나의 항목이 필요합니다.", 400),
    SEARCH_REQUIREMENT_ERROR("검색 조건이 맞지 않습니다.", 400),
    WRONG_IMAGE_FORMAT("파일을 확인해주세요.", 400),
    PASSWORD_MISMATCH("비밀번호가 비밀번호 확인과 일치하지 않습니다",400),
    DUPLICATE_MEMBERNAME("이미 추가한 유저입니다.", 400),
    IMAGE_UPLOAD_ERROR("이미지 업로드를 실패했습니다.", 400),
    WRONG_INPUT_IMAGE("이미지 파일이 아닙니다.", 400),


    CONTENT_NOT_FOUND("존재하지 않는 게시글 입니다.",404),
    RESERVATION_NOT_FOUND("존재하지 않는 양도글 입니다.",404),
    CAMPING_NOT_FOUND("해당 캠핑장 정보가 존재하지 않습니다.",404),
    REVIEW_NOT_FOUND("존재하지 않는 리뷰 입니다.", 404),
    USERNAME_NOT_FOUND("존재하지 않는 아이디 입니다.",404),
    USEREMAIL_NOT_FOUND("존재하지 않은 이메일 입니다.", 404),
    INCORRECT_PASSWORD("잘못된 비밀번호입니다.",404),
    CANNOT_FOUND_CHATROOM("채팅방을 찾지 못했습니다.", 404),
    CANNOT_MAKE_ROOM_ALONE("내 양도글에서는 채팅방 생성이 불가능합니다.", 404),
    AUTHORIZATION_DELETE_FAIL("삭제 권한이 없습니다.", 401),
    AUTHORIZATION_UPDATE_FAIL("수정 권한이 없습니다.", 401),
    AUTHORIZATION_CREATE_FAIL("생성 권한이 없습니다.", 401),
    AUTHORIZATION_READ_FAIL("읽기 권한이 없습니다.", 401),

    //필터단 에러
    FORBIDDEN_ERROR("서버 사용 권한이 없습니다.",403),
    TOKEN_ERROR("토큰이 유효하지 않습니다.",401),
    TOKEN_NOT_FOUND("토큰이 존재하지 않습니다. 로그인이 필요합니다.",401),
    USER_NOT_FOUND("존재하지 않는 유저 입니다.",404),

    INTERNAL_SERVER_ERROR("서버 에러입니다. 서버 팀에 연락주세요!", 500),

    //jwt
    DO_NOT_HAVE_PERMISSION_ERROR_MSG("사용 권한이 없습니다.", 403);




    private final String msg;
    private final int statusCode;
}
