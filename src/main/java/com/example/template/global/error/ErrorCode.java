package com.example.template.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCode Convention
 * - 도메인 별로 나누어 관리
 * - [주체_이유] 형태로 생성
 * - 코드는 도메인명 앞에서부터 1~2글자로 사용
 * - 메시지는 "~~다."로 마무리
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Global
    INTERNAL_SERVER_ERROR(500, "G001", "내부 서버 오류입니다."),
    METHOD_NOT_ALLOWED(405, "G002", "허용되지 않은 HTTP method입니다."),
    INPUT_VALUE_INVALID(400, "G003", "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "G004", "입력 타입이 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE(400, "G005", "request message body가 없거나, 값 타입이 올바르지 않습니다."),
    HTTP_HEADER_INVALID(400, "G006", "request header가 유효하지 않습니다."),
    IMAGE_TYPE_NOT_SUPPORTED(400, "G007", "지원하지 않는 이미지 타입입니다."),
    FILE_CONVERT_FAIL(500, "G008", "변환할 수 없는 파일입니다."),
    ENTITY_TYPE_INVALID(500, "G009", "올바르지 않은 entity type 입니다."),
    FILTER_MUST_RESPOND(500, "G010", "필터에서 처리해야 할 요청이 Controller에 접근하였습니다."),

    // Member
    MEMBER_NOT_FOUND(400, "M001", "존재 하지 않는 유저입니다."),
    MEMBER_ALREADY_EXIST(400, "M002", "이미 등록된 유저입니다."),

    // MemberGroup
    MEMBER_GROUP_NOT_FOUND(400, "MG001", "사용자의 소속을 찾을 수 없습니다."),

    // Resource
    RESOURCE_NOT_FOUND(400, "R001", "존재하지 않는 발전소입니다."),

    // ResourceGroup
    RESOURCE_GROUP_NOT_FOUND(400, "RG001", "존재하지 않는 집합자원입니다."),

    // Jwt
    JWT_INVALID(401, "J001", "유효하지 않은 토큰입니다."),
    JWT_EXPIRED(401, "J002", "만료된 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(401, "J003", "만료된 REFRESH 토큰입니다. 재로그인 해주십시오.")
    ;

    private final int status;
    private final String code;
    private final String message;
}
