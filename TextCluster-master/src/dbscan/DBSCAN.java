/**
 * DBSCAN�����ܶȵ��ı����࣬ʹ��df����������ȡ
 */

package dbscan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import agens.AGENS;
import chinese_feature_extraction.DF;

public class DBSCAN {
	
	/**
	 * ���ʹ��DF����ȡ�������ʺͶ�Ӧ��Ȩ��
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
	 * ��¼ÿ���������Ľ��ĵ�ľ���
	 */
	private double[] fourthDis;
	
	/**
	 * ��¼ÿƪ�ı����������ȣ��������������ƶ�ʱ�ظ�����
	 */
	private double[] eachDocumentVectorLength;
	
	/**
	 * �ص���ʽ��ÿ�����д�ŵ����ı�������
	 */
	private ArrayList<ArrayList<String>> cluster;
	
	/**
	 * ��¼ÿƪ�ı��������ʵ�tf-idfֵ����ʽΪ��<�ĵ�����<�����Ӧtfidfֵ>>
	 */
	private HashMap<String, HashMap<String, Double>>textWordHashMap;
	
	/**
	 * ��¼������
	 */
	private ArrayList<String> featureWords;
	
	/**
	 * ������¼����Щ���Ǻ��ĵ㣬������Щ���ֱ���ܶȿɴ������Щ
	 */
	private HashMap<Integer, ArrayList<Integer>> coreHashMap;
	
	/**
	 * ���Լ�¼���ĵ��Ƿ��Ѿ��������������˱�����¼�����еĺ��ĵ㣬ÿ����һ�����ĵ�ʹӴ˼������Ƴ������ݵ�
	 */
	private HashSet<Integer> recoreOfCore;
	
	/**
	 * ��¼ÿ�����ݵ��eps�����ڵ����ݵ����Ŀ
	 */
	private int[] numOfEachDot;
	
	/**
	 * ָ��һ��������Ӧ�ð�������С���ݵ���Ŀ~Ҳ�����жϵ�ǰ���Ƿ�Ϊ���ĵ�
	 */
	private double minPts;
	
	/**
	 * ָ��Eps����İ뾶
	 */
	private double Eps;
	
	/**
	 * �����߳���Ŀ
	 */
	private static final int numOfThread = 8;
	
	/**
	 * �жϸ����߳��Ƿ������
	 */
	private boolean[] isend;
	
	public DBSCAN() throws IOException{
		df = new DF();
		documentName = df.getDocumentName();
		distance = new double[documentName.length][documentName.length];
		fourthDis = new double[documentName.length];
		numOfEachDot = new int[documentName.length];
		featureWords = df.getFeatureWords();
		eachDocumentVectorLength = new double[documentName.length];
		cluster = new ArrayList<ArrayList<String>>();
		coreHashMap = new HashMap<Integer,ArrayList<Integer>>();
		recoreOfCore = new HashSet<Integer>();
		
		isend = new boolean[numOfThread];
	    for(int i = 0; i < numOfThread; i++){
	    	isend[i] = false;
	    }
	    
	    initTextWordHashMap();
		initEachDocumentVectorLength();
		calculate();
		choseEps();
		choseMinPts();
		findTheCore();
		System.out.println("Eps = " + Eps);
		System.out.println("minPts = " + minPts);
		
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
	 * �Զ�ѡȡ���ʵ�Epsֵ
	 */
	private void choseEps(){
		double[][] tempDistance = new double[documentName.length][documentName.length];
		for(int i = 0; i < documentName.length; i++){
			for(int j = 0; j < documentName.length; j++){
				tempDistance[i][j] = distance[i][j];
			}
		}
		
		for(int i = 0; i < documentName.length; i++){
			Arrays.sort(tempDistance[i]);
		}
		for(int i = 0; i < documentName.length; i++){//���������ϻ�����ͼ~ָ���Ե��Ľ��ľ���ľ�ֵ��ΪEps��ֵ���ڳ�����distinct�����д洢�����������ݵ���������ƶȣ������Ľ��ȼ������ƶȵ��Ĵ�
			fourthDis[i] = tempDistance[i][documentName.length - 4];
		}
		double sum = 0.0;
		for(int i = 0; i < documentName.length; i++){
			sum = fourthDis[i] + sum;
		}
		Eps = sum / documentName.length;	
	}
	
	/**
	 * �Զ�ѡȡ���ʵ�minPts
	 */
	private void choseMinPts(){
		for(int i = 0; i < documentName.length; i++){
			int num = 0;
			for(int j = 0; j < documentName.length; j++){
				if(distance[i][j] >= Eps)
					num = num + 1;
			}
			numOfEachDot[i] = num;
		}
		double sum = 0.0;
		for(int i = 0; i < documentName.length; i++){
			sum = sum + numOfEachDot[i];
		}
		minPts = sum / documentName.length;
		
	}
	
	/**
	 * �������е����ݵ㣬�ҵ���Щ���ĵ��Լ����Ӧ��ֱ���ܶȿɴ�����ݵ�
	 */
	private void findTheCore(){
		for(int i = 0; i < documentName.length; i++){
			int num = 0;
			ArrayList<Integer> tempArrayList = new ArrayList<Integer>();
			for(int j = 0; j < documentName.length; j++){
				if(distance[i][j] >= Eps){
					num = num + 1;
					tempArrayList.add(j);
				}
			}
			if(num >= minPts){//����������˵����ǰ���Ǻ��ĵ�
				coreHashMap.put(i, tempArrayList);
				recoreOfCore.add(i);//�����ĵ��±���뵽��¼δ����ĺ��ĵ�ı�����
			}
			else {
				ArrayList<Integer> tempArrayList2 = new ArrayList<Integer>();
				coreHashMap.put(i, tempArrayList2);
			}
		}
	}
	
	
	/*private void findTheCore(){
		for(int i = 0; i < documentName.length; i++){
			int num = 0;
			ArrayList<Integer> tempArrayList = new ArrayList<Integer>();
			for(int j = 0; j < documentName.length; j++){
				if(distance[i][j] >= Eps){
					num = num + 1;
					tempArrayList.add(j);
				}
			}
			if(num >= minPts){//����������˵����ǰ���Ǻ��ĵ�
				coreHashMap.put(i, tempArrayList);
				recoreOfCore.add(i);//�����ĵ��±���뵽��¼δ����ĺ��ĵ�ı�����
			}
			else {
				ArrayList<Integer> tempArrayList2 = new ArrayList<Integer>();
				coreHashMap.put(i, tempArrayList2);
			}
		}
	}
	*/
	
	/**
	 * dbscan���Ĵ����
	 */
	 public void dbscan(){
		 for(int i = 0; i < documentName.length; i++){
			 HashSet<Integer> tempHashSet = new HashSet<Integer>();
			 if(recoreOfCore.contains(i)){//��������˵����ǰ���Ǻ��ĵ�,���濪ʼ����ĺ��ĵ��Ӧ��ֱ���ܶȿɴ�����ݵ㣬ͬʱ���ú��ĵ��δ������ĵ��б����Ƴ�
				 recoreOfCore.remove(i);
				 ArrayList<Integer> tempArrayList = coreHashMap.get(i);
				 for(int j = 0; j < tempArrayList.size(); j++){
					 tempHashSet.add(tempArrayList.get(j));
				 }
				 for(int k = 0; k < tempHashSet.size(); k++){//ÿ�����е�����ĳ��Σ�tempHashSet�д洢�Ķ��Ǻ͵�i�����ĵ�ֱ���ܶȿɴ�����ݵ㣬����forѭ��������˱����Է����Ƿ��к��ĵ�
					 if(recoreOfCore.contains(tempHashSet.toArray()[k])){
						 recoreOfCore.remove(tempHashSet.toArray()[k]);
						 for(int m = 0; m < coreHashMap.get(tempHashSet.toArray()[k]).size(); m++){//---------------A
							 tempHashSet.add(coreHashMap.get(tempHashSet.toArray()[k]).get(m));
						 }
						 //����if����˵���ڵ�i�����ĵ��ֱ���ܶȿɴ����ݵ��з������µĺ��ĵ㣬����A����forѭ����tempHashSet�п����������µ����ݵ㣬
						 //���tempHashSet�¼��������ݵ���������ж��¼���ĵ��Ƿ�ҲΪ���ĵ㣬��Ϊ�޷��ж��µ����ݵ㱻���뵽tempHashSet�е��ĸ�λ�ã��˼��ǰ���ϣ���ŵ���~�����ã�
						 //���Խ�k����Ϊ-1���ص�forѭ��k++����k�ֱ�Ϊ0�����¿�ʼ����tempHashSet��ͬʱ��recoreOfCore����֤������ĺ��ĵ㱻�Ƴ���ȥ���Ա�֤�����ظ�������ĵ㣬
						 //��������k��forѭ����ֻ��tempHashSet�в�����δ����ĺ��ĵ㣬��ʱtempHashSet�оͱ����˵�i�����ĵ�Ϊ���������Ĵ���Ӧ���������е����ݵ�
						 k = -1;
					 }
				 }//��forѭ����������ҵ���һ���µĴ��Լ��ôذ��������е����ݵ�
				 
				 ArrayList<String> newCluster = new ArrayList<String>();
				 for(int j =0; j < tempHashSet.size(); j++){
					 newCluster.add(documentName[(int) tempHashSet.toArray()[j]]);
				 }
				 cluster.add(newCluster);
			 }
		 }
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
	
	public ArrayList<ArrayList<String>> getCluster() {
		return cluster;
	}
	
	public static void main(String[] args) throws IOException{
		DBSCAN mydbsacn = new DBSCAN();
		long startTime=System.currentTimeMillis();
		System.out.println("begin");
		mydbsacn.dbscan();
		long endTime=System.currentTimeMillis();
		System.out.println((endTime-startTime) + "ms");
		ArrayList<ArrayList<String>> result = mydbsacn.getCluster();
		System.out.println(result.size());
		for(int i = 0; i < result.size();i++){
			for(int j = 0; j < result.get(i).size(); j++){
				System.out.print(result.get(i).toArray()[j] + "  ");
			}
			System.out.println("");
		}
	}
	

}
