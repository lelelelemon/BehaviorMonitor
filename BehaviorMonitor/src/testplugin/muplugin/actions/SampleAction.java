package testplugin.muplugin.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import base.PMHibernateImpl;
import bean.ClusterRelation;
import bean.Duration;
import bean.FileReading;
import date.DateUtil;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private String toolTip = "nothing";
	boolean start = false;
	Duration duration = new Duration();
	FileReading filereading;

	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		if (start == false) {
			MessageDialog.openInformation(window.getShell(), "Muplugin",
					"start");
			Date st = new Date();
			duration.setStart(st);
			System.out.println(DateUtil.format(st, "yyyy-MM-dd HH:mm:ss"));
			start = true;
		} else {

			Date stop = new Date();
			duration.setEnd(stop);
			PMHibernateImpl.getInstance().save(duration);
			MessageDialog
					.openInformation(window.getShell(), "Muplugin", "stop");
			System.out.println(DateUtil.format(stop, "yyyy-MM-dd HH:mm:ss"));
			start = false;
			System.exit(0);
		}

	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IEditorPart editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		String filename = editor.getEditorInput().getToolTipText();
		System.out.println("File name is " + filename);
		if (!this.toolTip.equals(filename)) {
			this.toolTip = filename;
			Date date = new Date();
			String path = System.getProperty("user.dir");
			System.out.println("path is " + path);
			FileReading fileReading = new FileReading();
			fileReading.setName(this.toolTip);
			fileReading.setPath(path);
			fileReading.setStartTime(date);
			PMHibernateImpl.getInstance().save(fileReading);
			List<String> addresses = PMHibernateImpl.getInstance()
					.retrieveRecomWeb(filename);
			int i = 0;
			List<String> recoms = new ArrayList<String>();
			for (String ad : addresses) {

				if (i > 3)
					break;
				if (recoms.contains(ad))
					continue;
				i++;
				recoms.add(ad);
				List<ClusterRelation> clusterRelas = PMHibernateImpl
						.getInstance().retrieveClusterRelaByAddress(ad);
				if (!clusterRelas.equals(null) && clusterRelas.size() != 0) {
					ClusterRelation re = clusterRelas.get(0);
					System.out.println(re.getScore());
					List<ClusterRelation> clusterRelaLabel = PMHibernateImpl
							.getInstance().retrieveClusterRelaByLabel(
									re.getName());
					for (ClusterRelation cr : clusterRelaLabel) {
						if (!recoms.contains(cr.getMetaFile())
								&& cr.getScore() != 0) {
							recoms.add(cr.getMetaFile());
						}
					}

					recoms.add(re.getScore() + "\n");

				}

			}
			System.out.println("Start Recom\n");
			for (String rec : recoms) {
				System.out.println(rec);
			}

			System.out.println("Start Recom\n");
		}
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 * 
	 * @throws org.eclipse.core.commands.ExecutionException
	 */

}