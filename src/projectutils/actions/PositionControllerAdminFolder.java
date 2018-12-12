package projectutils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.PositionUtil;

/**
 * ��λ���������ļ��������admin
 */
public class PositionControllerAdminFolder implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;

	
	public PositionControllerAdminFolder() {
		positionUtil = new PositionUtil();
	}
	
	public void run(IAction action) {
		
		//��λָ���ļ���������ļ����ļ���
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