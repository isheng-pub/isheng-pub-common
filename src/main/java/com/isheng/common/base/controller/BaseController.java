package com.isheng.common.base.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isheng.common.model.Response;

/**
 * 基础Controller
 * @author isheng92
 * @date 2019年11月24日 下午5:46:25
 */
public abstract class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected static final String KEY_MESSAGE = "message";
	
	@SuppressWarnings("rawtypes")
	protected static final ThreadLocal<Response> resultLocal = new ThreadLocal<>();
	
	protected static final ThreadLocal<Map<String, Object>> dataLocal = new ThreadLocal<>();
	
//	/**
//	 * 验证Bean实例
//	 */
//	@Resource
//	Validator validator;
//	
//	/**
//	 * 添加Model消息
//	 * @param message
//	 */
//	protected void addMessage(Model model, String... messages) {
//		StringBuilder sb = new StringBuilder();
//		for (String message : messages){
//			sb.append(message).append(messages.length>1?"<br/>" : "");
//		}
//		model.addAttribute(KEY_MESSAGE, sb.toString());
//	}
//	
//	/**
//	 * 添加Flash消息
//	 * @param redirectAttrs
//	 * @param messages
//	 */
//	protected void addMessage(RedirectAttributes redirectAttrs, String... messages) {
//		StringBuilder sb = new StringBuilder();
//		for (String message : messages) {
//			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
//		}
//		redirectAttrs.addFlashAttribute(KEY_MESSAGE, sb.toString());
//	}
//	
//	/**
//	 * 服务端参数有效性验证
//	 * @param object 验证的实体对象
//	 * @param groups 验证组
//	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
//	 */
//	protected boolean beanValidator(Model model, Object object, Class<?>...groups) {
//		try {
//			BeanValidator.validateWithException(validator, object, groups);
//		} catch (ConstraintViolationException e) {
//			List<String> list = BeanValidator.extractPropertyAndMessageAsList(e, ": ");
//			list.add(0, "数据验证失败：");
//			addMessage(model, list.toArray(new String[] {}));
//			return false;
//		}
//		return true;
//	}
//	
//	/**
//	 * 服务端参数有效性验证
//	 * @param object 验证的实体对象
//	 * @param groups 验证组
//	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
//	 */
//	protected boolean beanValidator(RedirectAttributes redirectAttrs, Object object, Class<?>...groups) {
//		try {
//			BeanValidator.validateWithException(validator, object, groups);
//		} catch (ConstraintViolationException e) {
//			List<String> list = BeanValidator.extractPropertyAndMessageAsList(e, ": ");
//			list.add(0, "数据验证失败");
//			addMessage(redirectAttrs, list.toArray(new String[] {}));
//			return false;
//		}
//		return true;
//	}
//	
//	/**
//	 * 服务端参数有效性验证
//	 * @param object 验证的实体对象
//	 * @param groups 验证组，不传入此参数时，同@Valid注解验证
//	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
//	 */
//	protected void beanValidator(Object object, Class<?>... groups) {
//		BeanValidator.validateWithException(validator, object, groups);
//	}
//	
//	/**
//	 * 客户端返回json字符串
//	 * @param response
//	 * @param object
//	 * @return
//	 */
//	protected String renderString(HttpServletResponse response, Object object) {
//		return renderString(response, JsonMapper.toJsonString(object), "application/json");
//	}
//	
//	/**
//	 * 客户端返回字符串
//	 * @param response
//	 * @param jsonString
//	 * @param type
//	 * @return
//	 */
//	protected String renderString(HttpServletResponse response, String string, String type) {
//		try {
//			response.reset();
//			response.setContentType(type);
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().print(string);
//			return null;
//		} catch (IOException e) {
//			return null;
//		}
//	}
//	
//	/**
//	 * 参数绑定异常
//	 * @return
//	 */
//	@ExceptionHandler({BindException.class, ConstraintViolationException.class})
//	public String bindException() {
//		return "error/400";
//	
//	
//	/**
//	 * 授权登录异常
//	 * @return
//	 */
//	@ExceptionHandler({AuthenticationException.class})
//	public String authenticationException() {
//		return "error/403";
//	}
//	
//	/**
//	 * 初始化数据绑定
//	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
//	 * 2. 将字段中Date类型转换为String类型
//	 */
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
//		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text));
//			}
//			
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return null != value ? value.toString() : "";
//			}
//		});
//		
//		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				setValue(DateUtil.parseDate(text));
//			}
////			@Override
////			public String getAsText() {
////				Object value = getValue();
////				return value != null ? DateUtils.formatDateTime((Date)value) : "";
////			}
//		});
//	}
//	

}
