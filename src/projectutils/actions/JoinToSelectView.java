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
 * ��ת��ѡ�е�View
 * ��תѡ����ͼjspҳ��
 * @author Administrator
 */
public class JoinToSelectView implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;
	
	public JoinToSelectView() {
		positionUtil = new PositionUtil();
	}

	public void run(IAction action) {
		
		//��ȡѡ������
		//��ǰ��Ŀ
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			System.out.println("pageΪ��");
			return;
		}
		
		IEditorPart editor = page.getActiveEditor();
		
		if(editor == null){
			System.out.println("editorΪ��");
			return;
		}
		
		//��ǰ��Ŀ
		IEditorInput input = editor.getEditorInput();
		
		boolean isHaveAdminPath = false;
		IFile file = (IFile) input.getAdapter(IFile.class);
		 if (file != null) {
			 //��ǰ�༭�ļ���·��
			String path = file.getLocation().toString();
			if(path.indexOf("admin") != -1){
				isHaveAdminPath = true;
			}
		 }

		//���ѡ�е��ı�
		String clooseText = null;
		
		ITextSelection textSelection = (ITextSelection) editor.getEditorSite().getSelectionProvider().getSelection();
		
		//��ȡѡ���ı�
		clooseText = textSelection.getText();
		
		if(CommonUtil.isNotNull(clooseText) && clooseText.length() < 255){
			//��ѯjsp view������ת����jsp
			joinToFile(clooseText.trim(), isHaveAdminPath);
			
		}else{
			//��ѯjsp view������ת����jsp
			joinToFile("404", isHaveAdminPath);
		}
	}
	
	//��ת����jsp
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