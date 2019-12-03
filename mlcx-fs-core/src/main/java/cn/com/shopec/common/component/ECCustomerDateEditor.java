package cn.com.shopec.common.component;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 自定义日期转换
 *
 */
public class ECCustomerDateEditor extends PropertyEditorSupport {

	private static final Log log = LogFactory.getLog(ECCustomerDateEditor.class);
	
//	private final boolean allowEmpty;
//	
//	public ECCutomerDateEditor(boolean allowEmpty) {
//		this.allowEmpty = allowEmpty;
//	}

	public String getAsText() {
		Date value = (Date) getValue();
		if (null == value) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(value);
	}

	public void setAsText(String text) throws IllegalArgumentException {
		Date value = null;
		if (null != text && (text = text.trim()).length() != 0) {
			if (text.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
				value = parseDate(text, "yyyy-MM-dd");
			} else if (text.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
				value = parseDate(text, "yyyy-MM-dd hh:mm");
			} else if (text.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
				value = parseDate(text, "yyyy-MM-dd hh:mm:ss");
			}

		}
		setValue(value);
	}

	/**
	 * 功能描述：格式化日期
	 * 
	 * @param dateStr
	 *            String 字符型日期
	 * @param format
	 *            String 格式
	 * @return Date 日期
	 */
	public Date parseDate(String dateStr, String format) {
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			date = (Date) dateFormat.parse(dateStr);
		} catch (Exception e) {
			log.error(e.getMessage() + ", dateStr=" + dateStr + ",format=" + format);
		}
		return date;
	}
}
