package clarifying;

public enum Flags {
    v(false), e(false), c(false), w(false);
    private boolean flag;

    Flags(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }
/*
 *  When setting a flag status
 *  this method check if this flag
 *  is already active, also rechecks
 *  name of argument
 */
    public void setFlag(String argument) {
        if (!this.flag && this.name().equals(argument.substring(1))) {
            this.flag = true;
            System.out.println("Flag \"" + argument + "\" enabled.");
        }
    }

    public String toString() {
        return "-" + this.name();
    }
}




