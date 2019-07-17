/**
 * 
 */
package com.leo.security.core.validate.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.leo.security.core.properties.SecurityConstants;

/**
 * @author zhailiang
 *
 */
@RestController
public class ValidateCodeController {

	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;

	/**
	 * 验证码放入session时的前缀
	 */
	//private String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	//private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/**
	 * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
	 * 
	 * @param request
	 * @param response
	 * @param type
	 * @throws Exception
	 */
	@GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
	// @GetMapping("/code/image")
	public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
			// public void createCode(HttpServletRequest request, HttpServletResponse
			// response)
			throws Exception {
		validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
		// ImageCode imageCode = createImageCode(new ServletWebRequest(request,
		// response));
		// sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,
		// imageCode);
		// ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}

	// private ImageCode createImageCode(ServletWebRequest request) {
	// SecurityProperties securityProperties = new SecurityProperties();
	// ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
	// imageCodeGenerator.setSecurityProperties(securityProperties);
	// return imageCodeGenerator.generate(request);
	// }

}
