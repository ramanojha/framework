package com.myproject.qa.testing.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFTPUtils {

	// Get Shell log
		//credit https://kodehelp.com/java-program-downloading-directory-folder-content-recursively-sftp-server/
		private static String commandLineLogs(String logs) {
			if(!logs.isEmpty()) System.out.println(logs);
			return logs;
		}

		//get SSH Session
		public static Session getSSHSession(String host, String user, String password) throws JSchException {
			JSch jsch = new JSch();
			Session session=jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			System.out.println("Connected with credentials ! Host : "+session.getHost()+" , Username : "+session.getUserName());
			return session;
		}

		//get SSH Channel
		public static Channel getSSHChannel(String type, Session session) throws JSchException {
			if(type.equals("exec")) 
				return session.openChannel("exec");	
			else if(type.equals("sftp"))
				return session.openChannel("sftp");
			return null;	
		}

		//disconnectSSH
		public static void disconnectSSH(Channel channel, Session session) {
			channel.disconnect();
			session.disconnect();
		}


		// execute command in SSH
		public static String executeSSH(Channel channell, String cmd) throws IOException, JSchException {
			ChannelExec channel = (ChannelExec)channell;
			channel.setCommand(cmd);
			channel.setInputStream(null);
			InputStream in=channel.getInputStream();
			channel.connect();

			String commandOutput = FileUtils.readInputStreaminKB(in);
			return commandLineLogs(commandOutput);

		}

		// Upload a file
		//credit https://kodehelp.com/java-program-downloading-directory-folder-content-recursively-sftp-server/
		public static void sftpUpload(Session session, String localPath, String remotePath, String fileName) throws JSchException, SftpException {
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.put(localPath+fileName, remotePath+fileName); //put method is for uploading
			sftpChannel.exit();

		}

		// Download a file
		//credit https://kodehelp.com/java-program-downloading-directory-folder-content-recursively-sftp-server/
		public static void sftpDownload(Session session, String localPath, String remotePath, String fileName) throws JSchException, SftpException {
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.get(remotePath+fileName, localPath+fileName); //get method is for downloading 
			sftpChannel.exit();

		}

		// Download a Folder
		
		public static void sftpDownLoadFolder(Session session, String localPath, String remotePath ) throws JSchException, SftpException {
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.cd(remotePath);
			recursiveFolderDownload(remotePath, localPath, sftpChannel);
			sftpChannel.exit();

		}


		// Recursive folder download
		@SuppressWarnings("unchecked")
		private static void recursiveFolderDownload(String sourcePath, String destinationPath, ChannelSftp channelSftp) throws SftpException {

			Vector<ChannelSftp.LsEntry>  items = channelSftp.ls(sourcePath); //List folder items.

			for (ChannelSftp.LsEntry item : items) {
				File file = new File(destinationPath, item.getFilename());

				// Check if it is a file & Download only if changed later.
				if (!item.getAttrs().isDir()) { 
					if (!file.exists() || (item.getAttrs().getMTime() > Long.valueOf(file.lastModified()/ (long) 1000).intValue())) { 

						//new File(destinationPath +"/"+ item.getFilename());
						// Download file from source (source filename, destination filename).
						channelSftp.get(sourcePath+"/"+item.getFilename(), destinationPath+"/"+item.getFilename()); 
					}
				} 
				else if (!(".".equals(item.getFilename()) || "..".equals(item.getFilename()))) {
					// Empty folder copy.
					file.mkdirs(); 
					recursiveFolderDownload(sourcePath+"/"+item.getFilename(), destinationPath+"/"+ item.getFilename(), channelSftp); // Enter found folder on server to read its contents and create locally.
				}
			}
		}

		// Upload a Folder
		public static void sftpUploadFolder(Session session, String localPath, String remotePath ) throws JSchException, SftpException, FileNotFoundException {
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.cd(remotePath);
			recursiveFolderUpload(localPath,remotePath, sftpChannel);
			sftpChannel.exit();
		}

		// Upload Folder recursively
		private static void recursiveFolderUpload(String sourcePath, String destinationPath, ChannelSftp channelSftp) throws SftpException, FileNotFoundException {

			File sourceFile = new File(sourcePath);
			// copy if it is a file
			if (sourceFile.isFile()) {
				channelSftp.cd(destinationPath);
				if (!sourceFile.getName().startsWith("."))
					channelSftp.put(new FileInputStream(sourceFile), sourceFile.getName(), ChannelSftp.OVERWRITE);
			} 
			else {		
				File[] files = sourceFile.listFiles();
				if (files != null && !sourceFile.getName().startsWith(".")) {
					channelSftp.cd(destinationPath);
					SftpATTRS attrs = null;

					// check if the directory is already existing
					try {
						attrs = channelSftp.stat(destinationPath + "/" + sourceFile.getName());
					} catch (Exception e) {
						System.out.println(destinationPath + "/" + sourceFile.getName() + " not found");
					}

					// else create a directory
					if (attrs != null) {
						System.out.println("Directory exists IsDir=" + attrs.isDir());
					} else {
						System.out.println("Creating dir " + sourceFile.getName());
						channelSftp.mkdir(sourceFile.getName());
					}

					for (File f: files) {
						recursiveFolderUpload(f.getAbsolutePath(), destinationPath + "/" + sourceFile.getName(), channelSftp);
					}
				}
			}
		}


		// Delete a Folder
		public static void sftpDeleteFolder(Session session, String remotePath ) throws JSchException, SftpException, FileNotFoundException {
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			recursiveFolderDelete(remotePath, sftpChannel);
			System.out.println("Path is cleaned !");
			sftpChannel.exit();
		}


		// recursive Delete a Folder
		@SuppressWarnings("unchecked")
		private static void recursiveFolderDelete(String path, ChannelSftp channelSftp) throws SftpException {

			channelSftp.cd(path); 
			Vector<ChannelSftp.LsEntry>  items = channelSftp.ls(path); //List folder items.

			// Iterate objects in the list to get file/folder names.
			for (ChannelSftp.LsEntry item : items) { 

				if (!item.getAttrs().isDir()) 
					channelSftp.rm(path+"/"+ item.getFilename());
				else if (!(".".equals(item.getFilename()) || "..".equals(item.getFilename()))) { // If it is a subdir.
					try {	
						channelSftp.rmdir(path+"/"+item.getFilename()); 
					} catch (Exception e) { // If subdir is not empty and error occurs.
						recursiveFolderDelete(path+"/"+item.getFilename(), channelSftp); 
					}
				}
			}
			channelSftp.rmdir(path); // delete the parent directory after empty
		}

		// Delete a File
		public static void sftpDeleteFile(Session session, String remotePath, String filename ) throws JSchException, SftpException, FileNotFoundException {
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.cd(remotePath);
			sftpChannel.rm(filename);
			System.out.println("Path is cleaned !");
			sftpChannel.exit();
		}
}
