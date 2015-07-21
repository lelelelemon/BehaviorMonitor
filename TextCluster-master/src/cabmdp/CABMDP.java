/**
 * һ�˾���
 */

package cabmdp;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import chinese_feature_extraction.DF;

public class CABMDP {

	/**
	 * ��¼�����ĵ�ֵ
	 */
	private ArrayList<HashMap<String, Double>>center;	
	/**
	 * ��¼�����ص��ı������֣��������
	 */
	private ArrayList<ArrayList<String>> cluster;	
	/**
	 * ��¼������
	 */
	private ArrayList<String> featureWords;
	
	/**
	 * ��¼ûƪ�ı��������ʵ�tf-idfֵ
	 */
	private HashMap<String, HashMap<String, Double>>textWordHashMap;
	/**
	 * ��¼�����ĵ�������
	 */
	private String[] documentName;
	/**
	 * ��ô������ı��Ĵ��·��
	 */
	private String filePath;

	
	/**
	 * ����ÿƪ�ı��������̶ȣ��ڼ����������ƶ�ʱ��ֹ�ظ�����
	 */
	private HashMap<String, Double> eachTextVectorLength;
	/**
	 * �ı��������ĵ���������ֵ����������С�ڴ���ֵ��������һ��������
	 */
	private double R;
	
	/**
	 * ���ĵ����������ѡȡ��N0���ı����Լ���ƽ��������ȷ����ֵ��Χ
	 */
	private static final int N0 = 2500;
	private DF df;
	
	public CABMDP() throws IOException{
		filePath = "E:\\java_Eclipse\\����\\�ı��������Ͽ�\\����";
		//filePath = "C:\\Users\\Administrator\\Desktop\\����";
		center = new ArrayList<HashMap<String, Double>>();
		df = new DF();
		featureWords = new ArrayList<String>(df.getFeatureWords());
		cluster = new ArrayList<ArrayList<String>>();
		initDocumentNAme();
		initTextWordHashMap();
		initEachTextVectorLength();
		initCenter();
		calaulateR();
		calculate();
		for(int i = 0; i < cluster.size(); i++){
			System.out.println("+++" + cluster.get(i));
		}
		System.out.println("");
		
	}
	/**
	 * ��ʼ��documentName
	 */
	private void initDocumentNAme(){
		File documentDirectory = new File(filePath);
		
		if (!documentDirectory.isDirectory())           //�������ļ������Ƿ�Ϊ�ļ���,��Ϊ�����ǰѴ���������±��浽����ļ����µ�,��Ҫ�����ж�
        {
            throw new IllegalArgumentException("����ѡ���ĵ�����ʧ�ܣ� [" + filePath + "]");
        }
		documentName = documentDirectory.list();
	}

	
	/**
	 * ��ʼ��textWordHashMap
	 */
	private void initTextWordHashMap(){
		HashMap<String, Integer>wordDF = df.getWordDF();
		textWordHashMap = new HashMap<String,HashMap<String, Double>>();
		HashMap<String, HashMap<String, Double>> text = df.getText();
		for(int i = 0; i < documentName.length; i++){
			
			HashMap<String, Double> wordtf = text.get(documentName[i]);
			HashMap<String, Double> temphHashMap = new HashMap<String,Double>();
			for(int j = 0; j < featureWords.size(); j++){
				//System.out.println(featureWords.get(j));
				//System.out.println(documentName[i]);
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
	private void initEachTextVectorLength(){
		eachTextVectorLength = new HashMap<String, Double>();
		for(int i = 0; i < documentName.length; i++){
			double value = 0.0;
			HashMap<String, Double> temphHashMap = new HashMap<String,Double>(textWordHashMap.get(documentName[i]));
			for(int j = 0; j < featureWords.size(); j++){
				//System.out.println("***" + temphHashMap.get(featureWords.get(j)));
				value = value + temphHashMap.get(featureWords.get(j)) * temphHashMap.get(featureWords.get(j));
				//System.out.println(j + "---" + value);
			}
			value = Math.sqrt(value);
			eachTextVectorLength.put(documentName[i], value);
		}
	}

	/**
	 * ��ʼ��������
	 */
 	private void initCenter(){
		center = new ArrayList<HashMap<String, Double>>();
		int index = (int)(Math.random() * documentName.length);
		HashMap<String, Double> temphHashMap = new HashMap<String,Double>(textWordHashMap.get(documentName[index]));
		center.add(temphHashMap);
		ArrayList<String> firstText = new ArrayList<String>();
		firstText.add(documentName[index]);
		cluster.add(firstText);
	}
 	/**
 	 * ������ֵ
 	 */
 	private void calaulateR(){
 		double[] eachDistant = new double[N0];
 		for(int i = 0; i < N0; i++){
 			
 			int index1 = (int)(Math.random() * documentName.length);
 			int index2 = (int)(Math.random() * documentName.length);
 			while(index2 == index1){
 				index2 = (int)(Math.random() * documentName.length);
 			}
 			
 			double value = 0.0;
 			double A = 0.0;
 			double B = eachTextVectorLength.get(documentName[index1]);
 			double C = eachTextVectorLength.get(documentName[index2]);
 	
 			for(int j = 0; j < featureWords.size(); j++){
 				//System.out.println("***" + textWordHashMap.get(documentName[0]).get(featureWords.get(j)));
 				A = A + textWordHashMap.get(documentName[index1]).get(featureWords.get(j)) * 
 						textWordHashMap.get(documentName[index2]).get(featureWords.get(j));
 				//System.out.println(j + "---" + A);
 			}
 			value = A / (B * C);
 			eachDistant[i] = value;
 		}
 		
 		double EX = 0.0;
 		double DX = 0.0;
 		for(int i = 0; i < N0; i++){
 			EX = EX + eachDistant[i];
 		}
 		EX = EX / N0;
 		for(int i = 0; i < N0; i++){
 			DX = DX + (eachDistant[i] - EX) * (eachDistant[i] - EX);
 		}
 		DX = Math.sqrt(DX / N0 );
 		
 		//R = EX + 0.7 * DX;
 		R = EX + 0.3 * DX;
 	}
 	
 	/**
 	 * һ�˾����㷨����
 	 */
	private void calculate(){
		for(int i = 0; i < documentName.length; i++){
			double max = 0.0;
			int index = 0;
			HashMap<String, Double> textHashMap = new HashMap<String,Double>(textWordHashMap.get(documentName[i]));
			double C = eachTextVectorLength.get(documentName[i]);
			for(int j = 0; j < center.size(); j++){
				double temp = 0.0;
				double A = 0.0;
				double B = 0.0;
				for(int m = 0; m < featureWords.size(); m++){
					A = A + textHashMap.get(featureWords.get(m)) * center.get(j).get(featureWords.get(m));
					B = B + center.get(j).get(featureWords.get(m)) * center.get(j).get(featureWords.get(m));
				}
				
				temp = A / (Math.sqrt(B) * C);
				if(temp > max){
					max = temp;
					index = j;	
				}
			}
			//�ж��ǽ������ı���������Ĵػ���������һ����
			if(max >= R){
				cluster.get(index).add(documentName[i]);
				updateCenter(index,textHashMap);
				
			}
			else {
				center.add(textHashMap);
				ArrayList<String> newCluster = new ArrayList<String>();
				newCluster.add(documentName[i]);
				cluster.add(newCluster);
			}
		}
	}
	
	/**
	 * ���´�����
	 * @param index
	 * @param textHashMap
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
	 * �����ʵ�����������һ����������Ϣ����������������ʵ����һ�˾���Ĺ�����Ķ����û�е��ö���������
	 */
	public void information(){
		System.out.println("CABMDP has finish!");
	}
	
	

	
	public static void main(String[] args) throws IOException{
		CABMDP c1 = new CABMDP(); 
		c1.information();
	}
	
}
