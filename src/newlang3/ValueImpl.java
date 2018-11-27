package newlang3;

public class ValueImpl implements Value{

    private ValueType type;
    private String sval;
    private Integer ival;
    private Double dval;
    private Boolean bval;


    public ValueImpl(String s, ValueType type) {
        switch (type) {
            case INTEGER:
                ival = Integer.parseInt(s);
                break;
            case DOUBLE:
                dval = Double.parseDouble(s);
                break;
            case BOOL:
                bval = Boolean.parseBoolean(s);
                break;
            case STRING:
                sval = s;
                break;
        }
        this.type = type;
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
