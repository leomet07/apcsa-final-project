import java.util.ArrayList;

public class HittableList extends ArrayList<Hittable> {
    boolean hit(Ray r, double ray_tmin, double ray_tmax, Hit rec) {
        Hit tempRec = new Hit();
        boolean hit_anythng = false;
        double closest_so_far = ray_tmax;

        for (Hittable object : this) {
            if (object.hit(r, ray_tmin, closest_so_far, tempRec)) {
                hit_anythng = true;
                closest_so_far = tempRec.t;
                rec = tempRec;
            }
        }

        return hit_anythng;
    }
}
