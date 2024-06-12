import java.util.ArrayList;

public class HittableList extends ArrayList<Hittable> {
    Hit hit(Ray r, double ray_tmin, double ray_tmax) {
        Hit rec = new Hit();
        Hit tempRec = new Hit();
        double closest_so_far = ray_tmax;

        for (Hittable object : this) {
            tempRec = new Hit();
            if (object.hit(r, ray_tmin, closest_so_far, tempRec)) {
                closest_so_far = tempRec.t;
                // System.out.println("Normal should be set (temp): " + tempRec);
                // System.out.println("Normal should be set (temp)2: " + tempRec.normal);
                try {
                    rec = (Hit) tempRec.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            // System.out.println("Setting!");
        }
        // System.out.println("Set!");
        // System.out.println("Normal should be set (perm): " + rec);
        // System.out.println("Normal should be set (perm)2: " + rec.normal);

        return rec;
    }
}
