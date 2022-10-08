package cx.ksim.files_server;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class DirEd {

	public static void main(String[] args) throws Exception {

		Path here = FileSystems.getDefault().getPath(".").toAbsolutePath();
		System.out.printf("Here is absolute: %s\n",here.isAbsolute());

		getPath(".");
	}

	public static void getPath(String path) throws IOException {
		Path jdkPath = Paths.get(path);
		
		if(!jdkPath.isAbsolute()) jdkPath = jdkPath.toAbsolutePath();
		
		DirectoryStream<Path> stream = Files.newDirectoryStream(jdkPath);
		try { 
			Iterator<Path> iterator = stream.iterator();
			while(iterator.hasNext()) {
				Path p = iterator.next().normalize();
				System.out.println(p); 
			}
		} finally { 
			stream.close(); 
		} 
	}
	
	public static String toUl(String path) {
		
		return "";
	}
}
