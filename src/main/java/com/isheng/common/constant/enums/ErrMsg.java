package com.isheng.common.constant.enums;


public enum ErrMsg {
	
	UNKNOW_CODE("-9999", "未知的错误码"), //
	
	FAILED("-1", "操作失败"), //
	SUCCESS("0", "操作成功"), //
	
	WEB_200("200", "请求成功"), //
	WEB_300("300", "重定向"), //
	WEB_400("400", "请求无法解析"), //
	WEB_401("400", "未经授权"), //
	WEB_403("400", "访问被拒绝"), //
	WEB_404("400", "找不到文件或目录"), //
	WEB_500("500", "服务器内部错误"), //
	
	LOGIN_NAME_NULL("-1000", "账号不能为空"), //
	LOGIN_PWD_NULL("-1001", "登录密码不能为空"), //
	LOGIN_NAME_NOT_EXIST("-1002", "账户名或手机号不存在"), //
	LOGIN_ERR("-1002", "账号或密码错误"), //
	LOGIN_EXP("-1003", "账户状态异常"), //
	
	EXP_SYS("-2000", "系统异常"), //
	EXP_ADD("-2001", "新增异常"), //
	EXP_DEL("-2002", "删除异常"), //
	EXP_UP("-2003", "更新异常"), //
	EXP_QUERY("-2004", "查询异常"), //
	
	PARAM_NULL("-3000", "参数为空"), //
	PARAM_ID_NULL("-3001", "ID为空"), //
	PARAM_ERR("-3002", "参数错误"), //
	PARAM_MISS("-3003", "参数不全"), //
	PARAM_REPET("-3004", "重复添加"), //
	
	RESP_NULL("-4000", "返回数据为空"), //
	RESP_ERR("-4001", "返回数据错误"); //
	
	private String code;
	
	private String msg;
	
	private ErrMsg(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	/**
	 * 根据code得到对应的枚举
	 * 
	 * @param code
	 * @return
	 */
	public ErrMsg getEnum(String code) {
		ErrMsg m = ErrMsg.UNKNOW_CODE;
		if (null != code) {
			for (ErrMsg e : values()) {
				if (code.equals(e.getCode())) {
					m = e;
					break;
				}
			}
		}
		return m;
	}

	
	public String getCode() {
		return this.code;
	}

	
	public String getMsg() {
		return this.msg;
	}
	
}
