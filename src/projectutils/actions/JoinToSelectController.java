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
 * 跳转到选中Controller
 * 比如鼠标选中控制类EntryOrdersController@RequestMapping("/entryOrders")那么跳转到EntryOrdersController处
 * @author Administrator
 */
public class JoinToSelectController implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;
	private PositionUtil positionUtil = null;
	
	public JoinToSelectController() {
		positionUtil = new PositionUtil();
	}

	public void run(IAction action) {
		
		//获取选中内容
		//当前项目
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			return;
		}
		
		IEditorPart editor = page.getActiveEditor();
		
		if(editor == null){
			return;
		}
		
		//鼠标选中的文本
		String clooseText = null;
		
		ITextSelection textSelection = (ITextSelection) editor.getEditorSite().getSelectionProvider().getSelection();
		//获取选中文本
		clooseText = textSelection.getText();
		
		if(CommonUtil.isNotNull(clooseText) && clooseText.length() < 255){
			
		}else{
			clooseText = PlugUtil.getChooseFileName();
		}
		
		clooseText = CommonUtil.getRetainWords(clooseText);
		clooseText = CommonUtil.toUpperCaseFirstOne(clooseText) + "Controller";
			
		//查询jsp view，并跳转到该jsp
		joinToFile(clooseText);
		
		
	}
	
	//跳转到该jsp
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