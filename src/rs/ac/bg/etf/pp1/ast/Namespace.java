// generated with ast extension for cup
// version 0.8
// 7/1/2024 2:40:33


package rs.ac.bg.etf.pp1.ast;

public class Namespace implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String namespaceName;
    private DeclLists DeclLists;
    private MethodDeclList MethodDeclList;

    public Namespace (String namespaceName, DeclLists DeclLists, MethodDeclList MethodDeclList) {
        this.namespaceName=namespaceName;
        this.DeclLists=DeclLists;
        if(DeclLists!=null) DeclLists.setParent(this);
        this.MethodDeclList=MethodDeclList;
        if(MethodDeclList!=null) MethodDeclList.setParent(this);
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName=namespaceName;
    }

    public DeclLists getDeclLists() {
        return DeclLists;
    }

    public void setDeclLists(DeclLists DeclLists) {
        this.DeclLists=DeclLists;
    }

    public MethodDeclList getMethodDeclList() {
        return MethodDeclList;
    }

    public void setMethodDeclList(MethodDeclList MethodDeclList) {
        this.MethodDeclList=MethodDeclList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclLists!=null) DeclLists.accept(visitor);
        if(MethodDeclList!=null) MethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclLists!=null) DeclLists.traverseTopDown(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclLists!=null) DeclLists.traverseBottomUp(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Namespace(\n");

        buffer.append(" "+tab+namespaceName);
        buffer.append("\n");

        if(DeclLists!=null)
            buffer.append(DeclLists.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclList!=null)
            buffer.append(MethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Namespace]");
        return buffer.toString();
    }
}
