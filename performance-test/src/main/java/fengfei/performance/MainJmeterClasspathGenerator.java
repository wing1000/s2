package fengfei.performance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Modify jmeter/bin/user.properties, change search_paths and user.classpath.
 * 
 */
public class MainJmeterClasspathGenerator {

	private static void addDirPrefix(String[] array, String prefix) {
		for (int i = 0; i < array.length; i++) {
			array[i] = prefix + "/" + array[i];
		}
	}

	private static String[] listJars(File dir) {
		String[] fileNames = dir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		return fileNames;
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws Exception {

		MainJmeterClasspathGenerator generator = new MainJmeterClasspathGenerator(
				"../", "test_sampler/lib", "test_sampler/sampler",
				System.getProperty("path.separator"));
		generator.generate();

		String depClassPath = generator.getDepClassPath();
		String samplerClassPath = generator.getSamplerClassPath();

		System.out.println("depClassPath: " + depClassPath);
		System.out.println("samplerClassPath: " + samplerClassPath);

		generator.modifyJmeterProperties();
	}

	private String classPathSeparator = System.getProperty("path.separator");
	private String depClassPath;
	private String depDir = "test_sampler/lib";// relative to work dir
	private String samplerClassPath;
	private String samplerDir = "test_sampler/sampler";// relative to work dir
	private File workFile;

	/**
	 * 
	 * @param workDir
	 * @param depDir
	 * @param samplerDir
	 * @param classPathSeparator
	 */
	public MainJmeterClasspathGenerator(String workDir, String depDir,
			String samplerDir, String classPathSeparator) {
		workFile = new File(workDir);
		this.depDir = depDir;
		this.samplerDir = samplerDir;
		this.classPathSeparator = classPathSeparator;

		System.out.println("work dir: " + workFile.getAbsolutePath());
	}

	public void generate() {
		if (!workFile.exists()) {
			System.out.println("work dir not exists: " + workFile);
			return;
		}
		if (!workFile.isDirectory()) {
			System.out.println("work dir is not a directory: "
					+ workFile.getAbsolutePath());
			return;
		}

		// dependency jar
		File depFile = new File(FilenameUtils.concat(
				workFile.getAbsolutePath(), depDir));
		System.out.println("dep lib dir: " + depFile.getAbsolutePath());

		String[] depJars = listJars(depFile);
		if (depJars == null || depJars.length == 0) {
			System.out.println("dep jar not found!");
			return;
		}

		addDirPrefix(depJars, "../../" + depDir);// relative to jmeter/bin
		depClassPath = StringUtils.join(depJars, classPathSeparator);

		// sampler jar
		File samplerFile = new File(FilenameUtils.concat(
				workFile.getAbsolutePath(), samplerDir));
		System.out.println("sampler lib dir: " + samplerFile.getAbsolutePath());

		String[] samplerJars = listJars(samplerFile);
		if (samplerJars == null || samplerJars.length == 0) {
			System.out.println("sampler jar not found!");
			return;
		}

		addDirPrefix(samplerJars, "../../" + samplerDir);// relative to
															// jmeter/bin
		samplerClassPath = StringUtils.join(samplerJars, classPathSeparator);
	}

	/**
	 * Dependency class path .
	 * 
	 * @return
	 */
	public String getDepClassPath() {
		return this.depClassPath;
	}

	/**
	 * Sampler class path.
	 * 
	 * @return
	 */
	public String getSamplerClassPath() {
		return this.samplerClassPath;
	}

	public void modifyJmeterProperties() {
		File propFile = new File(FilenameUtils.concat(
				workFile.getAbsolutePath(), "jmeter/bin/user.properties"));
		if (!propFile.exists()) {
			System.out.println("user.properties not found: "
					+ propFile.getAbsolutePath());
			return;
		}

		// jmeter.properties file contents
		List<String> lines = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propFile);
			lines = IOUtils.readLines(fis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
		if (lines == null) {
			return;
		}

		boolean depPathFound = false;
		boolean samplerPathFound = false;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(propFile)));
			for (String line : lines) {
				if (line.startsWith("user.classpath")) {
					line = "user.classpath=" + getDepClassPath();
					depPathFound = true;

				} else if (line.startsWith("search_paths")) {
					line = "search_paths=" + getSamplerClassPath();
					samplerPathFound = true;
				}

				bw.write(line + System.getProperty("line.separator"));
			}

			if (!depPathFound) {
				// append new
				bw.write(System.getProperty("line.separator"));
				bw.write("user.classpath=" + getDepClassPath());
				bw.write(System.getProperty("line.separator"));
			}
			if (!samplerPathFound) {
				// append new
				bw.write(System.getProperty("line.separator"));
				bw.write("search_paths=" + getSamplerClassPath());
				bw.write(System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
