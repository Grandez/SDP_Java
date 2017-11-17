package com.jgg.sdp.rules;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.*;

import com.jgg.sdp.calc.Calculator;
import com.jgg.sdp.domain.services.rules.RULScriptsService;
import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.rules.objects.*;
import com.jgg.sdp.tools.*;

public class RulesProcessor {

	
	private RulesTree tree = RulesTree.getInstance();

	// Al modulo se pasan al final
	// Los issues del lexer pueden que no sepan el modulo
	private ArrayList<Issue> issues = new ArrayList<Issue>();

//	private RuleItem currItem = null;
	
	/***************************************************************/
	/* Manejadores de reglas                                       */
	/***************************************************************/
	
	public RulesProcessor() {
		
	}

	ArrayList<Issue> getIssues() { return issues; }
	
    void processItemByName(String name, RuleObject obj) {
    	RuleItem item = tree.getItemByName(name);
    	if (item != null) processItem(item, obj);
    }
	
    void processRuleByName(int group, String name, RuleObject obj) {
    	return;
    	/*
    	RuleItem item = getRuleItemByName(group, name);
    	if (item != null) processItem(item, obj);
    	*/
    }
    
	private void processItem(RuleItem item, RuleObject obj) {
//		currItem = item;
		
		if (!processCondition(item.getActivation(), obj)) return;
		
		for (RuleRule rule : item.getRules()) {
			if (processRule(rule, obj)) {
				applyIssue(rule, obj);
				if (rule.getPriority() > 0) return;
			}
		}		
	}
	
	boolean processRule(RuleRule rule, RuleObject obj) {
		if (!isRuleActive(rule.getActivation(), obj)) return false;
	    return processCondition(rule.getCondition(), obj);	
	}
	
	private boolean isRuleActive(RuleCond cond, RuleObject obj) {
		return processCondition(cond, obj);
	}

	/**
	 * Procesa Condiciones y activaciones, asi que puede llegar a null
	 * y entonces la regla estaria activa
	 */
	private boolean processCondition(RuleCond cond, RuleObject obj) {
		if (cond == null) return true;
		Object lval = calculateOperandL(cond.getLvalueType(), cond.getLvalue(), obj);
		Object rval = calculateOperandR(cond.getRvalueType(), cond.getRvalue(), obj);
		return applyOperator(lval, rval, cond.getOperator());
	}
	
	private Object calculateOperandL(int type, String data, RuleObject obj) {
		switch (type) {
	        case TYPE_PROPERTY:  return processProperty (type, data, obj); 
	        case TYPE_ATTRIBUTE: return processAttribute(type, data, obj);
	        case TYPE_SCRIPT:    return processScript(type, data, obj);
	        case TYPE_VALUE:    
	        case TYPE_BOOLEAN:  
	        case TYPE_STRING:   return processValue(type, obj.getObjectAsString(), obj);
//    case TYPE_VERB:    return processRuleVerb(rule, obj);
//    case TYPE_METHOD:  return processRuleMethod(rule, obj);
    //case TYPE_VALUE:   return processRuleValue(rule, obj);		    
		}
		return null;
	}

	private Object calculateOperandR(int type, String data, RuleObject obj) {
		switch (type) {
	        case TYPE_PROPERTY: return processProperty(type, data, obj);
	        case TYPE_VALUE:    
	        case TYPE_BOOLEAN:  
	        case TYPE_STRING:   return processValue(type, data, obj);
//    case TYPE_VERB:    return processRuleVerb(rule, obj);
//    case TYPE_METHOD:  return processRuleMethod(rule, obj);
    //case TYPE_VALUE:   return processRuleValue(rule, obj);		    
		}
		return null;
	}
	
	private boolean applyOperator(Object lval, Object rval, int operator) {
		switch(getOperatorType(lval, rval)) {
		  case OP_STRING:   return applyStringOperator (lval, rval, operator);
		  case OP_NUMERIC:  return applyNumericOperator(lval, rval, operator);
		  case OP_BOOLEAN:  return applyBooleanOperator(lval, rval, operator);
//		  default:          return applyDefaultOperator(lval, rval, operator); 
		}
		return false;
	}
	
	private boolean applyStringOperator(Object lval, Object rval, int operator) {
		boolean negated = isNegated(operator);
		boolean res = false;
		
		String l = (String) lval;
		String r = (String) rval;
	
		if (lval == null || rval == null) return isNegated(operator) ? true : false;
		switch (Math.abs(operator)) {
		   case OP_START:    res = l.startsWith(r); break;
		   case OP_END:      res = l.endsWith(r);   break;
		   case OP_CONTAINS: res = l.contains(r);   break;
		   case OP_EQ:       res = (l.compareToIgnoreCase(r) == 0);   break;
		   case OP_MATCH:    Pattern p = Pattern.compile(r);
		                     Matcher m = p.matcher(l);
		                     res = m.find();
		                     break;

		}
		return (negated) ? !res : res;
	}

	private boolean applyNumericOperator(Object lval, Object rval, int operator) {
		boolean negated = isNegated(operator);
		boolean res = false;		

		if (lval == null || rval == null) return isNegated(operator) ? true : false;
		
		Long   l = (lval instanceof Integer) ? new Long((Integer) lval) : (Long) lval;
		Long   r = (rval instanceof Integer) ? new Long((Integer) rval) : (Long) rval;
	    Long   x = l - r;
	    Long   n = (x == 0) ? 0L : x / Math.abs(x); 
	    
		switch (n.intValue()) {
		   case  0:   res = ((operator & MASK_EQ) != 0) ? true : false;  break;
		   case -1:   res = ((operator & MASK_LT) != 0) ? true : false;  break;
		   case  1:   res = ((operator & MASK_GT) != 0) ? true : false;  break;
		}
		return (negated) ? !res : res;
	}


	private boolean applyBooleanOperator(Object lval, Object rval, int operator) {
		boolean negated = isNegated(operator);
		Boolean res = (Boolean) lval ^ (Boolean) rval;
		return (negated) ? res : !res;
	}
	
/*	
		if (cond == null) return true;
		
		switch (cond.getLvalueType()) {
		    case TYPE_PROPERTY: return processProperty(cond, obj);
		    case TYPE_STRING:   return processType(cond, obj);
//	    case TYPE_VERB:    return processRuleVerb(rule, obj);
//	    case TYPE_METHOD:  return processRuleMethod(rule, obj);
	    //case TYPE_VALUE:   return processRuleValue(rule, obj);		    
//	    case TYPE_FORMULA: return processRuleFormula(rule, obj);
	    default:
	    	System.out.println("Falta tipo de objeto");
        }
	    return true;
		
	}
*/

		
	// Una propiedad es boolean. La cumple o no
	// El caso especial es exist
	// Se cumple siempre
	private Object processProperty(int type, String data, RuleObject obj) {
		Boolean lval = true;
		
		if (data.compareToIgnoreCase("exist()") != 0 &&
			data.compareToIgnoreCase("exist")   != 0) {
            String m = mountMethodName(type, data);
		    lval = (Boolean) SDPJava.executeMethod(obj.getComponent(), m);
		}
		
		return lval;
	}

	private Object processAttribute(int type, String data, RuleObject obj) {
       String m = mountMethodName(type, data);
       return SDPJava.executeMethod(obj.getComponent(), m);
	}

	private Object processValue(int type, String data, RuleObject obj) {
        if (data.compareToIgnoreCase("true") == 0) return new Boolean(true);
        if (data.compareToIgnoreCase("false") == 0) return new Boolean(false);
        try {
        	Long.parseLong(data);
        }
        catch (NumberFormatException n) {
        	return new String(data);
        }
        return new Long(data);
	}

	private Object processScript(int type, String data, RuleObject obj) {
		System.out.println("JGG processRuleFormula");
		
		RULScriptsService service = new RULScriptsService();
		String script = service.getScript(Long.parseLong(data));
        Calculator c = new Calculator(script);
//		c.setObjectBase(obj.getComponent());
//		c.setObjectRoot(obj.getRoot());
		
		try {
			return c.evaluateExpression();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean processType(RuleCond cond, RuleObject obj) {
//		Object lval = parseType(cond.getLvalueType(), (String) obj.getComponent());
	    Object lval;	
		switch (cond.getLvalueType()) {
	       case TYPE_STRING:   lval = (String) obj.getComponent();
		}
		Object rval = parseType(cond.getRvalue());
	//	applyOperator(lval, rval, cond.getOperator());
		return false;
	}
/*	
	private boolean processRuleVerb(RuleRule rule, RuleObject obj) {
		boolean res = false;
		
		switch (rule.getComparator() % 100) {
		   case OP_EXIST: return checkExist(rule, obj);
		   case OP_START: return checkTypeStart(rule, obj);     
		    default:
		    	System.out.println("Falta tipo de comparacion");
		   
		}
		return res;
	}
*/	
	private boolean processRuleMethod(RuleRule rule, RuleObject obj) {
		System.out.println("JGG processRuleMethod");
/*		
		String method = rule.getProperty();
		method = "get" + method.substring(0,1).toUpperCase() + method.substring(1);
		Object result = JGGJava.executeMethod(obj, method);
		if (result instanceof Integer) return  matchInteger((Integer) result, rule);
*/		
		return false;
	}
/*
	private boolean processRuleValue(RuleRule rule, RuleObject obj) {
        Object o = obj.getValue();		
		if (o instanceof Integer)    return  matchInteger((Integer) o, rule);
		if (o instanceof BigDecimal) return  matchBigDecimal((BigDecimal) o, rule);
		return false;
	}
*/
	
	private boolean processString(RuleCond cond, String lvalue) {
		return false;
	}
	/**
	 * Realmente existe, miramos si esta prohibido
	 * @param rule
	 * @param obj
	 * @return
	 */
/*	
	private boolean checkExist(RuleRule rule, RuleObject obj) {
		return (rule.getComparator() > 100);
	}
*/	
	/**
	 * Chequea si el objeto como cadena empieza con una subcadena
	 * @param rule La regla
	 * @param obj  El objeto asociado
	 * @return false si cierto
	 *         true  error
	 */
/*	
	private boolean checkTypeStart(RuleRule rule, RuleObject obj) {
		String value = obj.getObjectAsString();
		String target = rule.getValor();
		String src = Cadena.left(value, target.length());
		if (src == null) return true;
		
		//  Tabla de verdad
		//  Condicion Negada Issue
		//     F        F     T
		//     F        T     F
		//     T        F     F
		//     T        T     T
        //     XOR
		
		return (src.compareToIgnoreCase(target) == 0 ^ rule.getComparator() > 100);
			   
	}

	private boolean matchInteger (Integer source, RuleRule rule) {
		int aux = source - Integer.parseInt(rule.getValor());
		return matchMask(aux, rule.getComparator());
		
	}

	private boolean matchBigDecimal (BigDecimal source, RuleRule rule) {
		int res = source.compareTo(new BigDecimal(rule.getValor()));
		return matchMask(res, rule.getComparator());
	}
	
	private boolean matchMask(int value, int comp) {
		int res = (value == 0) ? 0 : value / Math.abs(value);
		int mask = 0;

		switch (res) {
		   case  0: mask = MASK_EQ; break;
		   case  1: mask = MASK_LT; break;
		   default: mask = MASK_GT;
		}
		
		// Cuidado con el XOR
		return !(((mask & (comp % 100)) > 0) ^ comp > 100);
	}
*/
	private void applyIssue(RuleRule rule, RuleObject obj) {
//		System.out.println("Aplica: " + rule.getIdGroup() + "-" + rule.getIdItem() + "-" + rule.getIdRule());
		Issue issue = new Issue(rule.getIdGroup(), rule.getIdItem(), rule.getIdRule());
		issue.setBegLine(obj.getBegLine());
		issue.setBegColumn(obj.getBegColumn());
		issue.setEndLine(obj.getEndLine());
		issue.setEndColumn(obj.getEndColumn());
		issue.setSeverity(rule.getSeverity());
		issue.setBloque(obj.getBloque());
		issue.setFirma(Firma.createDefault());
		issue.setPrefix(rule.getPrefix());
		issues.add(issue);
	}

	private Object getValueData(int type, String value) {
		switch (type) {
		   case TYPE_VALUE: return parseType(value); 
		}
		return null;
	}
	
	private Object parseType(String value) {
		if (Numero.isANumber(value)) return new Long(value);
		return new String(value);	
	}
		
	private String mountMethodName(int type, String name) {
		return name;
/*		
		StringBuilder sb = new StringBuilder("dyn");
		sb.append(name.substring(0, 1).toUpperCase());
		int pos = name.indexOf('(');
		if (pos != -1) {
			sb.append(name.substring(1,pos));
		}
		else {
			sb.append(name.substring(1));
		}
		return sb.toString();
*/		
	}
	
	private boolean isNegated(int o) {
		return (o < 0) ? true : false;
	}
	
	private int getOperatorType(Object l, Object r) {
		if (l instanceof Boolean) return OP_BOOLEAN;
		if (l instanceof Long)    return OP_NUMERIC;
		if (l instanceof Integer) return OP_NUMERIC;
		if (Numero.isANumber((String) l) && Numero.isANumber((String) r)) return OP_NUMERIC;
		return OP_STRING;
	}
}
