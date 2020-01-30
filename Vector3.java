class Vector3 {
    public static Vector3 Light = new Vector3(0, 0, -1);
    public double z;
    public double x;
    public double y;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Vector3 other) {
        x += other.x;
        y += other.y;
        z += other.z;
        //return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 sub(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Vector3 mult(double amount) {
        return new Vector3(x * amount, y * amount, z * amount);
    }

    public double dot(Vector3 other) {
        return (x * other.x + y * other.y + z * other.z);
    }

    public Vector3 cross(Vector3 other) {
        return new Vector3(y * other.z - z * other.y, 
                            z * other.x - x * other.z, 
                            x * other.y - y * other.x);
    }


    public void rotX(double angle) {
        double radians = Math.toRadians(angle);
        double newY = y * Math.cos(radians) - z * Math.sin(radians);
        double newZ = y * Math.sin(radians) + z * Math.cos(radians);
        y = newY;
        z = newZ;
    }

    public void rotY(double angle) {
        double radians = Math.toRadians(angle);
        double newZ = z * Math.cos(radians) - x * Math.sin(radians);
        double newX = z * Math.sin(radians) + x * Math.cos(radians);
        z = newZ;
        x = newX;
    }

    public void rotZ(double angle) {
        double radians = Math.toRadians(angle);
        double newX = x * Math.cos(radians) - y * Math.sin(radians);
        double newY = x * Math.sin(radians) + y * Math.cos(radians);
        x = newX;
        y = newY;
    }

    public double DTC() {
        double x2 = Math.abs(x);
        double y2 = Math.abs(y);
        double z2 = Math.abs(z);
        double sum = x2 + y2 + z2;
        if (sum <= 0) {
            return 0;
        }
        return Math.sqrt(sum);
    }

    public Vector2 screenCoordinate() {
        double middlex = 500;
        double middley = 500;
        double newZ = z;
        if(newZ < 0) {
            newZ = 0;
        }
        //System.out.println(newZ);
        double newX = x / (newZ * 0.001);
        double newY = y / (newZ * 0.001);
        newX += middlex;
        newY += middley;
        return new Vector2(newX, newY, z);
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public Vector3 normalize() {
        double mag = magnitude();
        x /= mag;
        y /= mag;
        z /= mag;
        return this;
    }

    public Vector3 dup() {
        return new Vector3(x, y, z);
    }

    public Vector3 inverse() {
        return new Vector3(-x, -y, -z);
    }

    public String toString() {
        return "Vector3 X:" + x + " Y:" + y + " Z:" + z;
    }

    public void set_gl() {
        screenCoordinate().set_gl();
    }
}
/*

	def to_s
		return "Vector3 X:#{@x} Y:#{@y} Z:#{@z}"
	end

  def col
      if @normal
          col = $light.dot(@normal) / (Math::PI / 2)
          col = 1 if col > 1
          col = 0 if col < 0
          return Gosu::Color.from_hsv(10, 1, col)
      end
  end
end
*/