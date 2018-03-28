
public class DPNode {

	/**当前节点的离散库容**/
	private double capacity;
	/**当前节点到下一个节点的下泄流量**/
	private double outFlow;
	/**当前节点至末节点的目标函数值**/
	private double targetValue;
	/**当前节点对应的下一个节点**/
	private DPNode nextDpNode;
	/**当前节点是否可行**/
	private boolean isFeasible;
	/**节点所在时段**/
	private int interval;
	public DPNode(double capacity, double outFlow, double targetValue, DPNode nextDpNode, boolean isFeasible,
			int interval) {
		super();
		this.capacity = capacity;
		this.outFlow = outFlow;
		this.targetValue = targetValue;
		this.nextDpNode = nextDpNode;
		this.isFeasible = isFeasible;
		this.interval = interval;
	}
	/**
	 * @return the capacity
	 */
	public double getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	/**
	 * @return the outFlow
	 */
	public double getOutFlow() {
		return outFlow;
	}
	/**
	 * @param outFlow the outFlow to set
	 */
	public void setOutFlow(double outFlow) {
		this.outFlow = outFlow;
	}
	/**
	 * @return the targetValue
	 */
	public double getTargetValue() {
		return targetValue;
	}
	/**
	 * @param targetValue the targetValue to set
	 */
	public void setTargetValue(double targetValue) {
		this.targetValue = targetValue;
	}
	/**
	 * @return the nextDpNode
	 */
	public DPNode getNextDpNode() {
		return nextDpNode;
	}
	/**
	 * @param nextDpNode the nextDpNode to set
	 */
	public void setNextDpNode(DPNode nextDpNode) {
		this.nextDpNode = nextDpNode;
	}
	/**
	 * @return the isFeasible
	 */
	public boolean isFeasible() {
		return isFeasible;
	}
	/**
	 * @param isFeasible the isFeasible to set
	 */
	public void setFeasible(boolean isFeasible) {
		this.isFeasible = isFeasible;
	}
	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	

	
}
