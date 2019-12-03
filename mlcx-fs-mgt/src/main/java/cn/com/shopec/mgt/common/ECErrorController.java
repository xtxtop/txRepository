package cn.com.shopec.mgt.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class ECErrorController {
	
	@RequestMapping("error")
	public String error(){
		return "error/error";
	}

}
