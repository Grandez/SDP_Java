package com.jgg.sdp.rules.components;

/**
 * Mantiene los Issues del modulo
 */
import static com.jgg.sdp.rules.ctes.CDGRules.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.*;

import com.jgg.sdp.calc.Calculator;
import com.jgg.sdp.common.config.Configuration;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.domain.services.rules.RULScriptsService;
import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.rules.objects.*;
import com.jgg.sdp.tools.*;

public class RulesProcessor {

    private        Configuration    cfg    = DBConfiguration.getInstance();	
	private        RulesTree        tree   = RulesTree.getInstance(true);
	private        ArrayList<Issue> issues = new ArrayList<Issue>();
    private static RulesProcessor   rules  = null;
	
	/***************************************************************/
	/* Manejadores de reglas                                       */
	/***************************************************************/
	
	private RulesProcessor() {
	}

	public static RulesProcessor getInstance() {
		return getInstance(false);
	}

	public static RulesProcessor initInstance() {
		return getInstance(true);
	}
	
	public static RulesProcessor getInstance(boolean refresh) {
		if (refresh) rules = null;
		if (rules == null) rules = new RulesProcessor();
		return rules;
	}
	
	public ArrayList<Issue> getIssues() { 
		return issues; 
	}

    public void processGroupByName(String name, RuleObject obj) {
    	RuleGroup group = tree.getGroupByName(name);
    	if (group == null) return;
    	for (RuleItem item : group.getItems()) {
    		processItem(item, obj);
    	}
    }
	
    void processItemByName(String name, RuleObject obj) {
    	RuleItem item = tree.getItemByName(name);
    	if (item != null) processItem(item, obj);
    }
	
	private void processItem(RuleItem item, RuleObject obj) {
		
    	if (!processCondition(item.getActivations(), obj)) return;

    	if (item.getType() != null) changeComponent(item, obj);
    	
    	boolean hasComponent =  obj.getFirstComponent();
    	
    	while (hasComponent) {
		   for (RuleRule rule : item.getRules()) {
			   if (processRule(rule, obj)) {
				   applyIssue(rule, obj);
				   if (rule.getPriority() > 0) break;
			   }
		   }		
		   hasComponent =  obj.getNextComponent();
    	}   
		if (item.getType() != null) 
			obj.restoreComponent();
	}
	
	private boolean processRule(RuleRule rule, RuleObject obj) {
//		if (rule.getName() != null && rule.getName().compareTo("date format") == 0) {
//			rule.getName();
//		}
		if (!isRuleActive(rule.getActivations(), obj)) return false;
	    return processCondition(rule.getCondition(), obj);	
	}
	
	private void changeComponent(RuleItem item, RuleObject obj) {
		StringBuilder sb;
		String method = null;
		String parm = item.getObject();
		obj.saveComponent();
		
		switch (item.getType()) {
		   case TYPE_OPTION:  method = "getOptionByName";     break;
		   case TYPE_LVALUE:  method = "getLValue";     break;
		   case TYPE_RVALUE:  method = "getRValue";     break;
		   case TYPE_LIST:    
			    parm = null;
			    sb = new StringBuilder("get");
			    sb.append(item.getObject());
			    sb.append("List");
			    method = sb.toString();
			    break;
		   default: 
//			   System.out.println("PARAR");
		}
		
		Object o = Reflect.executeMethod(obj.getComponent(), method, new String[] {parm});
		obj.setComponent(o);
	}
		
	private boolean isRuleActive(ArrayList<RuleCond> conds, RuleObject obj) {
		return processCondition(conds, obj);
	}

	/**
	 * Procesa Condiciones y activaciones, asi que puede llegar a null
	 * y entonces la regla estaria activa
	 */
	private boolean processCondition(ArrayList<RuleCond> conds, RuleObject obj) {
		for (RuleCond cond : conds) {
			if (!processCondition(cond, obj)) return false;
		}
		return true;
	}
	
	private boolean processCondition(RuleCond cond, RuleObject obj) {
		obj.setLVal(calculateOperandL(cond.getLvalueType(), cond.getLvalue(), obj));
		obj.setRVal(calculateOperandR(cond.getRvalueType(), cond.getRvalue(), obj));
		obj.setOperator(cond.getOperator());
		return applyOperator(obj);			
	}
	
	private Object calculateOperandL(Integer type, String data, RuleObject obj) {
		switch (type) {
	        case TYPE_PROPERTY:  return processProperty (type, data, obj); 
	        case TYPE_ATTRIBUTE: 
	             String m = mountMethodName(type, data);
	             return Reflect.executeMethod(obj.getComponent(), m);
	        case TYPE_SCRIPT:    return processScript(type, data, obj);
	        case TYPE_OPTION:    
	        	 String[] parms = {data};
	        	 String   method = "getOptionByName";
	        	 return Reflect.executeMethod(obj.getComponent(), method, parms);
	        case TYPE_CONFIG:    
	        	 return cfg.getString(data); 
	        case TYPE_VALUE:    
	        	return processValue(type, obj.getObjectValue(), obj); 
	        case TYPE_BOOLEAN:  
	        case TYPE_STRING:   return processValue(type, obj.getObjectAsString(), obj);
//    case TYPE_VERB:    return processRuleVerb(rule, obj);
//    case TYPE_METHOD:  return processRuleMethod(rule, obj);
    //case TYPE_VALUE:   return processRuleValue(rule, obj);		    
		}
		return null;
	}

	private Object calculateOperandR(Integer type, String data, RuleObject obj) {
		switch (type) {
	        case TYPE_PROPERTY: return processProperty(type, data, obj);
	        case TYPE_VALUE:
	        case TYPE_RVALUE:    
	        case TYPE_BOOLEAN:  
	        case TYPE_EXPRESSION:
	        case TYPE_STRING:   return processValue(type, data, obj);
//    case TYPE_VERB:    return processRuleVerb(rule, obj);
//    case TYPE_METHOD:  return processRuleMethod(rule, obj);
    //case TYPE_VALUE:   return processRuleValue(rule, obj);		    
		}
		return null;
	}
	
	private boolean applyOperator(RuleObject obj) {
		switch(getOperatorType(obj)) {
		  case OP_STRING:   return applyStringOperator (obj);
		  case OP_NUMERIC:  return applyNumericOperator(obj);
		  case OP_BOOLEAN:  return applyBooleanOperator(obj);
		  case OP_OBJECT:   return applyObjectOperator (obj);		  
		  case OP_DECIMAL:  return applyDecimalOperator(obj);		  
 		  default:          return applyDefaultOperator(obj); 
		}
	}
	
	private boolean applyStringOperator(RuleObject obj) {
		int operator = obj.getOperator();
		boolean negated = isNegated(operator);
		boolean res = false;
		
		String l = (String) obj.getLVal();
		String r = (String) obj.getRVal();
	
		if (l == null || r == null) return isNegated(operator) ? true : false;
        l = l.trim();
        r = r.trim();
        
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

	private boolean applyNumericOperator(RuleObject obj) {
		int operator = obj.getOperator();
		boolean negated = isNegated(operator);
		boolean res = false;
		
		Object lv = obj.getLVal();
		Object rv = obj.getRVal();
	
		if (lv == null || rv == null) return isNegated(operator) ? true : false;
		
		Long   l = (lv instanceof Integer) ? new Long((Integer) lv) : (Long) lv;
		Long   r = (rv instanceof Integer) ? new Long((Integer) rv) : (Long) rv;
	    Long   x = l - r;
	    Long   n = (x == 0) ? 0L : x / Math.abs(x); 
	    
		switch (n.intValue()) {
		   case  0:   res = ((operator & MASK_EQ) != 0) ? true : false;  break;
		   case -1:   res = ((operator & MASK_LT) != 0) ? true : false;  break;
		   case  1:   res = ((operator & MASK_GT) != 0) ? true : false;  break;
		}
		return (negated) ? !res : res;
	}

	private boolean applyDecimalOperator(RuleObject obj) {
		int operator = obj.getOperator();
		boolean negated = isNegated(operator);
		boolean res = false;
		
		Object lv = obj.getLVal();
		Object rv = obj.getRVal();
	
		if (lv == null || rv == null) return isNegated(operator) ? true : false;
		
		BigDecimal l = getBigDecimal(lv);
		BigDecimal r = getBigDecimal(rv);
	    BigDecimal x = l.subtract(r);
	    
	    Long       n = (x.compareTo(new BigDecimal(0)) == 0) ? 0L : x.divide(x.abs()).longValue(); 
	    
		switch (n.intValue()) {
		   case  0:   res = ((operator & MASK_EQ) != 0) ? true : false;  break;
		   case -1:   res = ((operator & MASK_LT) != 0) ? true : false;  break;
		   case  1:   res = ((operator & MASK_GT) != 0) ? true : false;  break;
		}
		return (negated) ? !res : res;
	}

	private BigDecimal getBigDecimal(Object o) {
		if (o instanceof BigDecimal) return (BigDecimal) o;
		if (o instanceof Long)       return new BigDecimal((Long) o);
		if (o instanceof Integer)    return new BigDecimal((Integer) o);
		return new BigDecimal(o.toString());
	}
	
	private boolean applyBooleanOperator(RuleObject obj) {
		int operator = obj.getOperator();
		boolean negated = isNegated(operator);

		Boolean lval = makeBoolean(obj.getLVal());
		Boolean rval = makeBoolean(obj.getRVal());	
		Boolean res  = lval ^ rval;
		return (negated) ? res : !res;
	}

	private Boolean makeBoolean(Object val) {
		if (val == null) return new Boolean(false);
		if (val instanceof Boolean) return (Boolean) val;
		
		char c = val.toString().charAt(0); 
		if (c == '0' || c == 'N' || c == 'n') return new Boolean(false);
        return  new Boolean(true);
	}
	
	/**
	 * Se aplica a propiedad EXIST o HAS
	 * El objeto no es nulo si existe
	 * @param obj
	 * @return
	 */
	private boolean applyObjectOperator(RuleObject obj) {
		int operator = obj.getOperator();
		boolean negated = isNegated(operator);
        boolean res = obj.getLVal() != null; 
        return (negated) ? !res : res;
	}
	
	private boolean applyDefaultOperator(RuleObject obj) {
		return true;
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
	private Object processProperty(int type, String data, RuleObject obj) {
		Boolean res = true;
		String pat = data.toUpperCase();
		
		// Exist and hasXXX son propiedades
		// Son true or false
		
		if (!pat.startsWith("EXIST") && pat.compareTo("HAS") != 0) {
            String m = mountMethodName(type, data);
		    res = (Boolean) Reflect.executeMethod(obj.getComponent(), m);
		}
		return res;
	}

	private Object processAttribute(int type, String data, RuleObject obj) {
       String m = mountMethodName(type, data);
       return Reflect.executeMethod(obj.getComponent(), m);
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
//		System.out.println("JGG processRuleFormula");
		
		RULScriptsService service = new RULScriptsService();
		String script = service.getScript(Long.parseLong(data));
        Calculator c = new Calculator(script);
		c.setObjectBase(obj.getComponent());
		c.setObjectRoot(obj.getRoot());
		
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
//		System.out.println("JGG processRuleMethod");
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
		return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
//		return name;
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
	
	private int getOperatorType(RuleObject obj) {
		int op = Math.abs(obj.getOperator() / OP_TYPES) * OP_TYPES;
		if (op != OP_NUMERIC) return op;

		Object l = obj.getLVal();
		Object r = obj.getRVal();

		if (r instanceof Boolean)    return OP_BOOLEAN;
		if (l instanceof BigDecimal) return OP_DECIMAL;
		if (r instanceof BigDecimal) return OP_DECIMAL;
		
		if (l instanceof String) {
			// 1 y 0 se utilizan para booleans
			if (Numero.isANumber((String) l) && Numero.isANumber((String) r)) {
				return (isBoolean(r)) ? OP_BOOLEAN : OP_NUMERIC;
			}
			return OP_STRING;
		}		
		return OP_NUMERIC;
	}
	
	private boolean isBoolean(Object r) {
		if (r instanceof String) {
			String v = (String) r;
			if (v.compareToIgnoreCase("TRUE")  == 0) return true;
			if (v.compareToIgnoreCase("FALSE") == 0) return true;
			return false;
		}
		return false;
	}
}
