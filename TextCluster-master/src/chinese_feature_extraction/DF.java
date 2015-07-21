/**
 * �����ĵ�Ƶ������������ȡ
 */
package chinese_feature_extraction;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;



/**
 * ���ڴ�����ĵ�Ƶ�ʽ��������������ȡ
 * @author Administrator
 */
public class DF {
	
	/**
	 * �����ı��ļ�·��
	 */
	private String filePath = "E:\\java_Eclipse\\����\\�ı��������Ͽ�\\����";
	//private String filePath = "C:\\Users\\Administrator\\Desktop\\����1";
	
	/**
	 * �洢�������ı��ı������зִʣ����ظ������Ա�����������ÿһ���ʼ����ĵ�Ƶ�ʣ���÷���Ҫ��Ĵ�����Ϊ�����������
	 */
	private HashSet<String> allWord;
	
	/**
	 * �洢�����ȡ������������
	 */
	private ArrayList<String> featureWords;
	
	/**
	 * ��¼�ĵ���Ϣ����ʽΪ���ĵ�������{��1����Ƶ����2����Ƶ...}
	 * �˱���������Ϊ������Ϣ�ļ�������������ڴ˻����Ͽ��Լ���tfidf��ͨ��ÿƪ�ĵ��Ĵ�1����2��Щkey�������ù�ϣ����ŵ�����жϸ�ƪ�ı��Ƿ�����ض��ʣ��Ӷ����ټ����df
	 */
	private HashMap<String, HashMap<String, Double>> text;
	
	/**
	 * ����ĵ���
	 */
	private String[] documentName;
	
	/**
	 * ������-�ĵ�Ƶ�ʼ�ֵ��
	 */
	private HashMap<String, Integer> wordDF;
	
	/**
	 * ��ȡ����������Ŀ
	 */
	private final int numOfFeature = 2500;
	
	
	
	
	
	public DF() throws IOException{
		
		text = new HashMap<String,HashMap<String, Double>>();
		
		
		File documentDirectory = new File(filePath);
		
		if (!documentDirectory.isDirectory())           //�������ļ������Ƿ�Ϊ�ļ���,��Ϊ�����ǰѴ���������±��浽����ļ����µ�,��Ҫ�����ж�
        {
            throw new IllegalArgumentException("����ѡ���ĵ�����ʧ�ܣ� [" + filePath + "]");
        }
		documentName = documentDirectory.list();
		
		initWordDocumentAndAllWords();
		
		initWordDF();
		initFeature();
		//System.out.println(allWord);
		System.out.println(featureWords);
	//	for(int i = 0; i < documentName.length; i++){
	//		System.out.println(text.get(documentName[i]));
	//	}

	}
	
	/**
	 * ��ʼ��wordDocument��allwords
	 * @param documentName
	 * @throws IOException
	 */
	private void initWordDocumentAndAllWords() throws IOException{
		
		StopWordsHandle stopWordsHandle = new StopWordsHandle();
		NlpirMethod.Nlpir_init();//��ʼ���ִ���
		allWord = new HashSet<String>();
		//wordDocument = new ArrayList<HashSet<String>>();
		for(int i = 0; i < documentName.length; i++){
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(filePath + File.separator + documentName[i]),"GBK");
			BufferedReader reader = new BufferedReader(isReader);
			StringBuilder stringBuilder = new StringBuilder();
			String aline = "";
			while((aline = reader.readLine()) != null){
				stringBuilder.append(aline);
			}
			isReader.close();
			reader.close();
			String []tempStrings=NlpirMethod.NLPIR_ParagraphProcess(stringBuilder.toString(), 0).split(" ");
			ArrayList<String> tempArrayList = new ArrayList<String>(stopWordsHandle.getNewText(tempStrings));//ȥ��ͣ�ô�
			//eachTextArrayList.add(tempArrayList);
			HashSet<String> tempSet = new HashSet<String>();
			HashMap<String, Double>tempHashMap = new HashMap<String,Double>();
			for(int j = 0; j < tempArrayList.size(); j++){
				tempSet.add(tempArrayList.get(j));
				allWord.add(tempArrayList.get(j));
			}
			String[] tempStrings2 = new String[tempSet.size()];
			tempSet.toArray(tempStrings2);
			for(int j = 0; j < tempStrings2.length; j++){
				tempHashMap.put(tempStrings2[j], 0.0);
			}
			for(int j = 0; j < tempArrayList.size(); j++){
				double value = tempHashMap.get(tempArrayList.get(j)) + 1.0;
				tempHashMap.put(tempArrayList.get(j), value);
			}
			for(int j = 0; j < tempStrings2.length; j++){
				double value = tempHashMap.get(tempStrings2[j]) / tempArrayList.size();
				tempHashMap.put(tempStrings2[j], value);
			}
			text.put(documentName[i], tempHashMap);
			//wordDocument.add(tempSet);
		}
		
	}
	
	
	
	/**
	 * ��ʼ���ִ��ĵ�Ƶ�ʼ�ֵ��
	 */
	private void initWordDF(){
		wordDF = new HashMap<String,Integer>();
		String[] wordStrings = new String[allWord.size()];
		allWord.toArray(wordStrings);
		int df = 0;
		for(int i = 0; i < wordStrings.length; i++){
			df = 0;
			for(int j =0; j < documentName.length; j++){
				if(text.get(documentName[j]).containsKey(wordStrings[i])){
					df = df + 1;
				}
			}
			wordDF.put(wordStrings[i], df);
		}
		
	}

	/**
	 * �Էִ��ĵ�Ƶ�ʼ�ֵ�Խ������򲢻�ȡ����Ҫ�����������
	 */
	private void initFeature(){
		featureWords = new ArrayList<String>();
		List<Map.Entry<String, Integer>> list_Data = new ArrayList<Map.Entry<String, Integer>>(wordDF.entrySet()); 
	    System.out.println(wordDF);
	    Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>(){    
	      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)  
	      {  
	        if ((o2.getValue() - o1.getValue())>0)  
	          return 1;  
	        else if((o2.getValue() - o1.getValue())==0)  
	          return 0;  
	        else   
	          return -1;
	        }  
	      }  
	    );  
        
	    for(int i = 0; i < numOfFeature; i++){
	    	featureWords.add(list_Data.get(i).getKey());
	    }
	}

	public ArrayList<String> getFeatureWords(){
		return featureWords;
	}
	
	public HashMap<String, Integer> getWordDF() {
		return wordDF;
	}
	
	public HashMap<String, HashMap<String, Double>> getText() {
		return text;
	}
	
	public String[] getDocumentName() {
		return documentName;
	}
	
	/*
	class TextHandleThread extends Thread{
		private int begin;
		private int end;
		private int index;
		private HashSet<String>threadResult;
		
		public TextHandleThread(int begin,int end,int index){
			
			this.begin = begin;
			this.end = end;
			switch (index) {
			case 0:
				threadResult = threadResult1;
				break;
			case 1:
				threadResult = threadResult2;
				break;
			case 2:
				threadResult = threadResult3;
				break;
			case 3:
				threadResult = threadResult4;
				break;
			case 4:
				threadResult = threadResult5;
				break;
			case 5:
				threadResult = threadResult6;
				break;
			case 6:
				threadResult = threadResult7;
				break;
			case 7:
				threadResult = threadResult8;
				break;
			default:
				break;
			}
			this.index = index;
			
		}
		
		public void run(){
			
			for(int i = begin; i < end; i++){
				try {
					InputStreamReader isReader;
					isReader = new InputStreamReader(new FileInputStream(filePath + File.separator + documentName[i]),"GBK");
					BufferedReader reader = new BufferedReader(isReader);
					StringBuilder stringBuilder = new StringBuilder();
					String aline = "";
					while((aline = reader.readLine()) != null){
						stringBuilder.append(aline);
					}
					isReader.close();
					reader.close();
					String []tempStrings=NlpirMethod.NLPIR_ParagraphProcess(stringBuilder.toString(), 0).split(" ");
					ArrayList<String> tempArrayList = new ArrayList<String>(stopWordsHandle.getNewText(tempStrings));//ȥ��ͣ�ô�
					
					HashSet<String> tempSet = new HashSet<String>();
					
					for(int j = 0; j < tempArrayList.size(); j++){
						tempSet.add(tempArrayList.get(j));
						threadResult.add(tempArrayList.get(j));
						
					}
					
					HashMap<String, Double> temphHashMap = new HashMap<String,Double>();
					String[] tempStrings2 = new String[tempSet.size()];
					tempSet.toArray(tempStrings2);
					for(int j = 0; j < tempStrings2.length; j++){
						temphHashMap.put(tempStrings2[j], 0.0);
					}//��׼����hashmap��������ͳ�ƴ�Ƶʱ�ظ��ж�hashmap�Ƿ��Ѿ�������ǰ����
					
					//ͳ�ƴ�ƪ�ı���������Ĵ�Ƶ~��ɴʡ�����Ƶ�ļ�ֵ�Ե���ʽ
					for(int j = 0; j < tempArrayList.size(); j++){
						double value = temphHashMap.get(tempArrayList.get(j)) + 1.0;
						temphHashMap.put(tempArrayList.get(j), value);
					}
					
					for(int j = 0; j < tempStrings2.length; j++){
						double value = temphHashMap.get(tempArrayList.get(j)) / tempArrayList.size();
						temphHashMap.put(tempStrings2[j], value);
					}//��forѭ��������temphHashMap�д���˴�ƪ�ı��ĸ��������Ӧ�Ĵ�Ƶ
					
					
					text.put(documentName[i], temphHashMap);
					
					
					
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				
				
			}
			System.out.println(index + "true");
			isend[index] = true;
			
		}
	}
	*/
	/*public static void main(String[] args) throws IOException{
		DF df = new DF();
	}*/
	
	
	

}
