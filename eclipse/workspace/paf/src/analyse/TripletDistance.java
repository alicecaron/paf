package analyse;

public class TripletDistance {
	private MyDocument doc1;
	private MyDocument doc2;
	private float distance;
	public MyDocument getDoc1() {
		return doc1;
	}
	public void setDoc1(MyDocument doc1) {
		this.doc1 = doc1;
	}
	public MyDocument getDoc2() {
		return doc2;
	}
	public void setDoc2(MyDocument doc2) {
		this.doc2 = doc2;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public TripletDistance(MyDocument doc1,MyDocument doc2,float distance){
		this.doc1=doc1;
		this.doc2=doc2;
		this.distance=distance;
	}
	
}
