public class Camera {
    public Point eye;

    public Camera(){
        this.eye = new Point(0, 0, 0);
    }

    public void see(){
        Point p = new Point(5, 5, 5);
        Ray rayToP = new Ray(this.eye, p);
        System.out.println(rayToP.getMag());
    }
}