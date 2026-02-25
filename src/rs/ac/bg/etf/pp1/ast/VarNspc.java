// generated with ast extension for cup
// version 0.8
// 7/1/2024 2:40:33


package rs.ac.bg.etf.pp1.ast;

public class VarNspc extends Designator {

    private DesignatorName DesignatorName;
    private MultipleDesignators MultipleDesignators;

    public VarNspc (DesignatorName DesignatorName, MultipleDesignators MultipleDesignators) {
        this.DesignatorName=DesignatorName;
        if(DesignatorName!=null) DesignatorName.setParent(this);
        this.MultipleDesignators=MultipleDesignators;
        if(MultipleDesignators!=null) MultipleDesignators.setParent(this);
    }

    public DesignatorName getDesignatorName() {
        return DesignatorName;
    }

    public void setDesignatorName(DesignatorName DesignatorName) {
        this.DesignatorName=DesignatorName;
    }

    public MultipleDesignators getMultipleDesignators() {
        return MultipleDesignators;
    }

    public void setMultipleDesignators(MultipleDesignators MultipleDesignators) {
        this.MultipleDesignators=MultipleDesignators;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorName!=null) DesignatorName.accept(visitor);
        if(MultipleDesignators!=null) MultipleDesignators.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorName!=null) DesignatorName.traverseTopDown(visitor);
        if(MultipleDesignators!=null) MultipleDesignators.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorName!=null) DesignatorName.traverseBottomUp(visitor);
        if(MultipleDesignators!=null) MultipleDesignators.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarNspc(\n");

        if(DesignatorName!=null)
            buffer.append(DesignatorName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleDesignators!=null)
            buffer.append(MultipleDesignators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarNspc]");
        return buffer.toString();
    }
}
