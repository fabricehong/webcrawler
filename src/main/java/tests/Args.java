package tests;

import tests.parameterreader.IParameterReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 15.04.13
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
public class Args {
    private Map<Character, Object> argValues;
    private final Parameters parameters;
    private final ParameterDescriptionFactory parameterDescriptionFactory = new ParameterDescriptionFactory();

    public Args(String pattern, String[] args) {
        parameters = parseParamDescr(pattern);
        argValues = parseArgValues(args);
    }

    public boolean getBoolean(Character varName) {
        return ((Boolean)getArgumentValue(varName)).booleanValue();
    }

    public String getString(Character varName) {
        return (String)getArgumentValue(varName);
    }

    public int getInteger(Character varName) {
        return ((Integer)getArgumentValue(varName)).intValue();
    }

    // ------------------------------ PRIVATE -----------------------------

    private Object getArgumentValue(Character varName) {
        return argValues.get(varName);
    }

    private Map<Character, Object> parseArgValues(String[] args) {
        Map<Character, Object> result = new HashMap<Character, Object>();
        String valueDescr;
        IParameterReader paramReader;
        Object value;
        String varDescr;
        String varValue;
        Parameter parameter;
        Character varSymbol;

        for (String arg : args) {
            valueDescr = arg.charAt(0)=='-'?arg.substring(1):arg;
            String[] split = valueDescr.split("=");
            if (split.length!=2) {
                throw new RuntimeException("parameterreader value assignation malformed, parameterreader description : " + valueDescr);
            }
            varDescr = split[0];
            varValue = split[1];
            varSymbol = varDescr.charAt(0);
            parameter = parameters.getParameter(varSymbol);
            value = parameter.readValue(varValue);
            result.put(varSymbol, value);

        }
        return result;
    }

    private Parameters parseParamDescr(String pattern) {
        Parameters result = new Parameters();

        for (String elem : pattern.split(",")) {
            result.add(parameterDescriptionFactory.createParamDescr(elem.trim()));
        }

        return result;
    }


    private class Parameters extends HashMap<Character, Parameter> {

        public Parameter getParameter(char c) {
            return get(c);
        }

        public void add(Parameter paramDescr) {
            put(paramDescr.getVarName(), paramDescr);
        }
    }
}
