package analyse;

public class CloseToDoc {

	private String name;
	private float distance;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public CloseToDoc(String name,float distance){
		this.name=name;
		this.distance=distance;
	}
}
