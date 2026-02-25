// generated with ast extension for cup
// version 0.8
// 7/1/2024 2:40:33


package rs.ac.bg.etf.pp1.ast;

public class IdentDesign extends MultipleDesignators {

    private MultipleDesignators MultipleDesignators;
    private String I2;

    public IdentDesign (MultipleDesignators MultipleDesignators, String I2) {
        this.MultipleDesignators=MultipleDesignators;
        if(MultipleDesignators!=null) MultipleDesignators.setParent(this);
        this.I2=I2;
    }

    public MultipleDesignators getMultipleDesignators() {
        return MultipleDesignators;
    }

    public void setMultipleDesignators(MultipleDesignators MultipleDesignators) {
        this.MultipleDesignators=MultipleDesignators;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleDesignators!=null) MultipleDesignators.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleDesignators!=null) MultipleDesignators.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleDesignators!=null) MultipleDesignators.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentDesign(\n");

        if(MultipleDesignators!=null)
            buffer.append(MultipleDesignators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentDesign]");
        return buffer.toString();
    }
}
