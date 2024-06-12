import processing.core.*;

public class Camera {
    PApplet pa;
    public static int max_depth = 100;
    public static int runs_to_average = 50; // default to be very heavy rendering
    public float aspect_ratio = 1 / 1;
    public int image_width = 500;

    // Calculate the image height, and ensure that it's at least 1.
    int image_height = (int) (image_width / aspect_ratio);

    // Camera
    public Point eye = new Point(0, 0, 0);
    public Point lookat = new Point(0, 0, -1); // Point camera is looking at
    public PVector vup = new PVector(0, 1, 0); // Camera-relative "up" direction

    HittableList world;

    public Camera(PApplet pa) {
        this.pa = pa;
        this.image_height = (int) (image_width / aspect_ratio);
        this.image_height = (image_height < 1) ? 1 : image_height;

        // Initialize world full of Hittables
        Sphere mySphere = new Sphere(new PVector((float) -0.7, 0, -2), (float) 0.5, new PVector(0, 0, 0));
        Sphere mySecondSphere = new Sphere(new PVector(0, 1, -4), (float) 0.5, new PVector(0, 0, 0));
        Sphere myThirdSphere = new Sphere(new PVector((float) 0.75, 0, -4), (float) 0.5, new PVector(0, 0, 0));
        Sphere myGround = new Sphere(new PVector(0, (float) -100.5, -1), (float) 100, new PVector(48, 11, 11)); // brown
                                                                                                                // ground

        PVector p1 = new PVector((float) 0, (float) 0, (float) -6); // right angle
        PVector p2 = new PVector((float) 2, (float) 0, (float) -6);
        PVector p3 = new PVector((float) 0, (float) 2, (float) -6);
        Triangle myTriangle = new Triangle(p1, p2, p3, new PVector(0, 0, 200));
        PVector a1 = new PVector((float) 0, (float) 2, (float) -6); //
        PVector a2 = new PVector((float) 2, (float) 0, (float) -6);
        PVector a3 = new PVector((float) 2, (float) 2, (float) -6); // right corner // NOTE: if triangle not showing
                                                                    // play around with vertice order
        Triangle myTriangle2 = new Triangle(a1, a2, a3, new PVector(0, 0, 200));

        world = new HittableList();
        world.add(myTriangle);
        world.add(myTriangle2);
        world.add(mySphere);
        world.add(mySecondSphere);
        world.add(myThirdSphere);
        world.add(myGround);
    }

    public void see() {

        // Camera stuffs
        float focal_length = PVector.sub(eye, lookat).mag();
        double fov = 75; // 90 degrees FOV
        double theta = fov * Math.PI / 180.0; // radians total of FOV
        float h = (float) Math.tan(theta / 2); // from center of viewport to top/bottom
        float viewport_height = 2 * h * focal_length;
        float viewport_width = viewport_height * (image_width / image_height);

        // Calculate the u,v,w unit basis vectors for the camera coordinate frame.
        PVector w = PVector.sub(eye, lookat).normalize(null);
        PVector u = PVector.cross(vup, w, null).normalize(null);
        PVector v = PVector.cross(w, u, null);

        // Calculate the vectors across the horizontal and down the vertical viewport
        PVector viewport_u = PVector.mult(u, viewport_width);
        PVector viewport_v = PVector.mult(v, -viewport_height);

        // Calculate the horizontal and vertical delta vectors from pixel to pixel.
        PVector pixel_delta_u = PVector.div(viewport_u, image_width);
        PVector pixel_delta_v = PVector.div(viewport_v, image_height);

        // Calculate the location of the upper left pixel.

        // to always LOOK at the "lookat" point (rotate towards it as needed)
        // PVector viewport_upper_left = PVector.sub(
        // PVector.sub(PVector.sub(eye, PVector.mult(w, focal_length)),
        // PVector.div(viewport_u, 2)),
        // PVector.div(viewport_v, 2));
        PVector viewport_upper_left = PVector.sub(
                PVector.sub(PVector.sub(eye, new PVector(0, 0, focal_length)), PVector.div(viewport_u, 2)),
                PVector.div(viewport_v, 2)); // to always LOOK forwards

        PVector pixel00_loc = PVector.add(viewport_upper_left,
                PVector.mult(PVector.add(pixel_delta_u, pixel_delta_v), (float) 0.5));

        this.pa.loadPixels(); // for every frame, load all existing pixels
        for (int j = 0; j < image_height; j++) {
            if (j % 25 == 0 || j == image_height - 1) {
                if (Camera.runs_to_average >= 50) {
                    System.out.println("\rScanlines remaining: " + (image_height - j)); // only print progress on heavy
                                                                                        // renders
                }
            }
            for (int i = 0; i < image_width; i++) {
                PVector pixel_center = PVector.add(PVector.add(pixel00_loc, PVector.mult(pixel_delta_u, i)),
                        PVector.mult(pixel_delta_v, j));
                PVector ray_direction = PVector.sub(pixel_center, this.eye);
                Ray rayToPixel = new Ray(this.eye, ray_direction);
                PVector rayColorVectorSum = new PVector(0, 0, 0);
                for (int z = 0; z < runs_to_average; z++) {
                    PVector rayColorVector = getRayColorVector(rayToPixel, Camera.max_depth);
                    rayColorVectorSum.add(rayColorVector);
                }
                rayColorVectorSum.div(runs_to_average);

                int rayColor = this.pa.color(rayColorVectorSum.x, rayColorVectorSum.y, rayColorVectorSum.z, 255);
                int x = i;
                int y = j;
                this.pa.pixels[y * this.image_width + x] = rayColor;

            }
        }
        this.pa.updatePixels(); // update the window after changing pixels
    }

    public PVector getRayColorVector(Ray r, int depth) {
        if (depth <= 0) {
            return new PVector(0, 0, 0); // if depth 0 is reached, something MUST have hit at least once
        }

        Hit rec = this.world.hit(r, .0001, Double.MAX_VALUE);
        if (rec.hitHappened) {
            PVector direction = Utils.random_on_hemisphere(rec.normal); // random bounce away from object's inside
            // darken from white on evert hit (hence, mult by 0.5)
            return PVector.add(
                    PVector.mult(getRayColorVector(new Ray(rec.location, direction), depth - 1), (float) 0.5),
                    rec.color);
        }
        return new PVector(255, 255, 255); // if not hit, white
    }
}
