package projectutils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.PositionUtil;

/**
 * ��λ��Admin��ͼ
 *
 */
public class PositionAdminViewsFolder implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;

	
	public PositionAdminViewsFolder() {
		positionUtil = new PositionUtil();
	}
	
	public void run(IAction action) {
		
		//��λָ���ļ���������ļ����ļ���
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