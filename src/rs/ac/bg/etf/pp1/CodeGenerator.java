package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;

	public static final int ADD = 0, SUB = 1, MUL = 2, DIV = 3, PERCENT = 4;

	private java.util.Collection<Obj> list = Tab.find("test301").getLocalSymbols();

	public int getMainPc() {
		return mainPc;
	}

	public void visit(MethodName methodTypeName) {

		if ("main".equalsIgnoreCase(methodTypeName.getName())) {
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());

	}

	public void visit(MethodDeclVoid methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(ExprDesign expr) {

	}

	public void visit(NewExprFact var) {
		int type = var.getType().struct.getKind();
		Code.put(Code.newarray);
		if (type == Struct.Char) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}

	public void visit(ConstFact var) {
		int val = var.getConstant().obj.getAdr();
		Code.loadConst(val);
	}

	public void visit(DesignatorAssign var) {
		Code.store(var.getDesignator().obj);
	}

	public void visit(DesignatorInc var) {
		Code.put(Code.const_1);
		Code.put(Code.add);
		Code.store(var.getDesignator().obj);
	}

	public void visit(DesignatorDec var) {
		Code.put(Code.const_m1);
		Code.put(Code.add);
		Code.store(var.getDesignator().obj);
	}

	public void visit(AddTerm var) {
		int opCode = var.getAddop().struct.getKind();
		if (opCode == ADD) {
			Code.put(Code.add);
		} else if (opCode == SUB) {
			Code.put(Code.sub);
		} else {
			// error
		}
	}

	public void visit(VarNspc var) {
		if (var.getMultipleDesignators().obj != null) { // ako je niz u pitanju
			Code.load(var.getDesignatorName().obj);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			if (var.getParent().getClass() == DesignatorInc.class
					|| var.getParent().getClass() == DesignatorDec.class) {
				Code.put(Code.dup2);
				Code.put(Code.aload);
			}
		} else {
			if ((var.getParent().getClass() != DesignatorAssign.class && var.getParent().getClass() != DesignFact.class
					&& var.getParent().getClass() != ReadDesignatorStmt.class)) {
				Code.load(var.getDesignatorName().obj);
			}
		}
	}

	public void visit(DesignName var) {

	}

	public void visit(DesignNameNspc var) {

	}

	public void visit(NegTerm var) {
		Code.put(Code.neg);
	}

	public void visit(MoreFactors var) {
		int opCode = var.getMulop().struct.getKind();
		if (opCode == MUL) {
			Code.put(Code.mul);
		} else if (opCode == DIV) {
			Code.put(Code.div);
		} else if (opCode == PERCENT) {
			Code.put(Code.rem);
		} else {
			// error
		}
	}

	public void visit(PrintStmt pstmt) {
		if (pstmt.getExpr().obj.getType().getKind() == Struct.Char) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(5);
			Code.put(Code.print);
		}
	}

	public void visit(ReadDesignatorStmt rstmt) {
		if (rstmt.getDesignator().obj.getType().getKind() == Struct.Char) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(rstmt.getDesignator().obj);
	}
	
	public void visit(PrintNumStmt prnumstmt) {
		Code.loadConst(prnumstmt.getNumConst());
		if (prnumstmt.getExpr().obj.getType().getKind() == Struct.Char) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}

	public void visit(DesignFact var) {
		Code.load(var.getDesignator().obj);
	}

	public void visit(Addoper op) {
		op.struct = new Struct(ADD);
	}

	public void visit(Subop op) {
		op.struct = new Struct(SUB);
	}

	public void visit(Muloper op) {
		op.struct = new Struct(MUL);
	}

	public void visit(Divop op) {
		op.struct = new Struct(DIV);
	}

	public void visit(Percop op) {
		op.struct = new Struct(PERCENT);
	}
}
