import processing.core.*;

public class Ray {
    public Point start;
    public Point end;
    public Ray(Point start, Point end){
        this.start = start;
        this.end = end;
    }
    public float getMag(){
        return new PVector(end.x - start.x, end.y - start.y, end.z - start.z).mag();
    }
    public static void test(){
        PVector test = new PVector(5, 5);
        System.out.println(test.mag());
    }
}
