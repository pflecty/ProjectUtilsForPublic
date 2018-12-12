package projectutils.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ��־������
 * @author Administrator
 *
 */
public class LogUtil {

	public static void main(String[] args) {
		writeLogToFile("hello");

	}
	
	/**
	 * ��ȡ�쳣��Ϣ
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
	 * д�ļ���֧�������ַ�����linux redhad�²��Թ�
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
			FileOutputStream out = new FileOutputStream(file, true); // ���׷�ӷ�ʽ��true
			StringBuffer sb = new StringBuffer();
			sb.append("-----------" + sdf.format(new Date()) + "------------\n");
			sb.append(str + "\r\n");
			out.write(sb.toString().getBytes("utf-8"));// ע����Ҫת����Ӧ���ַ���
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
    }

}
