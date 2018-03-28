import java.util.ArrayList;
import java.util.List;

public class DPCal {

	public static final double[] loadXT={6,4,3,7};
	public static final double[] inflow = {3,3,3,3};
	
	public static void main(String[] args) {
		DP dp = new DP(inflow, 2, 2, 4, 0, 5, 1, 5, 1, 1, 5);
		List<DPNode> list = new ArrayList<DPNode>();
		list = dp.calDP();
		DPNode firstDpNode = list.get(list.size()-1);
		System.out.println(firstDpNode.getTargetValue());
		for (int i = 0; i < 5; i++) {
			System.out.println(firstDpNode.getCapacity());
			firstDpNode=firstDpNode.getNextDpNode();
		}

	}

}
