package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {

	int varDeclCount = 0;
	Obj currMethod = null;
	boolean errorDetected = false;
	int nVars;

	public static final String[] TypeCodes = { "None", "Int", "Char", "Array", "Class", "Bool" };

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(Program Program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(Program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getP(), Tab.noType);
		Tab.openScope();
	}

	public void visit(MethodDeclVoid methDecl) {
		Tab.chainLocalSymbols(methDecl.getMethodName().obj);
		Tab.closeScope();
	}

	public void visit(MethodName name) {
		name.obj = Tab.insert(Obj.Meth, name.getName(), Tab.noType);
		Tab.openScope();
	}

	public void visit(VarDeclArr vardecl) {
		SyntaxNode node = (SyntaxNode) vardecl;
		while (!(node instanceof Namespace || node instanceof Program) && node.getParent() != null) {
			node = node.getParent();
		}

		if (node instanceof Namespace) {
			Tab.insert(Obj.Var, ((Namespace) node).getNamespaceName() + "::" + vardecl.getVarName(),
					new Struct(Struct.Array, vardecl.getType().struct));
		} else if (node instanceof Program) {
			Tab.insert(Obj.Var, vardecl.getVarName(), new Struct(Struct.Array, vardecl.getType().struct));
		}
	}

	public void visit(NonArrVarDecl vardecl) {
		SyntaxNode node = (SyntaxNode) vardecl;
		while (!(node instanceof Namespace || node instanceof Program) && node.getParent() != null) {
			node = node.getParent();
		}

		if (node instanceof Namespace) {
			Tab.insert(Obj.Var, ((Namespace) node).getNamespaceName() + "::" + vardecl.getVarName(),
					vardecl.getType().struct);
		} else {
			Tab.insert(Obj.Var, vardecl.getVarName(), vardecl.getType().struct);
		}
	}

	public void visit(MoreVarDeclsArr vardecl) {
		SyntaxNode node = vardecl.getParent();
		while (!(node instanceof VarDeclArr || node instanceof NonArrVarDecl) && node.getParent() != null) {
			node = node.getParent();
		}
		Struct type = null;
		if (node instanceof VarDeclArr)
			type = ((VarDeclArr) node).getType().struct;
		else if (node instanceof NonArrVarDecl)
			type = ((NonArrVarDecl) node).getType().struct;

		while (!(node instanceof Namespace || node instanceof Program) && node.getParent() != null) {
			node = node.getParent();
		}

		if (node instanceof Namespace) {
			Tab.insert(Obj.Var, ((Namespace) node).getNamespaceName() + "::" + vardecl.getVarName(),
					new Struct(Struct.Array, type));
		} else if (node instanceof Program) {
			Tab.insert(Obj.Var, vardecl.getVarName(), new Struct(Struct.Array, type));
		}
	}

	public void visit(MoreVarDecls vardecl) {
		SyntaxNode node = vardecl.getParent();
		while (!(node instanceof VarDeclArr || node instanceof NonArrVarDecl) && node.getParent() != null) {
			node = node.getParent();
		}
		Struct type = null;
		if (node instanceof VarDeclArr)
			type = ((VarDeclArr) node).getType().struct;
		else if (node instanceof NonArrVarDecl)
			type = ((NonArrVarDecl) node).getType().struct;

		while (!(node instanceof Namespace || node instanceof Program) && node.getParent() != null) {
			node = node.getParent();
		}

		if (node instanceof Namespace) {
			Tab.insert(Obj.Var, ((Namespace) node).getNamespaceName() + "::" + vardecl.getVarName(), type);
		} else if (node instanceof Program) {
			Tab.insert(Obj.Var, vardecl.getVarName(), type);
		}
	}

	public void visit(TypeIdent type) {
		Obj typeNode = Tab.find(type.getType());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getType() + " u tabeli simbola!", null);
			type.struct = null;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getType() + " ne predstavlja tip!", type);
				type.struct = Tab.noType;
			}
		}
	}

	public void visit(MethodTypeName methTypeName) {
		currMethod = Tab.insert(Obj.Meth, methTypeName.getMethName(), methTypeName.getType().struct);
		methTypeName.obj = currMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija" + methTypeName.getMethName(), methTypeName);
	}

	public void visit(MethodDeclNonVoid methDecl) {
		Tab.chainLocalSymbols(currMethod);
		Tab.closeScope();

		currMethod = null;
	}

	public void visit(VarNspc var) {
		Obj varNode = var.getDesignatorName().obj;

		if (varNode == Tab.noObj) {
			report_error(
					"Promenljiva " + var.getDesignatorName().obj.getName() + " nije definisana u datom prostoru imena",
					var);
			var.obj = null;
		} else {
			if (var.getMultipleDesignators().obj != null) {
				if (varNode.getType().getKind() != Struct.Array) {
					report_error("Ne mozete indeksirati promenljivu koja nije niz", var);
				} else {
					var.obj = new Obj(Obj.Elem, varNode.getName(), varNode.getType().getElemType());
				}
			} else {
				var.obj = varNode;
			}
		}
	}

	public void visit(DesignName var) {
		var.obj = Tab.find(var.getVarName());
	}

	public void visit(DesignNameNspc var) {
		var.obj = Tab.find(var.getNamespace() + "::" + var.getVarName());
	}

	public void visit(ConstDecl constDecl) {
		SyntaxNode node = (SyntaxNode) constDecl;
		if (constDecl.getConstant().obj != null) {
			if (constDecl.getConstant().obj.getType().getKind() != constDecl.getType().struct.getKind()) {
				report_error("Nekompatibilni tipovi podataka", node);
			}

			while (!(node instanceof Namespace || node instanceof Program) && node.getParent() != null) {
				node = node.getParent();
			}
			Struct type = constDecl.getType().struct;

			String name = "";
			if (node instanceof Namespace) {
				name = ((Namespace) node).getNamespaceName() + "::" + constDecl.getConstName();
			} else if (node instanceof Program) {
				name = constDecl.getConstName();
			}
			if (name != "") {
				Tab.insert(Obj.Con, name, type);
				Obj tmp = Tab.find(name);
				tmp.setAdr(constDecl.getConstant().obj.getAdr());
			}
		}
	}

	public void visit(MoreConstDecls constDecl) {
		SyntaxNode node = constDecl.getParent();

		while (!(node instanceof ConstDecl) && node.getParent() != null) {
			node = node.getParent();
		}
		Struct type = ((ConstDecl) node).getType().struct;

		if (constDecl.getConstant().obj != null) {
			if (constDecl.getConstant().obj.getType().getKind() != type.getKind()) {
				report_error("Nekompatibilni tipovi podataka", node);
			}

			while (!(node instanceof Namespace || node instanceof Program) && node.getParent() != null) {
				node = node.getParent();
			}
			String name = "";
			if (node instanceof Namespace) {
				name = ((Namespace) node).getNamespaceName() + "::" + constDecl.getConstName();
			} else if (node instanceof Program) {
				name = constDecl.getConstName();
			}
			if (name != "") {
				Tab.insert(Obj.Con, name, type);
				Obj tmp = Tab.find(name);
				tmp.setAdr(constDecl.getConstant().obj.getAdr());
			}
		}
	}

	public void visit(DesignatorInc var) {
		Obj varNode = Tab.find(var.getDesignator().obj.getName());

		if (varNode == Tab.noObj) {
			report_error("Promenljiva " + var.getDesignator().obj.getName() + " nije definisana.", var);
			var.getDesignator().obj = null;
		} else {
			if (varNode.getType().getKind() == Struct.Int || (varNode.getType().getKind() == Struct.Array
					&& varNode.getType().getElemType().getKind() == Struct.Int)) {
//				report_info("Inkrementiranje promenljive " + var.getDesignator().obj.getName(), var);
			} else {
				report_error(
						"Promenljiva " + var.getDesignator().obj.getName() + " nije int i ne moze se inkrementirati",
						var);
			}
		}
	}

	public void visit(DesignatorDec var) {
		Obj varNode = Tab.find(var.getDesignator().obj.getName());
		
		if (varNode == Tab.noObj) {
			report_error("Promenljiva " + var.getDesignator().obj.getName() + " nije definisana.", var);
			var.getDesignator().obj = null;
		} else {
			if (varNode.getType().getKind() == Struct.Int) {
				report_info("Dekrementiranje promenljive " + var.getDesignator().obj.getName(), var);
			} else {
				report_error(
						"Promenljiva " + var.getDesignator().obj.getName() + " nije int i ne moze se inkrementirati",
						var);
			}
		}
	}

	public void visit(ReadDesignatorStmt var) {
		Obj varNode = Tab.find(var.getDesignator().obj.getName());

		if (varNode == Tab.noObj) {
			report_error("Promenljiva " + var.getDesignator().obj.getName() + " nije definisana.", var);
			var.getDesignator().obj = null;
		} else {
			int type = varNode.getType().getKind();
			if (type == Struct.Int || type == Struct.Char || type == Struct.Bool) {
				report_info("Ucitavanje u promenljivu " + var.getDesignator().obj.getName(), var);
			} else {
				report_error("Promenljiva " + var.getDesignator().obj.getName() + " je nevalidnog tipa", var);
			}
		}
	}

	public void visit(NubmerConst val) {
		val.obj = new Obj(Obj.Con, "", new Struct(Struct.Int));
		val.obj.setAdr(val.getValue());
	}

	public void visit(CharacterConst val) {
		val.obj = new Obj(Obj.Con, "", new Struct(Struct.Char));
		int c = val.getValue().charAt(1);
		val.obj.setAdr(c);
	}

	public void visit(BooleanConst val) {
		val.obj = new Obj(Obj.Con, "", new Struct(Struct.Bool));
		String tmp = val.getValue();
		if (tmp.equals("true"))
			val.obj.setAdr(1);
		else
			val.obj.setAdr(0);
	}

	public void visit(NewExprFact var) {
		if (var.getExpr().obj != null) {
			if (var.getExpr().obj.getType().getKind() != Struct.Int) {
				report_error("Pogresan tip podatka izraza u []", var);
			} else {
				var.obj = new Obj(Obj.Var, "New", new Struct(Struct.Array, var.getType().struct));
			}
		}
	}

	public void visit(ExprDesign var) {
		if (var.getExpr().obj != null) {
			if (var.getExpr().obj.getType().getKind() != Struct.Int) {
				report_error("Operand pri indeksiranju mora biti tipa int", var);
			} else {
				var.obj = new Obj(Obj.Var, "array", new Struct(Struct.Array));
			}
		}
	}

	public void visit(AddTerm term) {
		if (term.getTerm().obj != null) {
			if (term.getTerm().obj.getType().getKind() != Struct.Int
					|| term.getExpr().obj.getType().getKind() != Struct.Int) {
				report_error("Nevalidni operandi operacije add", term);
			} else {
				term.obj = new Obj(Obj.Var, "expression", new Struct(Struct.Int));
			}
		}
	}

	public void visit(NegTerm term) {
		if (term.getTerm().obj != null) {
			if (term.getTerm().obj.getType().getKind() != Struct.Int) {
				report_error("Nevalidan operand", term);
			} else {
				term.obj = term.getTerm().obj;
			}
		}
	}

	public void visit(PosTerm term) {
		term.obj = term.getTerm().obj;
	}

	public void visit(OneFactor factor) {
		factor.obj = factor.getFactor().obj;
	}

	public void visit(MoreFactors factor) {
		if (factor.getTerm().obj != null) {
			if (factor.getTerm().obj.getType().getKind() != Struct.Int
					|| factor.getFactor().obj.getType().getKind() != Struct.Int) {
				report_error("Nevalidni operandi operacije", factor);
			} else {
				factor.obj = factor.getFactor().obj;
			}
		}
	}

	public void visit(ConstFact cnst) {
		cnst.obj = cnst.getConstant().obj;
	}

	public void visit(DesignFact factor) {
		Obj passingFactor = factor.getDesignator().obj;
		factor.obj = passingFactor;
	}

	public void visit(DesignatorAssign assignment) {
		if (assignment.getDesignator().obj.getKind() != Obj.Var
				&& assignment.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Levi operand operatora dodele mora biti promenljiva", assignment);
		} else {
			if (assignment.getExpr().obj != null) {
				Struct leftType = assignment.getDesignator().obj.getType();
				Struct rightType = assignment.getExpr().obj.getType();
				if (leftType.getKind() != rightType.getKind()) {
					report_error(
							"Nekompatibilni operandi operatora dodele" + leftType.getKind() + " " + rightType.getKind(),
							assignment);
				} else {

				}
			}
		}
	}

	public boolean passed() {
		return !errorDetected;
	}
}
