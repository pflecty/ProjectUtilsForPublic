package projectutils.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * λ�ù�����
 * @author Administrator
 *
 */
public class PositionUtil {
	
	//��λ�ļ�������
	public static String positionFolderName = null;
	//��λ�ļ��е����ļ�����
	public static String positionChildrenFolderName;
	//�Ƿ����ִ��
	static boolean isGo = false;
	//��ʱ����
	static String tempVar = null;
	
	//�Ƿ�maven��Ŀ
	static boolean isMavenProject = false;
	static boolean isHaveAdminPath = false;

	public static void main(String[] args) {

	}
	
	/**
	 * �ļ��ж�λ
	 * ˵������λ�������ļ���controller���ᶨλ��admin�ļ���
	 * �÷���ֻ��λ�����ļ����ļ���
	 * ����positionFolderName����Ҫ�������ļ�������
	 */
	public void positionFolder(){
		
		//��ǰ��Ŀ
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			System.out.println("pageΪ��");
			return;
		}
		
		IProject project = PlugUtil.getCurIProject(page);
		
		IFile classpathFile = project.getFile(".classpath");
		
		if(classpathFile.exists()){
			try {
				
				if(findKeyInFile(classpathFile.getLocation().toString(), "maven")){
					isMavenProject = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try{
			if(project != null && project.exists()){
				
				//������Ŀ��Ԫ��-root
		   		for (IResource childResource : project.members()) {
		   			
		   			if(childResource instanceof IFolder){//���ļ���
		   				
		   				//����.��ͷ���ļ���
		   				if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
		   						&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
		   					if(childResource.getName().indexOf(positionFolderName) != -1){//controller
		   						IFolder tempIFolder = childResource.getProject().getFolder(childResource.getName());
           					    //��λ
		   						PlugUtil.toPackageExplorerPartForIFolder(page, tempIFolder);
		   						return;
		   					}
		   					
		   					//��ѯ������������
		   					traverseFolder(page, childResource);
		   				}
	       			}
		   		}
			}
		} catch (Exception e) {
			System.err.println("error=" + e.getMessage());
		}
	}
	
	 /**
	  * ͨ���ݹ�õ�ĳһ·�������е�Ŀ¼�����ļ�
	  */
	 static void traverseFolder(IWorkbenchPage page, IResource resource) throws CoreException{
		 IResource resources[] = ((IFolder) resource).members();//�¼���
		 for (IResource childResource : resources) {
			 if(childResource instanceof IFolder){//���ļ���
	   				
   				//����.��ͷ���ļ���
   				if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
   						&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
   					if(childResource.getName().indexOf(positionFolderName) != -1){//controller
   						IFolder tempIFolder = (IFolder) childResource;//ת��
   						
   						//ʹ�õ�һ����Ԫ�ض�λ
   						if(tempIFolder.members() != null && tempIFolder.members().length > 0){
   							
   							IResource resources1[] = tempIFolder.members();//�¼���
   							for (IResource iResource : resources1) {
   								if(iResource instanceof IFile){
   									
   									IFile tempIFile = (IFile) iResource;//ת��
   									//�ļ���λ
   									PlugUtil.toPackageExplorerPartForIFile(page, tempIFile);
   		   	   						return;
   								}else if(iResource instanceof IFolder){
   									IFolder tempIFolder1 = (IFolder) iResource;//ת��
   									//�ļ��ж�λ
   									PlugUtil.toPackageExplorerPartForIFolder(page, tempIFolder1);
   								}
							}
   						}else{
   							//�ļ��ж�λ
   							PlugUtil.toPackageExplorerPartForIFolder(page, tempIFolder);
   						}
   						return;
   					}
   					
   					//��ѯ������������
   					traverseFolder(page, childResource);
   				}
			}
		}
	 }
	 

	 
	/**
	 * ��λָ���ļ���������ļ����ļ���
	 * �ų�maven��
	 * ��λ�������ļ���controller�ᶨλ��admin�ļ���
	 * ����positionFolderName����Ҫ�������ļ�������
	 */
	public void positionFolderAndChildrenName() {
		//��ǰ��Ŀ
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			System.out.println("pageΪ��");
			return;
		}
		
		IProject project = PlugUtil.getCurIProject(page);
		
		IFile classpathFile = project.getFile(".classpath");
		
		if(classpathFile.exists()){
			try {
				
				if(findKeyInFile(classpathFile.getLocation().toString(), "maven")){
					isMavenProject = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try{
			if(project != null && project.exists()){
				
				//������Ŀ��Ԫ��-root
		   		for (IResource childResource : project.members()) {
		   			
		   			if(childResource instanceof IFolder){//���ļ���
		   				
		   				//����.��ͷ���ļ���
		   				if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
		   						&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
		   					//����ҵ����������Ӽ���
		   					if(childResource.getName().indexOf(positionFolderName) != -1){//controller
		   						
		   						IResource resources[] = ((IFolder) childResource).members();//�¼���
		   						
		   						for (IResource childResource1 : resources) {
		   						 if(childResource1 instanceof IFolder){//���ļ���
		   							 
		   							//����.��ͷ���ļ���
		   			   				if(!childResource1.getName().startsWith(".") && childResource1.getName().indexOf("classes") == -1
		   			   					&& (isMavenProject == false || ( isMavenProject == true && !childResource1.getName().equals("target"))) ){
		   			   					
			   			   				if(childResource1.getName().indexOf(positionChildrenFolderName) != -1){//admin
			   			   					
			   			   					IFolder tempIFolder = (IFolder) childResource1;//ת��
			   			   					
			   			   					//ʹ�õ�һ����Ԫ�ض�λ
			   			   					boolean isDone = forMemberPosition(tempIFolder, page);
			   			   					if(isDone){
			   			   						return;
			   			   					}
			   			   				}
		   			   				}
		   						 }
		   						}
		   					}
		   					
		   					//��ѯ������������
		   					traverseFolderAndChildren(page, childResource);
		   					
		   				}
	       			}
		   		}
			}
		} catch (Exception e) {
			System.err.println("error=" + e.getMessage());
		}
	}
	
	 /**
	  * ͨ���ݹ�õ�ĳһ·�������е�Ŀ¼�����ļ�
	  */
	 static void traverseFolderAndChildren(IWorkbenchPage page, IResource resource) throws CoreException{
		 IResource resources[] = ((IFolder) resource).members();//�¼���
		 for (IResource childResource : resources) {
			 if(childResource instanceof IFolder){//���ļ���
	   				
  				//����.��ͷ���ļ���
  				if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
  						&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
  					
  					if(childResource.getName().indexOf(positionFolderName) != -1){//controller
  						
  						IResource resources1[] = ((IFolder) childResource).members();//�¼���
  						
  						for (IResource childResource1 : resources1) {
	   						 if(childResource1 instanceof IFolder){//���ļ���
	   							 
	   							//����.��ͷ���ļ���
	   			   				if(!childResource1.getName().startsWith(".") && childResource1.getName().indexOf("classes") == -1
	   			   					&& (isMavenProject == false || ( isMavenProject == true && !childResource1.getName().equals("target"))) ){
	   			   					
		   			   				if(childResource1.getName().indexOf(positionChildrenFolderName) != -1){//admin
		   			   					
		   			   					IFolder tempIFolder = (IFolder) childResource1;//ת��
		   			   					//ʹ�õ�һ����Ԫ�ض�λ
		   			   					boolean isDone = forMemberPosition(tempIFolder, page);
			   			   				if(isDone){
		   			   						return;
		   			   					}
		   			   				}
	   			   				}
	   						 }
  						}
  					}
  					
  					//��ѯ������������
  					traverseFolderAndChildren(page, childResource);
  				}
			}
		}
	 }
	 
	 /**
	  * ͨ���Ӽ���λ��ѡ���Ӽ���һ���ļ����ļ���
	 * @throws CoreException 
	 * @throws PartInitException 
	  */
	 public static boolean forMemberPosition(IFolder tempIFolder, IWorkbenchPage page) throws PartInitException, CoreException{
		//ʹ�õ�һ����Ԫ�ض�λ
		if(tempIFolder != null && tempIFolder.exists() && tempIFolder.members() != null && tempIFolder.members().length > 0){
			
			IResource resources1[] = tempIFolder.members();//�¼���
			for (IResource iResource : resources1) {
				
				if(iResource instanceof IFile){
				
					IFile tempIFile = (IFile) iResource;//ת��
					//�ļ���λ
					PlugUtil.toPackageExplorerPartForIFile(page, tempIFile);
					return true;
				}else if(iResource instanceof IFolder){
					IFolder tempIFolder1 = (IFolder) iResource;//ת��
					//�ļ��ж�λ
					PlugUtil.toPackageExplorerPartForIFolder(page, tempIFolder1);
				}
			}
		}
		 
		 return false;
	 }
	 
	 
	/**
	 * ��λ����ǰ�ļ�
	 */
	public static void positionCurFile(){
		try {
			// ȡ�ù���̨
			IWorkbench workbench = PlatformUI.getWorkbench();
			if (workbench != null) {
				// ��ʱ���Ǵ���ͼ������Ҫ���ⲿȡ��IWorkbenchPage������Ӳ˵����߹������ȣ���ʱ������ʹ������ķ�����
				// IWorkbenchPage workbenchPage =
				// Plugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
				// ȡ�ù���̨����
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
	
				if (window != null) {
					// ȡ�ù���̨ҳ��
					IWorkbenchPage page = window.getActivePage();
					if (page != null) {
	
						// ȡ�õ�ǰ���ڻ״̬�ı༭������
						IEditorPart editor = page.getActiveEditor();
						if (editor != null) {
							
							IEditorInput input = editor.getEditorInput();
	
							if ((input != null)
									&& ((input instanceof IFileEditorInput))) {
	
								IFile file = ((IFileEditorInput) input).getFile();
								if (file != null) {
	
									PackageExplorerPart packageView = (PackageExplorerPart) page.showView(org.eclipse.jdt.ui.JavaUI.ID_PACKAGES);
									packageView.selectReveal(new StructuredSelection(file));
	
								}
							}
						}
	
					}
				}
			}
		
		} catch (Exception e) {
			System.err.println("error=" + e.getMessage());
		}
	}
	
	
	public static String getProjectPath()
	{
          String path = null;
          path = Platform.getLocation().toString();
          return path;
	 }
	
	/**
	 * ���ļ��в�ѯ
	 * @param path
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static boolean findKeyInFile(String path, String key)
			throws IOException {

		boolean isHave = false;
		File file = new File(path);
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), "UTF-8");// ���ǵ������ʽ
		BufferedReader bufferedReader = new BufferedReader(read);

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			// ָ���ַ����жϴ�
			if (line.contains(key)) {
				isHave = true;
				return isHave;
			}
		}

		return isHave;
	}
	
	public void positionFileInView(String findFolderName, String findFileName, boolean isOpenDoc) {
		positionFileInView(findFolderName, findFileName, isOpenDoc, false);
	}
	
	/**
	 * ��λ�ļ���views�ļ�����
	 * ˵�������ļ���findFolderName�в��Ҷ�λ�ļ�findFileName
	 * @param isOpenDoc �Ƿ���Ĭ�Ϸ�ʽ���ļ�
	 * @param isHaveAdminPath 
	 */
	public void positionFileInView(String findFolderName, String findFileName, boolean isOpenDoc, boolean isHaveAdminPath) {
		
		isGo = false;
		positionFolderName = findFolderName;
		tempVar = findFileName;
		
		//�Ƿ�maven��Ŀ
		isMavenProject = false;
		this.isHaveAdminPath = isHaveAdminPath;
		
		//��ǰ��Ŀ
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			System.out.println("pageΪ��");
			return;
		}
		
		IProject project = PlugUtil.getCurIProject(page);
		
		IFile classpathFile = project.getFile(".classpath");
		
		if(classpathFile.exists()){
			
			try {
				
				if(findKeyInFile(classpathFile.getLocation().toString(), "maven")){
					isMavenProject = true;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try{
			if(project != null && project.exists()){
				
				//������Ŀ��Ԫ��-root
		   		for (IResource childResource : project.members()) {
		   			
		   			if(childResource instanceof IFile && isGo){//���ļ�
						 
						 if(childResource.getName().equals(tempVar)){
							 
							 //�ҵ���ִ���ļ���λ
							 IFile tempIFile = (IFile) childResource;//ת��
							 
							 PlugUtil.toPackageExplorerPartForIFile(page, tempIFile);
							
							 if(isOpenDoc){
								 
								 if (tempIFile != null) {
									 //��
									 IDE.openEditor(page, tempIFile);
					             }
							 }
							 return ;
						 }
						 
					}else{
						
						if(childResource instanceof IFolder){//���ļ���
							
							//����.��ͷ���ļ��У�maven������target
							if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1 
									&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
										
								//����views��ʾ����ִ���ļ���λ��
								if(childResource.getName().equals(positionFolderName)){
									isGo = true;
								}
								
								//��ѯ������������
								traverseFolderTemp(page, childResource, isOpenDoc);
							}
						}
						
					}
		   		}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 /**
	  * ͨ���ݹ�õ�ĳһ·�������е�Ŀ¼�����ļ�
	  * ��������positionFileInView����ʹ��
	 * @param isOpenDoc 
	  */
	 private static void traverseFolderTemp(IWorkbenchPage page, IResource resource, boolean isOpenDoc) throws CoreException{
		 
		 IResource resources[] = ((IFolder) resource).members();//�¼���
		 
		 for (IResource childResource : resources) {
			 
			 if(childResource instanceof IFile && isGo){//���ļ�
				 
				 if(childResource.getName().equals(tempVar)){
					 //�ҵ���ִ���ļ���λ
					 IFile tempIFile = (IFile) childResource;//ת��
					 PlugUtil.toPackageExplorerPartForIFile(page, tempIFile);
					 if(isOpenDoc){
						 if (tempIFile != null) {
							 IDE.openEditor(page, tempIFile);
			             }
					 }
					 return ;
				 }
				 
			 }else{
				 
				 if(childResource instanceof IFolder){//���ļ���
					 
					 //����.��ͷ���ļ���
					 if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
							 && (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target")))){
						 //����views��ʾ����ִ���ļ���λ��
						 if(childResource.getName().equals(positionFolderName)){
							 isGo = true;
						 }
						 
						 //��ѯ������������
						 traverseFolderTemp(page, childResource, isOpenDoc);
					 }
				 }
				 
			 }
		}
	 }
	 
}
