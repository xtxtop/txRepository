package cn.com.shopec.common.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.FastDFSClientUtil;

@Controller
@Component
@RequestMapping("fastDFSUpload")
public class FastDFSUploadController {

	private static final Log log = LogFactory.getLog(FastDFSUploadController.class);

	@RequestMapping("uploadFile")
	@ResponseBody
	public ResultInfo<Object> uploadFile(@RequestParam("file") CommonsMultipartFile[] files, String groupName) {
		ResultInfo<Object> resultInfo = new ResultInfo<Object>();
		try {
			if (files != null && files.length > 0) {
				String[] filePath = new String[files.length];
				int i = 0;
				for (CommonsMultipartFile file : files) {
					String fileName = file.getFileItem().getName();
					String extName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
					filePath[i] = FastDFSClientUtil.uploadFile(groupName, file.getBytes(), extName, null);
					i++;
				}
				String filePaths = "";
				for (int k = 0; k < filePath.length; k++) {
					filePaths += filePath[k] + ";";
				}
				filePaths = filePaths.substring(0, filePaths.length() - 1);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(filePaths);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("上传文件为空！");
			}
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return resultInfo;
	}

	@RequestMapping("deleteFile")
	@ResponseBody
	public ResultInfo<Object> deleteFile(@RequestParam("filePaths") String[] filePaths) {
		ResultInfo<Object> resultInfo = new ResultInfo<Object>();
		try {
			if (filePaths != null && filePaths.length > 0) {
				for (String path : filePaths) {
					FastDFSClientUtil.deleteFile(path);
				}
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("文件路径为空！");
			}
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return resultInfo;
	}
}
