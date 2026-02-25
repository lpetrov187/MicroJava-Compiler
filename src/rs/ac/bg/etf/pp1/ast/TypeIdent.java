// generated with ast extension for cup
// version 0.8
// 7/1/2024 2:40:33


package rs.ac.bg.etf.pp1.ast;

public class TypeIdent extends Type {

    private String Type;

    public TypeIdent (String Type) {
        this.Type=Type;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type=Type;
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
        buffer.append("TypeIdent(\n");

        buffer.append(" "+tab+Type);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeIdent]");
        return buffer.toString();
    }
}
