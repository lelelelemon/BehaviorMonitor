package k_means;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import chinese_feature_extraction.DF;



public class K_Means {
	
	
	private static int N = 0;
	
	private DF df;
	
	/**
	 * �����д������ĵ��������ռ����ʽ��ʾͬʱ��tf-idfֵ��������ռ�
	 */
	private HashMap<String, HashMap<String, Double>> textWordHashMap;
	
	/**
	 * ��¼���ĵ�ֵ
	 */
	private ArrayList<HashMap<String, Double>> center;
	
	/**
	 * ��¼�ı��������Խ�������ʺϵĴ�����
	 */
	private String[] documentName;
	
	/**
	 * ��Ϊ�ڼ���ĳһƪ���º͸������ĵ��������ƶ�ʱ����ƪ���µ����������ǲ���ģ�û�б�Ҫ�ظ����㣬�����ô˱������Ա���ÿƪ�ı�����������
	 */
	private HashMap<String, Double>textVectorLengthHashMap;
	
	/**
	 * ��¼�������е��ı������֣��������
	 */
	private ArrayList<ArrayList<String>> cluster;
	
	/**
	 * ��¼��������Ϊ�������ƶȵȼ��������
	 */
	private ArrayList<String>featureWords;
	
	/**
	 * ���Ĵصĸ���
	 */
	private static int k = 7;
	
	public K_Means() throws IOException{
		center = new ArrayList<HashMap<String, Double>>();
	//	center2 = new ArrayList<HashMap<String, Double>>();
		df = new DF();//ʱ�䳤
		documentName = df.getDocumentName();
		textVectorLengthHashMap = new HashMap<String,Double>();
		featureWords = df.getFeatureWords();
		cluster = new ArrayList<ArrayList<String>>();
		initTextWordsHashMap();
		initTextVectorLengthHashMap();
		initCluster();
		initCenter();
		calculate();
	}
	
	/**
	 * ����ÿƪ�ı���ÿ�������ʵ�tf-idfֵ����䵽textWordHashMap��
	 */
	private void initTextWordsHashMap(){
		HashMap<String, Integer>wordDF = df.getWordDF();
		textWordHashMap = new HashMap<String,HashMap<String, Double>>();
		HashMap<String, HashMap<String, Double>> text = df.getText();
		for(int i = 0; i < documentName.length; i++){
			HashMap<String, Double> wordtf = text.get(documentName[i]);
			HashMap<String, Double> temphHashMap = new HashMap<String,Double>();
			for(int j = 0; j < featureWords.size(); j++){
				if(wordtf.containsKey(featureWords.get(j))){
					double value = wordtf.get(featureWords.get(j));
					value = value * Math.log(documentName.length / wordDF.get(featureWords.get(j)));
					temphHashMap.put(featureWords.get(j), value);
				}
				else {
					temphHashMap.put(featureWords.get(j), 0.0);
				}	
			}
			textWordHashMap.put(documentName[i], temphHashMap);
		}
	}
	
	/**
	 * ��ʼ���������ģ��������ѡȡ�������ĵķ�ʽ
	 */
	private void initCenter(){
		HashSet<Integer> set = new HashSet<Integer>();//���Լ�¼������ɵ��ı�����������ֹ�����ظ���������ʵ����������С��kֵ
		
		while(set.size() < k){
			int centerIndex = (int)(Math.random() * documentName.length);
			if(!(set.contains(centerIndex)))
				set.add(centerIndex);
			else 
				continue;
			center.add(textWordHashMap.get(documentName[centerIndex]));
			
		}
		/*for(int i = 0; i < 144; i=i+18){
			center.add(textWordHashMap.get(documentName[i]));
			center2.add(textWordHashMap.get(documentName[i]));
		}*/
		
	}
	
	
	/**
	 * ����ÿƪ�ı�����������
	 */
	private void initTextVectorLengthHashMap(){
		double C = 0.0;
		for(int i = 0; i < documentName.length; i++){
			for(int j = 0; j < featureWords.size(); j++){
				HashMap<String, Double> textHashMap = textWordHashMap.get(documentName[i]);
				C = C + textHashMap.get(featureWords.get(j)) * textHashMap.get(featureWords.get(j));
			}
			C = Math.sqrt(C);
			textVectorLengthHashMap.put(documentName[i], C);	
		}
		System.out.println(textVectorLengthHashMap);

	}
	
	
	/**
	 * ��ʼ����Ž����cluster
	 */
	private void initCluster(){
		for(int i = 0; i < k; i++){
			cluster.add(new ArrayList<String>());
		}
	}
	
	
	/**
	 * ���´����ĵ�ֵ
	 */
	private void updateCenter(int index,HashMap<String, Double>textHashMap){
		HashMap<String, Double>tempHashMap = center.get(index);
		int length = cluster.get(index).size();
		for(int i = 0; i < featureWords.size(); i++){//����������Ϊ����
			double value = ((length - 1) * tempHashMap.get(featureWords.get(i)) + textHashMap.get(featureWords.get(i))) / length;
			tempHashMap.put(featureWords.get(i), value);
		}
	}
	
	
	/**
	 * �ж����дص������Ƿ����˱仯�����Ƿ���Ҫ�����ݹ�
	 * @return
	 */
	private boolean isContinue(ArrayList<HashMap<String, Double>> center2){
		if(center.equals(center2))
			return false;//������û�з����仯��k-means�㷨����
		else 
			return true;//�����ķ����˱仯����Ҫ��������
		
	}
	

	/**
	 * ��������ı����������ĵľ���
	 */
	private void calculate(){
		
		for(int i = 0; i < k; i++){
			cluster.get(i).add("center");
		}
		
		System.out.println("N = ===========================" + N);
		N = N + 1;
		
		ArrayList<HashMap<String, Double>> center2 = new ArrayList<HashMap<String,Double>>();
		for(int i = 0; i < center.size(); i++){
			center2.add(new HashMap<String,Double>(center.get(i)));
		}
		
		
		HashMap<String, Double> centerValueHashMap = new HashMap<String,Double>();
		
		for(int i = 0; i < documentName.length; i++){
			HashMap<String, Double> textHashMap = textWordHashMap.get(documentName[i]);
			double max = 0.0;
			double C = textVectorLengthHashMap.get(documentName[i]);//ֱ�ӻ�õ�ǰ�ı����������ȣ������ظ�����
			
			int index = 0;//��¼��ǰ�ı������ص�����
			for(int j = 0; j < center.size(); j++){
				centerValueHashMap = center.get(j);

				double temp = 0.0;
				double A = 0.0;//�������ƶȹ�ʽ�з��Ӳ��֣�����ǰ�ĵ��͵�ǰѡ���Ĵ����ĵĸ��������ķ����ĳ˻���
				double B = 0.0;//��ǰѡ���Ĵص����ĵ���������
				
				for(int m = 0; m < featureWords.size(); m++){
					A = A + centerValueHashMap.get(featureWords.get(m)) * textHashMap.get(featureWords.get(m));
					B = B + centerValueHashMap.get(featureWords.get(m)) * centerValueHashMap.get(featureWords.get(m));
				}
				temp = A / (C * Math.sqrt(B));
				
				
				//�ж��뵱ǰ���ĵ����ƶ��Ƿ����ǰ���ŵĴ����ĵ����ƶȻ�Ҫ�����ǣ�����µ�ǰ�ı������Ŵ�����,����������ֵ
				 
				if(max < temp){
					max = temp;
					index = j;
				}
			}//������ɺ󼴵õ���ǰ��iƪ�ı����������Ŵ�����
			
			//ͬ�����¸����ĵ�ֵ
			cluster.get(index).add(documentName[i]);
			updateCenter(index,textHashMap);
			//cluster.get(index).add(documentName[i]);
		}
		
		
		if(isContinue(center2)){
			if (N == 24)
				return;
			for(int i = 0; i < k; i++){
				cluster.get(i).clear();
			}
			
			calculate();
		}
	}

	public ArrayList<ArrayList<String>> getCluster() {
		return cluster;
	}
	
	public ArrayList<String> getFeatureWordStrings() {
		return featureWords;
	}
	
	public static void main(String[] args) throws IOException{
		K_Means k_means = new K_Means();
		
		ArrayList<ArrayList<String>> result = k_means.getCluster();
		for(int i = 0; i < 7; i++){
			System.out.println(result.get(i));
		}
		
		System.out.println(k_means.getFeatureWordStrings());

	}
}
