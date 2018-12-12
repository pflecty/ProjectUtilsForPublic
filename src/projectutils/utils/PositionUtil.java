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
 * 位置工具类
 * @author Administrator
 *
 */
public class PositionUtil {
	
	//定位文件夹名称
	public static String positionFolderName = null;
	//定位文件夹的子文件名称
	public static String positionChildrenFolderName;
	//是否继续执行
	static boolean isGo = false;
	//临时变量
	static String tempVar = null;
	
	//是否maven项目
	static boolean isMavenProject = false;
	static boolean isHaveAdminPath = false;

	public static void main(String[] args) {

	}
	
	/**
	 * 文件夹定位
	 * 说明：定位控制类文件夹controller不会定位到admin文件夹
	 * 该方法只定位单独文件或文件夹
	 * 常量positionFolderName是需要搜索的文件夹名称
	 */
	public void positionFolder(){
		
		//当前项目
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			System.out.println("page为空");
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
				
				//遍历项目子元素-root
		   		for (IResource childResource : project.members()) {
		   			
		   			if(childResource instanceof IFolder){//是文件夹
		   				
		   				//过滤.开头的文件夹
		   				if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
		   						&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
		   					if(childResource.getName().indexOf(positionFolderName) != -1){//controller
		   						IFolder tempIFolder = childResource.getProject().getFolder(childResource.getName());
           					    //定位
		   						PlugUtil.toPackageExplorerPartForIFolder(page, tempIFolder);
		   						return;
		   					}
		   					
		   					//查询不到继续遍历
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
	  * 通过递归得到某一路径下所有的目录及其文件
	  */
	 static void traverseFolder(IWorkbenchPage page, IResource resource) throws CoreException{
		 IResource resources[] = ((IFolder) resource).members();//下级的
		 for (IResource childResource : resources) {
			 if(childResource instanceof IFolder){//是文件夹
	   				
   				//过滤.开头的文件夹
   				if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
   						&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
   					if(childResource.getName().indexOf(positionFolderName) != -1){//controller
   						IFolder tempIFolder = (IFolder) childResource;//转换
   						
   						//使用第一个子元素定位
   						if(tempIFolder.members() != null && tempIFolder.members().length > 0){
   							
   							IResource resources1[] = tempIFolder.members();//下级的
   							for (IResource iResource : resources1) {
   								if(iResource instanceof IFile){
   									
   									IFile tempIFile = (IFile) iResource;//转换
   									//文件定位
   									PlugUtil.toPackageExplorerPartForIFile(page, tempIFile);
   		   	   						return;
   								}else if(iResource instanceof IFolder){
   									IFolder tempIFolder1 = (IFolder) iResource;//转换
   									//文件夹定位
   									PlugUtil.toPackageExplorerPartForIFolder(page, tempIFolder1);
   								}
							}
   						}else{
   							//文件夹定位
   							PlugUtil.toPackageExplorerPartForIFolder(page, tempIFolder);
   						}
   						return;
   					}
   					
   					//查询不到继续遍历
   					traverseFolder(page, childResource);
   				}
			}
		}
	 }
	 

	 
	/**
	 * 定位指定文件夹下面的文件或文件夹
	 * 排除maven的
	 * 定位控制类文件夹controller会定位到admin文件夹
	 * 常量positionFolderName是需要搜索的文件夹名称
	 */
	public void positionFolderAndChildrenName() {
		//当前项目
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			System.out.println("page为空");
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
				
				//遍历项目子元素-root
		   		for (IResource childResource : project.members()) {
		   			
		   			if(childResource instanceof IFolder){//是文件夹
		   				
		   				//过滤.开头的文件夹
		   				if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
		   						&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
		   					//如果找到父级就在子级找
		   					if(childResource.getName().indexOf(positionFolderName) != -1){//controller
		   						
		   						IResource resources[] = ((IFolder) childResource).members();//下级的
		   						
		   						for (IResource childResource1 : resources) {
		   						 if(childResource1 instanceof IFolder){//是文件夹
		   							 
		   							//过滤.开头的文件夹
		   			   				if(!childResource1.getName().startsWith(".") && childResource1.getName().indexOf("classes") == -1
		   			   					&& (isMavenProject == false || ( isMavenProject == true && !childResource1.getName().equals("target"))) ){
		   			   					
			   			   				if(childResource1.getName().indexOf(positionChildrenFolderName) != -1){//admin
			   			   					
			   			   					IFolder tempIFolder = (IFolder) childResource1;//转换
			   			   					
			   			   					//使用第一个子元素定位
			   			   					boolean isDone = forMemberPosition(tempIFolder, page);
			   			   					if(isDone){
			   			   						return;
			   			   					}
			   			   				}
		   			   				}
		   						 }
		   						}
		   					}
		   					
		   					//查询不到继续遍历
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
	  * 通过递归得到某一路径下所有的目录及其文件
	  */
	 static void traverseFolderAndChildren(IWorkbenchPage page, IResource resource) throws CoreException{
		 IResource resources[] = ((IFolder) resource).members();//下级的
		 for (IResource childResource : resources) {
			 if(childResource instanceof IFolder){//是文件夹
	   				
  				//过滤.开头的文件夹
  				if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
  						&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
  					
  					if(childResource.getName().indexOf(positionFolderName) != -1){//controller
  						
  						IResource resources1[] = ((IFolder) childResource).members();//下级的
  						
  						for (IResource childResource1 : resources1) {
	   						 if(childResource1 instanceof IFolder){//是文件夹
	   							 
	   							//过滤.开头的文件夹
	   			   				if(!childResource1.getName().startsWith(".") && childResource1.getName().indexOf("classes") == -1
	   			   					&& (isMavenProject == false || ( isMavenProject == true && !childResource1.getName().equals("target"))) ){
	   			   					
		   			   				if(childResource1.getName().indexOf(positionChildrenFolderName) != -1){//admin
		   			   					
		   			   					IFolder tempIFolder = (IFolder) childResource1;//转换
		   			   					//使用第一个子元素定位
		   			   					boolean isDone = forMemberPosition(tempIFolder, page);
			   			   				if(isDone){
		   			   						return;
		   			   					}
		   			   				}
	   			   				}
	   						 }
  						}
  					}
  					
  					//查询不到继续遍历
  					traverseFolderAndChildren(page, childResource);
  				}
			}
		}
	 }
	 
	 /**
	  * 通过子级定位，选中子级第一个文件或文件夹
	 * @throws CoreException 
	 * @throws PartInitException 
	  */
	 public static boolean forMemberPosition(IFolder tempIFolder, IWorkbenchPage page) throws PartInitException, CoreException{
		//使用第一个子元素定位
		if(tempIFolder != null && tempIFolder.exists() && tempIFolder.members() != null && tempIFolder.members().length > 0){
			
			IResource resources1[] = tempIFolder.members();//下级的
			for (IResource iResource : resources1) {
				
				if(iResource instanceof IFile){
				
					IFile tempIFile = (IFile) iResource;//转换
					//文件定位
					PlugUtil.toPackageExplorerPartForIFile(page, tempIFile);
					return true;
				}else if(iResource instanceof IFolder){
					IFolder tempIFolder1 = (IFolder) iResource;//转换
					//文件夹定位
					PlugUtil.toPackageExplorerPartForIFolder(page, tempIFolder1);
				}
			}
		}
		 
		 return false;
	 }
	 
	 
	/**
	 * 定位到当前文件
	 */
	public static void positionCurFile(){
		try {
			// 取得工作台
			IWorkbench workbench = PlatformUI.getWorkbench();
			if (workbench != null) {
				// 有时不是从视图，而是要从外部取得IWorkbenchPage，例如从菜单或者工具栏等，这时，可以使用下面的方法：
				// IWorkbenchPage workbenchPage =
				// Plugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
				// 取得工作台窗口
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
	
				if (window != null) {
					// 取得工作台页面
					IWorkbenchPage page = window.getActivePage();
					if (page != null) {
	
						// 取得当前处于活动状态的编辑器窗口
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
	 * 在文件中查询
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
				new FileInputStream(file), "UTF-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			// 指定字符串判断处
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
	 * 定位文件在views文件夹中
	 * 说明：在文件夹findFolderName中查找定位文件findFileName
	 * @param isOpenDoc 是否以默认方式打开文件
	 * @param isHaveAdminPath 
	 */
	public void positionFileInView(String findFolderName, String findFileName, boolean isOpenDoc, boolean isHaveAdminPath) {
		
		isGo = false;
		positionFolderName = findFolderName;
		tempVar = findFileName;
		
		//是否maven项目
		isMavenProject = false;
		this.isHaveAdminPath = isHaveAdminPath;
		
		//当前项目
		IWorkbenchPage page = PlugUtil.getCurIWorkbenchPage();
		if(page == null){
			System.out.println("page为空");
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
				
				//遍历项目子元素-root
		   		for (IResource childResource : project.members()) {
		   			
		   			if(childResource instanceof IFile && isGo){//是文件
						 
						 if(childResource.getName().equals(tempVar)){
							 
							 //找到了执行文件定位
							 IFile tempIFile = (IFile) childResource;//转换
							 
							 PlugUtil.toPackageExplorerPartForIFile(page, tempIFile);
							
							 if(isOpenDoc){
								 
								 if (tempIFile != null) {
									 //打开
									 IDE.openEditor(page, tempIFile);
					             }
							 }
							 return ;
						 }
						 
					}else{
						
						if(childResource instanceof IFolder){//是文件夹
							
							//过滤.开头的文件夹，maven不遍历target
							if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1 
									&& (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target"))) ){
										
								//存在views表示可以执行文件定位了
								if(childResource.getName().equals(positionFolderName)){
									isGo = true;
								}
								
								//查询不到继续遍历
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
	  * 通过递归得到某一路径下所有的目录及其文件
	  * 配合上面的positionFileInView方法使用
	 * @param isOpenDoc 
	  */
	 private static void traverseFolderTemp(IWorkbenchPage page, IResource resource, boolean isOpenDoc) throws CoreException{
		 
		 IResource resources[] = ((IFolder) resource).members();//下级的
		 
		 for (IResource childResource : resources) {
			 
			 if(childResource instanceof IFile && isGo){//是文件
				 
				 if(childResource.getName().equals(tempVar)){
					 //找到了执行文件定位
					 IFile tempIFile = (IFile) childResource;//转换
					 PlugUtil.toPackageExplorerPartForIFile(page, tempIFile);
					 if(isOpenDoc){
						 if (tempIFile != null) {
							 IDE.openEditor(page, tempIFile);
			             }
					 }
					 return ;
				 }
				 
			 }else{
				 
				 if(childResource instanceof IFolder){//是文件夹
					 
					 //过滤.开头的文件夹
					 if(!childResource.getName().startsWith(".") && childResource.getName().indexOf("classes") == -1
							 && (isMavenProject == false || ( isMavenProject == true && !childResource.getName().equals("target")))){
						 //存在views表示可以执行文件定位了
						 if(childResource.getName().equals(positionFolderName)){
							 isGo = true;
						 }
						 
						 //查询不到继续遍历
						 traverseFolderTemp(page, childResource, isOpenDoc);
					 }
				 }
				 
			 }
		}
	 }
	 
}
