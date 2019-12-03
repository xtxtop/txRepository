package cn.com.shopec.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片缩略工具
 *
 */
public class ECImageThumbUtils {
	
	private static final Log log = LogFactory.getLog(ECImageThumbUtils.class);

	public static final double IMAGE_COMPRESS_QUALITY_RATE = 1.0d;
	
	/**
	 * 对图片进行缩略，并保存为指定的文件名
	 * @param newFilePath 保存的新文件名
	 * @param thumbWidth 缩略后的图片宽
	 * @param thumbHeight 缩略后的图片高
	 * @param originalImageData 原图数据
	 */
	public static boolean thumbImageAndSave(String newFilePath, int thumbWidth, int thumbHeight, byte[] originalImageData) {
		boolean res = false;
		if(newFilePath == null || newFilePath.length() == 0 || thumbWidth <= 0 || thumbHeight <= 0 || originalImageData == null || originalImageData.length == 0) {
			return res;
		}
		try {
			InputStream is = new ByteArrayInputStream(originalImageData);
			
			Thumbnails.of(is).size(thumbWidth, thumbHeight).outputQuality(Double.valueOf(IMAGE_COMPRESS_QUALITY_RATE)).keepAspectRatio(true).toFile(newFilePath);
			
			res = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 对图片进行缩略，并保存为指定的文件名
	 * @param newFilePath 保存的新文件名
	 * @param thumbWidth 缩略后的图片宽
	 * @param thumbHeight 缩略后的图片高
	 * @param originalImageFile 原图文件对象
	 */
	public static boolean thumbImageAndSave(String newFilePath, int thumbWidth, int thumbHeight, File originalImageFile) {
		boolean res = false;
		if(originalImageFile == null) {
			return res;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(originalImageFile);
			int n = fis.available();
			byte[] buf = new byte[n];
			
			fis.read(buf);
			
			res = thumbImageAndSave(newFilePath, thumbWidth, thumbHeight, buf);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return res;
	}
	
	/**
	 * 对目标文件夹下的图片进行缩略处理，生成缩略图片
	 * @param targetDir 目标文件夹
	 * @param imageFileTypes 需要处理的文件类型
	 * @param isRecursive 是否处理子文件夹内的图片
	 * @param thumbWidth 缩略图宽
	 * @param thumbHeight 缩略图高
	 */
	public static void thumbImageAndSaveInDir(File targetDir, String[] imageFileTypes, boolean isRecursive, int thumbWidth, int thumbHeight) {
		if(targetDir == null || imageFileTypes == null || imageFileTypes.length == 0 || thumbWidth <= 0 || thumbHeight <= 0) {
			return;
		}
		
		if(!targetDir.exists() || !targetDir.isDirectory()) {
			return;
		}
		
		File[] files = targetDir.listFiles(); 
		if(files == null || files.length == 0) {
			return;
		}
		
		for(File file : files) {
			if(file.isDirectory() && isRecursive) { //如果是子目录，且需要处理子目录下的文件
				thumbImageAndSaveInDir(file, imageFileTypes, isRecursive, thumbWidth, thumbHeight); //递归调用，对子目录中的文件进行处理。
			}
			if(file.isFile()) { //如果只是文件
				for(String imageFileType : imageFileTypes) {
					if(!file.getName().toLowerCase().endsWith(imageFileType.toLowerCase())) { //不是需要处理的文件类型
						continue;
					}
					int idx = file.getName().toLowerCase().lastIndexOf(imageFileType.toLowerCase());
					String thumbImageFileName = file.getName().substring(0, idx - 1) + "_" + thumbWidth + "x" + thumbHeight + "." + imageFileType;
					
					thumbImageAndSave(targetDir.getAbsolutePath() + File.separator + thumbImageFileName, thumbWidth, thumbHeight, file);
				}
			}
		}
		
		
		return;
	}
	
	public static void main(String[] args) {
//		File file = new File("D:\\a.png");
//		String newFile = "D:\\a1.png";
//		int w = 100;
//		int h = 100;
//		boolean res = thumbImageAndSave(newFile, w, h, file);
//		System.out.println(res);
		
	}
}
