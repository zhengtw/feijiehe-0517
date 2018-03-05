package com.jfhealthcare.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xujinma
 */
@Slf4j
public class FileUtil {
	// 判断文件是否存在,不存在创建
	public static void judeFileExists(String file) {
		judeFileExists(new File(file));
	}

	public static void judeFileExists(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error("文件不存在，创建时异常！", e);
			}
		}

	}

	// 判断文件夹是否存在,不存在创建
	public static void judeDirExists(String file) {
		judeDirExists(new File(file));
	}

	public static void judeDirExists(File file) {
		if (!file.exists()) {
			try {
				file.mkdir();
			} catch (Exception e) {
				log.error("文件目录不存在，创建时异常！", e);
			}
		}

	}
}
