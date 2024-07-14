package jadam;

public interface SpeedFunction {
    public static final SpeedFunction LINEAR = new SpeedFunction() {
        @Override
        public double value(double v) {
            return 1;
        }

        @Override
        public String toString() {
            return "LINEAR";
        }
    };
    public static final SpeedFunction ACCELERATE = new SpeedFunction() {
        @Override
        public double value(double v) {
            return Math.sin(v * Math.PI / 2);
        }

        @Override
        public String toString() {
            return "ACCELERATE";
        }
    };

    public static final SpeedFunction DECELERATE = new SpeedFunction() {
        @Override
        public double value(double v) {
            return Math.cos(v * Math.PI / 2);
        }

        @Override
        public String toString() {
            return "DECELERATE";
        }
    };
    public static final SpeedFunction ACCELERATE_DECELERATE = new SpeedFunction() {
        @Override
        public double value(double v) {
            return Math.sin(v * Math.PI);
        }

        @Override
        public String toString() {
            return "ACCELERATE_DECELERATE";
        }
    };

    public static final SpeedFunction DECELERATE_ACCELERATE = new SpeedFunction() {
        @Override
        public double value(double v) {
            return Math.cos(v * Math.PI);
        }
        @Override
        public String toString() {
            return "DECELERATE_ACCELERATE";
        }
    };

    double value(double v);
}
