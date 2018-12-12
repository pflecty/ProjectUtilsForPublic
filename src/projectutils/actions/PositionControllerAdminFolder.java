package projectutils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.PositionUtil;

/**
 * 定位到控制类文件夹下面的admin
 */
public class PositionControllerAdminFolder implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;

	
	public PositionControllerAdminFolder() {
		positionUtil = new PositionUtil();
	}
	
	public void run(IAction action) {
		
		//定位指定文件夹下面的文件或文件夹
		positionUtil.positionFolderName = "controller";
		positionUtil.positionChildrenFolderName = "admin";//controller.admin
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