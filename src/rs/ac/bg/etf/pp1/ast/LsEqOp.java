// generated with ast extension for cup
// version 0.8
// 7/1/2024 2:40:33


package rs.ac.bg.etf.pp1.ast;

public class LsEqOp extends Relop {

    public LsEqOp () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LsEqOp(\n");

        buffer.append(tab);
        buffer.append(") [LsEqOp]");
        return buffer.toString();
    }
}
