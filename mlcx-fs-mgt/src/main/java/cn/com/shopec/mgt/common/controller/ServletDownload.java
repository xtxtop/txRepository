package cn.com.shopec.mgt.common.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.shopec.mgt.common.BaseController;

@Controller
public class ServletDownload extends BaseController {
	@Value(value = "${res_img_path}")
	private String resImgPath;
	@RequestMapping("downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String filename)
			throws Exception {
		if (filename != null) {
			String newFileName = "";
			if (filename != null && filename.length() > 0) {
				// String[] aa=filename.split("/");
				// if(aa.length!=0){
				// newFileName=aa[1];
				// }else{
				// newFileName=filename;
				// }
				newFileName = filename;
			}
			// 设置文件MIME类型
			// response.setContentType(request.getSession().getServletContext().getMimeType(newFileName));
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("UTF-8");
			// 设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename=" + newFileName);
			// 读取目标文件，通过response将目标文件写到客户端
			// 获取目标文件的绝对路径
			// String aa=imagePath+resImgPath.replaceAll("/", "//");
			String aa = resImgPath;
			String fullFileName = (aa + "/" + newFileName);
			// String fullFileName = (aa+"/item/" + newFileName);
			// String fullFileName =
			// request.getServletContext().getRealPath("http://120.24.241.95:81/image-server"
			// + filename);
			// System.out.println(fullFileName);
			// 读取文件
			InputStream in = new FileInputStream(fullFileName);
			OutputStream out = response.getOutputStream();
			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}

			in.close();
			out.close();
		}
	}
	//导出模板（这个 需要在 服务器上 加入模板  先不用这个）
	@RequestMapping("downloadExcelFile")
	public void downloadExcelFile(HttpServletRequest request, HttpServletResponse response, String filepath,
			String newFileName) throws Exception {
		response.setContentType("multipart/form-data");
		// response.setContentType(request.getSession().getServletContext().getMimeType(newFileName));
		response.setCharacterEncoding("UTF-8");
		// 设置Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename=" + newFileName);
		// 读取目标文件，通过response将目标文件写到客户端
		String fullFileName = (resImgPath + "/" + filepath + "/" + newFileName);
		// 读取文件
		InputStream in = new FileInputStream(fullFileName);
		OutputStream out = response.getOutputStream();
		// 写文件
		int b;
		while ((b = in.read()) != -1) {
			out.write(b);
		}
		in.close();
		out.close();
	}
}
