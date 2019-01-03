package newlang3;

public class ValueImpl implements Value{

    private ValueType type;
    private String sval;
    private int ival;
    private double dval;
    private Boolean bval;


    public ValueImpl(String s, ValueType type) {
        switch (type) {
            case INTEGER:
                dval = Double.parseDouble(s);
                ival = (int) dval;
                sval = s;
                break;
            case DOUBLE:
                dval = Double.parseDouble(s);
                sval = s;
                break;
            case STRING:
                sval = s;
                break;
        }
        this.type = type;
    }

    public ValueImpl(boolean bool) {
        bval = bool;
        this.type = ValueType.BOOL;
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
