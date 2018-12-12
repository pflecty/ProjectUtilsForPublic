package projectutils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.CommonUtil;
import projectutils.utils.PlugUtil;

/**
 * 复制当前编辑文件名
 * @author Administrator
 *
 */
public class CopyCurEditFileName implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	
	public CopyCurEditFileName() {
		
	}

	public void run(IAction action) {
		
		//复制当前编辑文件名
		copyCurEditFileName();
	
	}
	
	public static void copyCurEditFileName() {
		
		String name = PlugUtil.getChooseFileName();
		if(CommonUtil.isNotNull(name)){
			
			CommonUtil.setSysClipboardText(name);
			
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
	
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
}