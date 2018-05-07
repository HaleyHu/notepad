package org.hbin.notepad.filefilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * TXT文件过滤器
 * @author 
 */
public class TxtFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".txt");
	}

	@Override
	public String getDescription() {
		return "*.txt(文本文件)";
	}
}