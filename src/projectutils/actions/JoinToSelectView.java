package projectutils.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.CommonUtil;
import projectutils.utils.PlugUtil;
import projectutils.utils.PositionUtil;

/**
 * 跳转到选中的View
 * 跳转选中视图jsp页面
 * @author Administrator
 */
public class JoinToSelectView implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;
	
	public JoinToSelectView() {
		positionUtil = new PositionUtil();
	}

	public void run(IAction action) {
		
		//获取选中内容
		//当前项目
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			System.out.println("page为空");
			return;
		}
		
		IEditorPart editor = page.getActiveEditor();
		
		if(editor == null){
			System.out.println("editor为空");
			return;
		}
		
		//当前项目
		IEditorInput input = editor.getEditorInput();
		
		boolean isHaveAdminPath = false;
		IFile file = (IFile) input.getAdapter(IFile.class);
		 if (file != null) {
			 //当前编辑文件的路径
			String path = file.getLocation().toString();
			if(path.indexOf("admin") != -1){
				isHaveAdminPath = true;
			}
		 }

		//鼠标选中的文本
		String clooseText = null;
		
		ITextSelection textSelection = (ITextSelection) editor.getEditorSite().getSelectionProvider().getSelection();
		
		//获取选中文本
		clooseText = textSelection.getText();
		
		if(CommonUtil.isNotNull(clooseText) && clooseText.length() < 255){
			//查询jsp view，并跳转到该jsp
			joinToFile(clooseText.trim(), isHaveAdminPath);
			
		}else{
			//查询jsp view，并跳转到该jsp
			joinToFile("404", isHaveAdminPath);
		}
	}
	
	//跳转到该jsp
	private void joinToFile(String clooseText, boolean isHaveAdminPath) {
		
		positionUtil.positionFileInView("views", clooseText + ".jsp", true, isHaveAdminPath);
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
	
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
   
}