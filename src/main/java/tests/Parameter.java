package tests;

import tests.parameterreader.IParameterReader;

/**
* Created with IntelliJ IDEA.
* User: fabrice
* Date: 15.04.13
* Time: 21:45
* To change this template use File | Settings | File Templates.
*/
class Parameter {
    private Character varName;
    private IParameterReader parameterReader;

    public Parameter(Character varNAme, IParameterReader parameterReader) {
        this.varName = varNAme;
        this.parameterReader = parameterReader;
    }

    public Object readValue(String str) {
        return parameterReader.readValue(str);
    }

    public Character getVarName() {
        return varName;
    }
}
