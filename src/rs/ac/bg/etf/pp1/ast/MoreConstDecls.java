// generated with ast extension for cup
// version 0.8
// 7/1/2024 2:40:33


package rs.ac.bg.etf.pp1.ast;

public class MoreConstDecls extends MultipleConstDecl {

    private MultipleConstDecl MultipleConstDecl;
    private String constName;
    private Assignop Assignop;
    private Constant Constant;

    public MoreConstDecls (MultipleConstDecl MultipleConstDecl, String constName, Assignop Assignop, Constant Constant) {
        this.MultipleConstDecl=MultipleConstDecl;
        if(MultipleConstDecl!=null) MultipleConstDecl.setParent(this);
        this.constName=constName;
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.Constant=Constant;
        if(Constant!=null) Constant.setParent(this);
    }

    public MultipleConstDecl getMultipleConstDecl() {
        return MultipleConstDecl;
    }

    public void setMultipleConstDecl(MultipleConstDecl MultipleConstDecl) {
        this.MultipleConstDecl=MultipleConstDecl;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Constant getConstant() {
        return Constant;
    }

    public void setConstant(Constant Constant) {
        this.Constant=Constant;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleConstDecl!=null) MultipleConstDecl.accept(visitor);
        if(Assignop!=null) Assignop.accept(visitor);
        if(Constant!=null) Constant.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleConstDecl!=null) MultipleConstDecl.traverseTopDown(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
        if(Constant!=null) Constant.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleConstDecl!=null) MultipleConstDecl.traverseBottomUp(visitor);
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        if(Constant!=null) Constant.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MoreConstDecls(\n");

        if(MultipleConstDecl!=null)
            buffer.append(MultipleConstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Constant!=null)
            buffer.append(Constant.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MoreConstDecls]");
        return buffer.toString();
    }
}
