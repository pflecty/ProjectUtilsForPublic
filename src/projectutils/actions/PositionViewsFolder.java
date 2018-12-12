package projectutils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.PositionUtil;

/**
 * 定位到视图
 *
 */
public class PositionViewsFolder implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;
	
	
	public PositionViewsFolder() {
		positionUtil = new PositionUtil();
	}
	
	public void run(IAction action) {
		
		//定位到视图
		positionUtil.positionFolderName = "views";
		positionUtil.positionFolder();
		
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	public void dispose() {
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
}