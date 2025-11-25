package com.iteso.logic;

public class And extends BinaryOperator
{
    /*
    A-> true
    B -> false
    And(true, false)
     */
    public  And(Expresion left, Expresion right)
    {
        super(left, right);
    }

    @Override
    public boolean getValue()
    {
        return getLeft().getValue() && getRight().getValue();
    }
}
