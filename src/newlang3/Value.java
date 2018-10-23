package newlang3;

public interface Value {
// constructor should be implemented
//    public Value(String s);
//    public Value(int i);
//    public Value(double d);
//    public Value(boolean b);
//    public String get_sValue();

  public String getSValue();
    // get value as String. If necessary, convert to String.
  public int getIntValue();
    // get value as int. If necessary, convert to int.
    public double getDoubleValue();
    // get value as double. If necessary, convert to double.
  public boolean getBooleanValue();
    // get value as boolean. If necessary, convert to boolean.
    public ValueType getType();
}
