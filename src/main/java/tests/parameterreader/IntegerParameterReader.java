package tests.parameterreader;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 15.04.13
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */
public class IntegerParameterReader implements IParameterReader<Integer> {
    @Override
    public Integer readValue(String s) {
        return new Integer(s);
    }
}
