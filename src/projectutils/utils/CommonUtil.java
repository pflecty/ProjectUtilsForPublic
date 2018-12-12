package projectutils.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * 常用工具类
 * @author Administrator
 *
 */
public class CommonUtil {

	/**
	 * 判断文本不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str){
		if(str != null && !str.trim().equals("")){
			return true;
		}
		return false;
	}

	//文本转sql名称
	//autoGenerateAccruedAutoAdjustBalance auto_generate_accrued_auto_adjust_balance 
	//autoGenerate2 auto_generate2
	public static String toSqlName(String clooseText) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < clooseText.length(); i++) {
			
			CharSequence s = clooseText.subSequence(i, i+1);
			char c = s.charAt(0);
			if(c >= 'A' && c <= 'Z'){
				buffer.append("_" + (char)(c + 32));
			}else{
				buffer.append(c);
			}
		}
		return buffer.toString();
	}
	
	//文本转实体类名称
	//将下划线后面的字符大写
	//auto_generate_accrued_auto_adjust_balance autoGenerateAccruedAutoAdjustBalance
	//auto__generate_2 autoGenerate2
	public static String toEntityHandle(String clooseText) {
		StringBuffer buffer = new StringBuffer();
		if(clooseText.indexOf("_") != -1){
			
			String strs[] = clooseText.split("_");
			for (int i = 0; i < strs.length; i++) {
				
				if(isNotNull(strs[i])){
					if(i == 0){
						buffer.append(strs[0]);
					}else{
						String s = toUpperCaseFirstOne(strs[i]);
						buffer.append(s);
					}
				}
			}
		}
		return buffer.toString();
	}
		
	/**
	 * 打印
	 */
	private void p(Object obj) {
		System.out.println(obj);
	}
	
	
	/**
	 * 显示对话框
	 * @param message
	 */
	private void print(IWorkbenchWindow window, String message) {
		MessageDialog.openInformation(window.getShell(), "提示", message);
	}

	
	/**
	 * 字符串到剪切板
	 */
	public static void setSysClipboardText(String writeMe) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable tText = new StringSelection(writeMe);
		clip.setContents(tText, null);
	}
	
	/**
	 * 首字母转小写
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 首字母转大写
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	/**
	 * 描述：获取数字，为空和异常返回0
	 */
	public static int getNumberZero(Object number){
		
		 try{
			 if(number != null && !"".equals(number.toString().trim())){
				return Integer.parseInt(number.toString().trim());
			 }
		 }catch(Exception e){
			 
		 }
		 return 0;
	}
	
	/**
	 * 获取字符串，为空时返回""
	 */
	public static String getStringOfEmpty(Object object) {
		
		if(object == null)
			return "";
		return object.toString();
	}
	
	/**
	 * 获取保留词组
	 * @param clooseText
	 * @return
	 */
	public static String getRetainWords(String clooseText) {
		
		clooseText = clooseText.trim();
		
		if(clooseText.endsWith("Service")){
			
			clooseText = clooseText.replace("Service", "");
			
		}else if(clooseText.endsWith("DAO")){
			
			clooseText = clooseText.replace("DAO", "");
			
		}else if(clooseText.endsWith("Controller")){
			
			clooseText = clooseText.replace("Controller", "");
			
		}else if(clooseText.endsWith("Mapper")){
			
			clooseText = clooseText.replace("Mapper", "");
		}
		
		return  clooseText;
	}
		
		

}
	