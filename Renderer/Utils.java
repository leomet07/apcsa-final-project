import processing.core.PVector;

public class Utils {
    public static float random_float(double min, double max) {
      // Returns a random real in [min,max).
      return (float) (min + (max-min)* Math.random());
    }
  
    public static PVector random() {
        return new PVector((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    public static PVector random(double min, double max) {
        return new PVector(random_float(min,max), random_float(min,max), random_float(min,max));
    }
    
    public static PVector random_on_hemisphere(PVector normal) {
        PVector on_unit_sphere = PVector.random3D();
        System.out.println("Before dot: " + normal);
        if (PVector.dot(on_unit_sphere, normal) > 0.0){ // In the same hemisphere as the normal
            return on_unit_sphere;
        }
        else{
            return PVector.mult(on_unit_sphere, -1);
        }
    }
}
