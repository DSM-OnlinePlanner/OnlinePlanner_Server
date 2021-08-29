package online.planner.online_planner.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_BAD_REQUEST("email not send", 400),
    LOGIN_FAILED("login failed", 401),
    IS_NOT_REFRESH_TOKEN("is not refresh token", 403),
    INVALID_TOKEN("invalid token", 403),
    USER_NOT_FOUND("user not found", 404),
    REFRESH_TOKEN_NOT_FOUND("refresh token not found", 404),
    USER_LEVEL_NOT_FOUND("user level not found", 404),
    GOAL_NOT_FOUND("goal not found", 404),
    AUTH_CODE_NOT_FOUND("auth code not found", 404),
    MEMO_NOT_FOUND("memo not found", 404),
    NOTICE_NOT_FOUND("notice not found", 404),
    PLANNER_NOT_FOUND("planner not found", 404),
    ROUTINE_NOT_FOUND("routine not found", 404),
    DEVICE_TOKEN_NOT_FOUND("device token not found", 404),
    ACHIEVEMENT_NOT_FOUND("achievement not found", 404),
    NOT_SEND_EMAIL("user email not found", 404),
    FAILED_PLANNER("planner already failed", 409),
    SUCCEED_PLANNER("planner already succeed", 409),
    FAILED_ROUTINE("routine already failed", 409),
    SUCCEED_ROUTINE("routine already succeed", 409),
    ALREADY_USER_SIGNED("already user signed", 403),
    ALREADY_USER_MAIL_SEND("mail already send", 403),
    AUTH_CODE_FAILED("Auth Code auth Failed", 403),
    CONVERT_FAILED("convert failed", 500),
    DECODING_FAILED("aes256 decode failed", 500);

    private String message;
    private int status;
}
