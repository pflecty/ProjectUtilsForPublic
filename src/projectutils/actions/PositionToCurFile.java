package projectutils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.PositionUtil;

/**
 * 定位到当前文件
 *
 */
public class PositionToCurFile implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;
	
	public PositionToCurFile() {
		positionUtil = new PositionUtil();
	}
	
	public void run(IAction action) {
		
		//定位到当前文件
		positionUtil.positionCurFile();
		
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	public void dispose() {
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
}