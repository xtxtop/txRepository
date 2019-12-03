package cn.com.shopec.mgt.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECJsonUtils;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.core.device.model.DeviceUploadFile;
import cn.com.shopec.core.device.service.DeviceUploadFileService;

@Controller
@Component
@RequestMapping("upload")
public class UploadController {

	private static final Log log = LogFactory.getLog(UploadController.class);
	@Value("${image_path}")
	private String serverPath;
	@Value("${res_img_path}")
	private String resImgPath;
	@Value("${res_path}")
	private String resFilePath;
	@Value("${file_path}")
	private String serverFilePath;
	@Resource
	HttpServletRequest request;

	@Resource
	private DeviceUploadFileService deviceUploadFileService;

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");

	@RequestMapping("uploadFile")
	@ResponseBody
	public ResultInfo<Object> uploadFile(@RequestParam("qqfile") CommonsMultipartFile[] files,
			@RequestParam("storePath") String storePath, @RequestParam("resPath") String resPath) {
		ResultInfo<Object> resultInfo = new ResultInfo<Object>();
		try {
			if (files != null && files.length > 0) {
				// String storePath = request.getParameter("storePath");
				// String resPath = request.getParameter("resPath");
				if (storePath == null || storePath.equals("")) {
					storePath = request.getRealPath("/res/img");
				}
				if (resPath == null || resPath.equals("")) {
					resPath = "/res/img";
				}
				File fileDir = new File(storePath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				String[] filePath = new String[files.length];
				int i = 0;
				for (CommonsMultipartFile file : files) {
					String fileName = file.getFileItem().getName();
					fileName = System.currentTimeMillis() + "."
							+ fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
					File newFile = new File(storePath + File.separator + fileName);
					file.getFileItem().write(newFile);

					filePath[i] = resPath + "/" + fileName;
					i++;
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(filePath);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("上传文件为空！");
			}
		} catch (IOException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
			e.printStackTrace();
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
				String resPath = request.getParameter("resPath");
				if (resPath == null || resPath.equals("")) {
					resPath = request.getRealPath(".");
				}
				for (String path : filePaths) {
					File file = new File(resPath + "/" + path);
					file.delete();
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

	/**
	 * 图片上传后，经过重命名（可以对一个时间+随机数的值，进行MD5处理），得到一个如
	 * 43A2xxxxxxxxxxxx84EFC32.jpg这样的一个新文件名。
	 * 然后使用这个文件名的最后4位（不含.jpg），即"FC32"，做目录散列，做2层。 第一层目录名为 FC ，第二层目录名为
	 * 32，最终的存储文件全路径是：/res/img_server/member_icon/FC/32/43A2xxxxxxxxxxxx84EFC32.
	 * jpg
	 * 
	 * 其中/res/img_server是图片服务器基准路径 member_icon是业务路径（会员头像） FC/32，是散列路径，最后是图片名
	 * 
	 * @param files
	 * @param storePath(保存的业务路径)
	 * @param resPath
	 * @param response
	 */
	@RequestMapping("uploadFileNew")
	@ResponseBody
	public void uploadFileNew(@RequestParam("qqfile") CommonsMultipartFile[] files,
			@RequestParam("storePath") String storePath, @RequestParam("resPath") String resPath,
			HttpServletResponse response) {
		ResultInfo<Object> resultInfo = new ResultInfo<Object>();
		try {
			PrintWriter writer = response.getWriter();
			if (files != null && files.length > 0) {
				if (resPath == null || resPath.equals("")) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("请设置存储的业务路径!");
				} else {
					// 判断业务路径是否符合已设置的业务路径
					if (resPath.equals(Constant.CAR_DOC) || resPath.equals(Constant.CAR_PHOTO)
							|| resPath.equals(Constant.MEMBER_DOC) || resPath.equals(Constant.MEMBER_ICON)
							|| resPath.equals(Constant.PARK_PHOTO) || resPath.equals(Constant.ADVERT_PHOTO)
							|| resPath.equals(Constant.COUPONPLAN_PHOTO)
							|| resPath.equals(Constant.ITEM_PHOTO)||resPath.equals(Constant.ORDERSHARE_PHOTO)) {
						String[] filePath = new String[files.length];
						int i = 0;
						for (CommonsMultipartFile file : files) {
							// 原文件名
							String oldName = file.getFileItem().getName();
							// 新文件名
							String newFileName = ECMd5Utils
									.crypt(String.valueOf(System.nanoTime() + new Random().nextInt(100)));
							// 创建路径
							String paths = newFileName.substring(newFileName.length() - 4, newFileName.length());
							// 散列路径
							String path1 = paths.substring(0, 2);
							// 散列路径
							String path2 = paths.substring(2, paths.length());
							// 创建文件夹目录
							File dirFile = new File(storePath + File.separator + path1 + File.separator + path2);
							if (!dirFile.exists()) {
								dirFile.mkdirs();
							}
							// 判断文件后缀类型
							int idxSuffix = oldName.lastIndexOf(".");
							if (-1 != idxSuffix) {
								String suffix = oldName.substring(idxSuffix, oldName.length()).toLowerCase();
								File newFile = new File(storePath + File.separator + path1 + File.separator + path2
										+ File.separator + newFileName + suffix);
								file.getFileItem().write(newFile);
								// 文件路径回显
								filePath[i] = resPath + "/" + path1 + "/" + path2 + "/" + newFileName + suffix;
							} else {
								File newFile = new File(serverPath + File.separator + storePath + File.separator + path1
										+ File.separator + path2 + File.separator + newFileName);
								file.getFileItem().write(newFile);
								filePath[i] = resPath + "/" + path1 + "/" + path2 + "/" + newFileName;
							}
							i++;
						}
						System.err.println(filePath.length + "+++++++");
						writer.write("{\"success\":\"ok\",\"data\":" + ECJsonUtils.toJson(filePath) + "}");
						writer.flush();
						writer.close();
					} else {
						writer.write("{\"success\":\"fail\",\"data\":resPath is not right}");
						writer.flush();
						writer.close();
					}
				}

			} else {
				writer.write("{\"success\": \"fail\"}");
				writer.flush();
				writer.close();
			}
		} catch (IOException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 设备上传图片
	 * 
	 * @param files
	 * @param storePath
	 * @param resPath
	 * @param response
	 */
	@RequestMapping("uploadFileByDevice")
	@ResponseBody
	public void uploadFileByDevice(@RequestParam("files") CommonsMultipartFile[] files, String deviceSn, String time,
			HttpServletResponse response) {
		try {
			PrintWriter writer = response.getWriter();
			if (deviceSn == null || deviceSn.trim().length() == 0) {
				writer.write("{\"success\":\"fail\",\"data\":deviceSn is null}");
				writer.flush();
				writer.close();
				return;
			}
			if (time == null || time.trim().length() == 0) {
				writer.write("{\"success\":\"fail\",\"data\":time is null}");
				writer.flush();
				writer.close();
				return;
			}
			Date date = sdf.parse(time);
			if (files == null || files.length == 0) {
				writer.write("{\"success\":\"fail\",\"data\":file is null}");
				writer.flush();
				writer.close();
				return;
			}
			String resPath = Constant.DEVICE_PHOTO;
			String storePath = resImgPath;
			String requestPath = serverPath;

			String[] filePath = new String[files.length];
			int i = 0;
			for (CommonsMultipartFile file : files) {
				int fileType = 1;
				// 原文件名
				String oldName = file.getFileItem().getName();
				// 判断文件后缀类型
				int idxSuffix = oldName.lastIndexOf(".");
				String suffix = oldName.substring(idxSuffix, oldName.length()).toLowerCase();
				if (suffix.equals(".mp4")) {
					resPath = Constant.DEVICE_MP4;
					storePath = resFilePath;
					requestPath = serverFilePath;
					fileType = 2;
				}
				// 新文件名
				String newFileName = ECMd5Utils.crypt(String.valueOf(System.nanoTime() + new Random().nextInt(100)));
				// 创建路径
				String paths = newFileName.substring(newFileName.length() - 4, newFileName.length());
				// 散列路径
				String path1 = paths.substring(0, 2);
				// 散列路径
				String path2 = paths.substring(2, paths.length());
				// 创建文件夹目录
				File dirFile = new File(
						storePath + File.separator + resPath + File.separator + path1 + File.separator + path2);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				if (-1 != idxSuffix) {
					File newFile = new File(storePath + File.separator + resPath + File.separator + path1
							+ File.separator + path2 + File.separator + newFileName + suffix);
					file.getFileItem().write(newFile);
					// 文件路径回显
					filePath[i] = resPath + "/" + path1 + "/" + path2 + "/" + newFileName + suffix;
				} else {
					File newFile = new File(requestPath + File.separator + resPath + File.separator + path1
							+ File.separator + path2 + File.separator + newFileName);
					file.getFileItem().write(newFile);
					filePath[i] = resPath + "/" + path1 + "/" + path2 + "/" + newFileName;
				}
				DeviceUploadFile deviceUploadFile = new DeviceUploadFile();
				deviceUploadFile.setDeviceSn(deviceSn);
				deviceUploadFile.setFilePath(filePath[i]);
				deviceUploadFile.setFileType(fileType);
				deviceUploadFile.setCreateTime(date);
				deviceUploadFileService.addDeviceUploadFile(deviceUploadFile);
				i++;
			}
			writer.write("{\"success\":\"ok\",\"data\":" + ECJsonUtils.toJson(filePath) + "}");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
