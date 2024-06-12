import java.util.ArrayList;

int imageWidth = 800;
int imageHeight = int(imageWidth / (16.0 / 9.0));
HittableList world = new HittableList();
Camera cam = new Camera();

void settings(){
  size(imageWidth, imageHeight);
}

void setup() {
  loadPixels();
  world.add(new Sphere(new PVector(0, -100.5, -1), 100));
  world.add(new Sphere(new PVector(0, 0, -1), 0.5));
  cam.render(world);
  noLoop();
}

class Camera{
  //int samplesPerPixel = 10;
  PVector cameraCenter;
  PVector pixel100Loc;
  PVector pixelDeltaU;
  PVector pixelDeltaV;
  //double pixelSamplesScale;
  
  public void render(HittableList world){
    initialize();
    for (int j = 0; j < imageHeight; j++) {
      for (int i = 0; i < imageWidth; i++) {
         //color pixelColor = color(0, 0, 0);
         //for (int sample = 0; sample < samplesPerPixel; sample++){
         //  Ray r = getRay(i, j);
         //  pixelColor += rayColor(r, world);
         //}
         //writeColor(pixelColor.mult(pixelSamplesScale));
        PVector pixelCenter = PVector.add(pixel100Loc, PVector.add(PVector.mult(pixelDeltaU, i), PVector.mult(pixelDeltaV, j)));
        PVector rayDirection = PVector.sub(pixelCenter, cameraCenter);
        Ray r = new Ray(cameraCenter, rayDirection);
        color pixelColor = rayColor(r, world);
        int index = i + j * imageWidth;
        pixels[index] = pixelColor;
      }
    }
    updatePixels();
  }
  
  private void initialize(){
    imageHeight = int(imageWidth / (16.0 / 9.0)); // auto scales
    //pixelSamplesScale = 1.0 / samplesPerPixel;
    double focalLength = 1; // shorter = wider view, longer = narrow, distance btwn viewport and cam
    double viewportHeight = 2.0; // vitual rect, shows how much scene visible
    double viewportWidth = viewportHeight * ((imageWidth)/(double)(imageHeight));
    cameraCenter = new PVector(0, 0, 0); // the "eye"
    PVector viewportU = new PVector((float)viewportWidth, 0, 0); // left to right
    PVector viewportV = new PVector(0, -(float)viewportHeight, 0); // top to bottom
    pixelDeltaU = viewportU.copy().div(imageWidth); // size of a pixel in viewport
    pixelDeltaV = viewportV.copy().div(imageHeight);
    PVector viewportUpperLeft = PVector.sub(cameraCenter, new PVector(0, 0, (float)focalLength)).sub(viewportU.copy().div(2)).sub(viewportV.copy().div(2));
    PVector pixelDeltaSum = PVector.add(pixelDeltaU, pixelDeltaV);
    pixel100Loc = PVector.add(viewportUpperLeft, pixelDeltaSum.mult(0.5)); // center of pixel
  }
  
  //private Ray getRay(int i; int j){
  //  float offset = sampleSquare();
  //  float pixelSample = pixel100Loc.add(pixelDeltaU)
  //}
  
  private color rayColor(Ray r, HittableList world) {
  hit_record rec = new hit_record();
  if (world.hit(r, new interval(0, 1000), rec)){
    color baseColor = color(255, 255, 255);
    color recColor = color(rec.normal.x * 255, rec.normal.y * 255, rec.normal.z * 255);

    float red = red(baseColor) * 0.5 + red(recColor);
    float green = green(baseColor) * 0.5 + green(recColor);
    float blue = blue(baseColor) * 0.5 + blue(recColor);

    return color(red, green, blue); // Create a new color with t
  }
  // bkgd color based on vertical dir of ray
  PVector unitDirection = r.dir.copy().normalize();
  float a = 0.5 * (unitDirection.y + 1.0);
  
  float rc = (1.0 - a) * 255 + a * 128;
  float gc = (1.0 - a) * 255 + a * 179;
  float bc = (1.0 - a) * 255 + a * 255;
  
  return color(rc, gc, bc);
} // a 0 (down), approach 255 255 255 WHITE, when 1 (up) 0 0 255 blue

}

//void writeColor(PVector pixelColor){
//  double r = pixelColor.x;
//  double g = pixelColor.y;
//  double b = pixelColor.z;
  
//  interval intensity = new interval(0.000, 0.999);
//  int rByte = (int) (256 * intensity.clamp(r));
//  int gByte = (int) (256 * intensity.clamp(g));
//  int bByte = (int) (256 * intensity.clamp(b));

//  System.out.println(rByte + " " + gByte + " " + bByte);
//}

static class interval{
  public double min;
  public double max;
  public static final interval empty = new interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
  public static final interval universe = new interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
  
  public interval(){
    this.min = Double.POSITIVE_INFINITY;
    this.max = Double.NEGATIVE_INFINITY;
  }
  
  public interval(double min, double max){
    this.min = min;
    this.max = max;
  }
  
  public double size(){
    return max - min;
  }
  
  public boolean contains(double x){
    return min <= x && x <= max;
  }
  
  public boolean surrounds(double x){
   return min < x && x < max; 
  }
  
  //public double clamp(double x){
  //  if (x < min) return min;
  //  if (x > max) return max;
  //  return x;
  //}
  
}

class HittableList implements Hittable{
  private ArrayList<Hittable> objects;
  
  public HittableList(){
    objects = new ArrayList<>();
  }
  
  public HittableList(Hittable object){
    add(object);
  }
  
  public void clear(){
    objects.clear();
  }
  
  public void add(Hittable object){
    objects.add(object);
  }
  
  public boolean hit(Ray r, interval ray_t, hit_record rec){
    hit_record tempRec = new hit_record();
    boolean hitAnything = false;
    float closestSoFar = (float)ray_t.max;
    
    for (Hittable object: objects){
      if (object.hit(r, ray_t, tempRec)){
        hitAnything = true;
        closestSoFar = (float)tempRec.t;
        rec.t = tempRec.t;
        rec.p = tempRec.p;
        rec.normal = tempRec.normal;
        rec.front_face = tempRec.front_face;
      }
    }
    
    return hitAnything;
  }
}

class hit_record{
  public PVector p;
  public PVector normal;
  public double t;
  public boolean front_face;
  
  public void setFaceNormal(Ray r, PVector outwardNormal){
    front_face = PVector.dot(r.direction(), outwardNormal) < 0;
    normal = front_face ? outwardNormal.copy() : outwardNormal.copy().mult(-1);
    
  }
}

interface Hittable{
  boolean hit(Ray r, interval ray_t, hit_record rec);
}

//class Hittable{
//  boolean hit(Ray r, interval ray_t, hit_record rec){
//     return false; 
//  };
//}

class Sphere implements Hittable{
  private PVector center;
  private float radius;
  
  public Sphere(PVector center, float radius){
    this.center = center;
    this.radius = Math.max(0, radius);
  }
  
  public boolean hit(Ray r, interval ray_t, hit_record rec){
    PVector oc = PVector.sub(center, r.origin());
    float a = r.direction().magSq();
    float h = PVector.dot(r.direction(), oc);
    float c = oc.magSq() - (radius * radius);
    
    float discriminant = h * h - a * c;
    if (discriminant < 0){
      return false;
    }
    
    float sqrtd = (float)Math.sqrt(discriminant);
    
    // nearest root in acceptable range
    float root = (h - sqrtd) / a;
    if (!ray_t.surrounds(root)){
      root = (h + sqrtd) / a;
      if (!ray_t.surrounds(root)){
        return false;
      }
    }
    
     rec.t = root;
     rec.p = r.at((float)rec.t);
     PVector outwardNormal = PVector.sub(rec.p, center).div(radius);
     rec.setFaceNormal(r, outwardNormal);
     
     return true;
  }
}

class Ray {
  PVector orig, dir;
  
  Ray(PVector origin, PVector direction) {
    orig = origin;
    dir = direction;
  }
  
  PVector at(float t) {
    return PVector.add(orig, PVector.mult(dir, t));
  }
  
  
  PVector origin(){
   return orig; 
  }
  
  PVector direction(){
    return dir;
  }
}

double hit_sphere(PVector center, float radius, Ray r) {
  PVector oc = PVector.sub(center, r.origin()); // vector from ray origin to sphere
  float a = r.direction().magSq();
  float h = PVector.dot(r.direction(), oc);
  float c = oc.magSq() - (radius * radius);
  float discriminant = (h * h) - (a * c);
  if (discriminant < 0){
    return -1.0; // no intersection
  } else{
    return (h - Math.sqrt(discriminant)) / (a); // t of intersection
  }
}
