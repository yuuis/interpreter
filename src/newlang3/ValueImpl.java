package newlang3;

public class ValueImpl implements Value{

    private ValueType type;
    private String sval;
    private Integer ival;
    private Double dval;
    private Boolean bval;


    public ValueImpl(String s) {
        type = ValueType.STRING;
        sval = s;
    }

    public ValueImpl(int i) {
        type = ValueType.INTEGER;
        ival = i;
    }

    public ValueImpl(double d) {
        type = ValueType.DOUBLE;
        dval = d;
    }

    public ValueImpl(boolean b) {
        type = ValueType.BOOL;
        bval = b;
    }

    @Override
    public String getSValue() {
        return sval;
    }

    @Override
    public int getIntValue() {
        return ival;
    }

    @Override
    public double getDoubleValue() {
        return dval;
    }

    @Override
    public boolean getBooleanValue() {
        return bval;
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
