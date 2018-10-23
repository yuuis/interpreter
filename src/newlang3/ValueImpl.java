package newlang3;

public class ValueImpl implements Value{

    private ValueType type;
    private String val;


    public ValueImpl(String s) {
        type = ValueType.STRING;
        val = s;
    }

    public ValueImpl(int i) {
        type = ValueType.INTEGER;
        val = i + "";
    }

    public ValueImpl(double d) {
        type = ValueType.DOUBLE;
        val = d + "";
    }

    public ValueImpl(boolean b) {
        type = ValueType.BOOL;
        val = b + "";
    }

    @Override
    public String getSValue() {
        return val;
    }

    @Override
    public int getIntValue() {
        return Integer.parseInt(val);
    }

    @Override
    public double getDoubleValue() {
        return Double.parseDouble(val);
    }

    @Override
    public boolean getBooleanValue() {
        return Boolean.parseBoolean(val);
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
