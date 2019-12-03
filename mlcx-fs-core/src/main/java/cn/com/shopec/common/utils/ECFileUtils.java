package cn.com.shopec.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * 文件操作工具类
 *  
 */

public class ECFileUtils {
	
	private static Log log = LogFactory.getLog(ECFileUtils.class);
	
	/** 
	 * 写文件到磁盘
	 * @param dirPath
	 * @param fileName
	 * @param fileData
	 * @return  
	 * @throws 
	 */
	public static boolean writeFileToDisc(String dirPath, String fileName, byte[] fileData) {
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		return writeFileToDisc(dir, fileName, fileData);
	}
	
	/** 
	 * 写文件到磁盘
	 * @param dir
	 * @param fileName
	 * @param fileData
	 * @return  
	 * @throws 
	 */
	public static boolean writeFileToDisc(File dir, String fileName, byte[] fileData) {
		File file = new File(dir, fileName);

		return writeToFileOutputStream(file, fileData);
	}
	
	/** 
	 * 写文件到磁盘
	 * @param fileFullPath
	 * @param fileData
	 * @return  
	 * @throws 
	 */
	public static boolean writeFileToDisc(String fileFullPath, byte[] fileData) {
		File file = new File(fileFullPath);
		return writeFileToDisc(file, fileData);
	}
	
	/** 
	 * 写文件到磁盘
	 * @param file
	 * @param fileData
	 * @return  
	 * @throws 
	 */
	public static boolean writeFileToDisc(File file, byte[] fileData) {
		return writeToFileOutputStream(file, fileData);
	}
	
	/**
	 * 创建文件夹路径
	 * @param dirPath
	 * @return
	 */
	public static boolean createdir(String dirPath){
		boolean flag = true;
		File dirFile = new File(dirPath);
		if(!dirFile.exists()){
			flag = dirFile.mkdirs();
		}
		return flag;
	}
	
	
	/**
	 * 删除文件
	 * @param dirPath
	 * @return
	 */
	public static boolean delFile(String filePath){
		File dirFile = new File(filePath);
		return dirFile.delete();
	}
	
	/**
	 * 将临时文件写入磁盘
	 * @param stream 文件流
	 * @param dirPath 文件目录
	 * @param prefix 临时文件前缀
	 * @param suffix 临时文件后缀
	 * @param delOnExit jvm退出时删除临时文件
	 * @return
	 */
	public static boolean writeTempFile(InputStream stream,String dirPath,String prefix, String suffix,boolean delOnExit) {
		boolean res = true;
		File file = null;
		try {
			file = File.createTempFile(prefix, suffix, new File(dirPath));
		} catch (IOException e) {
			res =false;
			log.error(e);
			e.printStackTrace();
		}
		if(null != file){
			res = writeFileOutputStream(file,stream);
		}
		if(delOnExit && null != file){
			file.deleteOnExit();
		}
		return res;
	}

	
	/**
	 * 将文件写入磁盘
	 * @param dirPath 目标文件目录
	 * @param fileName 文件名
	 * @param stream 文件流
	 * @return
	 */
	public static boolean writeFile(String dirPath,String fileName, InputStream stream) {
		
		File file =new File(dirPath+File.separator+fileName);
		
		return  writeFileOutputStream(file,stream);
	}
	
	
	 // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 
     * @param srcFileName
     * @param destFileName
     * @param srcCoding
     * @param destCoding
     * @throws IOException
     */
    public static void copyFile(File srcFileName, File destFileName, String srcCoding, String destCoding) throws IOException {// 把文件转换为GBK文件
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFileName), srcCoding));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFileName), destCoding));
            char[] cbuf = new char[1024 * 5];
            int len = cbuf.length;
            int off = 0;
            int ret = 0;
            while ((ret = br.read(cbuf, off, len)) > 0) {
                off += ret;
                len -= ret;
            }
            bw.write(cbuf, 0, off);
            bw.flush();
        } finally {
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();
        }
    }

    /**
     * 
     * @param filepath
     * @throws IOException
     */
    public static void del(String filepath) throws IOException {
        File f = new File(filepath);// 定义文件路径
        if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
            if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                f.delete();
            } else {// 若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();// 删除文件
                }
            }
        }else if(f.exists() && f.isFile()){
        	f.delete();
        }
    }
	
	/**
	 * 写数据到文件输出流
	 * @param file
	 * @param stream
	 * @return
	 */
	private static boolean writeFileOutputStream(File file, InputStream stream){
		if(null == file || null == stream){
			return false;
		}
		
		boolean res = true;
		FileOutputStream fs = null;		
		try {
			fs = new FileOutputStream(file);
			byte[] buffer = new byte[1024 * 5];
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
		} catch (IOException e) {
			res = false;
			log.error(e);
			e.printStackTrace();
		} finally {
			if (null != fs) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	/** 
	 * 写数据到文件输出流
	 * @param file
	 * @param buf
	 * @return  
	 * @throws 
	 */
	private static boolean writeToFileOutputStream(File file, byte[] buf) {
		boolean res = false;

		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(buf);
			
			res = true;
		} catch (FileNotFoundException e) {
			log.error(e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally {
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		
		return res;
	}
}
