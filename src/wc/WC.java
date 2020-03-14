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
			System.out.println("ָ���������£�");
			System.out.println("-c  �����ļ� file.c ���ַ�����");
			System.out.println("-w  �����ļ� file.c �Ĵʵ���Ŀ ");
			System.out.println("-l  �����ļ� file.c ������");
			System.out.println("-a  �����ļ� file.c �Ŀ��У������У�ע����");
			System.out.println("-s  �ݹ鴦��Ŀ¼�·����������ļ�,�����ļ� file.c �Ŀ��У������У�ע����");
			System.out.println("����ָ�");
			Scanner input=new Scanner(System.in);//�Ӽ���������ָ�ִ��
			String commend=input.nextLine();
			System.out.println("����·����");
		    String path=input.nextLine();
			switch(commend) {
			
			case "-l":
				count=lineCount(path);
	        	if(count==-1) {
	        		System.out.println("�޷��ҵ��ļ�����ȷ���ļ����ڲ���������");
	        	}else {
	        		System.out.println("���ı��ļ�������Ϊ��"+count);
	        	}
	            break;
	        case "-c":
	        	count=count(path,CHAR_REGEX);
	        	if(count==-1) {
	        		System.out.println("�޷��ҵ��ļ�����ȷ���ļ����ڲ���������");
	        	}else {
	        		System.out.println("���ı��ļ����ַ���Ϊ��"+count);
	        	}
	            break;
	        case "-w":
	        	count=count(path,WORD_REGEX);
	        	if(count==-1) {
	        		System.out.println("�޷��ҵ��ļ�����ȷ���ļ����ڲ���������");
	        	}else {
	        		System.out.println("���ı��ļ��ĵ�����Ϊ��"+count);
	        	};
	             break;
	        case "-a":
	        	Complex(path);
	            break;
	        case "-s":
	        	System.out.println("�����ļ����ͣ��磺.c");
	        	String fileKind=input.nextLine();
	        	diffline(path,fileKind);
	            break;
	        default:
	            System.out.println("����ָ����������룺");
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
	            	//�ҵ���Ӧ�ַ�ʱ�ַ���+1
	                
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
	    return -1;//-1��ʾ�쳣
		
	}
	
	private static  int lineCount(String path) {
		    BufferedReader fis;
			try {
				fis = new BufferedReader(new FileReader(path));
				int count=0;
			    while(fis.readLine()!=null) {  
			    	//��ǰ�в�Ϊ��ʱ������+1
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
	
	//���ظ����ӵ����ݣ������� / ���� / ע���У���
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
	            	   //����ע�Ϳ�ʼ���
	                   notecount++;
	                   state = true;
	               }
	               else if(state) {
	                   notecount++;
	                   if(c.contains("*/")) {  
	                //����ע�ͽ������
	                   state = false;}
	               }
	               else if(c.contains("//")) {  
	            	   //����ע�ͱ��
	                   notecount++;
	               }
	               else if(c.trim().length()>1) {  
	            	   //�ж�Ϊ����������
	                   codecount++;
	               }
	               else {spacecount++;}
	               
	           }
			System.out.println("���ı��ļ���ע����Ϊ��"+notecount+"\n"
	                 +"       ������Ϊ"+codecount+"\n"
	                 +"        �հ���"+spacecount);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�����Ҳ����ļ�");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}
	
	//�ݹ����·�������з����������ļ�
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
	            		System.out.println(tempList[i].getName()+"�ļ�����Ϣ����");
	            		Complex(tempList[i].toString());
	            	}
	            }
	            
	        }
		}else {
			System.out.println(f.getName()+"�ļ�����Ϣ����");
			Complex(path);
		}
		

	}
	
	//�ж�·��Ϊ�ļ��л����ļ�
	private static boolean isDirect(File f) {
		if(f.isDirectory()){//�ļ���
			return true;
		   } 
		return false; 
	}
}

