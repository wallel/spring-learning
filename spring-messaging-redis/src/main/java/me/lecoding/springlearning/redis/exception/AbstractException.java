package me.lecoding.springlearning.redis.exception;

public abstract class AbstractException extends RuntimeException {
	protected int errorCode;
	protected abstract int getCode();

	public AbstractException() {
		super();
	}

	public AbstractException(String msg) {
		super(msg);
	}
}
