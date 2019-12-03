package cn.com.shopec.common.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * pdf操作
 * @author zzm
 * @createDate 2016/07/10
 * @version 1.0
 */
@Controller
@RequestMapping("pdf")
public class PdfController {
	
	/**
	 * pdf导出
	 * @param request
	 * @param printUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("export")
	public ResponseEntity<byte[]> export(HttpServletRequest request, String printUrl) throws Exception {
		String basePath = request.getSession().getServletContext().getRealPath("/");
		String sessionId = request.getSession().getId();
		String tool = "";
		String exportPath = "";
		String os = System.getProperty("os.name"); 
		if (os.toLowerCase().startsWith("win")){  
			tool = basePath + "res\\pdf\\wkhtmltopdf\\wkhtmltopdf.exe"; 
			exportPath = basePath + "res\\pdf\\temp\\" + System.currentTimeMillis() + ".pdf";
		} else {
			tool = basePath + "res/pdf/wkhtmltox/bin/wkhtmltopdf";
			exportPath = basePath + "res/pdf/temp/" + System.currentTimeMillis() + ".pdf";
		}
		printUrl = printUrl + "&print=1";
		if (printUrl.indexOf("export.do") != -1) {
			return null;
		}

		String command =  tool + " --cookie JSESSIONID " + sessionId + " \"" + printUrl + "\" " + exportPath;//前半段是我的安装路径，根据自己的安装路径换上即可  
		
		File file = new File(exportPath);
		Process proc = Runtime.getRuntime().exec(command);
        proc.waitFor();  
		byte[] bytes = FileUtils.readFileToByteArray(file);  
        if (file.exists()) {  
            file.delete();  
        }
		HttpHeaders headers = new HttpHeaders();    
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String("打印.pdf".getBytes("UTF-8"), "ISO8859-1"));  //解决文件名中文乱码问题  
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK); 
	}
   
}
