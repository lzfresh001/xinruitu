package com.yx.xinruitu.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class FileUtil {
	public FileUtil() {
	}

	/**
	 *  新建目录
	 *  @param  folderPath  String  如  c:/fqf
	 *  @return  boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到扩展名
	 * @return
	 */
	public static String getExtensionName(String filename)
	{
		int cc=filename.lastIndexOf(".");
		String bb=filename.substring(cc,filename.length());
		return bb;
	}
	/**
	 *  新建目录
	 *  @param  folderPath  String  如  c:/fqf
	 *  @return  boolean
	 */
	public static boolean newFolderBoolean(String folderPath) {
		boolean temp=true;
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}else
			{
				temp=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 *  新建文件
	 *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt
	 *  @param  fileContent  String  文件内容
	 *  @return  boolean
	 */
	public static void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 *  删除文件
	 *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt
	 *  @return  boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 *  删除文件夹
	 *  @return  boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); //删除空文件夹

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 *  删除文件夹里面的所有文件
	 *  @param  path  String  文件夹路径  如  c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);//再删除空文件夹
			}
		}
	}

	/**
	 *  复制单个文件
	 *  @param  oldPath  String  原文件路径  如：c:/fqf.txt
	 *  @param  newPath  String  复制后路径  如：f:/fqf.txt
	 *  @return  boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在时
				InputStream inStream = new FileInputStream(oldPath); //读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数  文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 *  复制整个文件夹内容
	 *  @param  oldPath  String  原文件路径  如：c:/fqf
	 *  @param  newPath  String  复制后路径  如：f:/fqf/ff
	 *  @return  boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); //如果文件夹不存在  则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {//如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 *  移动文件到指定目录
	 *  @param  oldPath  String  如：c:/fqf.txt
	 *  @param  newPath  String  如：d:/fqf.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);

	}

	/**
	 *  移动文件到指定目录
	 *  @param  oldPath  String  如：c:/fqf.txt
	 *  @param  newPath  String  如：d:/fqf.txt
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);

	}



	// 拷贝文件
	private static void copyFile2(String source, String dest) {
		try {
			File in = new File(source);
			File out = new File(dest);
			FileInputStream inFile = new FileInputStream(in);
			FileOutputStream outFile = new FileOutputStream(out);
			byte[] buffer = new byte[1024];
			int i = 0;
			while ((i = inFile.read(buffer)) != -1) {
				outFile.write(buffer, 0, i);
			}
			inFile.close();
			outFile.close();
		}
		catch (Exception e) {

		}
	}
	/**
	 * 文件写入
	 * @param file
	 * @param data
	 */
	 public  static void rewrite(File file, String data) {
	        BufferedWriter bw = null;
	        try {
	            bw = new BufferedWriter(new FileWriter(file));
	            bw.write(data);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if(bw != null) {
	               try {
	                   bw.close();
	               } catch(IOException e) {
	                   e.printStackTrace();
	               }
	            }
	        }
	    }
	    /**
	     * 文件读取
	     * @param file
	     * @return
	     */
	    public static List<String> readList(File file) {
	        BufferedReader br = null;
	        List<String> data = new ArrayList<String>();
	        try {
	            br = new BufferedReader(new FileReader(file));
	            for(String str = null; (str = br.readLine()) != null; ) {
	                data.add(str);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if(br != null) {
	               try {
	                   br.close();
	               } catch(IOException e) {
	                   e.printStackTrace();
	               }
	            }
	        }
	        return data;
	    }



	    /**
	     * 读取property文件
	     * fzd
	     */
	        //根据Key读取Value
	        public static String GetValueByKey(String filePath, String key) {
	            Properties pps = new Properties();
	            try {
	                InputStream in = new BufferedInputStream (new FileInputStream(filePath));  
	                pps.load(in);
	                String value = pps.getProperty(key);
	                System.out.println(key + " = " + value);
	                return value;
	                
	            }catch (IOException e) {
	                e.printStackTrace();
	                return null;
	            }
	        }
	        
	        //读取Properties的全部信息
	        public static void GetAllProperties(String filePath) throws IOException {
	            Properties pps = new Properties();
	            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
	            pps.load(in);
	            Enumeration en = pps.propertyNames(); //得到配置文件的名字
	            while(en.hasMoreElements()) {
	                String strKey = (String) en.nextElement();
	                String strValue = pps.getProperty(strKey);
	                System.out.println(strKey + "=" + strValue);
	            }
	            
	        }
	        
	        //写入Properties信息
	        public static void WriteProperties (String filePath, String pKey, String pValue) {
	        	try {
	            Properties pps = new Properties();
	            
	            InputStream in;
					in = new FileInputStream(filePath);
				
	            //从输入流中读取属性列表（键和元素对） 
	            pps.load(in);
	            //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。  
	            //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
	            OutputStream out = new FileOutputStream(filePath);
	            pps.setProperty(pKey, pValue);
	            //以适合使用 load 方法加载到 Properties 表中的格式，  
	            //将此 Properties 表中的属性列表（键和元素对）写入输出流  
	            pps.store(out, "Update " + pKey + " name");
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }

}
