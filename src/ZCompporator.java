import java.util.Comparator;


public class ZCompporator implements Comparator<Triangle> {

	public int compare(Triangle o1,Triangle o2) {
		if(o1.z>o2.z) return -1;
		return 1;
	}

}
