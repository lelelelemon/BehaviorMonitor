package Operation;

import io.ReadFromFile;

import java.io.File;
import java.util.List;

import base.PMHibernateImpl;
import bean.FileClassRelation;
import bean.MetaFile;

//extract all files in a project
public class FileExtraction {
	public static void main(String[] args) {
		String path = System.getProperty("user.dir");
		System.out.println(path);
		getName(path);
	}

	public static void getName(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] dirFile = file.listFiles();
			for (File f : dirFile)
				if (f.isDirectory())
					getName(f.getAbsolutePath());
				else {
					if (f.getName().endsWith(".java")) {
						String f_path = f.getPath();
						System.out.println(f_path);
						List<String> contents = ReadFromFile
								.readFileByLines(f_path);
						for (String line : contents) {
							if (line.startsWith("import")) {
								String[] class_import = line.split(" ");
								System.out.println(class_import[1]);
								String class_name = class_import[1];
								FileClassRelation fcr = new FileClassRelation();
								fcr.setFileAbsName(f_path);
								fcr.setClassName(class_import[1]);
								PMHibernateImpl.getInstance().save(fcr);
								
							}
						}
						MetaFile mf = new MetaFile();
						mf.setPath(f_path);
						// mf.setContents(contents);
						// PMHibernateImpl.getInstance().save(mf);
					}
				}
		}
	}
}
