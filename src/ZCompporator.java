import java.util.Comparator;


public class ZCompporator implements Comparator<triangle> {

	public int compare(triangle o1, triangle o2) {
		if(o1.z>o2.z) return -1;
		return 1;
	}

}
