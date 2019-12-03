/**
 * 
 */
package cn.com.shopec.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class FastDFSClientUtil {

	private static final String CONFIG_FILENAME = "fdfs_client.conf";

	private static StorageClient1 storageClient1 = null;

	static {
		try {
			System.out.println(FastDFSClientUtil.class.getResource("/").getPath() + CONFIG_FILENAME);
			ClientGlobal.init(FastDFSClientUtil.class.getResource("/").getPath() + CONFIG_FILENAME);
			TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
			TrackerServer trackerServer = trackerClient.getConnection();
			if (trackerServer == null) {
				throw new IllegalStateException("getConnection return null");
			}

			StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
			if (storageServer == null) {
				throw new IllegalStateException("getStoreStorage return null");
			}
			storageClient1 = new StorageClient1(trackerServer, storageServer);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String uploadFile(String groupName, byte[] buff, String extName, Map<String, String> metaList) {
		try {
			NameValuePair[] nameValuePairs = null;
			if (metaList != null) {
				nameValuePairs = new NameValuePair[metaList.size()];
				int index = 0;
				for (Iterator<Map.Entry<String, String>> iterator = metaList.entrySet().iterator(); iterator
						.hasNext();) {
					Map.Entry<String, String> entry = iterator.next();
					String name = entry.getKey();
					String value = entry.getValue();
					nameValuePairs[index++] = new NameValuePair(name, value);
				}
			}
			return storageClient1.upload_file1(groupName, buff, extName, nameValuePairs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> getFileMetadata(String fileId) {
		try {
			NameValuePair[] metaList = storageClient1.get_metadata1(fileId);
			if (metaList != null) {
				HashMap<String, String> map = new HashMap<String, String>();
				for (NameValuePair metaItem : metaList) {
					map.put(metaItem.getName(), metaItem.getValue());
				}
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int deleteFile(String fileId) {
		try {
			return storageClient1.delete_file1(fileId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static ResponseEntity<byte[]> download(String fileId, String specFileName) {
		byte[] content = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			content = storageClient1.download_file1(fileId);
			headers.setContentDispositionFormData("attachment",
					new String(specFileName.getBytes("UTF-8"), "iso-8859-1"));
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
	}

	// public static void main(String[] args) {
	//
	// File file = new File(
	// "C:\\resource\\images\\image-server\\member_doc\\46\\c2\\b481fb1a743d4f2d97eef93aedca46c2.jpg");
	// Map<String, String> metaList = new HashMap<String, String>();
	// metaList.put("width", "1024");
	// metaList.put("height", "768");
	// metaList.put("author", "wo");
	// metaList.put("date", "20170804");
	// String extName = file.getName().substring(file.getName().lastIndexOf(".")
	// + 1, file.getName().length());
	// try {
	// byte[] buff = IOUtils.toByteArray(new FileInputStream(file));
	// String fid = FastDFSClientUtil.uploadFile("file", buff, extName,
	// metaList);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

}
