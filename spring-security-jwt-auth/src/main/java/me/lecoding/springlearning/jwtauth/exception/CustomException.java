package me.lecoding.springlearning.jwtauth.exception;

public class CustomException extends AbstractException {

	private ErrorInfoCode errorInfoCode;
	
	private String customStr = "custom exception";

	public CustomException(String message) {
		super();
		this.customStr = message;
	}
	
	public CustomException(ErrorInfoCode errorInfoCode) {
		super();
		this.errorInfoCode = errorInfoCode;
	}
	
	public CustomException(int code) {
		super();
		this.errorInfoCode = ErrorInfoCode.getErrorInfoCodeByCode(code);
	}

	public String getMsg() {
		if(errorInfoCode != null){
			return this.errorInfoCode.getMsg();
		}
		return customStr;
	}

	public ErrorInfoCode getErrorInfoCode() {
		return errorInfoCode;
	}

	@Override
	public int getCode() {
		if(errorInfoCode != null){
			return this.errorInfoCode.getCode();
		}
		return ErrorInfoCode.UNKNOWN_ERROR.getCode();
	}
}
