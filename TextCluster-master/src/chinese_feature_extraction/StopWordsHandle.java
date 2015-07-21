/**
 * ȥͣ�ô���
 */

package chinese_feature_extraction;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class StopWordsHandle {

	/**
	 * �洢ͣ�ôʱ���ĵ�·��
	 */
	String filePath = "E:\\java_Eclipse\\FeatureExtractionForCluster\\����ͣ�ôʱ�.txt";
	
	/**
	 * �洢ͣ�ô�
	 */
	HashSet<String> stopWords;
	
	/**
	 * �洢ȥ��ͣ�ôʵ��ı�
	 */
	ArrayList<String> newText;
	
	public StopWordsHandle() throws IOException{
		stopWords = new HashSet<String>();
		newText = new ArrayList<String>();
		InputStreamReader isReader = new InputStreamReader(new FileInputStream(filePath),"GBK");
		BufferedReader reader = new BufferedReader(isReader);
		String aline = "";
		while ((aline = reader.readLine()) != null) {
			stopWords.add(aline);
		}
		isReader.close();
		reader.close();
	}
	
	/**
	 * ���ȥ��ͣ�ôʵ��µ��ı�
	 * @param text
	 * @return
	 */
	public ArrayList<String> getNewText(String[] text) {
		IsStopWord(text);
		return newText;
	}
	
	/**
	 * ȥ��ԭ�ı���ͣ�ô�
	 * @param text
	 */
	private void IsStopWord(String[] text)
    {
		newText.clear();
        for(int i = 0;i < text.length; i++)
        {
            if(!(stopWords.contains(text[i])))
            	newText.add(text[i]);
        }
    }
}
