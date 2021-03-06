package edn.nju.constant;

import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 17:56
 * @description： constant class
 */

public class Constant {
    public static class Admin {
        public static final String DEFAULT_ADMIN_USERNAME = "admin";
        public static final String DEFAULT_ADMIN_SALT = "2020";
        public static final String DEFAULT_ADMIN_PASSWORD = "f02715fe2e69b75577edd1814edc070c";
    }

    public static class User {
        public static final Date LATEST_USER_BIND_TIME = new Date("2020/02/21 07:34:21");
    }

    public static class Completion {
        public static final double PARTIAL_INTERVAL = 3600000.0;
        public static final double PARTIAL_BIAS = 1800000.0;
        public static final double V2V3_INTERVAL = 30000.0;
        public static final double V2V3_BIAS = 10000.0;
    }

    public static class MachineData {
        public static final int BEST_METHOD = 1;
        public static final int LAST_MONTH = 29;
    }
}
