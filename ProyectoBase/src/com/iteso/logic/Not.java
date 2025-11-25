package com.iteso.logic;

public class Not extends BinaryOperator
{
    private Expresion expresion;
    public Not(Expresion left, Expresion right)
    {
        super(left, right);
    }

    @Override
    public boolean getValue()
    {
        return !expresion.getValue();
    }
}
