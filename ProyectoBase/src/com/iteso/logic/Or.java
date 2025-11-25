package com.iteso.logic;

public class Or extends BinaryOperator
{
    public Or(Expresion left, Expresion right)
    {
        super(left, right);
    }

    @Override
    public boolean getValue()
    {
        return getLeft().getValue() || getRight().getValue();
    }

}
