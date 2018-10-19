package me.lecoding.springlearning.redis.exception;

/**
 * errorcode
 */
public enum ErrorInfoCode {
	PARAMS_ERROR (1001, "params error"),
	PASSWORD_ERROR(1002, "username or password error"),
	TOKEN_CHECK_ERROR (1003, "token check error"),
    NEED_AUTHENTICATION(1004, "need login first"),
	UNKNOWN_ERROR(99999, "unknown error"),
	;

	private int code;
	
	private String msg;
	
	private ErrorInfoCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static ErrorInfoCode getErrorInfoCodeByCode(int code) {
		for(ErrorInfoCode errorInfoCode : ErrorInfoCode.values()){
			if(errorInfoCode.getCode() == code){
				return errorInfoCode;
			}
		}
		return UNKNOWN_ERROR;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
