package projectutils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.PositionUtil;

/**
 * 定位到Admin视图
 *
 */
public class PositionAdminViewsFolder implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;

	
	public PositionAdminViewsFolder() {
		positionUtil = new PositionUtil();
	}
	
	public void run(IAction action) {
		
		//定位指定文件夹下面的文件或文件夹
		positionUtil.positionFolderName = "views";
		positionUtil.positionChildrenFolderName = "admin";//views.admin
		positionUtil.positionFolderAndChildrenName();
		
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	public void dispose() {
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
}