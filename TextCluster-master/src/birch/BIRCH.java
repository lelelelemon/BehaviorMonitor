package birch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import chinese_feature_extraction.DF;

public class BIRCH {

	private DF df;
	
	//private String filePath = "C:\\Users\\Administrator\\Desktop\\birch��������.txt";
	
	/**
	 * ���ݵ������ά�ȣ������ı�������˱�����¼�ı��������ʵĸ�����
	 */
    public static int dimen = 0;
    LeafNode leafNodeHead=new LeafNode();
    
    /**
     * ��¼���ݵ�ĸ��������������ı���ƪ��
     */
    private int point_num;
    
    /**
     * ����dfʵ���������Ĵ������ĵ���
     */
    private String[] documentName;
    
    /**
     * ����dfʵ����������������
     */
    private ArrayList<String> featureWords;
    
    private HashMap<String, HashMap<String, Double>>documenttfidf;
    
    
    public BIRCH() throws IOException{
    	df = new DF();
    	dimen = df.getFeatureWords().size();
    	documentName = df.getDocumentName();
    	featureWords = df.getFeatureWords();
    	point_num = 0;
    	documenttfidf = new HashMap<String,HashMap<String, Double>>();
    	initDocumennttfidf();
    	
    }
    
    
    /**
     * ����ÿƪ�ı��������ʵ�tf-idfֵ
     */
    private void initDocumennttfidf(){
    	for(int i = 0; i < documentName.length; i++){
    		HashMap<String, Double>temphaHashMap = new HashMap<String,Double>();
    		for(int j = 0; j < featureWords.size(); j++){
    			if(df.getText().get(documentName[i]).containsKey(featureWords.get(j))){
    				double value = df.getText().get(documentName[i]).get(featureWords.get(j));
    				value = value * (Math.log(documentName.length / df.getText().get(documentName[i]).get(featureWords.get(j))));
    				temphaHashMap.put(featureWords.get(j), value);
    			}
    			else {
					temphaHashMap.put(featureWords.get(j), 0.0);
				}
    			documenttfidf.put(documentName[i], temphaHashMap);
    		}
    	}
    }
    
    
    
    public  TreeNode buildTree(){
    	//�Ƚ���һ��Ҷ�ӽڵ�
    	//�����޲ι��캯��ʵ����һ��ֻ�п�ArrayList��LeafNode��������ArrayList������ŵ�ǰ���Ҷ�ӽڵ�����ĺ��ӵĸ���
        LeafNode leaf=new LeafNode();
        TreeNode root=leaf;
        
      //��Ҷ�ӽڵ����洢Ҷ�ӽڵ��˫������
        leafNodeHead.setNext(leaf);
        leaf.setPre(leafNodeHead);
        
        
        double[] data = new double[dimen];
        for(int i = 0; i < documentName.length; i++){
        	point_num++;
        	for(int j = 0; j < featureWords.size(); j++){
        		data[j] = documenttfidf.get(documentName[i]).get(featureWords.get(j));
        	}
        	String mark=documentName[i];
        	
        	CF cf=new CF(data);
            //�����޲ι��캯������һ���յ�MinCluster���͵�ʵ��
            MinCluster subCluster=new MinCluster();
            //cf�������Ϣ��䵽MinCluster���͵ı�����
            subCluster.setCf(cf);
            subCluster.getInst_marks().add(mark);
            
            //���µ���point instance��������
            root.absorbSubCluster(subCluster);
            //Ҫʼ�ձ�֤root�����ĸ��ڵ�
            while(root.getParent()!=null){
                root=root.getParent();
            }
        }

        return root;
    }
    
  //��ӡB-��������Ҷ�ӽڵ�
    public void printLeaf(LeafNode header){
        //point_num��0
        point_num=0;
        while(header.getNext()!=null){
            System.out.println("\nһ��Ҷ�ӽڵ�:");
            header=header.getNext();
            for(MinCluster cluster:header.getChildren()){
                System.out.println("\nһ����С��:");
                for(String mark:cluster.getInst_marks()){
                    point_num++;
                    System.out.print(mark+"\t");
                }
            }
        }
    }
     
    //��ӡָ�����ڵ������
    public void printTree(TreeNode root){
        if(!root.getClass().getName().equals("birch.LeafNode")){
            NonleafNode nonleaf=(NonleafNode)root;
            for(TreeNode child:nonleaf.getChildren()){
                printTree(child);
            }
        }
        else{
            System.out.println("\nһ��Ҷ�ӽڵ�:");
            LeafNode leaf=(LeafNode)root;
            for(MinCluster cluster:leaf.getChildren()){
                System.out.println("\nһ����С��:");
                for(String mark:cluster.getInst_marks()){
                    System.out.print(mark+"\t");
                    point_num++;
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException{
    	BIRCH birch=new BIRCH();
    	TreeNode root=birch.buildTree();
        birch.point_num=0;
        birch.printTree(root);
        System.out.println();
        //birch.printLeaf(birch.leafNodeHead);
        //ȷ�ϱ������point instance��ɨ�����ݿ�ʱ¼���point instance�ĸ�����һ�µ�
        System.out.println(birch.point_num);
    }
    
    
}
