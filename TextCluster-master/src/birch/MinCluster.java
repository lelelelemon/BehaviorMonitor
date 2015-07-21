package birch;

import java.util.ArrayList;

public class MinCluster {

	private CF cf;
    private ArrayList<String> inst_marks;
     
    public MinCluster(){
        cf=new CF();
        inst_marks=new ArrayList<String>();
    }
 
    public CF getCf() {
        return cf;
    }
 
    public void setCf(CF cf) {
        this.cf = cf;
    }
 
    public ArrayList<String> getInst_marks() {
        return inst_marks;
    }
 
    public void setInst_marks(ArrayList<String> inst_marks) {
        this.inst_marks = inst_marks;
    }
     
    //����������֮��ľ���
    public static double getDiameter(CF cf){
        double diameter=0.0;
        int n=cf.getN();
        for(int i=0;i<cf.getLS().length;i++){
            double ls=cf.getLS()[i];
            double ss=cf.getSS()[i];
            diameter=diameter+(2*n*ss-2*ls*ls);
        }
        diameter=diameter/(n*n-n);
        return Math.sqrt(diameter);
    }
     
    //���������һ���غϲ����ֱ��
    public static double getDiameter(MinCluster cluster1,MinCluster cluster2){
        CF cf=new CF(cluster1.getCf());
        cf.addCF(cluster2.getCf(), true);
        return getDiameter(cf);
    }
    
    /**
     * �ϲ�������
     * @param cluster
     */
    public void mergeCluster(MinCluster cluster){
    	//���ô˺��������뵱ǰcluster��ӽ���һ�����ӽڵ㣬thisָ�ľ���������ӽڵ�
        this.getCf().addCF(cluster.getCf(), true);
        //cluster��������Ȼ��MinCluster����һ��cluster��һ��ֻ����һ�����ݵ㣬���������foeѭ����֤���¼ӽ�����cluster�е�ÿ�����ݵ�������Ϣ���ӽ��µ�cluster�У���Ȼ�����forѭ���ͱ����㷨��ϵ����
        //������Ϊ��������ʱ���Կ������ݵ�ԭ����𣬷��������������
        for(int i=0;i<cluster.getInst_marks().size();i++){
            this.getInst_marks().add(cluster.getInst_marks().get(i));
        }
    }
}