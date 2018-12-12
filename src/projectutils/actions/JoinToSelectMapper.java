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
 * ��ת��ѡ��Mapper.xml
 * mybatis��Ŀ��ʹ��
 * @author Administrator
 */
public class JoinToSelectMapper implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;
	
	public JoinToSelectMapper() {
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
		clooseText = CommonUtil.toUpperCaseFirstOne(clooseText) + "Mapper";
		
		//��ת
		joinToFile(clooseText);
		
	}
	
	//��ת����jsp
	private void joinToFile(String clooseText) {
		positionUtil.positionFileInView("src", clooseText + ".xml", true);
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
	
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
   
}