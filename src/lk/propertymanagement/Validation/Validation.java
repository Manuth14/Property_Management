package lk.propertymanagement.Validation;

public enum Validation {
    VALIDATE_EMAIL {
        @Override
        public String validate() {
            return "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        }
    }, VALIDATE_MOBILE {
        @Override
        public String validate() {
            return "^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$";
        }
    }, VALIDATE_PASSWORD {
        @Override
        public String validate() {
            return "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&.,/?-_#]{8,20}$";
        }
    }, VALIDATE_NIC {
        @Override
        public String validate() {
            return "^([0-9]{9}[x|X|v|V]|[0-9]{11})$";
        }
    };

    public String validate() {
        return "";
    }
}
