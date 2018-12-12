package projectutils.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * 插件工具类
 * @author Administrator
 *
 */
public class PlugUtil {
	
	/**
	 * 控制类名称转小写EntryOrdersController
	 */
	public static void nameToLowerCase(){
		String fileName = getChooseFileName();
		if(fileName != null && fileName.trim().length() > 0){
			
			if(fileName.endsWith("Controller")){
				
				fileName = fileName.replace("Controller", "");
				fileName = CommonUtil.toLowerCaseFirstOne(fileName);
				CommonUtil.setSysClipboardText(fileName);
			}
		}
	}
	
	/**
	 * 获取选择的文件名
	 */
	public static String getChooseFileName(){
		String fileName = "";
		//取得工作台  
		IWorkbench workbench = PlatformUI.getWorkbench();
	    if (workbench != null)
	    {
	    	//取得工作台窗口  
	      IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
	      if (window != null)
	      {
	    	//取得工作台页面  
	        IWorkbenchPage page = window.getActivePage();
	        if (page != null)
	        {
	        	//取得当前处于活动状态的编辑器窗口  
	          IEditorPart editor = page.getActiveEditor();
	          if (editor != null) {
	            IEditorInput input = editor.getEditorInput();
	            
	            if ((input != null) && 
	              ((input instanceof IFileEditorInput))) {
	            	
	              IFile file = ((IFileEditorInput)input).getFile();
	              if (file != null)
	              {
	                if (file.getName().lastIndexOf(".") != -1)
	                  fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
	                else {
	                  fileName = file.getName();
	                }
	              }
	            }
	          }
	        }
	      }
	    }
		return fileName;
	}
	
	
	/**
	 * 获取当前IWorkbenchPage
	 */
	public static IWorkbenchPage getCurIWorkbenchPage(){
		
		try {
			// 取得工作台
			IWorkbench workbench = PlatformUI.getWorkbench();
			
			if (workbench != null) {
				// 有时不是从视图，而是要从外部取得IWorkbenchPage，例如从菜单或者工具栏等，这时，可以使用下面的方法：
				// IWorkbenchPage workbenchPage =
				// Plugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
				// 取得工作台窗口
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

				if (window != null) {
					// 取得工作台页面
					return window.getActivePage();
				}
			}
			
		} catch (Exception e) {
			System.err.println("error=" + e.getMessage());
			// e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 定位到PackageExplorerPart
	 * @param page
	 * @param ifile0
	 * @throws PartInitException
	 */
	public static void toPackageExplorerPartForIFile(IWorkbenchPage page, IFile ifile0) throws PartInitException{
		PackageExplorerPart packageView = (PackageExplorerPart) page.showView(org.eclipse.jdt.ui.JavaUI.ID_PACKAGES);//org.eclipse.jdt.ui.PackageExplorer
		packageView.selectReveal(new StructuredSelection(ifile0));
	}
	
	
	/**
	 * 定位到PackageExplorerPart
	 * @param page
	 * @param ifile0
	 * @throws PartInitException
	 */
	public static void toPackageExplorerPartForIFolder(IWorkbenchPage page, IFolder ifile0) throws PartInitException{
		PackageExplorerPart packageView = (PackageExplorerPart) page.showView(org.eclipse.jdt.ui.JavaUI.ID_PACKAGES);//org.eclipse.jdt.ui.PackageExplorer
		packageView.selectReveal(new StructuredSelection(ifile0));
	}
	
	/**
	 * 获取当前IProject
	 */
	public static IProject getCurIProject(IWorkbenchPage page){
		
		// 取得当前处于活动状态的编辑器窗口
		IEditorPart editor = page.getActiveEditor();
		if (editor != null) {
			//当前项目
			Object object = editor.getEditorInput().getAdapter(IFile.class);  
            if(object != null){
            	return ((IFile)object).getProject();//IProject
            }
		}
		return null;
	}
	
	/**
	 * 获取当前选中文本
	 */
	public static String getCurClooseText() {
		//当前项目
		IWorkbenchPage page = getCurIWorkbenchPage();
		if(page == null){
			System.out.println("page为空");
			return null;
		}
				
		IEditorPart editor = page.getActiveEditor();
		if(editor == null){
			//取得选中文本
		    ITextSelection textSelection = (ITextSelection) editor.getEditorSite().getSelectionProvider().getSelection();
			return textSelection.getText();
		}
		return null;
	}

}
