package wc;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WC {
	
	private static final String CHAR_REGEX = "\\S";
	private static final String WORD_REGEX = "[a-zA-Z]+\\b";
	
	
	public static void main(String[] args){
		int count;
		while(true){
			System.out.println("指令类型如下：");
			System.out.println("-c  返回文件 file.c 的字符数：");
			System.out.println("-w  返回文件 file.c 的词的数目 ");
			System.out.println("-l  返回文件 file.c 的行数");
			System.out.println("-a  返回文件 file.c 的空行，代码行，注释行");
			System.out.println("-s  递归处理目录下符合条件的文件,返回文件 file.c 的空行，代码行，注释行");
			System.out.println("输入指令：");
			Scanner input=new Scanner(System.in);//从键盘上输入指令并执行
			String commend=input.nextLine();
			System.out.println("输入路径：");
		    String path=input.nextLine();
			switch(commend) {
			
			case "-l":
				count=lineCount(path);
	        	if(count==-1) {
	        		System.out.println("无法找到文件，请确定文件存在并重新输入");
	        	}else {
	        		System.out.println("该文本文件的行数为："+count);
	        	}
	            break;
	        case "-c":
	        	count=count(path,CHAR_REGEX);
	        	if(count==-1) {
	        		System.out.println("无法找到文件，请确定文件存在并重新输入");
	        	}else {
	        		System.out.println("该文本文件的字符数为："+count);
	        	}
	            break;
	        case "-w":
	        	count=count(path,WORD_REGEX);
	        	if(count==-1) {
	        		System.out.println("无法找到文件，请确定文件存在并重新输入");
	        	}else {
	        		System.out.println("该文本文件的单词数为："+count);
	        	};
	             break;
	        case "-a":
	        	Complex(path);
	            break;
	        case "-s":
	        	System.out.println("输入文件类型，如：.c");
	        	String fileKind=input.nextLine();
	        	diffline(path,fileKind);
	            break;
	        default:
	            System.out.println("错误指令，请重新输入：");
	            break; 
			}
		}
	 }
	
	
	private static int count(String path,String regex) {
		
		String c;
		int count=0;
    	Pattern p =Pattern.compile(regex);
	    try {
			BufferedReader f=new BufferedReader(new FileReader(path));
			while((c=f.readLine()) != null) {
	            Matcher m =p.matcher(c);
	            while(m.find())    
	            	//找到对应字符时字符数+1
	                
	               count ++;
				
	        }   
			f.close();
			return count;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return -1;//-1表示异常
		
	}
	
	private static  int lineCount(String path) {
		    BufferedReader fis;
			try {
				fis = new BufferedReader(new FileReader(path));
				int count=0;
			    while(fis.readLine()!=null) {  
			    	//当前行不为空时，行数+1
			        count++;
			        }
			    fis.close();
			    return count;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    return -1;
		
	}
	
	//返回更复杂的数据（代码行 / 空行 / 注释行）。
	private static void Complex(String path)  {
		int spacecount = 0;
        int notecount = 0;
        int codecount = 0;
        boolean state = false;
        BufferedReader fis;
        String c;
        try {
			fis = new BufferedReader(new FileReader(path));
			while((c=fis.readLine())!=null) {
	               if(c.contains("/*")) {   
	            	   //多行注释开始标记
	                   notecount++;
	                   state = true;
	               }
	               else if(state) {
	                   notecount++;
	                   if(c.contains("*/")) {  
	                //多行注释结束标记
	                   state = false;}
	               }
	               else if(c.contains("//")) {  
	            	   //单行注释标记
	                   notecount++;
	               }
	               else if(c.trim().length()>1) {  
	            	   //判定为代码行条件
	                   codecount++;
	               }
	               else {spacecount++;}
	               
	           }
			System.out.println("该文本文件的注释行为："+notecount+"\n"
	                 +"       代码行为"+codecount+"\n"
	                 +"        空白行"+spacecount);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("错误，找不到文件");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}
	
	//递归遍历路径下所有符合条件的文件
	private static void diffline(String path,String fileKind) {
		File f = new File(path);
		if(isDirect(f)) {
			ArrayList<String> files = new ArrayList<String>();
	        File file = new File(path);
	        File[] tempList = file.listFiles();

	        for (int i = 0; i < tempList.length; i++) {
	            if (isDirect(tempList[i])) {
	            	diffline(tempList[i].toString(),fileKind);	
	            }else {
	            	if(tempList[i].toString().contains(fileKind)) {
	            		System.out.println(tempList[i].getName()+"文件的信息如下");
	            		Complex(tempList[i].toString());
	            	}
	            }
	            
	        }
		}else {
			System.out.println(f.getName()+"文件的信息如下");
			Complex(path);
		}
		

	}
	
	//判断路径为文件夹还是文件
	private static boolean isDirect(File f) {
		if(f.isDirectory()){//文件夹
			return true;
		   } 
		return false; 
	}
}

