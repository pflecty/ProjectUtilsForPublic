package projectutils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import projectutils.utils.CommonUtil;
import projectutils.utils.PlugUtil;
import projectutils.utils.PositionUtil;

/**
 * ��ת��ѡ��Controller
 * �������ѡ�п�����EntryOrdersController@RequestMapping("/entryOrders")��ô��ת��EntryOrdersController��
 * @author Administrator
 */
public class JoinToSelectController implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;
	
	public JoinToSelectController() {
		positionUtil = new PositionUtil();
	}

	public void run(IAction action) {
		
		//��ȡѡ������
		//��ǰ��Ŀ
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			return;
		}
		
		IEditorPart editor = page.getActiveEditor();
		
		if(editor == null){
			return;
		}
		
		//���ѡ�е��ı�
		String clooseText = null;
		
		ITextSelection textSelection = (ITextSelection) editor.getEditorSite().getSelectionProvider().getSelection();
		//��ȡѡ���ı�
		clooseText = textSelection.getText();
		
		if(CommonUtil.isNotNull(clooseText) && clooseText.length() < 255){
			
		}else{
			clooseText = PlugUtil.getChooseFileName();
		}
		
		clooseText = CommonUtil.getRetainWords(clooseText);
		clooseText = CommonUtil.toUpperCaseFirstOne(clooseText) + "Controller";
			
		//��ѯjsp view������ת����jsp
		joinToFile(clooseText);
		
		
	}
	
	//��ת����jsp
	private void joinToFile(String clooseText) {
		
		positionUtil.positionFileInView("src", clooseText + ".java", true);
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
	
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
   
}