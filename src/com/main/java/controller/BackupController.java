package com.main.java.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.java.utils.FileUtils;
import com.main.java.utils.NormandyConfig;
import com.main.java.utils.TimeUtils;

/**
 * 备份类
 * @author david.du
 *
 */
@Controller
@RequestMapping("/backup")
public class BackupController {
	
	/**
	 * 初始化方法
	 * @param model
	 * @return
	 */
	@RequestMapping("/init")
	public String backupInit(Model model) {
		String folderPath = NormandyConfig.get("backupPath");//"D:\\backupY\\";
		if(!FileUtils.isFolderExist(folderPath)){
			FileUtils.makeFolder(folderPath);
		}
		ArrayList<String> listFolders = FileUtils.getFolderNameFromFolder(folderPath);
		model.addAttribute("listFolders", listFolders);
		return "backup/backup";
	}
	
	/**
	 * 备份文件方法
	 * @param path
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/backupStart/{path}")
	public String backupStart(@PathVariable String path,Model model) throws IOException {
		path = TimeUtils.getCurrentTime(TimeUtils.FORMAT_DATE_NO) +"_"+path;
		String folderPath = NormandyConfig.get("backupBatPath");//"D:\\backupY\\backup";
		String password = NormandyConfig.get("password");
		String filePath =  NormandyConfig.get("backupPath");
		if(!FileUtils.isFolderExist(folderPath)){
			FileUtils.makeFolder(folderPath);
		}
		String mysqlBatpath = NormandyConfig.get("backupBatPath")+"\\backup.bat";
		String mongoBatpath = NormandyConfig.get("backupBatPath")+"\\backupM.bat";
		if(!FileUtils.isFileExist(mysqlBatpath)){
			FileUtils.makeFile(mysqlBatpath);
		}
		if(!FileUtils.isFileExist(mongoBatpath)){
			FileUtils.makeFile(mongoBatpath);
		}
		String backupPath =  NormandyConfig.get("backupPath")+"\\"+path;
		//創建備份路徑
		FileUtils.makeFolder(backupPath);
		String mysqlBackupCmd = "\""+NormandyConfig.get("installMysqlPath")+"\\mysqldump.exe\" --opt -Q test -uroot -p"+password+" > "+filePath+"\\"+path+"\\mysql.sql";
		String mongoBackupCmd = "\""+NormandyConfig.get("installMongoPath")+"\\mongodump.exe\"   -h 127.0.0.1:27017  -o "+filePath+"\\"+path;
		
		//寫入mysql 備份腳本
		WriteStringToFile5(mysqlBackupCmd,mysqlBatpath);
		//寫入mongo 備份腳本
		WriteStringToFile5(mongoBackupCmd,mongoBatpath);
		//分別執行備份腳本
		processBat(mysqlBatpath);
		processBat(mongoBatpath);
		ArrayList<String> listFolders = FileUtils.getFolderNameFromFolder(filePath);
		model.addAttribute("listFolders", listFolders);
		return "backup/backup";
	}
	
	/**
	 * 恢复文件方法
	 * @param path
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/recoverStart/{path}")
	public String recoverStart(@PathVariable String path,Model model) throws IOException {
		String password = NormandyConfig.get("password");
		String filePath =  NormandyConfig.get("backupPath");
		String mysqlBatpath = NormandyConfig.get("backupBatPath")+"\\recover.bat";
		String mongoBatpath = NormandyConfig.get("backupBatPath")+"\\recoverM.bat";
		if(!FileUtils.isFileExist(mysqlBatpath)){
			FileUtils.makeFile(mysqlBatpath);
		}
		if(!FileUtils.isFileExist(mongoBatpath)){
			FileUtils.makeFile(mongoBatpath);
		}
		String mysqlBackupCmd = "\""+NormandyConfig.get("installMysqlPath")+"\\mysql.exe\" -uroot -p"+password+" test  < "+filePath+"\\"+path+"\\mysql.sql";
		String mongoBackupCmd = "\""+NormandyConfig.get("installMongoPath")+"\\mongorestore.exe\" -h 127.0.0.1:27017  "+filePath+"\\"+path+"\\admin";
//		String mysqlBackupCmd ="\"C:\\Program Files\\MySQL\\MySQL Server 5.0\\bin\\mysql.exe\" -uroot -pduqiao test < D:\\backupX\\20171004_test4\\mysql.sql";
		//写入mysql 恢复腳本
		WriteStringToFile5(mysqlBackupCmd,mysqlBatpath);
		//写入mongo 恢复腳本
		WriteStringToFile5(mongoBackupCmd,mongoBatpath);
		//分別執行備份腳本
		processBat(mysqlBatpath);
//		processCmd(mysqlBackupCmd);
		processBat(mongoBatpath);
		ArrayList<String> listFolders = FileUtils.getFolderNameFromFolder(filePath);
		model.addAttribute("listFolders", listFolders);
		return "backup/backup";
	}
	
	/**
	 * 初始化方法
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteBackup/{path}")
	public String deleteBackup(@PathVariable String path,Model model) {
		String filePath =  NormandyConfig.get("backupPath");
		FileUtils.deleteFolder(filePath+"\\"+path);
		ArrayList<String> listFolders = FileUtils.getFolderNameFromFolder(filePath);
		model.addAttribute("listFolders", listFolders);
		return "backup/backup";
	
	}
	

	/**
	 * 
	 * @param path
	 * @return
	 */
	private String processCmd(String cmd) {
		
		Runtime run = Runtime.getRuntime();
		try {
			// run.exec("cmd /k shutdown -s -t 3600");
			Process process = run.exec(cmd);
			InputStream in = process.getInputStream();
			while (in.read() != -1) {
				System.out.println(in.read());
			}
			in.close();
			process.waitFor();
//			process.destroy();
			System.out.println("CMD:"+cmd);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmd;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	private String processBat(String path) {
		
		Runtime run = Runtime.getRuntime();
		try {
			// run.exec("cmd /k shutdown -s -t 3600");
			Process process = run.exec("cmd.exe /c " + path);
			InputStream in = process.getInputStream();
			while (in.read() != -1) {
				System.out.println(in.read());
			}
			in.close();
			process.waitFor();
//			process.destroy();
			System.out.println("PATH:"+path);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public void WriteStringToFile5(String cmd ,String filePath) {
		 FileOutputStream fos = null;
        try {
        	fos = new FileOutputStream(filePath);
            fos.write(cmd.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	if(fos != null){
        		fos = null;
        	}
        }
    }
}
