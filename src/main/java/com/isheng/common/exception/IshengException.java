
package com.isheng.common.exception;

/**
 * 通用异常类
 * @author isheng92
 * @date 2019年11月25日 下午10:38:04
 */
public class IshengException extends RuntimeException {

	private static final long serialVersionUID = 2904660424864747918L;
	
	private String code;
	
	public IshengException() {
		super();
	}
	
	public IshengException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public IshengException(String message) {
		super(message);
	}
	
	public IshengException(Throwable throwable) {
		super(throwable);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
