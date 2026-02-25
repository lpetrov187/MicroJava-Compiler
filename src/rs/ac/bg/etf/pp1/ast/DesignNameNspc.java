// generated with ast extension for cup
// version 0.8
// 7/1/2024 2:40:33


package rs.ac.bg.etf.pp1.ast;

public class DesignNameNspc extends DesignatorName {

    private String namespace;
    private String varName;

    public DesignNameNspc (String namespace, String varName) {
        this.namespace=namespace;
        this.varName=varName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace=namespace;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
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
        buffer.append("DesignNameNspc(\n");

        buffer.append(" "+tab+namespace);
        buffer.append("\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignNameNspc]");
        return buffer.toString();
    }
}
