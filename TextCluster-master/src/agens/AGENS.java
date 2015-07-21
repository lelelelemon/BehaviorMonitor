/**
 * ���۲�ξ���,���̣߳�ʹ��df����������ȡ
 */

package agens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import chinese_feature_extraction.DF;


public class AGENS {

	/**
	 * ���ʹ��DF����ȡ�������ʺͶ�ӦȨ��
	 */
	private DF df;
	
	/**
	 * ��¼�������ı����ĵ���
	 */
	private String[] documentName;
	
	/**
	 * �Զ�ά�������ʽ��¼ÿƪ�ĵ������������ĵ��ľ���
	 */
	private double[][] distance;
	
	/**
	 * ��¼ÿƪ�ı����������ȣ��������������ƶ�ʱ�ظ�����
	 */
	private double[] eachDocumentVectorLength;

	/**
	 * �ص���ʽ��ÿ�����д�ŵ����ı�������
	 */
	private ArrayList<ArrayList<Integer>> cluster;
	
	/**
	 * ��¼ÿƪ�ı��������ʵ�tf-idfֵ
	 */
	private HashMap<String, HashMap<String, Double>>textWordHashMap;
	
	/**
	 * ��¼������
	 */
	private ArrayList<String> featureWords;
	
	/**
	 * �趨�صĸ���
	 */
	private static final int N = 7;
	
	/**
	 * �����߳���Ŀ
	 */
	private static final int numOfThread = 8;
	
	/**
	 * �жϸ����߳��Ƿ������
	 */
	private boolean[] isend;
	
	
	public AGENS() throws IOException{
		df = new DF();
		documentName = df.getDocumentName();
		distance = new double[documentName.length][documentName.length];
		featureWords = df.getFeatureWords();
		eachDocumentVectorLength = new double[documentName.length];
		cluster = new ArrayList<ArrayList<Integer>>();
		isend = new boolean[numOfThread];
	    for(int i = 0; i < numOfThread; i++){
	    	isend[i] = false;
	    }
		initTextWordHashMap();
		initEachDocumentVectorLength();
		calculate();
		initCluster();
		agens();
		
		for(int i = 0; i < cluster.size(); i++){
			for(int j = 0; j < cluster.get(i).size(); j++){
				System.out.print(documentName[cluster.get(i).get(j)] + "  ");
			}
			System.out.println("");
		}
		
	}
	
	/**
	 * ����ÿƪ�ı���ÿ�������ʵ�tf-idfֵ
	 */
	private void initTextWordHashMap(){
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
	 * ����ÿƪ�ı�����������
	 */
	private void initEachDocumentVectorLength(){

		for(int i = 0; i < documentName.length; i++){
			double sum = 0.0;
			HashMap<String, Double>tempHashMap = textWordHashMap.get(documentName[i]);
			for(int j = 0; j < featureWords.size(); j++){
				sum = sum + tempHashMap.get(featureWords.get(j)) * tempHashMap.get(featureWords.get(j));
			}
			sum = Math.sqrt(sum);
			eachDocumentVectorLength[i] = sum;
		}
	}

	
	/**
	 * ��¼��ƪ���µ��������µľ���
	 */
	private void calculate() {
		
		int begin = 0;
		int length = documentName.length / numOfThread;
		
		
		for(int i = 0; i < numOfThread - 1; i++){
			CalculateThread thread = new CalculateThread(begin,begin + length,i);
			thread.start();
			begin = begin + length;
		}
		CalculateThread thread = new CalculateThread(begin, documentName.length,numOfThread - 1);
		thread.start();
		
		boolean isOK = false;
		while(!isOK){
			isOK = true;
			for(int i = 0; i < numOfThread; i++){
				isOK = isOK && isend[i];
			}
		}
		//�������whileѭ��˵�������̶߳��Ѵ������~distance�����д洢�������ĵ�����֮��ľ���
		
	}
	
	/**
	 * ��ʼ��������
	 */
	private void initCluster(){
		for(int i = 0; i < documentName.length; i++){
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			arrayList.add(i);
			cluster.add(arrayList);
		}
	}
	
	/**
	 * ʵ�����۲�ξ�����Ĵ����
	 */
	private void agens(){
		//��¼�����Ƶ�������
		int index1 = 0;
		int index2 = 0;
		double max = 0.0;
		//================================
		for(int i = 0; i < cluster.size(); i++){
			ArrayList<Integer> cluster1 = cluster.get(i);
			for(int j = i + 1; j < cluster.size(); j++){
				ArrayList<Integer> cluster2 = cluster.get(j);
				double sum = 0.0;
				for(int m = 0; m < cluster1.size(); m++){
					for(int n = 0; n < cluster2.size(); n++){
						sum = sum + distance[cluster1.get(m)][cluster2.get(n)];
					}
				}
				sum = sum / (cluster1.size() * cluster2.size());
				if(max < sum){
					max = sum;
					index1 = i;
					index2 = j;
				}
			}
		}
		
		//System.out.println(cluster.get(index1).size());
		//System.out.println(cluster.get(index2).size());
		
		for(int i = 0; i < cluster.get(index2).size(); i++){
			cluster.get(index1).add(cluster.get(index2).get(i));

		}
		cluster.remove(index2);
		
		if(cluster.size() <= N)
			return;
		else 
			agens();
			
	}
	
	class CalculateThread extends Thread{
		
		private int begin;
		private int end;
		private int index;
		
		public CalculateThread(int begin,int end,int index){
			this.begin = begin;
			this.end = end;
			this.index = index;
		}
		
		public void run(){
			
			for(int i = begin; i < end; i++){
				HashMap<String, Double> text = textWordHashMap.get(documentName[i]);
				
				int length = text.size();
				String[] key = new String[length];
				text.keySet().toArray(key);
				for(int j = i; j < documentName.length; j++){
					
					HashMap<String, Double> text2 = textWordHashMap.get(documentName[j]);
					double value = 0.0;
					
					for(int k = 0; k < featureWords.size(); k++){
						value = value + text.get(featureWords.get(k)) * text2.get(featureWords.get(k));
					}
					value = value / (eachDocumentVectorLength[i] * eachDocumentVectorLength[j]);	
		
					distance[i][j] = value; 
					distance[j][i] = value;
				}
			}
			
			isend[index] = true;

		}
	}
	
	/**
	 * ���������۲�ξ���ʵ�����������������Ϣ���������۲�ξ����㷨���ʵ������û�б�ʹ�ù�����������
	 */
	public void information(){
		System.out.println("AGENS is finish!");
	}
	
	public static void main(String[] args) throws IOException{
		
		AGENS agens = new AGENS();
		agens.information();
	}
	
	
}
