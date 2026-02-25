// generated with ast extension for cup
// version 0.8
// 7/1/2024 2:40:33


package rs.ac.bg.etf.pp1.ast;

public class ExprDesign extends MultipleDesignators {

    private MultipleDesignators MultipleDesignators;
    private Expr Expr;

    public ExprDesign (MultipleDesignators MultipleDesignators, Expr Expr) {
        this.MultipleDesignators=MultipleDesignators;
        if(MultipleDesignators!=null) MultipleDesignators.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public MultipleDesignators getMultipleDesignators() {
        return MultipleDesignators;
    }

    public void setMultipleDesignators(MultipleDesignators MultipleDesignators) {
        this.MultipleDesignators=MultipleDesignators;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleDesignators!=null) MultipleDesignators.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleDesignators!=null) MultipleDesignators.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleDesignators!=null) MultipleDesignators.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprDesign(\n");

        if(MultipleDesignators!=null)
            buffer.append(MultipleDesignators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprDesign]");
        return buffer.toString();
    }
}
