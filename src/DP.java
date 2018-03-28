import java.util.ArrayList;
import java.util.List;

 
public class DP {
	
	public double[] inflow;       //来流
	public double startCapacity;  //起始库容
	public double endCapacity;    //末水位
	public double maxCapacity;	  //最大库容
	public double minCapacity;    //最小库容
	public double maxPs;          //水电最大出力
	public double minPs;          //水电最小出力
	public double maxPh;          //火电最大出力
	public double minPh;          //火电最小出力
	public double precision;      //离散精度
	public int    intervalNum;    //时段数,此问题为5
	
	
	
	public DP(double[] inflow, double startCapacity, double endCapacity, double maxCapacity, double minCapacity,
			double maxPs, double minPs, double maxPh, double minPh, double precision, int intervalNum) {
		super();
		this.inflow = inflow;
		this.startCapacity = startCapacity;
		this.endCapacity = endCapacity;
		this.maxCapacity = maxCapacity;
		this.minCapacity = minCapacity;
		this.maxPs = maxPs;
		this.minPs = minPs;
		this.maxPh = maxPh;
		this.minPh = minPh;
		this.precision = precision;
		this.intervalNum = intervalNum;
	}

	//火电耗煤特性
	public double calCoalConsumption(double Ph) {
		double coalConsumption;
		coalConsumption=Ph*Ph+1;
		return coalConsumption;
	}
	
	//水电流量特性
	public double calOutputFlow(double Ps) {
		double outputFlow;
		outputFlow=Ps;
		return outputFlow;
	}
	
	
	
	public List<DPNode> calDP() {
		List<DPNode> dpNodesList =new  ArrayList<DPNode>();
		
		//离散库容
		int capacityLength=(int) ((maxCapacity-minCapacity)/precision)+1;
		double[] capacityDiscrete=new double[capacityLength];
		for (int i = 0; i < capacityDiscrete.length; i++) {
			capacityDiscrete[i]=minCapacity+precision*i;
		}
		
		//初始化末节点，加入链表
		DPNode endDpNode = new DPNode(2, 0, 0, null, true, intervalNum-1);  //时段数从零开始
		dpNodesList.add(endDpNode);
		
		//计算倒数第一个阶段
		for (int i = 0; i < capacityDiscrete.length; i++) {
			//判断下泄流量非负
			if ((capacityDiscrete[i]-endCapacity+inflow[3])>=0) {
				double tempOutFlow = capacityDiscrete[i]-endCapacity+inflow[3];
				double tempTargetValue;
					if ((DPCal.loadXT[intervalNum-2]-calOutputFlow(tempOutFlow))<0) {	//水电承载全部负荷，火电空载
						tempTargetValue = 1;
				}else {	//水电不能全部承担负荷，火电承担剩余负荷
					tempTargetValue=calCoalConsumption(DPCal.loadXT[intervalNum-2]-calOutputFlow(tempOutFlow));
				}
				DPNode temDpNode = new DPNode(capacityDiscrete[i], tempOutFlow, tempTargetValue, endDpNode, true, intervalNum-2);
				dpNodesList.add(temDpNode);
			}
		}
		
		//倒推，从intervalNum-1到第二个时段
		for (int i = intervalNum-3; i > 0; i--) {
			for (int j = 0; j < capacityDiscrete.length; j++) {
				double tempTargetValue = Double.MAX_VALUE;
				double tempOutFlow = -1;
				DPNode temDpNode = new DPNode(capacityDiscrete[j], tempOutFlow, tempTargetValue, null, false, i);
				for (int k = 0; k <dpNodesList.size(); k++) {	//遍历list表
					
					if (dpNodesList.get(k).getInterval()==i+1) {
						//判断下泄流量非负
						if ((capacityDiscrete[j]-dpNodesList.get(k).getCapacity()+inflow[i])>=0) {
							 tempOutFlow = capacityDiscrete[j]-dpNodesList.get(k).getCapacity()+inflow[i];
							
							double currentTargetValue;
								if ((DPCal.loadXT[i]-calOutputFlow(tempOutFlow))<0) {	//水电承载全部负荷，火电空载
									currentTargetValue = 1;
							}else {	//水电不能全部承担负荷，火电承担剩余负荷
								currentTargetValue = calCoalConsumption(DPCal.loadXT[i]-calOutputFlow(tempOutFlow));
							}
								
							if ((currentTargetValue+dpNodesList.get(k).getTargetValue())<tempTargetValue) {
								tempTargetValue=currentTargetValue+dpNodesList.get(k).getTargetValue();
								temDpNode.setOutFlow(tempOutFlow);
								temDpNode.setTargetValue(tempTargetValue);
								temDpNode.setFeasible(true);
								temDpNode.setNextDpNode(dpNodesList.get(k));
							}
						}
					}
				}
				if (temDpNode.isFeasible()) {
					dpNodesList.add(temDpNode);	
				}else {
					System.out.println("第"+i+"阶段无解");
				}
			}
		}
		
		//最后一个节点
		for (int k = 0; k < dpNodesList.size(); k++) {
			if (dpNodesList.get(k).getInterval()==1) {
				double tempTargetValue = Double.MAX_VALUE;
				
				//判断下泄流量非负
				if ((startCapacity-dpNodesList.get(k).getCapacity()+inflow[0])>=0) {
					double tempOutFlow = startCapacity-dpNodesList.get(k).getCapacity()+inflow[0];
					
					double currentTargetValue;
						if ((DPCal.loadXT[0]-calOutputFlow(tempOutFlow))<0) {	//水电承载全部负荷，火电空载
							currentTargetValue = 1;
					}else {	//水电不能全部承担负荷，火电承担剩余负荷
						currentTargetValue = calCoalConsumption(DPCal.loadXT[0]-calOutputFlow(tempOutFlow));
					}
						
						if ((currentTargetValue+dpNodesList.get(k).getTargetValue())<tempTargetValue) {
							tempTargetValue=currentTargetValue+dpNodesList.get(k).getTargetValue();
							DPNode temDpNode = new DPNode(startCapacity, tempOutFlow, tempTargetValue, dpNodesList.get(k), true, 0);
							dpNodesList.add(temDpNode);
						}
				}
			
			}
		}
		return dpNodesList;
	}
}
