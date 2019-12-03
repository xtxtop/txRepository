package cn.com.shopec.mapi.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.noggit.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.exception.BaseException;
import cn.com.shopec.common.exception.ECException;
import cn.com.shopec.common.utils.ECJsonUtils;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * Created by guanfeng.li on 2016/7/5.
 * 统一异常处理类
 * 所有Spring MVC 抛出异常处理
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    private final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

//    //品牌logo和名称
//    @Resource
//    private BrandService brandService;
//    //产品分类服务类
//    @Resource
//    private ItemCatService itemCatService;
    @Resource
    private SysParamService sysParamService;

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //获取堆栈信息
        String stackTraceInfo = getStackTraceInfo(e);

        // 记录日志
        log.error("================ cn.com.shopec.mall.common.exception.ExceptionHandler =================");
        log.error(stackTraceInfo);

        String code = "500";
        String modelName = "";

        //设置HTTP响应状态
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        //判断是否是自定义异常
        if(e instanceof BaseException){
            code = ((BaseException)e).getErrorCode();
            modelName = ((BaseException)e).getModuleName();
        } else if(e instanceof ECException){
            code = ((ECException)e).getErrorCode();
            modelName = ((BaseException)e).getModuleName();
        }

        //封装成ResultInfo
        ResultInfo<Exception> result = new ResultInfo<>();
        result.setCode(code);
        result.setData(e);
        result.setMsg(e.getMessage());

        //判断是否是ajax请求 ---- 错误处理
        if (isAjaxRequest(httpServletRequest)){
            PrintWriter out = null;
            try{
                httpServletResponse.addHeader("stackTraceInfo",stackTraceInfo);
                httpServletResponse.addHeader("exception",JSONUtil.toJSON(e));
                httpServletResponse.addHeader("modelName",modelName);
                out = httpServletResponse.getWriter();
                out.write(ECJsonUtils.toJson(result));
                out.flush();
            } catch (IOException ex) {

            } finally {
                out.close();
            }

            return null;
        }
        //不是ajax请求 ---- 跳转页面处理

        ModelAndView view = new ModelAndView("error");
//
//        List<ItemCat> itemCats = itemCatService.getAllCat();
//        List<Brand> brands =brandService.getBrandList(new Query());
//        view.addObject("brands", brands);
//        view.addObject("itemCats", itemCats);

        view.addObject("result",result);
        view.addObject("modelName",modelName);
        view.addObject("exection",e);
        view.addObject("stackTraceInfo",stackTraceInfo);
        
		// 首页增加版本
		try
		{
			String version = sysParamService.getVersion();
			view.addObject("version", version);
		}
		catch (Exception e1)
		{
			log.error("--------查询版本好出错，错误信息：" + e1.getMessage(), e1);
		}
        return view;
    }




    /**
     * 判断是否ajax请求
     * @param request
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        boolean res = false;
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(requestType)) {
            res = true;
        }
        return res;
    }

    //获取堆栈信息
    public String  getStackTraceInfo(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuffer sb = new StringBuffer();
        sb.append(e.getMessage()).append("\r\n");
        for (StackTraceElement ex : stackTrace) {
            sb.append(ex.getClassName()).append("(").append(ex.getLineNumber()).append(")").append("\r\n");
        }
        return sb.toString();
    }
}
