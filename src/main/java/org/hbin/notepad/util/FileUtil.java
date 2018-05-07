package org.hbin.notepad.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtil {
	private static String charsetName;
	
	/**
	 * 保存文本内容到文本文件中
	 * @param file 文本文件。
	 * @param content 文本内容。\n表示换行符
	 * @return true：保存成功/false：保存失败
	 */
	public static boolean saveFile(String file, String content){
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), charsetName));
			for (String value : content.split("\n")) {
				writer.write(value);
				writer.newLine();	//换行
			}
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public static String readFile(String f){
		BufferedReader br = null;
		try {
			charsetName = codeString(f);
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), charsetName));
			StringBuilder b = new StringBuilder();
			String s = null;
			while((s=br.readLine())!=null){
				b.append(s).append("\n");
			}
			return b.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 判断文件的编码格式
	 * @param fileName :file
	 * @return 文件编码格式
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String codeString(String fileName) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
				fileName));
		int p = (bin.read() << 8) + bin.read();
		String code = null;

		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			code = "GBK";
		}

		return code;
	}
}
