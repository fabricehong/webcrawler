package tests.parameterreader;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 15.04.13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class StringParameterReader implements IParameterReader<String> {
    @Override
    public String readValue(String s) {
        return s;
    }
}
