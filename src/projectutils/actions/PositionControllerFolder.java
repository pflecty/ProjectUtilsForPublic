package projectutils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.PositionUtil;

/**
 * ��λ���������ļ���
 */
public class PositionControllerFolder implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;

	
	public PositionControllerFolder() {
		positionUtil = new PositionUtil();
	}
	
	public void run(IAction action) {
		
		positionUtil.positionFolderName = "controller";
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