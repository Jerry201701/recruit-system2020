package com.nsd.recruitment.controller;

import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.domain.SysUser;
import com.nsd.recruitment.security.JwtAuthenticatioToken;
import com.nsd.recruitment.service.CompanyInfoService;
import com.nsd.recruitment.service.SysDeptService;
import com.nsd.recruitment.service.SysUserService;
import com.nsd.recruitment.utils.PasswordUtils;
import com.nsd.recruitment.utils.SecurityUtils;
import com.nsd.recruitment.vo.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 登录控制器
 * @author Louis
 * @date Oct 29, 2018
 */
@RestController
public class SysLoginController {


	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private AuthenticationManager authenticationManager;


/*
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到验证码到 session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);	
		IOUtils.closeQuietly(out);
	}
*/
	/**
	 * 登录接口
	 */
	@PostMapping(value = "/login")
	public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
		String username = loginBean.getAccount();
		String password = loginBean.getPassword();

		///	String captcha = loginBean.getCaptcha();
		
		// 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
		/*
		Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(kaptcha == null){
			return HttpResult.error("验证码已失效");
		}
		if(!captcha.equals(kaptcha)){
			return HttpResult.error("验证码不正确");
		}
		 */
		
		// 用户信息
		SysUser user = sysUserService.findByName(username);


		// 账号不存在、密码错误
		if (user == null) {
			return HttpResult.error("账号不存在");
		}
		
		if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
			return HttpResult.error("密码不正确");
		}

		// 账号锁定
		if (user.getStatus() == 0) {
			return HttpResult.error("账号已被锁定,请联系管理员");
		}

		// 系统登录认证
		JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
		token.setUserId(user.getId());

		token.eraseCredentials();

		return HttpResult.ok(token);
	}

//
//	@PostMapping(value = "/register")
//	public HttpResult register(@RequestBody RegisterBean registerBean){
//		if (sysUserService.findByMobilAndType(registerBean.getName(),registerBean.getRegisterType())!=null){
//			return HttpResult.error("用户名已存在!");
//		}
////		if(sysUserService.findByName(registerBean.getName()) != null) {
////			return HttpResult.error("用户名已存在!");
////		}
//		SysUser sysUser=new SysUser();
//		sysUser.setPassword(registerBean.getPassword());
//		sysUser.setName(registerBean.getName());
//		sysUser.setRegisterType(registerBean.getRegisterType());
//		sysUser.setStatus(registerBean.getStatus());
//		sysUser.setCreateTime(new Date());
//
//		String salt =PasswordUtils.getSalt();
//		sysUser.setSalt(salt);
//		sysUser.setPassword(PasswordUtils.encode(sysUser.getPassword(),salt ));
//		Long userId=sysUserService.addUserInfo(sysUser);
//		if (userId==null){
//		return HttpResult.error(500,"服务器异常");
//		}
//		if (registerBean.getRegisterType()==1){
//		CompanyInfo companyInfo=new CompanyInfo();
//		companyInfo.setUserId(userId);
//		companyInfo.setCompanyName(registerBean.getRoleName());
//		companyInfo.setOperatePassword(registerBean.getOperatePassword());
//		return HttpResult.ok(companyInfoService.saveCompanyInfo(companyInfo));
//		}
//		if(registerBean.getRegisterType()==2){
//			Delivery delivery=new Delivery();
//			delivery.setCreateTime(new Timestamp(System.currentTimeMillis()));
//			delivery.setDeliveryName(registerBean.getRoleName());
//			delivery.setUserId(userId);
//			delivery.setStatus(3);
//			delivery.setDeliveryPhone(registerBean.getName());
//			delivery.setFlag(false);
//			return HttpResult.ok(deliveryService.insertSelective(delivery));
//		}
//		return HttpResult.error("注册失败");
//	}

	@GetMapping(value="/departments")
	public HttpResult findTree() {
		return HttpResult.ok(sysDeptService.findTree());
	}

}
