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
 * ���������
 * @author Administrator
 *
 */
public class PlugUtil {
	
	/**
	 * ����������תСдEntryOrdersController
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
	 * ��ȡѡ����ļ���
	 */
	public static String getChooseFileName(){
		String fileName = "";
		//ȡ�ù���̨  
		IWorkbench workbench = PlatformUI.getWorkbench();
	    if (workbench != null)
	    {
	    	//ȡ�ù���̨����  
	      IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
	      if (window != null)
	      {
	    	//ȡ�ù���̨ҳ��  
	        IWorkbenchPage page = window.getActivePage();
	        if (page != null)
	        {
	        	//ȡ�õ�ǰ���ڻ״̬�ı༭������  
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
	 * ��ȡ��ǰIWorkbenchPage
	 */
	public static IWorkbenchPage getCurIWorkbenchPage(){
		
		try {
			// ȡ�ù���̨
			IWorkbench workbench = PlatformUI.getWorkbench();
			
			if (workbench != null) {
				// ��ʱ���Ǵ���ͼ������Ҫ���ⲿȡ��IWorkbenchPage������Ӳ˵����߹������ȣ���ʱ������ʹ������ķ�����
				// IWorkbenchPage workbenchPage =
				// Plugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
				// ȡ�ù���̨����
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

				if (window != null) {
					// ȡ�ù���̨ҳ��
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
	 * ��λ��PackageExplorerPart
	 * @param page
	 * @param ifile0
	 * @throws PartInitException
	 */
	public static void toPackageExplorerPartForIFile(IWorkbenchPage page, IFile ifile0) throws PartInitException{
		PackageExplorerPart packageView = (PackageExplorerPart) page.showView(org.eclipse.jdt.ui.JavaUI.ID_PACKAGES);//org.eclipse.jdt.ui.PackageExplorer
		packageView.selectReveal(new StructuredSelection(ifile0));
	}
	
	
	/**
	 * ��λ��PackageExplorerPart
	 * @param page
	 * @param ifile0
	 * @throws PartInitException
	 */
	public static void toPackageExplorerPartForIFolder(IWorkbenchPage page, IFolder ifile0) throws PartInitException{
		PackageExplorerPart packageView = (PackageExplorerPart) page.showView(org.eclipse.jdt.ui.JavaUI.ID_PACKAGES);//org.eclipse.jdt.ui.PackageExplorer
		packageView.selectReveal(new StructuredSelection(ifile0));
	}
	
	/**
	 * ��ȡ��ǰIProject
	 */
	public static IProject getCurIProject(IWorkbenchPage page){
		
		// ȡ�õ�ǰ���ڻ״̬�ı༭������
		IEditorPart editor = page.getActiveEditor();
		if (editor != null) {
			//��ǰ��Ŀ
			Object object = editor.getEditorInput().getAdapter(IFile.class);  
            if(object != null){
            	return ((IFile)object).getProject();//IProject
            }
		}
		return null;
	}
	
	/**
	 * ��ȡ��ǰѡ���ı�
	 */
	public static String getCurClooseText() {
		//��ǰ��Ŀ
		IWorkbenchPage page = getCurIWorkbenchPage();
		if(page == null){
			System.out.println("pageΪ��");
			return null;
		}
				
		IEditorPart editor = page.getActiveEditor();
		if(editor == null){
			//ȡ��ѡ���ı�
		    ITextSelection textSelection = (ITextSelection) editor.getEditorSite().getSelectionProvider().getSelection();
			return textSelection.getText();
		}
		return null;
	}

}
