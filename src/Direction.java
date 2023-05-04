public enum Direction {

    RIGHT {
        @Override
        public String toString() {
            return "R";
        }
    },

    RIGHT_DOWN {
        @Override
        public String toString() {
            return "RD";
        }
    },

    DOWN {
        @Override
        public String toString() {
            return "D";
        }
    },

    LEFT_DOWN {
        @Override
        public String toString() {
            return "LD";
        }
    },

    LEFT {
        @Override
        public String toString() {
            return "L";
        }
    },

    LEFT_UP {
        @Override
        public String toString() {
            return "LU";
        }
    },

    UP {
        @Override
        public String toString() {
            return "U";
        }
    },

    RIGHT_UP {
        @Override
        public String toString() {
            return "RU";
        }
    };
}
