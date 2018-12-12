package projectutils.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类
 * @author Administrator
 *
 */
public class LogUtil {

	public static void main(String[] args) {
		writeLogToFile("hello");

	}
	
	/**
	 * 获取异常信息
	 * @param e
	 * @return
	 */
	public static String getErrorMsg(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
	
	/**
	 * 写文件，支持中文字符，在linux redhad下测试过
	 * @param str
	 */
    public static void writeLogToFile(String str)
    {
		try {
			String path = "C:/CommonUtils.log";
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			FileOutputStream out = new FileOutputStream(file, true); // 如果追加方式用true
			StringBuffer sb = new StringBuffer();
			sb.append("-----------" + sdf.format(new Date()) + "------------\n");
			sb.append(str + "\r\n");
			out.write(sb.toString().getBytes("utf-8"));// 注意需要转换对应的字符集
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
    }

}
